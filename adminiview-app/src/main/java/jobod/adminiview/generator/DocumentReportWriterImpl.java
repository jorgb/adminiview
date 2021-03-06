package jobod.adminiview.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import jobod.adminiview.document.Document;
import jobod.adminiview.generator.report.DocumentOrder;
import jobod.adminiview.generator.report.Keyword;
import jobod.adminiview.generator.report.ReportedYear;
import jobod.adminiview.generator.report.Topic;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class DocumentReportWriterImpl implements DocumentReportWriter {
	private final String KEYWORD_PAGE_PREFIX = "keyword_";
	private final String TOPIC_PAGE_PREFIX = "topic_";
	private final String YEAR_PAGE_PREFIX = "year_";
	
	private final Collection<Document> _documents;
	private final String _outputPath;

	public DocumentReportWriterImpl(String outputPath, Collection<Document> documents) {
		_outputPath = outputPath;
		_documents = documents;
	
		Properties p = new Properties(); 
		
		// TODO: File references to template file(s) outside class
		
		p.setProperty("resource.loader", "class");
		//p.setProperty("file.resource.loader.path", "src/main/resources");
		p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
		
		Velocity.init(p);	
	}

	@Override
	public void write() throws IOException {
		
		Collection<Topic> topics = getTopicElements();
		Collection<ReportedYear> reportedYears = getReportedYears();
		List<Keyword> keywordCloud = getKeywordCloud();
		
		generateMainPage(topics, reportedYears, keywordCloud);
		
		for(Topic t : topics) {
			generateTopicPage(t.getId(), t.getName());
		}
		
		for(ReportedYear ry : reportedYears) {
			generateReportedYear(ry);
		}
		
		for(Keyword k : keywordCloud) {
			generateKeywordPage(k);
		}
		
		copyStaticFile("adminiview.css");
		
	}

	private void generateKeywordPage(Keyword k) throws IOException {
		Template t = Velocity.getTemplate("templates/keywordpage.vm");

		VelocityContext context = new VelocityContext();
		
		context.put("keyword", k);
						
		Writer writer = getOutputFor(KEYWORD_PAGE_PREFIX + k.getId() + ".html");
		try {
			t.merge(context, writer);		
		}
		finally {
			writer.close();
		}			
	}

	private void generateReportedYear(ReportedYear ry) throws IOException {
		Template t = Velocity.getTemplate("templates/yearpage.vm");

		VelocityContext context = new VelocityContext();
		
		context.put("reportedYear", ry);
						
		Writer writer = getOutputFor(YEAR_PAGE_PREFIX + ry.getYear() + ".html");
		try {
			t.merge(context, writer);		
		}
		finally {
			writer.close();
		}	
	}

	private void generateTopicPage(int id, String name) throws IOException {
		Template t = Velocity.getTemplate("templates/topicpage.vm");

		VelocityContext context = new VelocityContext();
		
		context.put("topicName", name);
		
		// create a list of Documents for this topic, sorted
		// by date, descending (newest first)
		
		List<Document> topicDocs = new ArrayList<Document>();
		for(Document d : _documents) {
			if(d.topic().equals(name)) {
				topicDocs.add(d);
			}
		}
		
		DocumentOrder.sortTimeDescending(topicDocs);
		context.put("topicDocuments", topicDocs);
		
		Writer writer = getOutputFor(TOPIC_PAGE_PREFIX + id + ".html");
		try {
			t.merge(context, writer);		
		}
		finally {
			writer.close();
		}	
	}

	private void generateMainPage(Collection<Topic> topics, Collection<ReportedYear> reportedYears, List<Keyword> keywordCloud) throws IOException {

		Template t = Velocity.getTemplate("templates/mainpage.vm");
		
		
		VelocityContext context = new VelocityContext();
		context.put("topicPagePrefix", TOPIC_PAGE_PREFIX);
		context.put("yearPagePrefix", YEAR_PAGE_PREFIX);
		context.put("keywordPagePrefix", KEYWORD_PAGE_PREFIX);
		
		context.put("documentTopics", topics);
		context.put("keywordCloud", keywordCloud);
		context.put("documentsByYear", reportedYears);
		
		Writer writer = getOutputFor("index.html");
		try {
			t.merge(context, writer);		
		}
		finally {
			writer.close();
		}
	}

	private List<ReportedYear> getReportedYears() {
		Map<Integer, ReportedYear> reportedYear = new HashMap<Integer, ReportedYear>();
		for(Document d : _documents) {
			ReportedYear ry = reportedYear.get(d.year());
			if(ry == null) {
				ry = new ReportedYear(d.year());
				reportedYear.put(d.year(), ry);
			}
			ry.addDocument(d);
		}
		
		List<ReportedYear> result = new ArrayList<ReportedYear>(reportedYear.values());
		Collections.sort(result);
		
		return result;
	}

	private Writer getOutputFor(String fileName) throws IOException {
		return new FileWriter(new File(_outputPath, fileName));
	}

	private Collection<Topic> getTopicElements() {
		Map<String, Integer> topics = new HashMap<String, Integer>();

		for(Document d : _documents) {
			String topic = d.topic();
			int count = 1;
			
			if(topics.containsKey(topic)) {
				count = topics.get(topic) + 1;
			}

			topics.put(topic, count);
		}

		int id = 1;
		List<Topic> elements = new ArrayList<Topic>();
		for(Entry<String, Integer> e : topics.entrySet()) {
			elements.add(new Topic(id++, e.getKey(), e.getValue()));
		}
		
		Collections.sort(elements);
		
		return elements;
	}

	private List<Keyword> getKeywordCloud() {
		Map<String, Keyword> keywords = new HashMap<String, Keyword>();
		
		int maxCount = 0;
		int id = 0;
		for(Document d : _documents) {
			for(String kwd : d.keywords()) {
				String keyword = kwd.toLowerCase();
				Keyword k = keywords.get(kwd);
				if(k == null) {
					k = new Keyword(keyword, id++);
					keywords.put(keyword, k);
				}

				k.addDocument(d);
				
				maxCount = Math.max(maxCount, k.getCount());
			}
		}
		
		List<Keyword> result = new ArrayList<Keyword>(keywords.values());
	
		double granularity = 325; // + 85 is max percentage
		for(Keyword k : result) {
			double ratio = (double)k.getCount() / (double)maxCount; 	
			int percRatio = (int)((granularity * ratio) + 85);
			k.finalizeKeyword(percRatio);
		}
		
		Collections.sort(result);
		return result;
	}

	private void copyStaticFile(String fileRef) {
		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("copying/" + fileRef);
			OutputStream out = new FileOutputStream(new File(_outputPath, fileRef));
			
			try {
				IOUtils.copy(stream,  out);
			}
			finally {
				if(stream != null) {
					stream.close();
				}
				if(out != null) {
					out.close();
				}
			}
		}
		catch(IOException e) {
			// ignore
		}
	}
}

package jobod.adminiview.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import jobod.adminiview.generator.report.ReportedYear;
import jobod.adminiview.generator.report.Topic;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class DocumentReportWriterImpl implements DocumentReportWriter {
	private final String TOPIC_PAGE_PREFIX = "topic_";
	private final String YEAR_PAGE_PREFIX = "year_";
	
	private final Collection<Document> _documents;
	private final String _outputPath;

	public DocumentReportWriterImpl(String outputPath, Collection<Document> documents) {
		_outputPath = outputPath;
		_documents = documents;
	
		Properties p = new Properties(); 
		
		p.setProperty("resource.loader", "file, class");
		p.setProperty("file.resource.loader.path", "src/main/resources");
		p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
		
		Velocity.init(p);	
	}

	@Override
	public void write() throws IOException {
		
		Collection<Topic> topics = getTopicElements();
		Collection<ReportedYear> reportedYears = getReportedYears();
		
		generateMainPage(topics, reportedYears);
		
		for(Topic t : topics) {
			generateTopicPage(t.getId(), t.getName());
		}
		
		for(ReportedYear ry : reportedYears) {
			generateReportedYear(ry);
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

	private void generateMainPage(Collection<Topic> topics, Collection<ReportedYear> reportedYears) throws IOException {

		Template t = Velocity.getTemplate("templates/mainpage.vm");

		VelocityContext context = new VelocityContext();
		context.put("topicPagePrefix", TOPIC_PAGE_PREFIX);
		context.put("yearPagePrefix", YEAR_PAGE_PREFIX);
		context.put("documentTopics", topics);
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
}

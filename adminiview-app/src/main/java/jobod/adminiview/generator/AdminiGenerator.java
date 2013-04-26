package jobod.adminiview.generator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import jobod.adminiview.document.Document;
import jobod.adminiview.document.DocumentClassify;
import jobod.adminiview.document.DocumentClassifyImpl;
import jobod.adminiview.document.DocumentSink;
import jobod.adminiview.document.DocumentSinkImpl;
import jobod.adminiview.document.DocumentTraverserImpl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AdminiGenerator {
	private String _outputPath;
	private String _inputPath;
	private boolean _verbose = false;

	public Options getOptions() {
		Options options = new Options();
    	options.addOption("i", "input-dir", true, "The input directory where the adminstrative files are found, that match the needed wildcard");
    	options.addOption("o", "output-dir", true, "The output directory where the HTML files will be written");
    	options.addOption("v", "verbose", false, "List progress in a verbose way");

    	return options;
	}
	
	public void intialize(CommandLine cmd) throws ParseException {
		_outputPath = optionNotEmpty(cmd, "o");
		_inputPath = optionNotEmpty(cmd, "i");
		_verbose  = cmd.hasOption("v");
	}

	private String optionNotEmpty(CommandLine cmd, String option) throws ParseException {
		String result = cmd.getOptionValue(option);
		if(result == null || result.isEmpty()) {
			throw new ParseException(String.format("Option %s is not allowed to be empty", option));
		}
		return result;
	}

	public int execute() {		
		
		DocumentSink sink = new DocumentSinkImpl(_verbose);
		DocumentClassify classifier = new DocumentClassifyImpl();
		
		DocumentTraverserImpl trav = new DocumentTraverserImpl(classifier, sink);

		trav.recurseDocuments(new File(_inputPath));
		
		// sink contains all our documents
		
		Collection<Document> documents = sink.documents();
		
		if(_verbose) {
			System.out.println("Found " + documents.size() + " documents!");
		}
		
		DocumentReportWriter writer = new DocumentReportWriterImpl(_outputPath, documents);
		
		int errorCode = 0;
		try {
			writer.write();
		} catch (IOException e) {
			System.err.println("Error generating : " + e.getMessage());
			errorCode = 1;
		}
		
		return errorCode;	
	}
}

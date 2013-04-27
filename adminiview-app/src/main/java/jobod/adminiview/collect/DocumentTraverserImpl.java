package jobod.adminiview.collect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jobod.adminiview.document.DocumentBuilderFactory;
import jobod.adminiview.document.DocumentFileImpl;
import jobod.adminiview.document.DocumentInfo;

public class DocumentTraverserImpl implements DocumentTraverser {

	private final DocumentClassify _classifier;
	private final DocumentSink _sink;
	private final DocumentBuilderFactory _factory;
	
	public DocumentTraverserImpl(DocumentBuilderFactory factory, DocumentClassify classifier, DocumentSink sink) {
		_classifier = classifier;
		_factory = factory;
		_sink = sink;
	}

	@Override
	public int recurseDocuments(File path) {
		int count = 0;
		Map<String, DocumentBuilder> documents = new HashMap<String, DocumentBuilder>();

		for(File item : path.listFiles()) {
			if(item.isFile()) {
				DocumentInfo info = _classifier.classify(item);
				if(info != null) {
					DocumentBuilder builder = documents.get(info.baseName());
					if(builder == null) {
						 builder = _factory.createBuilder(info, item.getParentFile());
						 documents.put(info.baseName(), builder);
					}					
					builder.addFile(info.pageNumber(), path.getName());
				}				
			}
			else if(item.isDirectory()) {
				count += recurseDocuments(item);
			}
		}
		
		// all registered builders in this directory are considered
		// done, let's make some documents
		for(DocumentBuilder b : documents.values()) {
			_sink.register(b.build());
		}
		
		return count + documents.size();
	}
}

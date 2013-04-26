package jobod.adminiview.document;

import java.io.File;

public class DocumentTraverserImpl implements DocumentTraverser {

	private final DocumentClassify _classifier;
	private final DocumentSink _sink;
	
	public DocumentTraverserImpl(DocumentClassify classifier, DocumentSink sink) {
		_classifier = classifier;
		_sink = sink;
	}

	@Override
	public int recurseDocuments(File path) {
		int count = 0;
		for(File item : path.listFiles()) {
			if(item.isFile()) {
				Document document = _classifier.classify(item);
				if(document != null) {
					count++;
					_sink.register(document);
				}
			}
			else if(item.isDirectory()) {
				count += recurseDocuments(item);
			}
		}
		return count;
	}
}

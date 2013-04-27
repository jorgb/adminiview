package jobod.adminiview.collect;

import java.util.Collection;

import jobod.adminiview.document.Document;


public interface DocumentSink {

	void register(Document document);
	
	Collection<Document> documents();
	
}

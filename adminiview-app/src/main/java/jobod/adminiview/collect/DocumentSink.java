package jobod.adminiview.collect;

import java.util.List;

import jobod.adminiview.document.Document;


public interface DocumentSink {

	void register(Document document);
	
	List<Document> documents();
	
}

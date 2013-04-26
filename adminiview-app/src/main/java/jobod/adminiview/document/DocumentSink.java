package jobod.adminiview.document;

import java.util.Collection;


public interface DocumentSink {

	void register(Document document);
	
	Collection<Document> documents();
	
}

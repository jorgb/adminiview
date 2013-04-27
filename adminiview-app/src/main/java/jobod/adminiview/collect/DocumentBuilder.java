package jobod.adminiview.collect;

import jobod.adminiview.document.Document;

public interface DocumentBuilder {

	Document build();
	
	void addFile(int pageNumber, String fileName);
	
}

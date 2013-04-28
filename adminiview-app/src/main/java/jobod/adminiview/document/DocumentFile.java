package jobod.adminiview.document;

public interface DocumentFile extends Comparable<DocumentFile> {

	String fileName();
	
	int pageNumber();
	
}

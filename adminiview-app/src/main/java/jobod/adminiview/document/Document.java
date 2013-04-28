package jobod.adminiview.document;

import java.util.List;

public interface Document {

	/**
	 * Returns part of the name excluding everything
	 * from the time signature on.
	 * 
	 * @return the base name
	 */
	String baseName();
	
	String parentPathURL();
	
	List<DocumentFile> documentFiles();

	String topic();
		
	int day();

	int month();

	int year();

	int dateInt();	
	
	String dateString();

	int pageCount();
}

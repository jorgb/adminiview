package jobod.adminiview.document;

import java.util.Collection;

public interface Document {

	/**
	 * Returns part of the name excluding everything
	 * from the time signature on.
	 * 
	 * @return the base name
	 */
	String baseName();
	
	String parentPathURL();
	
	Collection<DocumentFile> documentFiles();

	String topic();
		
	int day();

	int month();

	int year();

	int dateInt();	
	
	String dateString();
}

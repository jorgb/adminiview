package jobod.adminiview.document;

public interface Document {

	/**
	 * Returns part of the name excluding everything
	 * from the time signature on.
	 * 
	 * @return the base name
	 */
	String baseName();
	
	String pathURL();
	
	String fileName();

	String topic();
		
	int day();

	int month();

	int year();

	int dateInt();	
	
	String dateString();
}

package jobod.adminiview.document;

public interface Document {

	String pathURL();
	
	String fileName();

	String topic();
		
	int day();

	int month();

	int year();

	int dateInt();	
	
	String dateString();
}

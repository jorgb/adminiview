package jobod.adminiview.document;

import java.io.File;

public class DocumentClassifyImpl implements DocumentClassify {

	// convention:
	//  {subject}_{keyword0}_{keyword1}@YYZZZZ.ext    -- 1st, month, year
	//  {subject}_{keyword0}_{keyword1}@XXYYZZZZ.ext  -- day, month, year
	//  {subject}_{keyword0}_{keyword1}@ZZZZ.ext      -- 1st January, year
	//  
	
	@Override
	public Document classify(File path) {		
		
		String fileName = path.getName();
		int timeSepPos = fileName.lastIndexOf("@");
		
		if(timeSepPos == -1 || timeSepPos + 1 >= fileName.length()) {
			return null;
		}
		
		String[] firstparts = fileName.substring(0, timeSepPos).split("_");
		if(firstparts == null || firstparts.length == 0) {
			return null;
		}
		
		String subject = firstparts[0];
		if(subject.isEmpty()) {
			return null;
		}
		
		// TODO: -- Implement keywords
		
		String datePart = fileName.substring(timeSepPos + 1);
		
		int dotSepPos = datePart.lastIndexOf(".");
		if(dotSepPos != -1) {
			datePart = datePart.substring(0, dotSepPos);
		}

		DocumentImpl.Builder builder = new DocumentImpl.Builder();
		builder.setFilePath(path);
		builder.setSubject(subject);

		try {
			int year = 0;
			int day = 1;
			int month = 1;
			if(datePart.length() == 4) {
				year = Integer.parseInt(datePart);
			}
			else if(datePart.length() == 6){
				month = Integer.parseInt(datePart.substring(0, 2));
				year = Integer.parseInt(datePart.substring(2));
			}
			else if(datePart.length() == 8) {
				day = Integer.parseInt(datePart.substring(0, 2));
				month = Integer.parseInt(datePart.substring(2, 4));
				year = Integer.parseInt(datePart.substring(4));
			}
			else {
				return null;
			}
			
			if(day < 1 || day > 31 
					|| month < 1 || month > 12 
					|| year < 1900 || year > 2100) {
				return null;
			}
			
			builder.setDay(day);
			builder.setMonth(month);
			builder.setYear(year);
		}
		catch(NumberFormatException e) {
			return null;
		}

		return builder.build();
	}
}

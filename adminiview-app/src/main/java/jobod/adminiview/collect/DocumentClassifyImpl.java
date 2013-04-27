package jobod.adminiview.collect;

import java.io.File;

import jobod.adminiview.document.DocumentInfo;
import jobod.adminiview.document.DocumentInfoImpl;

public class DocumentClassifyImpl implements DocumentClassify {

	// convention:
	//  {subject}_{keyword0}_{keyword1}@YYZZZZ.ext    -- 1st, month, year
	//  {subject}_{keyword0}_{keyword1}@XXYYZZZZ.ext  -- day, month, year
	//  {subject}_{keyword0}_{keyword1}@ZZZZ.ext      -- 1st January, year
	//  
	
	@Override
	public DocumentInfo classify(File path) {		
		
		String fileName = path.getName();
		int timeSepPos = fileName.lastIndexOf("@");
		
		if(timeSepPos == -1 || timeSepPos + 1 >= fileName.length()) {
			return null;
		}

		String keywordsString = fileName.substring(0, timeSepPos);
		if(timeSepPos == 0) {
			return null;
		}
		
		String[] keywords = keywordsString.split("_");
		if(keywords == null || keywords.length == 0) {
			return null;
		}

		String datePart = fileName.substring(timeSepPos + 1);

		int pageNumber = 1;

		int pageStart = datePart.indexOf("_p");
		if(pageStart != -1) {
			int pageEnd = datePart.indexOf(".");
			if(pageEnd == -1) {
				pageEnd = datePart.length();
			}
			
			if(pageEnd > (pageStart + 2)) {
				String page = datePart.substring(pageStart + 2, pageEnd);
				try {
					pageNumber = Integer.parseInt(page);
				}
				catch(NumberFormatException e) {
					return null;
				}
				
				datePart = datePart.substring(0, pageStart);
			}
		}
		
		// remove dot if still present
		int dotSepPos = datePart.indexOf(".");
		if(dotSepPos > 0) {
			datePart = datePart.substring(0, dotSepPos);
		}

		int year = 0;
		int day = 1;
		int month = 1;

		try {
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
		}
		catch(NumberFormatException e) {
			return null;
		}
		
		if(day < 1 || day > 31 
				|| month < 1 || month > 12 
				|| year < 1900 || year > 2100) {
			return null;
		}

		String baseName = keywordsString + "@" + datePart;
		
		return new DocumentInfoImpl(baseName, keywords, pageNumber, day, month, year);
	}
}

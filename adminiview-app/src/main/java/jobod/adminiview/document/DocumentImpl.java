package jobod.adminiview.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jobod.adminiview.collect.DocumentBuilder;

public class DocumentImpl implements Document {
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-YYYY");

	public static class Builder implements DocumentBuilder {
		
		private final String _baseName;
		private final String[] _keywords;
		private final String _subject;
		private final File _parentPath;
		private final int _day;
		private final int _month;
		private final int _year;
		List<DocumentFile> _documentFiles;

		public Builder(DocumentInfo info, File parentPath) {
			_documentFiles = new ArrayList<DocumentFile>();

			_day = info.day();
			_month = info.month();
			_year = info.year();
			_baseName = info.baseName();
			
			String[] keywords = info.keywords();
			if(keywords.length == 0) {
				throw new IllegalArgumentException("Keywords are empty");
			}
			
			_subject = keywords[0];
			_keywords = new String[keywords.length - 1];
			
			if(keywords.length > 1) {
				System.arraycopy(keywords, 1, _keywords, 0, keywords.length - 1);			
			}

			_parentPath = parentPath;
		}
		
		@Override
		public Document build() {
			// TODO: -- Date verification!

			if(_documentFiles.size() == 0) {
				throw new IllegalArgumentException("No filenames found to build with!");
			}
			
			return new DocumentImpl(this);
		}

		@Override
		public void addFile(int pageNumber, String fileName) {
			_documentFiles.add(new DocumentFileImpl(pageNumber, fileName));
		}
	}

	private final File _parentPath;
	private final List<DocumentFile> _documentFiles;
	private final int _year;
	private final int _month;
	private final int _day;
	private final String _subject;
	private final int _dateInt;
	private final String _dateString;
	private final String _baseName;
	
	private DocumentImpl(Builder b) {
		_parentPath = b._parentPath;
		_baseName = b._baseName;
		_day = b._day;		
		_month = b._month;
		_year = b._year;

		_subject = b._subject;

		_documentFiles = new ArrayList<DocumentFile>(b._documentFiles);
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DAY_OF_MONTH, _day);
		cal.set(Calendar.MONTH, _month - 1);
		cal.set(Calendar.YEAR, _year);
		
		Date date = cal.getTime();

		_dateInt = (int) (date.getTime() / 1000);
		_dateString = DATE_FORMAT.format(date);
	}

	@Override
	public String topic() {
		return _subject;
	}

	@Override
	public int day() {
		return _day;
	}

	@Override
	public int month() {
		return _month;
	}

	@Override
	public int year() {
		return _year;
	}

	@Override
	public String parentPathURL() {
		return _parentPath.toURI().toASCIIString();
	}

	@Override
	public int dateInt() {
		return _dateInt;
	}

	@Override
	public String dateString() {
		return _dateString;
	}

	@Override
	public String baseName() {
		return _baseName;
	}

	@Override
	public Collection<DocumentFile> documentFiles() {
		return _documentFiles;
	}

}

package jobod.adminiview.document;

import java.util.Arrays;

public class DocumentInfoImpl implements DocumentInfo {

	private final String _baseName;
	private final String[] _keywords;
	private final int _pageNumber;
	private final int _day;
	private final int _month;
	private final int _year;
	
	public DocumentInfoImpl(String baseName, String[] keywords, int pageNumber, 
			int day, int month, int year) {
		
		_baseName = baseName;
		_keywords = Arrays.copyOf(keywords, keywords.length);
		_pageNumber = pageNumber;
		_day = day;
		_month = month;
		_year = year;
	}

	@Override
	public String baseName() {
		return _baseName;
	}

	@Override
	public String[] keywords() {
		return _keywords;
	}

	@Override
	public int pageNumber() {
		return _pageNumber;
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
}

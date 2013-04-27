package jobod.adminiview.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DocumentImpl implements Document {
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-YYYY");

	public static class Builder {
		
		private File _filePath;
		private String _subject = "";
		private int _day;
		private int _month;
		private int _year;
		public String _baseName = "";

		public Builder() {
			_day = 1;
			_month = 1;
			_year = Calendar.getInstance().get(Calendar.YEAR);
		}
		
		void setBaseName(String baseName) {
			_baseName = baseName;
		}
		
		void setFilePath(File filePath) {
			_filePath = filePath;
		}
		
		void setSubject(String subject) {
			_subject = subject;
		}
		
		void setDay(int day) {
			_day = day;
		}
		
		void setMonth(int month) {
			_month = month;
		}
		
		void setYear(int year) {
			_year = year;
		}
		
		public Document build() {
			// TODO: -- Date verification!
			
			return new DocumentImpl(this);
		}
	}

	private final File _path;
	private final String _name;
	private final int _year;
	private final int _month;
	private final int _day;
	private final String _subject;
	private final int _dateInt;
	private final String _dateString;
	private final String _baseName;
	
	private DocumentImpl(Builder b) {
		_path = b._filePath;
		_name = b._filePath.getName();
		_baseName = b._baseName;
		_day = b._day;		
		_month = b._month;
		_year = b._year;

		_subject = b._subject;

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
	public String fileName() {
		return _name;
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
	public String pathURL() {
		return _path.toURI().toASCIIString();
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

}

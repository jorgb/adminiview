package jobod.adminiview.generator.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jobod.adminiview.document.Document;

public class ReportedYear implements Comparable<ReportedYear> {
	private final static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM");
	
	Map<Integer, List<Document>> _docsByMonth = new HashMap<Integer, List<Document>>();	
	
	private int _year;
	private int _totalCount;
	
	public ReportedYear(int year) {
		_year = year;
		_totalCount = 0;
	}

	public void addDocument(Document doc) {
		if(doc.year() != _year) {
			return;
		}
		
		List<Document> list = _docsByMonth.get(doc.month());
		if(list == null) {
			list = new ArrayList<Document>();
			_docsByMonth.put(doc.month(), list);
		}
		
		list.add(doc);
		_totalCount++;
	}
	
	@Override
	public int compareTo(ReportedYear ry) {
		return ry._year - _year;
	}
	
	public int getYear() {
		return _year;
	}
	
	public boolean hasMonth(int month) {
		return _docsByMonth.containsKey(month);
	}
	
	public String monthName(int month) {
		if(month < 1 || month > 12) {
			return "ERR";
		}

		Calendar cal = Calendar.getInstance();
		cal.clear();
		
		cal.set(Calendar.MONTH, month - 1);
		return MONTH_FORMAT.format(cal.getTime());
	}
	
	public 	int getCount() {
		return _totalCount;
	}
	
	public List<Document> documentsByMonth(int month) {
		List<Document> result = _docsByMonth.get(month);
		if(result == null) {
			return Collections.emptyList();
		}
		else {
			List<Document> sorted = new ArrayList<Document>(result);
			DocumentOrder.sortTimeDescending(sorted);
			
			return sorted;
		}
	}
}

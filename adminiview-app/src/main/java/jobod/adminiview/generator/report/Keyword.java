package jobod.adminiview.generator.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jobod.adminiview.document.Document;

public class Keyword implements Comparable<Keyword> {

	private final String _keyword;
	private int _percentRatio;
	private List<Document> _documents;
	private final int _id;
	
	public Keyword(String keyword, int id) {
		_keyword = keyword;
		_id = id;
		_percentRatio = 100;
		_documents = new ArrayList<Document>();
	}

	@Override
	public int compareTo(Keyword other) {
		return _keyword.compareTo(other._keyword);
	}
	
	public int percentRatio() {
		return _percentRatio;
	}

	public String getLabel() {
		return _keyword;
	}

	public int getCount() {
		return _documents.size();
	}

	public void finalizeKeyword(int percRatio) {
		_percentRatio = percRatio;
		DocumentOrder.sortTimeDescending(_documents);
	}

	public void addDocument(Document d) {
		_documents.add(d);
	}

	public int getId() {
		return _id;
	}

	public Collection<Document> documents() {
		return _documents;
	}
}

package jobod.adminiview.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentSinkImpl implements DocumentSink {

	private List<Document> _documents;
	private boolean _verbose;

	public DocumentSinkImpl(boolean verbose) {
		_verbose = verbose;
		_documents = new ArrayList<Document>();
	}
	
	public DocumentSinkImpl() {
		this(false);
	}

	@Override
	public void register(Document document) {
		// TODO: -- Check for doubles?
		if(_verbose) {
			System.out.println("Found: " + document.fileName());
		}
		_documents.add(document);
	}

	@Override
	public Collection<Document> documents() {
		return _documents;
	}
}

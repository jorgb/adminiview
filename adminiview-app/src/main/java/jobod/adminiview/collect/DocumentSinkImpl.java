package jobod.adminiview.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jobod.adminiview.document.Document;
import jobod.adminiview.document.DocumentFile;

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
			System.out.println("Found document with file(s) :");
			for(DocumentFile df : document.documentFiles()) {
				System.out.println(" - " + df.fileName() + " (page " + df.pageNumber());
			}
		}

		_documents.add(document);
	}

	@Override
	public List<Document> documents() {
		return _documents;
	}
}

package jobod.adminiview.document;

public class DocumentFileImpl implements DocumentFile {

	private final int _pageNumber;
	private final String _fileName;
	
	public DocumentFileImpl(int pageNumber, String fileName) {
		_pageNumber = pageNumber;
		_fileName = fileName;
	}

	@Override
	public String fileName() {
		return _fileName;
	}

	@Override
	public int pageNumber() {
		return _pageNumber;
	}

}

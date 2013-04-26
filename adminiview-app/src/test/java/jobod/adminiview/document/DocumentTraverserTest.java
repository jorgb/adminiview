package jobod.adminiview.document;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;

import jobod.adminiview.document.Document;
import jobod.adminiview.document.DocumentClassifyImpl;
import jobod.adminiview.document.DocumentSink;
import jobod.adminiview.document.DocumentTraverserImpl;

import org.junit.Test;

public class DocumentTraverserTest {

	@Test
	public void testDocumentTraversion() {
		
		DocumentSink sink = mock(DocumentSink.class);
		DocumentClassifyImpl classifier = new DocumentClassifyImpl();
		
		DocumentTraverserImpl trav = new DocumentTraverserImpl(classifier, sink);
		
		assertEquals(4, trav.recurseDocuments(new File("src/test/resources/travtest")));
		
		verify(sink, times(4)).register(any(Document.class));
	}
}

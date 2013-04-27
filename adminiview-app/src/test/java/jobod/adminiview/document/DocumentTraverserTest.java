package jobod.adminiview.document;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;

import jobod.adminiview.collect.DocumentClassifyImpl;
import jobod.adminiview.collect.DocumentSink;
import jobod.adminiview.collect.DocumentTraverserImpl;
import jobod.adminiview.document.Document;

import org.junit.Test;

public class DocumentTraverserTest {

	@Test
	public void testDocumentTraversion() {
		
		DocumentSink sink = mock(DocumentSink.class);
		DocumentClassifyImpl classifier = new DocumentClassifyImpl();
		DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
		
		DocumentTraverserImpl trav = new DocumentTraverserImpl(factory, classifier, sink);
		
		assertEquals(4, trav.recurseDocuments(new File("src/test/resources/travtest")));
		
		verify(sink, times(4)).register(any(Document.class));
	}
}

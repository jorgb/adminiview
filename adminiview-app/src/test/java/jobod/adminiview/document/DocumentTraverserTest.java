package jobod.adminiview.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jobod.adminiview.collect.DocumentClassifyImpl;
import jobod.adminiview.collect.DocumentSink;
import jobod.adminiview.collect.DocumentTraverserImpl;

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
	
	@Test
	public void testDocumentPageGrouping() {

		DocumentSink sink = new DocumentSink() {
			private List<Document> _docs = new ArrayList<Document>();
			
			@Override
			public void register(Document document) {
				_docs.add(document);
			}

			@Override
			public List<Document> documents() {
				return _docs;
			}
		};
		
		DocumentClassifyImpl classifier = new DocumentClassifyImpl();
		DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
		
		DocumentTraverserImpl trav = new DocumentTraverserImpl(factory, classifier, sink);

		int count = trav.recurseDocuments(new File("src/test/resources/multipage"));
		
		// one grouped and one single
		assertEquals(2, count);
		
		List<Document> list = sink.documents();
		
		assertEquals(2, list.size());
		
		// document 1 should be a multipage document
		
		for(Document d : list) {
			if("keyword1_a_b@01012002".equals(d.baseName())) {
				// should have two pages

				assertEquals(2, d.pageCount());
				count = 0;
				
				for(DocumentFile di : d.documentFiles()) {
					if("keyword1_a_b@01012002_p1.tar.gz".equals(di.fileName())) {
						assertEquals(1, di.pageNumber());
						
					}
					else if("keyword1_a_b@01012002_p2.tar.gz".equals(di.fileName())) {
						assertEquals(2, di.pageNumber());						
					}
					else {
						fail("Document should not be in here : " + di.fileName());
					}
					count++;	
				}
				
				assertEquals(2, count);
			}
			else if("keyword1_a_b@02022002".equals(d.baseName())) {
				assertEquals(1, d.pageCount());
				List<DocumentFile> dfl = d.documentFiles();
				
				assertEquals(1, dfl.size());
				
				assertEquals(1, dfl.get(0).pageNumber());
				
			}
			else {
				fail("Incorrect basename " + d.baseName());
			}
		}		
	}
	
}

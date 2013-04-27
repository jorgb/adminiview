package jobod.adminiview.document;

import static org.junit.Assert.*;

import java.io.File;

import jobod.adminiview.collect.DocumentClassifyImpl;
import jobod.adminiview.document.Document;

import org.junit.Before;
import org.junit.Test;

public class DocumentInfoClassifyTest {

	private DocumentClassifyImpl _c;

	@Before
	public void setup() {
		_c = new DocumentClassifyImpl();
	}
	
	@Test
	public void classifyOnlySubjectValidYear2013() {
		
		String fileName = "subject@2013.ext";
		
		DocumentInfo di = _c.classify(new File(fileName));
		assertNotNull(di);
		
		assertEquals("subject@2013", di.baseName());
		assertEquals(2013, di.year());
		assertEquals("subject", di.keywords()[0]);

		// implicit set
		assertEquals(1, di.day());
		assertEquals(1, di.month());
	}
	
	@Test
	public void classifyOnlySubjectValidYear2009() {
		
		String fileName = "topic@2009.pdf";
		
		DocumentInfo di = _c.classify(new File(fileName));
		assertNotNull(di);
		
		assertEquals("topic@2009", di.baseName());
		assertEquals(2009, di.year());
		assertEquals("topic", di.keywords()[0]);

		// implicit set
		assertEquals(1, di.day());
		assertEquals(1, di.month());
	}
	
	@Test
	public void classifyInvalidYearsBefore1900AndUpTo2100() {
		for(int i = 0; i < 1900; i++) {
			String fileName = String.format("topic@%d.pdf", i);
			assertNull("Year : " + i, _c.classify(new File(fileName)));
			
			// with padding 0's
			fileName = String.format("topic@%04d.pdf", i);
			assertNull("Year : " + i, _c.classify(new File(fileName)));
		}
		
		for(int i = 1900; i < 2101; i++) {
			String fileName = String.format("topic@%d.pdf", i);
			DocumentInfo d = _c.classify(new File(fileName));
			assertNotNull("Year : " + i, d);
			assertEquals(i, d.year());
			assertEquals(1, d.day());
			assertEquals(1, d.month());
			
			// with padding 0's
			fileName = String.format("topic@%04d.pdf", i);
			d = _c.classify(new File(fileName));
			assertNotNull("Year : " + i, d);
			assertEquals(i, d.year());
			assertEquals(1, d.day());
			assertEquals(1, d.month());
		}
		
		for(int i = 2101; i < 9999; i++) {
			String fileName = String.format("topic@%d.pdf", i);
			assertNull("Year : " + i, _c.classify(new File(fileName)));
			// with padding 0's
			fileName = String.format("topic@%04d.pdf", i);
			assertNull("Year : " + i, _c.classify(new File(fileName)));
		}
	}
	
	@Test
	public void noTopicShouldNotClassify() {
		assertNull(_c.classify(new File("@2009.pdf")));
		assertNull(_c.classify(new File("@122009.pdf")));
		assertNull(_c.classify(new File("@01052010.pdf")));
	}
	
	@Test
	public void noTimeSeperationShouldNotClassify() {
		assertNull(_c.classify(new File("topic2009.pdf")));
		assertNull(_c.classify(new File("topic122009.pdf")));
		assertNull(_c.classify(new File("topic01052010.pdf")));
	}
	
	@Test
	public void documentsWithoutExtensionsShouldClassify() {
		DocumentInfo doc1 = _c.classify(new File("topic@2009"));
		assertNotNull(doc1);
		assertEquals(2009, doc1.year());
		
		DocumentInfo doc2 = _c.classify(new File("topic@121992"));
		assertNotNull(doc2);
		assertEquals(1992, doc2.year());
		assertEquals(12, doc2.month());
		
		DocumentInfo doc3 = _c.classify(new File("topic@21052010"));
		assertNotNull(doc3);
		assertEquals(2010, doc3.year());
		assertEquals(05, doc3.month());
		assertEquals(21, doc3.day());
	}
	
	@Test
	public void documentWithPageSeperation() {
		String fileName = "a@02032001_p4.ext";
		
		DocumentInfo di = _c.classify(new File(fileName));
		assertNotNull(di);
		
		assertEquals("a@02032001", di.baseName());
		assertEquals(2001, di.year());
		assertEquals(02, di.day());
		assertEquals(03, di.month());
		
		assertEquals(4, di.pageNumber());		
	}
	
}

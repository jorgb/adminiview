package jobod.adminiview.document;

import static org.junit.Assert.*;

import java.io.File;

import jobod.adminiview.document.Document;
import jobod.adminiview.document.DocumentClassifyImpl;

import org.junit.Before;
import org.junit.Test;

public class DocumentClassifyTest {

	private DocumentClassifyImpl _c;

	@Before
	public void setup() {
		_c = new DocumentClassifyImpl();
	}
	
	@Test
	public void classifyOnlySubjectValidYear2013() {
		
		String fileName = "subject@2013.ext";
		
		Document d = _c.classify(new File(fileName));
		assertNotNull(d);
		
		assertEquals("subject@2013.ext", d.fileName());
		assertEquals(2013, d.year());
		assertEquals("subject", d.topic());

		// implicit set
		assertEquals(1, d.day());
		assertEquals(1, d.month());
		
		
		
	}
	
	@Test
	public void classifyOnlySubjectValidYear2009() {
		
		String fileName = "topic@2009.pdf";
		
		Document d = _c.classify(new File(fileName));
		assertNotNull(d);
		
		assertEquals("topic@2009.pdf", d.fileName());
		assertEquals(2009, d.year());
		assertEquals("topic", d.topic());

		// implicit set
		assertEquals(1, d.day());
		assertEquals(1, d.month());
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
			Document d = _c.classify(new File(fileName));
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
		Document doc1 = _c.classify(new File("topic@2009"));
		assertNotNull(doc1);
		assertEquals(2009, doc1.year());
		
		Document doc2 = _c.classify(new File("topic@121992"));
		assertNotNull(doc2);
		assertEquals(1992, doc2.year());
		assertEquals(12, doc2.month());
		
		Document doc3 = _c.classify(new File("topic@21052010"));
		assertNotNull(doc3);
		assertEquals(2010, doc3.year());
		assertEquals(05, doc3.month());
		assertEquals(21, doc3.day());
	}
}

package jobod.adminiview.document;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import jobod.adminiview.document.Document;
import jobod.adminiview.document.DocumentClassify;
import jobod.adminiview.document.DocumentClassifyImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BatchClassifyInvalidTimeForDocumentTest {

	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			    // too small 	
			    { "0" }, 
			   	{ "1" }, 
			   	{ "10" }, 
			   	{ "02" }, 
			   	{ "100" }, 
			   	{ "220" }, 
			   	{ "310" }, 
			   	{ "162" }, 
			   	{ "999" }, 
			   	// negative
			   	{ "-1" }, 
			   	// invalid range
			   	{ "0000" }, 
			   	{ "000000" }, 
			   	{ "00000000" }, 
			   	{ "00000001" }, 
			   	{ "01002009" },
			   	{ "00002006" }, 
			   	{ "002010" },
			   	{ "32012000" }, 
			   	{ "31132000" },
			   	{ "01262000" },
			   	{ "31232000" },
			   	{ "31992000" },
			   	{ "30002012" }, 
			   	{ "1899" },
			   	{ "12121899" }, 
			   	{ "1201201" }, 
			   	{ "12012" }, 			   	
			   	{ "99999999" } 
	   			};
	   return Arrays.asList(data);
	}
	
	private DocumentClassify _c;
	private String _dateBase;
	
	public BatchClassifyInvalidTimeForDocumentTest(String dateBase) {
		_dateBase = dateBase;
		_c = new DocumentClassifyImpl();
	}
	 
	@Test
	public void testInvalidDocumentExtractionWithExtension() {
		Document d = _c.classify(new File("keyword@" + _dateBase + ".pdf"));
		assertNull(d);
	}

	@Test
	public void testInvalidDocumentExtractionWithExtensionTwoKeywords() {
		Document d = _c.classify(new File("keyword_blah@" + _dateBase + ".pdf"));
		assertNull(d);
	}

	@Test
	public void testInvalidDocumentExtractionWithoutExtension() {
		Document d = _c.classify(new File("keyword@" + _dateBase));
		assertNull(d);
	}

}

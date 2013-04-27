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
public class BatchClassifyProperDocumentTest {

	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   	{ "topic@1900.txt", "topic", 1900, 1, 1 }, 
			   	{ "two_keywords@2012.txt", "two_keywords", 2012, 1, 1 },
			   	{ "aaa_bbb_hhhhh_f@29052010.pdf", "aaa_bbb_hhhhh_f", 2010, 5, 29 },
			   	{ "aaa_gggggggggggggg@012009.pdf", "aaa_gggggggggggggg", 2009, 1, 1 },
			   	{ "aa_xxxxxxx_rrrr_abcdefg@03032010.pdf", "aa_xxxxxxx_rrrr_abcdefg", 2010, 3, 3 },
			   	{ "aaabbb_cc_e_dd_fff_f_f_f_F_F_f@24122008.pdf", "aaabbb_cc_e_dd_fff_f_f_f_F_F_f", 2008, 12, 24 },
			   	{ "uyet_rtert_ewerewe_ewrewrw@112011.pdf", "uyet_rtert_ewerewe_ewrewrw", 2011, 11, 1 },
			   	{ "aaa_bbb_vvvvv@14112011_p1.jpg", "aaa_bbb_vvvvv", 2011, 11, 14 },	// multi page test
			   	{ "aaa_bbb_vvvvv@14112011_p2.jpg", "aaa_bbb_vvvvv", 2011, 11, 14 }	// multi page test
	   			};
	   return Arrays.asList(data);
	}
	
	private String _topic;
	private int _day;
	private int _month;
	private int _year;
	private DocumentClassify _c;
	private String _fileName;
	private String _baseName;
	
	 public BatchClassifyProperDocumentTest(String fileName, String keywords, int year, int month, int day) {
		 _fileName = fileName;
		 _topic = keywords.split("_")[0];
		 _baseName = keywords;
		 _day = day;
		 _month = month;
		 _year = year;
		 _c = new DocumentClassifyImpl();
	 }
	 
	 @Test
	 public void testValidDocumentExtraction() {
		 Document d = _c.classify(new File(_fileName));
		 assertNotNull(d);
	 
		 assertEquals(_day, d.day());
		 assertEquals(_month, d.month());
		 assertEquals(_year, d.year());

		 // TODO: -- Test keywords
	 
		 assertEquals(_topic, d.topic());
		 
		 assertEquals(_baseName, d.baseName());
	 }
}

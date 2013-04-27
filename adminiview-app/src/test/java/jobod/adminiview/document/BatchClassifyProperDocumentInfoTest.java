package jobod.adminiview.document;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import jobod.adminiview.collect.DocumentClassify;
import jobod.adminiview.collect.DocumentClassifyImpl;
import jobod.adminiview.document.Document;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BatchClassifyProperDocumentInfoTest {

	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   	{ "topic@1900.txt", "topic", "topic@1900", 1900, 1, 1, 1 }, 
			   	{ "two_keywords@2012.txt", "two", "two_keywords@2012", 2012, 1, 1, 1 },
			   	{ "aaa_bbb_hhhhh_f@29052010.pdf", "aaa", "aaa_bbb_hhhhh_f@29052010", 2010, 5, 29, 1},
			   	{ "aaa_gggggggggggggg@012009.pdf", "aaa", "aaa_gggggggggggggg@012009", 2009, 1, 1, 1 },
			   	{ "aa_xxxxxxx_rrrr_abcdefg@03032010.pdf", "aa", "aa_xxxxxxx_rrrr_abcdefg@03032010", 2010, 3, 3, 1},
			   	{ "aaabbb_cc_e_dd_fff_f_f_f_F_F_f@24122008.pdf", "aaabbb", "aaabbb_cc_e_dd_fff_f_f_f_F_F_f@24122008", 2008, 12, 24, 1 },
			   	{ "uyet_rtert_ewerewe_ewrewrw@112011.pdf", "uyet", "uyet_rtert_ewerewe_ewrewrw@112011", 2011, 11, 1, 1 },
			   	{ "aaa_bbb_vvvvv@14112011_p1.jpg", "aaa", "aaa_bbb_vvvvv@14112011", 2011, 11, 14, 1 },	
			   	{ "aaa@14112011_p1", "aaa", "aaa@14112011", 2011, 11, 14, 1 },
			   	{ "aaa@14112011_p2", "aaa", "aaa@14112011", 2011, 11, 14, 2 },
			   	{ "aaa_b_b@25102001_p1.tar.gz", "aaa", "aaa_b_b@25102001", 2001, 10, 25, 1 },
			   	{ "aaa_b_b@25102001_p2.tar.gz", "aaa", "aaa_b_b@25102001", 2001, 10, 25, 2 },
			   	{ "aaa_b_b@25102001_p3.tar.gz", "aaa", "aaa_b_b@25102001", 2001, 10, 25, 3 },
			   	{ "aaa_b_b@25102001_p4.tar.gz", "aaa", "aaa_b_b@25102001", 2001, 10, 25, 4 },
			   	{ "aaa@25102001_p04.jpg", "aaa", "aaa@25102001", 2001, 10, 25, 4 },
			   	{ "aaa@25102001_p14.pdf", "aaa", "aaa@25102001", 2001, 10, 25, 14 }			   	
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
	private int _page;
	
	 public BatchClassifyProperDocumentInfoTest(String fileName, String topic,  String basename, int year, int month, int day, int page) {
		 _fileName = fileName;
		 _topic = topic;
		 _baseName = basename;
		 _day = day;
		 _month = month;
		 _year = year;
		 _page = page;
		 _c = new DocumentClassifyImpl();
	 }
	 
	 @Test
	 public void testValidDocumentExtraction() {
		 DocumentInfo di = _c.classify(new File(_fileName));
		 assertNotNull(di);
	 
		 assertEquals(_day, di.day());
		 assertEquals(_month, di.month());
		 assertEquals(_year, di.year());

		 // TODO: -- Test keywords

		 assertEquals(_page, di.pageNumber());
	 
		 assertEquals(_topic, di.keywords()[0]);
		 
		 assertEquals(_baseName, di.baseName());
	 }
}

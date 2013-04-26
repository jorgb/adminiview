package jobod.adminiview.generator.report;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jobod.adminiview.document.Document;

public class DocumentOrder {

	public static void sortTimeDescending(List<Document> topicDocs) {
		
		Collections.sort(topicDocs, new Comparator<Document>() {
			@Override
			public int compare(Document o1, Document o2) {
				int diff = o2.dateInt() - o1.dateInt();
				if(diff == 0) {
					diff = o1.fileName().compareTo(o2.fileName());
				}
				return diff;
			}
		});
	}
}

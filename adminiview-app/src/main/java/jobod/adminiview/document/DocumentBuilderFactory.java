package jobod.adminiview.document;

import java.io.File;

import jobod.adminiview.collect.DocumentBuilder;

public interface DocumentBuilderFactory {

	DocumentBuilder createBuilder(DocumentInfo info, File parentFile);
	
}

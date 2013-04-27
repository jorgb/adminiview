package jobod.adminiview.document;

import java.io.File;

import jobod.adminiview.collect.DocumentBuilder;

public class DocumentBuilderFactoryImpl implements DocumentBuilderFactory {

	@Override
	public DocumentBuilder createBuilder(DocumentInfo info, File parentFile) {
		return new DocumentImpl.Builder(info, parentFile);
	}
}

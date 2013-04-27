package jobod.adminiview.collect;

import java.io.File;

import jobod.adminiview.document.DocumentInfo;

public interface DocumentClassify {

	DocumentInfo classify(File path);
}

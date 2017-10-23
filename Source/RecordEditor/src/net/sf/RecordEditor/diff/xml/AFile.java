package net.sf.RecordEditor.diff.xml;

import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Date;

public class AFile {

	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
	public static final int FILES_THE_SAME = 1;
	public static final int YET_TO_COMPARE = 2;
	public static final int LEFT_ONLY = 3;
	public static final int RIGHT_ONLY = 4;
	public static final int FILES_DIFFERENT = 9;

	public final String fileName, keyName, leftDate;
	private String rightDate;
	public final Path leftFile;
	private Path rightFile;
	

	private int compareResult = YET_TO_COMPARE;
	
	
	public AFile(Path leftFile, Path rightFile) {
		super();
		if (leftFile == null) {
			this.fileName = rightFile.getFileName().toString();
			compareResult = RIGHT_ONLY;
			leftDate = "";
			rightDate = DATE_FORMAT.format(new Date(rightFile.toFile().lastModified()));
		} else {
			this.fileName = leftFile.getFileName().toString();
			compareResult = LEFT_ONLY;
			rightDate = "";
			leftDate = DATE_FORMAT.format(new Date(leftFile.toFile().lastModified()));
		}
		this.keyName = fileName;
		this.leftFile = leftFile;
		this.rightFile = rightFile;
	}


	public Path getRightFile() {
		return rightFile;
	}


	public void setRightFile(Path rightFile) {
		this.rightFile = rightFile;
		this.compareResult = YET_TO_COMPARE;
		this.rightDate = DATE_FORMAT.format(new Date(rightFile.toFile().lastModified()));
		
		File lf = leftFile.toFile();
		long lfSize = lf.length();
		if (lfSize != 0) {
			File rf = rightFile.toFile();
			long lfm = lf.lastModified();
			long rfm = rf.lastModified();
			
			if (lfm != 0 && lfm == rfm && lfSize == rf.length() ) {
				this.compareResult = FILES_THE_SAME;
			}
		}
	}
	
	public Object getFieldNoDir(int idx) {
		switch (idx) {
		case 0 : return fileName;
		case 1 : return leftDate;
		case 2 : return rightDate;
		}
		return null;
	}

	
	public Object getFieldDir(int idx) {
		switch (idx) {
		case 0 : return fileName;
		case 2 : return leftFile == null ? "" : leftFile.toString();
		case 3 : return leftDate;
		case 4 : return rightFile == null ? "" : rightFile.toString();
		case 5 : return rightDate;
		}
		return null;
	}


	public int getCompareResult() {
		return compareResult;
	}


	public void setCompareResult(int compareResult) {
		this.compareResult = compareResult;
	}



}

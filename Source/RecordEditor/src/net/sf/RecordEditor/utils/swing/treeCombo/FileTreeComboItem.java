package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;


public class FileTreeComboItem extends TreeComboItem {
	private final String fullname;
	public final File theFile;


	public FileTreeComboItem(Integer idx, File masterFile, File parentFile, File f) {
		this(idx, getReducedName(parentFile,f), f);
	}

	public FileTreeComboItem(Integer idx, File f) {
		this(idx, getReducedName(f), f);
	}

	private FileTreeComboItem(Integer idx, String s, File f) {
		super(idx, s, s);

		fullname = f.getPath();
		theFile = f;
	}

	/**
	 * @return the fullname
	 */
	public final String getFullname() {
		return fullname;
	}


	private static String getReducedName(File parentFile, File f) {
		StringBuffer s = new StringBuffer(f.getName());
		f = f.getParentFile();
		String parentName = null;

		while ( f != null && ! f.equals(parentFile)
				&& (parentName = f.getName()) != null
				&&  s.length() + parentName.length() < 45
				&&  ! "".equals(parentName))  {
			s.insert(0, parentName + "/");
			f = f.getParentFile();
		}

		return s.toString();
	}


	private static String getReducedName(File f) {
		StringBuffer s = new StringBuffer(f.getName());
		f = f.getParentFile();
		String parentName = null;

		while ( f != null
			&& (parentName = f.getName()) != null
			&&  s.length() + parentName.length() < 45
			&&  ! "".equals(parentName))  {
			s.insert(0, parentName + "/");
			f = f.getParentFile();
		}

		if (parentName != null) {
			s.insert(0, "../");
		}

		return s.toString();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem#getEditString()
	 */
	@Override
	public String getEditString() {
		return fullname;
	}
}

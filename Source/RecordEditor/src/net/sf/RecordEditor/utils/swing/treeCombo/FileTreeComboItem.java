package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;


public class FileTreeComboItem extends TreeComboItem {
	private String fullname;


	public FileTreeComboItem(Integer idx, File masterFile, File parentFile, File f) {
		this(idx, getReducedName(parentFile,f), f);
	}

	public FileTreeComboItem(Integer idx, File f) {
		this(idx, getReducedName(f), f);
	}

	private FileTreeComboItem(Integer idx, String s, File f) {
		super(idx, s, s);

		fullname = f.getPath();
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
			&&  s.length() < 45
			&& (parentName = f.getName()) != null
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

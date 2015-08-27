package net.sf.RecordEditor.utils.params;

import java.io.File;

public interface IEnvironmentValues {

	public File getDocumentDirectory();

	public File getDesktopDirectory();

	public File[] getStandardMountPoints();

	public File[] getOtherMountPoints();
}

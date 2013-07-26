package net.sf.RecordEditor.re.install;

public class InstallDetails {

	public static final int IT_UNDEFINED = 0;
	public static final int IT_USE_CURRENT_INSTALL = 1;
	public static final int IT_COPY_FROM_EXISTING_INSTALL = 2;
	public static final int IT_NEW_INSTALL         = 3;

	public boolean existingDefinition = false;

	public int installType = IT_UNDEFINED;

	public String oldInstallDir = "";
	public String oldInstallHomeDir = "";

	public boolean dropSemiColon = false;

	public String driver = "", source   = "", createExtension = "",
				  user   = "", password = "", jarName   = "";
}

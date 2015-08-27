package net.sf.RecordEditor.re.file.filter;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;

public class ExecuteSavedFileBatch<details> {

	@SuppressWarnings("rawtypes")
	private final Class dtlsClass;
	private final AbstractExecute<details> action;


	public ExecuteSavedFileBatch(@SuppressWarnings("rawtypes") Class detailsClass, AbstractExecute<details> action) {
		super();
		this.dtlsClass = detailsClass;
		this.action = action;
	}


	public AbstractFileDisplay execAction(boolean run, String fileName) {

		try {
			return execActionRaw(run, fileName);
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
			Common.logMsg("Jibx Call Failed: Class not loaded", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Common.logMsg("Execute Error", ex);
		}
		return null;
	}


	public AbstractFileDisplay execActionRaw(boolean run, String fileName) throws Exception {
		details saveDetails;

		net.sf.RecordEditor.jibx.JibxCall<details> jibx
			= new net.sf.RecordEditor.jibx.JibxCall<details>(dtlsClass);

		//System.out.println(fileName);
		saveDetails = jibx.marshal(fileName);

		if (run) {
			return action.execute(saveDetails);
		} else {
			action.executeDialog(saveDetails);
		}
		return null;
	}

}

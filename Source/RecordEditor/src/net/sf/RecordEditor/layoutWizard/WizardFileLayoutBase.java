package net.sf.RecordEditor.layoutWizard;

import java.awt.Component;

import javax.swing.RootPaneContainer;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;


public abstract class WizardFileLayoutBase extends AbstractWizard<Details> {

	private BasicLayoutCallback callbackClass;
	private ExternalRecord externalRecord = null;

	private static final String fixDirectory(String dir) {
		if (dir != null && dir.length() > 0) {
			while (dir.endsWith("*")) {
				dir = dir.substring(0, dir.length() - 1);
			}
			if ((! dir.endsWith("/")) && (! dir.endsWith("\\"))) {
				dir = dir + Common.FILE_SEPERATOR;
			}
		}
		return dir;
	}

	public <T extends Component & RootPaneContainer> WizardFileLayoutBase(T frame, BasicLayoutCallback callback) {
		super(frame, new Details());
		
		this.callbackClass = callback;
	}

	/**
	 * Save the new layout at the end
	 */
	@Override
	public void finished(Details details) {
	
		if ("".equals(details.layoutName)) {
			this.getMessage().setText("Layout filename must be entered");
		} else {
	        try {
	        	String copybookFile;
		        externalRecord = details.createRecordLayout();
		        CopybookWriter w = CopybookWriterManager.getInstance().get(details.layoutWriterIdx);
	
	
		        String dir = details.layoutDirectory;
	
		        if (dir == null || dir.length() == 0) {
		        	dir = Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get();
		        }
		        
			    if (dir != null && dir.length() > 0) {
		        	dir = fixDirectory(dir);
	
					copybookFile = w.writeCopyBook(dir, externalRecord, Common.getLogger());
	
			        if (callbackClass != null) {
			            callbackClass.setRecordLayout(externalRecord.getRecordId(),  copybookFile, details.filename);
			        }
		        }
	
	         } catch (Exception ex) {
	            this.getMessage().setText(ex.getMessage());
	            Common.logMsgRaw(ex.getMessage(), ex);
	            ex.printStackTrace();
	        } finally {
	        	try {
	        		this.setClosed(true);
	        	} catch (Exception e) {
	
				}
	        }
		}
	}


	public ExternalRecord getExternalRecord() {
		return externalRecord;
	}

}
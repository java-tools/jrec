package net.sf.RecordEditor.edit.display.SaveAs;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.common.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FileView;

public final class CommonSaveAsFields {
    public static final String OPT_FILE = "File";
    public static final String OPT_VIEW = "Current View";
    public static final String OPT_SELECTED = "Selected Records";


	public final static int FMT_DATA = 0;
	public final static int FMT_CSV = 1;
	public final static int FMT_FIXED = 2;
	public final static int FMT_XML = 3;
	public final static int FMT_HTML = 4;
	public final static int FMT_XSLT = 5;
	public final static int FMT_VELOCITY = 6;
	
    public final JComboBox saveWhat   = new JComboBox();
    
    public final JCheckBox treeExportChk    = new JCheckBox(),
    		               nodesWithDataChk = new JCheckBox(),
    		               keepOpenChk      = new JCheckBox(),
    		               editChk          = new JCheckBox();
    
    public final JTextArea message = new JTextArea();

    public final FileView<?> file;
    public final SaveAsWrite flatFileWriter;
    private final AbstractFileDisplay recordFrame;
    private AbstractTreeFrame treeFrame = null;
    
    protected AbstractRecordDetail<?> printRecordDetails;
    
    
	/**
	 * @return the recordFrame
	 */
	protected AbstractFileDisplay getRecordFrame() {
		return recordFrame;
	}


	/**
	 * @param recordFrame the recordFrame to set
	 */
	protected CommonSaveAsFields(final AbstractFileDisplay recordFrame, final FileView<?> file) {
		this.recordFrame = recordFrame;
		this.file = file;
		
		treeFrame = null;
        if (recordFrame instanceof AbstractTreeFrame) {
        	treeFrame = (AbstractTreeFrame) recordFrame;
        }
        flatFileWriter = SaveAsWrite.getWriter(file, getRecordFrame());
	}


	/**
	 * @return the treeFrame
	 */
	protected AbstractTreeFrame getTreeFrame() {
		return treeFrame;
	}
	
    
    public final int getWhatToSave(String selection) {
        int whatToSave = SaveAsWrite.SAVE_SELECTED;
        if (OPT_FILE.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_FILE;
        } else if (OPT_VIEW.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_VIEW;
        } 
        return whatToSave;
    }

    
    public final FileView getViewToSave(String selection) {
    	return getViewToSave(getWhatToSave(selection));
    }

    
    public final FileView getViewToSave(int whatToSave) {
    	FileView ret = null;
    	switch (whatToSave) {
    	case SaveAsWrite.SAVE_SELECTED: ret = file.getView(recordFrame.getSelectedRows()); break;
    	case SaveAsWrite.SAVE_FILE: ret = file.getBaseFile();
    	case SaveAsWrite.SAVE_VIEW: ret = file;
    	}
    	
    	return ret;
   	}
}

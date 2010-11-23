package net.sf.RecordEditor.edit.display.util;

import java.util.ArrayList;

import javax.swing.JButton;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.display.LineList;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.jibx.compare.FieldTest;
import net.sf.RecordEditor.jibx.compare.Record;
import net.sf.RecordEditor.jibx.compare.Layout;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.filter.AbstractExecute;
import net.sf.RecordEditor.utils.filter.AbstractSaveDetails;
import net.sf.RecordEditor.utils.filter.ExecuteSavedFile;
import net.sf.RecordEditor.utils.filter.FilterField;
import net.sf.RecordEditor.utils.filter.SaveButton;

public class SaveRestoreHiddenFields 
implements AbstractSaveDetails<EditorTask>, AbstractExecute<EditorTask> {

	private AbstractFileDisplayWithFieldHide display; 
	private HideFields hideFields;
	
  
   /**
	 * @param display
	 */
	public SaveRestoreHiddenFields(AbstractFileDisplayWithFieldHide display,
			HideFields hFields) {
		this.display = display;
		this.hideFields = hFields;
	}


	/**
	 * @see net.sf.RecordEditor.utils.filter.AbstractSaveDetails#getSaveDetails()
	 */
	@Override
	public EditorTask getSaveDetails() {
		EditorTask ret = new EditorTask();
		
		ret.type = EditorTask.TASK_VISIBLE_FIELDS;
		ret.filter =  getExternalLayout();
		if (display instanceof LineList 
		&& hideFields.isSaveSeqSelected()) {
			ret.fieldSequence = ((LineList) display).getFieldSequence();
		}
		return ret;
	}
	
    private Layout getExternalLayout() {
    	int j, k, count;
		Layout tmpLayoutSelection = new Layout();
		Record rec;
		boolean allSelected = true;
		AbstractRecordDetail recordDetail;
		AbstractLayoutDetails layout = display.getFileView().getLayout();
		
		boolean[] recordFields;
		
		
		tmpLayoutSelection.name = layout.getLayoutName();
		for (int i =0; i < layout.getRecordCount(); i++) {
//			if (isInclude(i)) {
			rec = new net.sf.RecordEditor.jibx.compare.Record();
			if (i == hideFields.getRecordIndex()) {
				recordFields = hideFields.getVisibleFields();
			} else {
				recordFields = display.getFieldVisibility(i);
			}
			recordDetail = layout.getRecord(i);
			rec.name = recordDetail.getRecordName();

			if (recordFields != null) {
				count = 0;
				for (j = 0; j < recordDetail.getFieldCount() ; j++) {
					if ((recordFields[j])) {
						count += 1;
					} 
				}
	
				
				if (count != recordDetail.getFieldCount()) {
					k = 0;
					allSelected = false;
					rec.fields = new String[count];
					for (j = 0; j < recordDetail.getFieldCount() ; j++) {
						if ((recordFields[j])) {
							rec.fields[k++] = recordDetail.getField(j).getName();
						} 
					}
				}		
			}
			FilterField filterFld;
			FieldTest test;
			rec.fieldTest = new ArrayList<FieldTest>(0); 		
			
			tmpLayoutSelection.getRecords().add(rec);
			
		}

		if (allSelected) {
			tmpLayoutSelection.records = null;
		}
		
		return tmpLayoutSelection;
    }

  

	public void execute(EditorTask details) {
		
	   	int idx, fieldIdx, j;
		Record rec;
		boolean fieldsPresent;
		AbstractRecordDetail recordDetail;
		AbstractLayoutDetails layout = display.getFileView().getLayout();
		
		boolean[] recordFields;

		if (details.filter.records == null 
		||  details.filter.records.size() == 0) {
			for (int i =0; i < layout.getRecordCount(); i++) {
				recordDetail = layout.getRecord(i);
				recordFields = createBooleanArray(recordDetail.getFieldCount(), true);
				display.setFieldVisibility(i, recordFields);
			}
		} else {
			for (int i =0; i < layout.getRecordCount(); i++) {
				rec = details.filter.records.get(i);
				idx = layout.getRecordIndex(rec.name);
				if (idx >= 0) { 
					recordDetail = layout.getRecord(idx);
					fieldsPresent = rec.fields != null && rec.fields.length > 0;
					recordFields = createBooleanArray(recordDetail.getFieldCount(), ! fieldsPresent);
					
					if (fieldsPresent) {
						for (j = 0; j  < rec.fields.length; j++) {
							fieldIdx = recordDetail.getFieldIndex(rec.fields[j]);
							if (fieldIdx >= 0) {
								recordFields[fieldIdx] = true;
							}
						}
					}
					display.setFieldVisibility(idx, recordFields);
				}
			}
		}
		
		if (details.fieldSequence != null 
		&& display instanceof LineList) {
			((LineList) display).setFieldSequence(details.fieldSequence);
		}
	}
	
	public boolean[] createBooleanArray(int size, boolean initValue) {
		boolean[] ret = new boolean[size];
		for (int i = 0; i < size; i++) {
			ret[i] = initValue;
		}
		
		return ret;
	}
	
	public void executeDialog(EditorTask details) {
		execute(details);
	}

    
	public static JButton getSaveButton(
			AbstractFileDisplayWithFieldHide pnl, HideFields hideFields) {
		String dir = Parameters.getFileName(Parameters.HIDDEN_FIELDS_SAVE_DIRECTORY);
		return new SaveButton<EditorTask>(
				new net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields(pnl, hideFields),
				dir);
	}
	
	   
	public static void restoreHiddenFields(AbstractFileDisplayWithFieldHide pnl) {
		SaveRestoreHiddenFields action = new SaveRestoreHiddenFields(pnl, null);
		FileView fileView = pnl.getFileView();
		
		new ExecuteSavedFile<EditorTask>(
				fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", fileView,
				Parameters.getFileName(Parameters.HIDDEN_FIELDS_SAVE_DIRECTORY), 
				action, EditorTask.class
		);

	}
			

}

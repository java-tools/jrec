package net.sf.RecordEditor.edit.display.util;

import java.util.ArrayList;

import javax.swing.JPanel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.jibx.compare.FieldTest;
import net.sf.RecordEditor.jibx.compare.Layout;
import net.sf.RecordEditor.jibx.compare.Record;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.AbstractExecute;
import net.sf.RecordEditor.re.file.filter.ExecuteSavedFile;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.saveRestore.ISaveUpdateDetails;
import net.sf.RecordEditor.utils.swing.saveRestore.SaveLoadPnl;

public class SaveRestoreHiddenFields
implements ISaveUpdateDetails<EditorTask>, AbstractExecute<EditorTask> {

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
	 * @see net.sf.RecordEditor.utils.swing.saveRestore.ISaveDetails#getSaveDetails()
	 */
	@Override
	public EditorTask getSaveDetails() {
		EditorTask ret = new EditorTask();

		ret.type = EditorTask.TASK_VISIBLE_FIELDS;
		ret.filter =  getExternalLayout();
		if (display instanceof AbstractFieldSequencePnl
		&& hideFields.isSaveSeqSelected()) {
			ret.fieldSequence = ((AbstractFieldSequencePnl) display).getFieldSequence();
		}
		return ret;
	}

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails#update(java.lang.Object)
	 */
	@Override
	public void update(EditorTask serialisedData) {
		execute(serialisedData);
		ReFrame.setActiveFrame(hideFields.frame);
	}


	@SuppressWarnings("rawtypes")
	private Layout getExternalLayout() {
    	int j, k, count;
		Layout tmpLayoutSelection = new Layout();
		Record rec;
		boolean allSelected = true;
		AbstractRecordDetail recordDetail;
		AbstractLayoutDetails layout = display.getFileView().getLayout();

		boolean[] recordFields;


		tmpLayoutSelection.name = layout.getLayoutName();
		for (int i = 0; i < layout.getRecordCount(); i++) {
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

			rec.fieldTest = new ArrayList<FieldTest>(0);

			tmpLayoutSelection.getRecords().add(rec);

		}

		if (allSelected) {
			tmpLayoutSelection.records = null;
		}

		return tmpLayoutSelection;
    }



	@SuppressWarnings("rawtypes")
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
			for (int i = 0; i <  details.filter.records.size(); i++) {
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
					if (hideFields != null && idx ==  hideFields.getRecordIndex()) {
						hideFields.updateIncludes(recordFields);
					}
				}
			}
		}

		if (details.fieldSequence != null
		&& display instanceof AbstractFieldSequencePnl) {
			((AbstractFieldSequencePnl) display).setFieldSequence(details.fieldSequence);
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


	public static JPanel getSaveLoadPnl(
			AbstractFileDisplayWithFieldHide pnl, HideFields hideFields) {
		String dir = Parameters.getFileName(Parameters.FIELD_SAVE_DIRECTORY);
		return (new SaveLoadPnl<EditorTask>(
				new net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields(pnl, hideFields),
				dir, EditorTask.class)).panel;
	}


	public static void restoreHiddenFields(AbstractFileDisplayWithFieldHide pnl) {
		SaveRestoreHiddenFields action = new SaveRestoreHiddenFields(pnl, null);
		@SuppressWarnings("rawtypes")
		FileView fileView = pnl.getFileView();

		new ExecuteSavedFile<EditorTask>(
				fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", fileView,
				Parameters.getFileName(Parameters.FIELD_SAVE_DIRECTORY),
				action, EditorTask.class
		);

	}


}

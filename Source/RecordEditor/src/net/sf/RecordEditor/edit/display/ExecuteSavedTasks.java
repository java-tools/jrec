package net.sf.RecordEditor.edit.display;

import net.sf.RecordEditor.edit.display.util.HideFields;
import net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields;
import net.sf.RecordEditor.edit.display.util.SortFrame;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.IExecuteSaveAction;
import net.sf.RecordEditor.re.display.IUpdateExecute;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.AbstractExecute;
import net.sf.RecordEditor.re.file.filter.ExecuteSavedFile;
import net.sf.RecordEditor.re.file.filter.ExecuteSavedFileBatch;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class ExecuteSavedTasks implements IExecuteSaveAction {

	private final BaseDisplay parentDisplay;
	private final FileView parentView;


	/**
	 * Executes a variety of saved tasks
	 * @param display display to have the tasks execute on
	 * @param fileView fileView the task is to act on.
	 */
	public ExecuteSavedTasks(BaseDisplay display, FileView fileView) {
		super();
		this.parentDisplay = display;
		this.parentView = fileView;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedFilterDialog()
	 */
	@Override
	public final void executeSavedFilterDialog() {

		new ExecuteSavedFile<EditorTask>(
				parentView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", parentView,
				Parameters.getFileName(Parameters.FILTER_SAVE_DIRECTORY),
				getFilterAction(), EditorTask.class
		);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedSortTreeDialog()
	 */
	@Override
	public final void executeSavedSortTreeDialog() {

		new ExecuteSavedFile<EditorTask>(
				parentView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Sort Tree", parentView,
				Parameters.getFileName(Parameters.SORT_TREE_SAVE_DIRECTORY),
				getSortTreeAction(), EditorTask.class
		);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedRecordTreeDialog()
	 */
	@Override
	public final void executeSavedRecordTreeDialog() {

		new ExecuteSavedFile<EditorTask>(
				parentView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Sort Tree", parentView,
				Parameters.getFileName(Parameters.RECORD_TREE_SAVE_DIRECTORY),
				getRecordTreeAction(), EditorTask.class
		);
	}


	/*
	 * ------------------------------------------------------------------------------------------------------
	 */

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedRecordTree(java.lang.String)
	 */
	@Override
	public final AbstractFileDisplay executeSavedTask(String fileName) {

		return (new ExecuteSavedFileBatch<EditorTask>(EditorTask.class, getTaskAction()))
			.execAction(true, fileName);
	}


//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedFilter(java.lang.String)
//	 */
//	@Override
//	public final void executeSavedFilter(String fileName) {
//
//		(new ExecuteSavedFileBatch<EditorTask>(EditorTask.class, getFilterAction()))
//			.execAction(true, fileName);
//	}
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedSortTree(java.lang.String)
//	 */
//	@Override
//	public final void executeSavedSortTree(String fileName) {
//
//		(new ExecuteSavedFileBatch<EditorTask>(EditorTask.class, getSortTreeAction()))
//			.execAction(true, fileName);
//	}
//
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedSort(java.lang.String)
//	 */
//	@Override
//	public final void executeSavedSort(String fileName) {
//
//		(new ExecuteSavedFileBatch<EditorTask>(EditorTask.class, getSortAction()))
//			.execAction(true, fileName);
//	}
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.edit.display.IExecuteSaveAction#executeSavedRecordTree(java.lang.String)
//	 */
//	@Override
//	public final void executeSavedRecordTree(String fileName) {
//
//		(new ExecuteSavedFileBatch<EditorTask>(EditorTask.class, getRecordTreeAction()))
//			.execAction(true, fileName);
//	}



	/*
	 * ------------------------------------------------------------------------------------------------------
	 */

	public final AbstractExecute<EditorTask> getFilterAction() {
		return new AbstractExecute<EditorTask>() {
			public AbstractFileDisplay execute(EditorTask details) {
				return executeFilter(details);
			}

			public void executeDialog(EditorTask details) {
				(new FilterFrame(parentDisplay, parentView))
						.updateFromExternalLayout(details.filter);
			}
		};
	}


	public final AbstractExecute<EditorTask> getSortTreeAction() {
		return new AbstractExecute<EditorTask>() {
			public AbstractFileDisplay execute(EditorTask details) {
				CreateSortedTree treePnl = new CreateSortedTree(parentDisplay, parentView);
				treePnl.setFromSavedDetails(details);
				return treePnl.doAction();
			}

			public void executeDialog(EditorTask details) {
				(new CreateSortedTree(parentDisplay, parentView)).setFromSavedDetails(details);
			}
		};
	}


	public final AbstractExecute<EditorTask> getRecordTreeAction() {
		return new AbstractExecute<EditorTask>() {
			public AbstractFileDisplay execute(EditorTask details) {
				CreateRecordTree treePnl = new CreateRecordTree(parentDisplay, parentView);
				treePnl.treeDisplay.setFromSavedDetails(details);
				return treePnl.doAction();
			}

			public void executeDialog(EditorTask details) {
				(new CreateRecordTree(parentDisplay, parentView)).treeDisplay.setFromSavedDetails(details);

			}
		};
	}

		public final AbstractExecute<EditorTask> getTaskAction() {
			return new AbstractExecute<EditorTask>() {
				public AbstractFileDisplay execute(EditorTask details) {
					return execTask(details, true);
				}

				public void executeDialog(EditorTask details) {
					execTask(details, false);
				}

				private AbstractFileDisplay execTask(EditorTask details, boolean execute) {
					IUpdateExecute<EditorTask> task = null;

					if (EditorTask.TASK_FILTER.equals(details.type)) {
						if (execute) {
							return executeFilter(details);
						}
						(new FilterFrame(parentDisplay, parentView))
							.updateFromExternalLayout(details.filter);
						return null;
					} else if (EditorTask.TASK_RECORD_TREE.equals(details.type)) {
						task = new CreateRecordTree(parentDisplay, parentView);
					} else if (EditorTask.TASK_SORT_TREE.equals(details.type)) {
						task = new CreateSortedTree(parentDisplay, parentView);
					} else if (EditorTask.TASK_SORT.equals(details.type)) {
						task = new SortFrame(parentDisplay, parentView);
					} else if (EditorTask.TASK_FIELD_TREE.equals(details.type)) {
						task = new CreateFieldTree(parentDisplay, parentView);
					} else if (EditorTask.TASK_VISIBLE_FIELDS.equals(details.type)
							&& parentDisplay instanceof AbstractFileDisplayWithFieldHide) {
						AbstractFileDisplayWithFieldHide displ = (AbstractFileDisplayWithFieldHide)parentDisplay;
						HideFields hFields = new HideFields(displ);
						SaveRestoreHiddenFields sr = new SaveRestoreHiddenFields(displ, hFields);
						if (execute) {
							AbstractFileDisplay ret = sr.execute(details);
							//displ.getFileView().fireTableStructureChanged();
							hFields.updateSourcePanel();
							hFields.frame.doDefaultCloseAction();
							return ret;
						}
						sr.executeDialog(details);
						return null;
//					} else if (EditorTask.TASK_FIELD_SEQUENCE.equals(details.type)
//							&& parentDisplay instanceof AbstractFileDisplayWithFieldHide) {
//						AbstractFileDisplayWithFieldHide displ = (AbstractFileDisplayWithFieldHide)parentDisplay;
//						task = new SaveRestoreHiddenFields(displ, new HideFields(displ));
					} else {
						throw new RuntimeException("Task: " + details.type + " is not supported");
					}

					task.setFromSavedDetails(details);

					if (execute) {
						return task.doAction();
					}
					return null;
				}

			};
	}

	private AbstractFileDisplay executeFilter(EditorTask details) {
		FilterDetails filter = new FilterDetails(parentView.getLayout(), FilterDetails.FT_NORMAL);

		filter.updateFromExternalLayout(details.filter);
    	FileView view = parentView.getFilteredView(filter);
    	if (view == null) {
    		Common.logMsg("No records matched the filter", null);
    	} else {
    		return DisplayBuilderFactory.newLineList(parentDisplay.getParentFrame(), view.getLayout(), view, view.getBaseFile());
    	}
    	return null;
	}

	/*
	 * ------------------------------------------------------------------------------------------------------
	 */

}

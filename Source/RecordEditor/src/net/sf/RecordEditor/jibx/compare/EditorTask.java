package net.sf.RecordEditor.jibx.compare;

public class EditorTask {

	public static final String TASK_FILTER      = "Filter";
	public static final String TASK_SORT_TREE   = "SortTree";
	public static final String TASK_RECORD_TREE = "RecordTree";
	public static final String TASK_VISIBLE_FIELDS      = "VisibleFields";
	
	public String type;
	public String layoutName="";
	public Layout filter = null;
	public SortTree sortTree = null;
	public RecordTree recordTree;
	
	
	public EditorTask setFilter(Layout filterDetails) {
		type = TASK_FILTER;
		
		filter = filterDetails;
		layoutName = filterDetails.name;

		return this;
	}
	
	public EditorTask setSortTree(String recordLayoutName, SortTree sortDetails) {
		type = TASK_SORT_TREE;
		
		layoutName = recordLayoutName;
		sortTree = sortDetails;

		return this;
	}

	/**
	 * set Record Tree
	 * @param recordDetails record Tree
	 * @return this
	 */
	public EditorTask setRecordTree(String recordLayoutName, RecordTree recordDetails) {
		type = TASK_RECORD_TREE;
		
		layoutName = recordLayoutName;
		recordTree = recordDetails;

		return this;
	}
}

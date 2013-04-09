/*
 * @Author Bruce Martin
 * Created on 4/01/2007
 *
 * Purpose:
 *   Interface for processing Record Editor ScreenActions
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added Sort action code
 */
package net.sf.RecordEditor.utils.common;

/**
 * Interface for processing Record Editor ScreenActions
 *
 * @author Bruce Martin
 *
 */
public interface ReActionHandler {
    public static final int SAVE      = 1;

    public static final int SAVE_AS           = 2;
    public static final int EXPORT_AS_HTML    = 3;
    public static final int EXPORT_AS_HTML_TBL_PER_ROW = 4;
    public static final int EXPORT_VELOCITY   = 5;
    public static final int SAVE_AS_XML     = 44;
    public static final int SAVE_LAYOUT_XML   = 45;
    public static final int EXPORT_HTML_TREE  = 46;
    public static final int EXPORT_AS_CSV     = 51;
    public static final int EXPORT_AS_FIXED   = 52;

    public static final int DELETE    = 6;

    public static final int FIND      = 7;

    public static final int FILTER    = 8;

    public static final int OPEN      = 9;

    public static final int CLOSE     = 10;
    public static final int CLOSE_ALL = 11;

    public static final int NEW       = 12;
    public static final int CORRECT_RECORD_LENGTH = 13;

    public static final int TABLE_VIEW_SELECTED  = 14;
    public static final int RECORD_VIEW_SELECTED = 15;
    public static final int COLUMN_VIEW_SELECTED = 16;
    public static final int SELECTED_VIEW        = 55;
    public static final int BUILD_FIELD_TREE     = 17;
    public static final int BUILD_SORTED_TREE    = 18;
    public static final int BUILD_RECORD_TREE    = 19;
    public static final int BUILD_LAYOUT_TREE    = 20;

    public static final int BUILD_XML_TREE_SELECTED    = 21;
    public static final int BUILD_LAYOUT_TREE_SELECTED = 22;

    public static final int HELP            = 23;

    public static final int COPY_RECORD     = 24;

    public static final int CUT_RECORD      = 25;

    public static final int PASTE_RECORD    = 26;

    public static final int PASTE_RECORD_PRIOR = 27;
    public static final int INSERT_RECORDS  = 28;

    public static final int DELETE_RECORD   = 29;

    public static final int SORT            = 30;

    public static final int REPEAT_RECORD_POPUP   = 31;
    public static final int NEXT_RECORD     = 32;
    public static final int PREVIOUS_RECORD = 33;

    public static final int CREATE_CHILD    = 34;
    public static final int EDIT_CHILD      = 35;
    public static final int PRINT           = 36;
    public static final int PRINT_SELECTED  = 54;
    public static final int REBUILD_TREE    = 37;


    public static final int ADD_ATTRIBUTES          = 38;

    public static final int FULL_TREE_REBUILD       = 39;
    public static final int EXECUTE_SAVED_FILTER    = 40;
    public static final int EXECUTE_SAVED_SORT_TREE = 41;
    public static final int EXECUTE_SAVED_RECORD_TREE = 42;
    public static final int COMPARE_WITH_DISK       = 43;

    public static final int PASTE_TABLE_OVERWRITE   = 47;
    public static final int PASTE_TABLE_INSERT      = 48;

    public static final int SHOW_INVALID_ACTIONS    = 49;
    public static final int AUTOFIT_COLUMNS         = 50;

    public static final int INSERT_RECORD_PRIOR     = 53;
    public static final int EXPORT_XSLT             = 56;
    public static final int EXPORT                  = 57;
    public static final int EXPORT_SCRIPT           = 58;
    public static final int RUN_SCRIPT              = 59;
    public static final int REFRESH                 = 60;

    public static final int CLOSE_TAB               = 61;
    public static final int UNDOCK_TAB              = 62;
    public static final int UNDOCK_ALL_TABS         = 63;
    public static final int DOCK_TAB                = 64;
    public static final int DOCK_ALL_SCREENS        = 65;

    public static final int REMOVE_CHILD_SCREEN     = 66;
    public static final int ADD_CHILD_SCREEN        = 67;
    public static final int ADD_CHILD_SCREEN_RIGHT  = 68;
    public static final int ADD_CHILD_SCREEN_BOTTOM = 69;
    public static final int ADD_CHILD_SCREEN_SWAP   = 70;

    public static final int INSERT_RECORDS_POPUP      = 71;
    public static final int INSERT_RECORD_PRIOR_POPUP = 72;
    public static final int PASTE_RECORD_POPUP        = 73;

    public static final int PASTE_RECORD_PRIOR_POPUP  = 74;
    public static final int DELETE_RECORD_POPUP       = 75;
    public static final int DELETE_BUTTON             = 76;

    public static final int MAX_ACTION      = 77;

    /**
     * Execute a form action
     *
     * @param action action to be performed
     */
    public abstract void executeAction(int action);

    /**
     * check if a form action is available
     *
     * @param action action to be checked
     *
     * @return wether action is available
     */
    public abstract boolean isActionAvailable(int action);
}
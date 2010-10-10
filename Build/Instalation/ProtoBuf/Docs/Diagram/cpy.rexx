/* rexx */


    Call copyFile 'StartMenu.GIF'
    Call copyFile 'RightClick.GIF'
    Call copyFile 'RecordEdit_FileCopy.GIF'
    Call copyFile 'RecordEdit_jEdit.GIF'
    Call copyFile 'RecordEdit_Table.GIF'
    Call copyFile 'RecordEdit_LayoutCombo.GIF'
    Call copyFile 'RecordEdit_Buttons.GIF'
    Call copyFile 'RecordEdit_BrowseButtons.GIF'
    Call copyFile 'RecordEdit_Selection.GIF'
    Call copyFile 'RecordEdit_Heading.GIF'
    Call copyFile 'RecordEdit_TableTxt.GIF'
    Call copyFile 'RecordEdit_RecordTxt.GIF'
    Call copyFile 'RecordEdit_Record.GIF'
    Call copyFile 'RecordEdit_GenericCSV.GIF'
    Call copyFile 'RecordEdit_Popup.GIF'
    Call copyFile 'RecordEdit_Find.GIF'
    Call copyFile 'RecordEdit_Filter.GIF'
    Call copyFile 'RecordEdit_Filter_Records.GIF'
    Call copyFile 'RecordEdit_Filter_Fields.GIF'
    Call copyFile 'RecordEdit_Filter_FieldValues.GIF'
    Call copyFile 'RecordEdit_SaveAs.GIF'
    Call copyFile 'RecordEdit_Sort.GIF'
    Call copyFile 'RecordEdit_ViewMenu.GIF'
    Call copyFile 'RecordEdit_FieldTreeDef1.GIF'
    Call copyFile 'RecordEdit_SortedField1.GIF'
    Call copyFile 'AmsPOfile.GIF'
    Call copyFile 'RecordEdit_RecordField1.GIF'
    Call copyFile 'RecordTree.GIF'
    Call copyFile 'RecordEdit_View3.GIF'
    Call copyFile 'Record_Standard.GIF'
    Call copyFile 'Record_Group.GIF'
    Call copyFile 'RecordEdit_Filter_tbl_xmpl1a.GIF'
    Call copyFile 'RecordEdit_Filter_tbl_xmpl1b.GIF'
    Call copyFile 'RecordEdit_Filter_tbl_xmpl1.GIF'
    Call copyFile 'RecordEdit_Filter_xmpl1.GIF'
    Call copyFile 'RecordEdit_Filter_SaveAs_xmpl1.GIF'
return

copyFile:
parse arg f z
	say f
	'cp' f '/home/bm/Work/JRecord/Docs/Diagram/' 
return

useFixture(default)

def test():
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%')

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg6')
			click('OK')
		close()

		click('Save1')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxxxzFLDg6')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Child Name,1')
		select_menu('View Record Selections Tree')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('JTreeTable', 'cell:Boolean op 1  ,1(  )')
		rightclick('JTreeTable', 's,1')
		select('Lines to Insert_Txt', '3')
		click('Insert')
		time.sleep(0.9)	
		select('RecordSelectionJTbl', 'cell:Field,0()')
		select('RecordSelectionJTbl', 'fld 21', 'Field,0')
		select('RecordSelectionJTbl', '21', 'Field Value,0')
		time.sleep(0.9)	
		select('RecordSelectionJTbl', 'fld 21', 'Field,1')
		select('RecordSelectionJTbl', '21aa', 'Field Value,1')
		select('RecordSelectionJTbl', 'fld 22', 'Field,2')
		select('RecordSelectionJTbl', '22', 'Field Value,2')
		select('RecordSelectionJTbl', 'cell:Field Value,2()')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')
		select('RecordSelectionJTbl', 'cell:Field Value,2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Boolean op 1  ,1(  )')
		select('JTreeTable', 'cell:Boolean op 1  ,1(  )')
##		click('MetalInternalFrameTitlePane', 1416, 0)
##		select_menu('Window>>Menu1>>Record Selection Tree')
		select('JTreeTable', 'cell:Boolean op 2  ,1(  )')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Child Name,1')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:Child Name,1()')
		click('Delete3')

		if window('Delete: zxxxzFLDg6'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

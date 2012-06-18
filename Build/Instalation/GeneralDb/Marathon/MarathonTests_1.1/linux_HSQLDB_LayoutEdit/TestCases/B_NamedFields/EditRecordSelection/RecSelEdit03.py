useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%')

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg4')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg1%')

		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zxxxzFLDg4')

		select('RecordList.Description_Txt', '%')

#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		select('Lines to Insert_Txt', '3')
		click('Insert')
		select('RecordSelectionJTbl', 'cell:Field,0()')
		select('RecordSelectionJTbl', 'fld 11', 'Field,0')
		select('RecordSelectionJTbl', '11', 'Field Value,0')
		select('RecordSelectionJTbl', 'cell:Field Value,0()')
		rightclick('RecordSelectionJTbl', 'and,1')
		select('RecordSelectionJTbl', 'fld 11', 'Field,1')
		select('RecordSelectionJTbl', '11a', 'Field Value,1')
		select('RecordSelectionJTbl', 'fld 12', 'Field,2')
		select('RecordSelectionJTbl', '12', 'Field Value,2')
		select('RecordSelectionJTbl', 'cell:Field Value,1(11a)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12]]')

		
		select('RecordSelectionJTbl', 'cell:Field Value,1(11a)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('ChildRecordsJTbl', 'fff', 'Field,0')
		select('ChildRecordsJTbl', 'vvv1', 'Field Value,0')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('Edit Record Selections')


##		select('ChildRecordsJTbl', 'cell:rs,0(null)')
##		select('Lines to Insert_Txt', '2')
		select('Lines to Insert_Txt1', '2')
		click('Insert1')
##		click('Insert')


		select('RecordSelectionJTbl1', 'cell:Field,0()')
		select('RecordSelectionJTbl1', 'fld 21', 'Field,0')
		select('RecordSelectionJTbl1', 'fld 22', 'Field,1')
		select('RecordSelectionJTbl1', '21', 'Field Value,0')
		select('RecordSelectionJTbl1', '22', 'Field Value,1')
		select('RecordSelectionJTbl1', 'cell:Field Value,0(21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 22, =, 22]]')
		select('RecordSelectionJTbl1', 'cell:Field Value,0(21)')

		

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		select('ChildRecordsJTbl', 'ggg', 'Field,1')
		select('ChildRecordsJTbl', 'vv2', 'Field Value,1')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('Edit Record Selections')
##		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')

		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 22, =, 22]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		rightclick('ChildRecordsJTbl', 'Field,2')
		select_menu('Edit Record Selections')
##		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')

		click('Insert2')
##		click('Insert')
		select('RecordSelectionJTbl2', 'cell:Field,0()')
		select('RecordSelectionJTbl2', 'fld 32', 'Field,0')
		select('RecordSelectionJTbl2', '32', 'Field Value,0')
		select('RecordSelectionJTbl2', 'cell:or,0()')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 32]]')
		select('RecordSelectionJTbl2', 'cell:or,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('View Record Selections Tree')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv1], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 11a], [, ,   ,   , And  , fld 12, =, 12], [, ,   ,   ,   , ggg, =, vv2], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   , And  , fld 22, =, 22], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 32, =, 32]]')

		select('JTreeTable', 'cell:Boolean op 2  ,1(  )')

		click('BasicInternalFrameTitlePane$NoFocusButton2')






		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		select('ChildRecordsJTbl', 'cell:Field,1(ggg)')
		click('Delete3')

		if window('Delete: zxxxzFLDg4'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

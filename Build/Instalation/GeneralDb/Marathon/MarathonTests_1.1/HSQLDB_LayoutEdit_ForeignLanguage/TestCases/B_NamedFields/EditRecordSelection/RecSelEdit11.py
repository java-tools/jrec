useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%')

		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg88')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg88')

		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', 'fld 12', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', '12', commonBits.fl('Field Value') + ',1')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zxxxzFLD1')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		click(commonBits.fl('Insert'))


##		select('RecordSelectionJTbl', '')
		rightclick('RecordSelectionJTbl', 'and,2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',2()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '121', commonBits.fl('Field Value') + ',2')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zxxxzFLD2')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0(fld 21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21]]')
		select('TabbedPane', 'zxxxzFLD1')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 121]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zxxxzFLD3')
		click(commonBits.fl('Insert') + '2')
		click(commonBits.fl('Insert') + '2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl2', 'fld 31', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl2', '31', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl2', 'fld 32', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl2', '32', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('RecordSelectionJTbl2', 'and,1')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',0(31)')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 32, =, 32]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'f1', commonBits.fl('Field') + ',0')
		select('ChildRecordsJTbl', 'v1', commonBits.fl('Field Value') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',1')
		select_menu(commonBits.fl('View Record Selections Tree'))
##		select('TabbedPane1', 'Summary')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field Value') + ',1()')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

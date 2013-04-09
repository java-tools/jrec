useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxxxzFLDg65')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))
		select('RecordDef.System_Txt', 'Unkown')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Child Records')
##		click('ScrollPane$ScrollBar', 5, 43)
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zxxxzFLD3', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'zxxxzFLD3', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Tree Parent') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',1(zxxxzFLD1)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


##		select('RecordSelectionJTbl', '')
		rightclick('RecordSelectionJTbl', 'and,1')
##		select('RecordSelectionJTbl', '')
		rightclick('RecordSelectionJTbl', 'and,2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', 'fld 12', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', 'fld 13', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', '12', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', '13', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 12, =, 12], [Or, , fld 13, =, 13]]')
		select('TabbedPane', 'zxxxzFLD2')
		click(commonBits.fl('Insert') + '1')
		click(commonBits.fl('Insert') + '1')
###		select('RecordSelectionJTbl1', '')
		rightclick('RecordSelectionJTbl1', 'and,1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', '12', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0(21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zxxxzFLD3')
		click(commonBits.fl('Insert') + '2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl2', 'fld 31', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl2', '31', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0(fld 31)')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', commonBits.fl('Summary'))
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 2') + '    ,3(Or  )')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 2') + '    ,3(Or  )')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31]]')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 2') + '    ,3(Or  )')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg654')
			click('OK')
		close()

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 12, =, 12], [Or, , fld 13, =, 13]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 12, =, 12], [Or, , fld 13, =, 13]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',2()')
		select('RecordSelectionJTbl', 'fld 12', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '21', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',2()')
		rightclick('RecordSelectionJTbl', 'and,2')
		select('TabbedPane', 'zxxxzFLD3')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',0(31)')
		click(commonBits.fl('Insert') + '2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',1()')
		select('RecordSelectionJTbl2', 'fld 31', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl2', '13', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('RecordSelectionJTbl2', 'and,1')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 12, =, 21], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 31, =, 13]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

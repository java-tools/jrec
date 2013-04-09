useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zx3g3xzFLDg543')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxxxzFLD2)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',0()')
		rightclick('RecordSelectionJTbl', 'and,1')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '121', commonBits.fl('Field Value') + ',1')
		select('TabbedPane', 'zxxxzFLD2')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 23', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', '23', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', 'cell:or,0()')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 121]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		select('JTreeTable', 'fff', commonBits.fl('Field Name') + '    ,0')
		select('JTreeTable', 'vvv', commonBits.fl('Test Value') + '    ,0')
		select('JTreeTable', 'cell:' + commonBits.fl('Test Value') + '    ,1(11)')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		select('JTreeTable', 'cell:' + commonBits.fl('Test Value') + '    ,1(11)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , fff, vvv, , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD2, , , , , ]]')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zx3g3xzFLDg5432')
			click('OK')
		close()


##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , fff, vvv, , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg543')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg543')
		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , fff, vvv, , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg5432')
		select('RecordList.Description_Txt', '%%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

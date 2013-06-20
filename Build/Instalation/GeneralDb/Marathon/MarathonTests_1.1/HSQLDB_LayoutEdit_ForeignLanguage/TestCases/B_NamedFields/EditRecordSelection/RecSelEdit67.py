useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg543')
		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , fff, vvv, , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg5432')
		select('RecordList.Description_Txt', '%%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 23, =, 23]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 23, =, 23], [, ,   ,   ,   , , =, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)
##
##  Depends on 31 running
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg65')
		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 12, =, 12], [' + commonBits.fl('Or') + ', , fld 13, =, 13]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [' + commonBits.fl('Or') + ', , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', commonBits.fl('Summary'))
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 12], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		select('JTreeTable', '1199', commonBits.fl('Test Value') + '    ,1')
		select('JTreeTable', '1288', commonBits.fl('Test Value') + '    ,2')
		select('JTreeTable', '1377', commonBits.fl('Test Value') + '    ,3')
		select('JTreeTable', 'cell:' + commonBits.fl('Test Value') + '    ,2(1288)')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 1199], [, ,   , Or  ,   , fld 12, =, 1288], [, ,   , Or  ,   , fld 13, =, 1377], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 1199], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 1288], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 13, =, 1377], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 1199], [' + commonBits.fl('Or') + ', , fld 12, =, 1288], [' + commonBits.fl('Or') + ', , fld 13, =, 1377]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [' + commonBits.fl('Or') + ', , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', commonBits.fl('Summary'))
		select('JTreeTable', 'fld 13', commonBits.fl('Field Name') + '    ,1')
		select('JTreeTable', 'fld 11', commonBits.fl('Field Name') + '    ,2')
		select('JTreeTable', 'fld 12', commonBits.fl('Field Name') + '    ,3')
		select('JTreeTable', '31987', commonBits.fl('Test Value') + '    ,8')
		select('JTreeTable', 'fld 32', commonBits.fl('Field Name') + '    ,8')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,5(  )')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 13, =, 1199], [, ,   , Or  ,   , fld 11, =, 1288], [, ,   , Or  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 32, =, 31987]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 13, =, 1199], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 1288], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 32, =, 31987]]')

		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 31987]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [' + commonBits.fl('Or') + ', , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 13, =, 1199], [' + commonBits.fl('Or') + ', , fld 11, =, 1288], [' + commonBits.fl('Or') + ', , fld 12, =, 1377]]')
		select('TabbedPane', commonBits.fl('Summary'))
		select('JTreeTable', 'ff', commonBits.fl('Field Name') + '    ,0')
		select('JTreeTable', 'vv1', commonBits.fl('Test Value') + '    ,0')
		select('JTreeTable', 'gg', commonBits.fl('Field Name') + '    ,7')
		select('JTreeTable', 'vv2', commonBits.fl('Test Value') + '    ,7')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,5(  )')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff, =, vv1], [, , And  ,   ,   , fld 13, =, 1199], [, ,   , Or  ,   , fld 11, =, 1288], [, ,   , Or  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , gg, =, vv2], [, , And  ,   ,   , fld 32, =, 31987]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff, =, vv1], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 13, =, 1199], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 1288], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 12], [, ,   ,   ,   , gg, =, vv2], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 32, =, 31987]]')

		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,5(  )')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , ff, vv1, , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , gg, vv2, , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

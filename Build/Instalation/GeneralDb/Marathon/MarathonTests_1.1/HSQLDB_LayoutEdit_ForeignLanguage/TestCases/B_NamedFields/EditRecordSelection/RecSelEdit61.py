useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))

		select('RecordDef.Record Name_Txt', 'zx3e3xzFLDg543')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('RecordDef.System_Txt', 'Unkown')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zxxxzFLD3', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxxxzFLD3)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


#		select('RecordSelectionJTbl', 'And')
		rightclick('RecordSelectionJTbl', 'and,1')
#		select('RecordSelectionJTbl', 'And')
		rightclick('RecordSelectionJTbl', 'and,2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '123', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', '321', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', '121', commonBits.fl('Field Value') + ',2')
		select('TabbedPane', 'zxxxzFLD2')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0()')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',1()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl1', '22', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('RecordSelectionJTbl1', 'and,1')
		select('TabbedPane', 'zxxxzFLD3')
		click(commonBits.fl('Insert') + '2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl2', 'fld 31', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl2', '31', commonBits.fl('Field Value') + ',0')
		select('TabbedPane', commonBits.fl('Summary'))
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,2(  )')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,2(  )')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 123], [, ,   , Or  ,   , fld 11, =, 321], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 22], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 123], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 321], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 22], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		select('JTreeTable', '3219', commonBits.fl('Test Value') + '    ,2')
		select('JTreeTable', '12399', commonBits.fl('Test Value') + '    ,1')
		select('JTreeTable', '121876', commonBits.fl('Test Value') + '    ,3')
		select('JTreeTable', 'fld 12', commonBits.fl('Field Name') + '    ,2')
		select('JTreeTable', 'fld 22', commonBits.fl('Field Name') + '    ,6')
		select('JTreeTable', '219', commonBits.fl('Test Value') + '    ,5')
		select('JTreeTable', 'ff', commonBits.fl('Field Name') + '    ,4')
		select('JTreeTable', 'vv', commonBits.fl('Test Value') + '    ,4')
		select('JTreeTable', '99', commonBits.fl('Field Name') + '    ,7')
		select('JTreeTable', '99', commonBits.fl('Test Value') + '    ,7')
		select('JTreeTable', 'cell:' + commonBits.fl('Test Value') + '    ,6(22)')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 12399], [, ,   , Or  ,   , fld 12, =, 3219], [, ,   , Or  ,   , fld 11, =, 121876], [, ,   ,   ,   , ff, =, vv], [, , And  ,   ,   , fld 21, =, 219], [, ,   , Or  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 12399], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 3219], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121876], [, ,   ,   ,   , ff, =, vv], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 219], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		select('TabbedPane', 'zxxxzFLD3')
		assert_p('TabbedPane', 'Content', '[[zxxxzFLD1, zxxxzFLD2, zxxxzFLD3, ' + commonBits.fl('Summary') + ']]')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 219], [' + commonBits.fl('Or') + ', , fld 22, =, 22]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 12399], [' + commonBits.fl('Or') + ', , fld 12, =, 3219], [' + commonBits.fl('Or') + ', , fld 11, =, 121876]]')
		select('RecordSelectionJTbl', '32197', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', '1218765', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'fld 13', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',2(fld 13)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 12399], [' + commonBits.fl('Or') + ', , fld 12, =, 32197], [' + commonBits.fl('Or') + ', , fld 13, =, 1218765]]')
		select('TabbedPane', commonBits.fl('Summary'))
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 12399], [, ,   , Or  ,   , fld 12, =, 32197], [, ,   , Or  ,   , fld 13, =, 1218765], [, ,   ,   ,   , ff, =, vv], [, , And  ,   ,   , fld 21, =, 219], [, ,   , Or  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 12399], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 32197], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 13, =, 1218765], [, ,   ,   ,   , ff, =, vv], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 219], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , ff, vv, , ], [, zxxxzFLD3, , 99, 99, , ]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg543')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , ff, vv, , ], [, zxxxzFLD3, , 99, 99, , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu(commonBits.fl('View Record Selections Tree'))
##		select('TabbedPane1', 'Summary')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 12399], [, ,   , Or  ,   , fld 12, =, 32197], [, ,   , Or  ,   , fld 13, =, 1218765], [, ,   ,   ,   , ff, =, vv], [, , And  ,   ,   , fld 21, =, 219], [, ,   , Or  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , And  ,   ,   , fld 31, =, 31]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 12399], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 12, =, 32197], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 13, =, 1218765], [, ,   ,   ,   , ff, =, vv], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 219], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31]]')

		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 219], [' + commonBits.fl('Or') + ', , fld 22, =, 22]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 12399], [' + commonBits.fl('Or') + ', , fld 12, =, 32197], [' + commonBits.fl('Or') + ', , fld 13, =, 1218765]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

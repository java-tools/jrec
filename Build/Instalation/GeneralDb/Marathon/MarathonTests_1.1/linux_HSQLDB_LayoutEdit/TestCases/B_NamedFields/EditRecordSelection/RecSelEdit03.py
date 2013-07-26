useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%')

		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg4')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg1%')

		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zxxxzFLDg4')

		select('RecordList.Description_Txt', '%')

#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		select('Lines to Insert_Txt', '3')
		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',0()')
		rightclick('RecordSelectionJTbl', 'and,1')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '11a', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', 'fld 12', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '12', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(11a)')
##		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12]]')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 11, =, 11a], [, ' + commonBits.fl('And') + ', fld 12, =, 12]]')

		
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(11a)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('ChildRecordsJTbl', 'fff', commonBits.fl('Field') + ',0')
		select('ChildRecordsJTbl', 'vvv1', commonBits.fl('Field Value') + ',0')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',1')
		select_menu( commonBits.fl('Edit Record Selections'))


##		select('ChildRecordsJTbl', 'cell:rs,0(null)')
##		select('Lines to Insert_Txt', '2')
		select('Lines to Insert_Txt1', '2')
		click(commonBits.fl('Insert') + '1')
##		click(commonBits.fl('Insert'))




		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', 'fld 22', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', '22', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0(21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, ' + commonBits.fl('And') + ', fld 22, =, 22]]')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0(21)')

		

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		select('ChildRecordsJTbl', 'ggg', commonBits.fl('Field') + ',1')
		select('ChildRecordsJTbl', 'vv2', commonBits.fl('Field Value') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',1')
		select_menu( commonBits.fl('Edit Record Selections'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')

		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, ' + commonBits.fl('And') + ', fld 22, =, 22]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',2')
		select_menu( commonBits.fl('Edit Record Selections'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')

		click(commonBits.fl('Insert') + '2')
##		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl2', 'fld 32', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl2', '32', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl2', 'cell:or,0()')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 32]]')
		select('RecordSelectionJTbl2', 'cell:or,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',1')
		select_menu(commonBits.fl('View Record Selections Tree'))
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
##		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv1], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 11a], [, ,   ,   , And  , fld 12, =, 12], [, ,   ,   ,   , ggg, =, vv2], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   , And  , fld 22, =, 22], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 32, =, 32]]')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , fff, =, vvv1], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 11a], [, ,   ,   , ' + commonBits.fl('And') + '  , fld 12, =, 12], [, ,   ,   ,   , ggg, =, vv2], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   ,   , ' + commonBits.fl('And') + '  , fld 22, =, 22], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 32, =, 32]]')

		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 2') + '    ,1(  )')

		click('BasicInternalFrameTitlePane$NoFocusButton2')






		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ggg)')
		commonBits.delete3(click)
		if window( commonBits.fl('Delete: zxxxzFLDg4')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

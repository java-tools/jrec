useFixture(default)

def test():
	from Modules import commonBits
	import time
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


		commonBits.save1(click)
		select('ChildRecordsJTbl', 'ff1', commonBits.fl('Field') + ',0')
		select('ChildRecordsJTbl', 'ff2', commonBits.fl('Field') + ',1')
		select('ChildRecordsJTbl', 'ff33', commonBits.fl('Field') + ',2')
		select('ChildRecordsJTbl', 'v11', commonBits.fl('Field Value') + ',0')
		select('ChildRecordsJTbl', 'v22', commonBits.fl('Field Value') + ',1')
		select('ChildRecordsJTbl', 'v333', commonBits.fl('Field Value') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ff2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , ff1, v11, , ], [, zxxxzFLD2, , ff2, v22, , ], [, zxxxzFLD3, , ff33, v333, , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu(commonBits.fl('View Record Selections Tree'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')

		time.sleep(0.9)	
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff1, =, v11], [, ,   ,   ,   , ff2, =, v22], [, ,   ,   ,   , ff33, =, v333]]')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 3') + '    ,1(  )')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '11a', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('RecordSelectionJTbl', 'and,1')
		select('RecordSelectionJTbl', 'fld 13', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '33', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(11a)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 11a], [, And, fld 13, =, 33]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(11a)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ff2)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',1')
		select_menu( commonBits.fl('Edit Record Selections'))
####		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ff2)')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0(fld 21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21]]')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0(fld 21)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1(ff2)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',2(ff33)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',2')
		select_menu( commonBits.fl('Edit Record Selections'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',2(ff33)')
		click(commonBits.fl('Insert') + '2')
		click(commonBits.fl('Insert') + '2')
		click(commonBits.fl('Insert') + '2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl2', 'fld 31', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl2', '31', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl2', 'fld 32', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl2', '32', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl2', 'fld 32', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl2', '32', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',1(32)')
		rightclick('RecordSelectionJTbl2', 'and,1')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 32, =, 32], [, And, fld 32, =, 32]]')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',1(32)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',2(ff33)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu(commonBits.fl('View Record Selections Tree'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff1, =, v11], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 11a], [, ,   ,   , And  , fld 13, =, 33], [, ,   ,   ,   , ff2, =, v22], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , ff33, =, v333], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32], [, ,   ,   , And  , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg4')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

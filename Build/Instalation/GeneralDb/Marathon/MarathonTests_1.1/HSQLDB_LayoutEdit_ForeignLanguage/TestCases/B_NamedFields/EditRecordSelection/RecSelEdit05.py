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
			select('OptionPane.textField', 'zxxxzFLDg6')
			click('OK')
		close()

		commonBits.save1(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxxxzFLDg6')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu(commonBits.fl('View Record Selections Tree'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 1') + '    ,1(  )')
		rightclick('JTreeTable', 's,1')
		select('Lines to Insert_Txt', '3')
		click(commonBits.fl('Insert'))


		time.sleep(0.9)	
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', '21', commonBits.fl('Field Value') + ',0')
		time.sleep(0.9)	
		select('RecordSelectionJTbl', 'fld 21', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '21aa', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', 'fld 22', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '22', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',2()')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 1') + '    ,1(  )')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 1') + '    ,1(  )')
##		click('MetalInternalFrameTitlePane', 1416, 0)
##		select_menu('Window>>Menu1>>Record Selection Tree')
		select('JTreeTable', 'cell:' + commonBits.fl('Boolean op 2') + '    ,1(  )')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [, And, fld 21, =, 21aa], [Or, , fld 22, =, 22]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg6')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

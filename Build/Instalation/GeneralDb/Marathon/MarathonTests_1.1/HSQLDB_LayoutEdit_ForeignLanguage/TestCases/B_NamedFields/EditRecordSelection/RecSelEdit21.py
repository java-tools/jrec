useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxxxzFLDg56')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records')
)

		select('RecordDef.System_Txt', 'Unkown')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zxxxzFLD2)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',0()')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
##		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',0()')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '22', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1()')
		rightclick('RecordSelectionJTbl', 'and,1')
		click(commonBits.fl('Insert'))


		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',2()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',2')
		select('RecordSelectionJTbl', '33', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(22)')
		rightclick('RecordSelectionJTbl', 'and,2')
		
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 11, =, 22], [' + commonBits.fl('Or') + ', , fld 11, =, 33]]')
		select('TabbedPane', 'zxxxzFLD2')
		click(commonBits.fl('Insert') + '1')
		click(commonBits.fl('Insert') + '1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl1', '21', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0()')
		rightclick('RecordSelectionJTbl1', 'and,1')
		select('RecordSelectionJTbl1', 'fld 21', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl1', '12', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl1', 'cell:' + commonBits.fl('Field Value') + ',0(21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [' + commonBits.fl('Or') + ', , fld 21, =, 12]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 22], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 33], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 21], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 21, =, 12]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg567')
			click('OK')
		close()


		##select('TabbedPane', 'Child Records')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

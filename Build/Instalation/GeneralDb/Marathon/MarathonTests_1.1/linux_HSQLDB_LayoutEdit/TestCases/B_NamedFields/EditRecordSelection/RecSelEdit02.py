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
		select('Lines to Insert_Txt', '2')
		click(commonBits.fl('Insert'))


		select('Lines to Insert_Txt', '')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		select('RecordSelectionJTbl', 'fld 11', commonBits.fl('Field') + ',0')
		select('RecordSelectionJTbl', 'fld 12', commonBits.fl('Field') + ',1')
		select('RecordSelectionJTbl', '11', commonBits.fl('Field Value') + ',0')
		select('RecordSelectionJTbl', '12', commonBits.fl('Field Value') + ',1')
		select('RecordSelectionJTbl', 'rows:[0,1],columns:[' + commonBits.fl('Field Value') + ']')
		commonBits.copy2(click)
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',1(12)')
		commonBits.paste2(click)
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',3(12)')
		commonBits.paste2(click)
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',5(12)')
		commonBits.paste2(click)
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12]]')
		select('RecordSelectionJTbl', '11a', commonBits.fl('Field Value') + ',2')
		select('RecordSelectionJTbl', '12a', commonBits.fl('Field Value') + ',3')
		select('RecordSelectionJTbl', '11b', commonBits.fl('Field Value') + ',4')
		select('RecordSelectionJTbl', '12b', commonBits.fl('Field Value') + ',5')
		select('RecordSelectionJTbl', '11c', commonBits.fl('Field Value') + ',6')
		select('RecordSelectionJTbl', '12c', commonBits.fl('Field Value') + ',7')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [, And, fld 11, =, 11b], [, And, fld 12, =, 12b], [, And, fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'or,6')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [, And, fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'or,4')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:' + commonBits.fl('Field Value') + ',6(11c)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zxxxzFLDg4')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg4')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

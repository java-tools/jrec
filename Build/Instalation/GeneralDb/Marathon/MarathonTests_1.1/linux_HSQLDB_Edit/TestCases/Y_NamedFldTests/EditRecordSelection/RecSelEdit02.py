useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('RecordList.Description_Txt', '%')

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zx33xzFLDg4')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg1%')

		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zx33xzFLDg4')

		select('RecordList.Description_Txt', '%')

#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		select('Lines to Insert_Txt', '2')
		click('Insert')
		select('Lines to Insert_Txt', '')
		select('RecordSelectionJTbl', 'cell:Field,0()')
		select('RecordSelectionJTbl', 'fld 11', 'Field,0')
		select('RecordSelectionJTbl', 'fld 12', 'Field,1')
		select('RecordSelectionJTbl', '11', 'Field Value,0')
		select('RecordSelectionJTbl', '12', 'Field Value,1')
		select('RecordSelectionJTbl', 'rows:[0,1],columns:[Field Value]')
		click('Copy2')
		select('RecordSelectionJTbl', 'cell:Field Value,1(12)')
		click('Paste2')
		select('RecordSelectionJTbl', 'cell:Field Value,3(12)')
		click('Paste2')
		select('RecordSelectionJTbl', 'cell:Field Value,5(12)')
		click('Paste2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11], [, And, fld 12, =, 12]]')
		select('RecordSelectionJTbl', '11a', 'Field Value,2')
		select('RecordSelectionJTbl', '12a', 'Field Value,3')
		select('RecordSelectionJTbl', '11b', 'Field Value,4')
		select('RecordSelectionJTbl', '12b', 'Field Value,5')
		select('RecordSelectionJTbl', '11c', 'Field Value,6')
		select('RecordSelectionJTbl', '12c', 'Field Value,7')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [, And, fld 11, =, 11b], [, And, fld 12, =, 12b], [, And, fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'or,6')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [, And, fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'or,4')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'or,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')
		rightclick('RecordSelectionJTbl', 'and,2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		select('RecordSelectionJTbl', 'cell:Field Value,6(11c)')

		

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zx33xzFLDg4')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [, And, fld 11, =, 11a], [, And, fld 12, =, 12a], [Or, , fld 11, =, 11b], [, And, fld 12, =, 12b], [Or, , fld 11, =, 11c], [, And, fld 12, =, 12c]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Delete3')

		if window('Delete: zx33xzFLDg4'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

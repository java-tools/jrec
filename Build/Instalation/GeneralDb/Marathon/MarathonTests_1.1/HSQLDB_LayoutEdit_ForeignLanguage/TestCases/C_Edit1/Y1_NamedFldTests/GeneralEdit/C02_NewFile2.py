useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('Record Layout_Txt', 'DTAR020')
		assert_p('Record Layout_Txt', 'Text', 'DTAR020')
		select('File_Txt', commonBits.sampleDir() + 'xxxcvxx')
		assert_p('Record Layout_Txt', 'Text', 'DTAR020')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		select('RecordDef.Record Name_Txt', 'xxxcvxx')
		click(commonBits.fl('Insert'))
		click(commonBits.fl('Insert'))
		click(commonBits.fl('Insert'))
		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '11', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', '12', commonBits.fl('Position') + ',1')
		select('RecordFieldsJTbl', '11', commonBits.fl('Length') + ',1')
		select('RecordFieldsJTbl', '23', commonBits.fl('Position') + ',2')
		select('RecordFieldsJTbl', '33', commonBits.fl('Length') + ',2')
		select('RecordFieldsJTbl', 'f1', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'f2', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'f3', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 11, f1, , 0, 0, 0, , , ], [12, 11, f2, , 0, 0, 0, , , ], [23, 33, f3, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Record Layout_Txt', 'Text', 'xxxcvxx')
		select('File_Txt', commonBits.sampleDir() + 'xxxcvxxx')
		assert_p('Record Layout_Txt', 'Text', 'xxxcvxx')
		click(commonBits.fl('Edit') + '1')
		select('LineFrame.FileDisplay_JTbl', '11', commonBits.fl('Data') + ',0')
		select('LineFrame.FileDisplay_JTbl', '22', commonBits.fl('Data') + ',1')
		select('LineFrame.FileDisplay_JTbl', '33', commonBits.fl('Data') + ',2')
		select('LineFrame.FileDisplay_JTbl', 'cell:' + commonBits.fl('Data') + ',1(22)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[f1, 1, 11, 11, 11], [f2, 12, 11, 22, 22], [f3, 23, 33, 33, 33]]')
		select('LineFrame.FileDisplay_JTbl', 'cell:' + commonBits.fl('Data') + ',1(22)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[11, 22, 33]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window(commonBits.fl('Save Changes to file: ' + commonBits.sampleDir() + 'xxxcvxxx')):
			click('Yes')
		close()

		click(commonBits.fl('Edit') + '1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[11, 22, 33]]')
		click('Delete2')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'xxxcvxx')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 11, f1, , 0, 0, 0, , , ], [12, 11, f2, , 0, 0, 0, , , ], [23, 33, f3, , 0, 0, 0, , , ]]')
		##click('Delete3')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: xxxcvxx')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

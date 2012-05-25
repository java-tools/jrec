useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('Record Layout_Txt', 'DTAR020')
		assert_p('Record Layout_Txt', 'Text', 'DTAR020')
		select('File_Txt', commonBits.sampleDir() + 'xxxcvxx')
		assert_p('Record Layout_Txt', 'Text', 'DTAR020')
		select_menu('Record Layouts>>Create Layout')
		select('RecordDef.Record Name_Txt', 'xxxcvxx')
		click('Insert')
		click('Insert')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '11', 'Length,0')
		select('RecordFieldsJTbl', '12', 'Position,1')
		select('RecordFieldsJTbl', '11', 'Length,1')
		select('RecordFieldsJTbl', '23', 'Position,2')
		select('RecordFieldsJTbl', '33', 'Length,2')
		select('RecordFieldsJTbl', 'f1', 'FieldName,0')
		select('RecordFieldsJTbl', 'f2', 'FieldName,1')
		select('RecordFieldsJTbl', 'f3', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 11, f1, , 0, 0, 0, , , ], [12, 11, f2, , 0, 0, 0, , , ], [23, 33, f3, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Record Layout_Txt', 'Text', 'xxxcvxx')
		select('File_Txt', commonBits.sampleDir() + 'xxxcvxxx')
		assert_p('Record Layout_Txt', 'Text', 'xxxcvxx')
		click('Edit1')
		select('LineFrame.FileDisplay_JTbl', '11', 'Data,0')
		select('LineFrame.FileDisplay_JTbl', '22', 'Data,1')
		select('LineFrame.FileDisplay_JTbl', '33', 'Data,2')
		select('LineFrame.FileDisplay_JTbl', 'cell:Data,1(22)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[f1, 1, 11, 11, 11], [f2, 12, 11, 22, 22], [f3, 23, 33, 33, 33]]')
		select('LineFrame.FileDisplay_JTbl', 'cell:Data,1(22)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[11, 22, 33]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'xxxcvxxx'):
			click('Yes')
		close()

		click('Edit1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[11, 22, 33]]')
		click('Delete2')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'xxxcvxx')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 11, f1, , 0, 0, 0, , , ], [12, 11, f2, , 0, 0, 0, , , ], [23, 33, f3, , 0, 0, 0, , , ]]')
		click('Delete3')

		if window('Delete: xxxcvxx'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*6')
		select('Cobol Copybook_Txt', commonBits.cobolDir() + 'ZZ_Tst5.cbl')
		select('System_Txt', 'Other')
		click('Go')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'ZZ_Tst5%')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 5, f01, , 22, 2, 0, , , f01], [7, 3, f02, , 33, 2, 0, , , f02], [11, 4, f03, , 23, 2, 0, , , f03], [16, 4, f04, , 23, 2, 0, , , f04], [20, 8, f05, , 25, 0, 0, , , f05], [20, 4, yyyy, , 25, 0, 0, , , yyyy], [24, 2, MM, , 25, 0, 0, , , MM], [26, 2, DD, , 25, 0, 0, , , DD], [28, 10, , , 0, 0, 0, , , ]]')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

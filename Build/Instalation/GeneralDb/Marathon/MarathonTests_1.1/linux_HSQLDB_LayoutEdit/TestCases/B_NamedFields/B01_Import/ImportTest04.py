useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*6')
		select('Cobol Copybook_Txt', commonBits.cobolDir() + 'ZZ_Tst4.cbl')
		select('System_Txt', 'Other')
		click('Go')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'ZZ_Tst4%')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 5, f01, , 22, 2, 0, , , f01], [6, 3, f02, , 33, 2, 0, , , f02], [9, 4, f03, , 23, 2, 0, , , f03], [13, 4, f04, , 23, 2, 0, , , f04], [17, 7, f05, , 25, 2, 0, , , f05], [24, 8, f06, , 6, 2, 0, , , f06]]')
	close()

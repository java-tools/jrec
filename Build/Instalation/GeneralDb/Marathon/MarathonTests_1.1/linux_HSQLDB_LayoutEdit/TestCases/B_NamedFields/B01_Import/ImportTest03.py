useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*6')
		select('Cobol Copybook_Txt', commonBits.cobolDir() + 'ZZ_Tst3.cbl')
		select('System_Txt', 'Other')
		click('Go')
		assert_p('TextArea', 'Text', r'''

-->> ''' + commonBits.cobolDir() + '''ZZ_Tst3.cbl processed

      Copybook: ZZ_Tst3''')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'ZZ_Tst3%')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 7, f01, , 6, 0, 0, , , f01], [8, 7, f02, , 22, 0, 0, , , f02], [15, 7, f03, , 6, 0, 0, , , f03], [22, 6, f05, , 6, 0, 0, , , f05], [28, 6, f06, , 22, 0, 0, , , f06], [34, 6, f07, , 6, 0, 0, , , f07], [40, 6, f08, , 22, 0, 0, , , f08], [46, 6, f21, , 6, 0, 0, , , f21], [52, 7, f23, , 6, 0, 0, , , f23], [59, 6, f25, , 6, 0, 0, , , f25], [65, 6, f26, , 6, 0, 0, , , f26], [71, 8, f31, , 6, 2, 0, , , f31], [79, 8, f31, , 6, 2, 0, , , f31], [87, 8, f33, , 6, 2, 0, , , f33], [95, 9, f34, , 6, 2, 0, , , f34], [104, 9, f35, , 6, 2, 0, , , f35], [113, 9, f36, , 6, 2, 0, , , f36], [122, 6, f37, , 41, 2, 0, , , f37], [128, 6, f38, , 22, 2, 0, , , f38], [134, 8, f41, , 0, 0, 0, , , f41], [142, 8, f42, , 0, 0, 0, , , f42], [150, 8, f43, , 0, 0, 0, , , f43], [158, 8, f44, , 0, 0, 0, , , f44]]')
	close()

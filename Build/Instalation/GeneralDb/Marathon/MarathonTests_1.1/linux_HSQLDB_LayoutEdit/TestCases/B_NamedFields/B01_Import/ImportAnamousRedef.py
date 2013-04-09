useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Load Cobol Copybook')
		select('Cobol Copybook_Txt', commonBits.cobolDir() + 'Z_AnamousRedf.cbl')
		select('Binary Format_Txt', 'Open Cobol Little Endian (Intel)')
		select('System_Txt', 'Other')
		click('Go')
		assert_p('TextArea', 'Text', r'''

-->> ''' + commonBits.cobolDir() + '''Z_AnamousRedf.cbl processed

      Copybook: Z_AnamousRedf''')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'Z_%')
		select('RecordList.Description_Txt', '%')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Fields')
		assert_p('RecordList.RecordListTbl_JTbl', 'Text', 'Z_AnamousRedf', 'Record Name,0')
		assert_p('RecordList.RecordListTbl_JTbl', 'Content', '[[Z_AnamousRedf, ]]')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, AB, , 0, 0, 0, , , AB], [1, 1, A, , 0, 0, 0, , , A], [2, 1, B, , 0, 0, 0, , , B]]')

	close()

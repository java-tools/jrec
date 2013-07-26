useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20050101.txt')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Table', 'false', commonBits.fl('Include') + ',0')
		select('Table', 'false', commonBits.fl('Include') + ',2')
		select('Table', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')
		select('Table1', 'false', commonBits.fl('Include') + ',6')
		select('Table1', 'false', commonBits.fl('Include') + ',7')
		select('Table1', 'false', commonBits.fl('Include') + ',1')
		select('Table1', 'false', commonBits.fl('Include') + ',0')
		select('Table1', 'false', commonBits.fl('Include') + ',5')
		#select('Table1', 'cell:' + commonBits.fl('Include') + ',5(false)')
		commonBits.filter(click)
		select('Table', 'cell:30 - 6|Entry Date,4(040929)')
		assert_p('Table', 'Text', '040929', '30 - 6|Entry Date,6')
		select('Table', 'cell:48 - 4|Department,6(200)')
		assert_p('Table', 'Content', '[[4338, 233863, 040929, 290, 050103, 050107, , , , OPTIONS PL, C, FT], [4338, 233872, 040929, 290, 050103, 050107, , , , OPTIONS PL, C, FT], [4468, 255906, 040929, 290, 050103, 050107, , , , OPTIONS PL, C, FT], [4448, 290908, 040929, 290, 050103, 050107, , , , OPTIONS PL, C, FT], [4228, 292210, 040929, 290, 050103, 050107, , , , OPTIONS PL, C, FT], [5220, 211984, 040929, 200, 050103, 050107, , , , LADIES KNI, C, FT], [5110, 211985, 040929, 200, 050103, 050107, , , , LADIES KNI, C, FT], [5110, 211987, 040929, 200, 050103, 050106, , , , LADIES KNI, C, FT], [13112, 211549, 041005, 220, 050103, 050108, , , , LADIES KNI, C, FT], [13312, 211555, 041005, 220, 050103, 050107, , , , LADIES KNI, C, FT], [12212, 222556, 041005, 220, 050103, 050107, , , , LADIES KNI, C, FT], [1312, 211617, 041005, 220, 050103, 050117, , , , LADIES KNI, C, FT]]')
		select('Table', 'cell:52 - 6|Expected Reciept Date,8(050103)')
		assert_p('Table', 'RowCount', '12')
		select('Table', 'cell:52 - 6|Expected Reciept Date,8(050103)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table2', 'cell:' + commonBits.fl('Field') + ',0(null)')
	close()

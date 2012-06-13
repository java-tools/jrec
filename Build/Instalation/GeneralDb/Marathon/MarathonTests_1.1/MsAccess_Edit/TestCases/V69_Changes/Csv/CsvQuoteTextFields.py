useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR1000_Store_file_std.bin.csv')
		select('ComboBox1', 'CSV')
		select('ComboBox2', 'Generic CSV - enter details')
		click('Edit1')

		if window(''):
			select('BmKeyedComboBox', 'Parser - Quotes based on field Type')
			click('Go')
		close()

		select('Table', 'cell:2|REGION-NO,0(20)')
		rightclick('Table', '3|STORE-NAME,4')
##		select('Table', 'cell:2|REGION-NO,0(20)')
		select_menu('Edit Record')
##		select('Table1', 'cell:2|REGION-NO,0(20)')
		assert_p('Table', 'Content', '[[STORE-NO, 1, , 5, 5], [REGION-NO, 2, , 20, 20], [STORE-NAME, 3, , V Albury, V Albury], [NEW-STORE, 4, , N, N], [ACTIVE-STORE, 5, , Y, Y], [CLOSED-STORE, 6, , N, N], [DC-TYPE, 7, , N, N], [SRC-TYPE, 8, , N, N], [HO-TYPE, 9, , N, N]]')
		assert_p('TextArea', 'Text', '5	20	"V Albury"	"N"	"Y"	"N"	"N"	"N"	"N"')
		select('Table', 'N 1', 'Data,3')
		select('Table', 'Y 2', 'Data,4')
		select('Table', 'N 3', 'Data,5')
		select('Table', 'cell:Data,6(N)')
		assert_p('Table', 'Content', '[[STORE-NO, 1, , 5, 5], [REGION-NO, 2, , 20, 20], [STORE-NAME, 3, , V Albury, V Albury], [NEW-STORE, 4, , N 1, N 1], [ACTIVE-STORE, 5, , Y 2, Y 2], [CLOSED-STORE, 6, , N 3, N 3], [DC-TYPE, 7, , N, N], [SRC-TYPE, 8, , N, N], [HO-TYPE, 9, , N, N]]')
		select('Table', 'cell:Data,6(N)')
		assert_p('TextArea', 'Text', '5	20	"V Albury"	"N 1"	"Y 2"	"N 3"	"N"	"N"	"N"')

	close()

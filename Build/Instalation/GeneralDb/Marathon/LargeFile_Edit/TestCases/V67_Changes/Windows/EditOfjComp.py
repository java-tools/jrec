useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		if commonBits.isJRecord():
			select('FileChooser', commonBits.cobolTestDir() + 'fjComp.bin')
		else:
			select_menu('Record Layouts>>Load Cobol Copybook')
			select('FileChooser', commonBits.cobolTestDir() + 'fjComp.cbl')
			select('ComputerOptionCombo', 'Fujitsu')
			select('BmKeyedComboBox1', 'Other')
##			select('BmKeyedComboBox1', '9')
			click('Go')
			click('BasicInternalFrameTitlePane$NoFocusButton2')
			click('Open')
			select('FileChooser', commonBits.cobolTestDir() + 'fjComp.bin')
			click('Open')
			select_menu('Record Layouts>>Load Cobol Copybook')
			select('FileChooser', commonBits.cobolTestDir() + 'fjComp.cbl')
			select('BmKeyedComboBox1', 'Other')
#			select('BmKeyedComboBox1', '9')
			select('ComputerOptionCombo', 'Fujitsu')
			click('Go')
			click('BasicInternalFrameTitlePane$NoFocusButton2')
			click('Open')

		commonBits.setCobolLayout(select, 'fjComp', 'Fujitsu')
		click('Edit1')
		select('Table', 'cell:30 - 2|Num1,5(-123.45)')
		assert_p('Table', 'Text', '19.63', '30 - 2|Num1,7')
		select('Table', 'cell:30 - 2|Num1,12(-63.79)')
		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [23.45, ;, 23, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, -23, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, 23.45], [123.45, ;, 23, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, -23, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, 123.45], [4567.89, ;, 67, ;, -19.63, ;, -19.63, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, -67, ;, 19.63, ;, 19.63, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, 4567.89], [34567.89, ;, 67, ;, -166.19, ;, -166.19, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, -67, ;, 166.19, ;, 166.19, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, 34567.89], [234567.89, ;, 67, ;, -50.99, ;, -50.99, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89], [-234567.89, ;, -67, ;, 50.99, ;, 50.99, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, 234567.89], [91234567.89, ;, 67, ;, -63.79, ;, -63.79, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 5335221.97], [-91234567.89, ;, -67, ;, 63.79, ;, 63.79, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, 5335221.97], [987654321.12, ;, 21, ;, -68.64, ;, -68.64, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, 87654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 188156.96], [-987654321.12, ;, -21, ;, 68.64, ;, 68.64, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, -87654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, 188156.96], [1987654321.12, ;, 21, ;, -130.08, ;, -130.08, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 11969364.96], [-1987654321.12, ;, -21, ;, 130.08, ;, 130.08, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, 11969364.96], [21987654321.12, ;, 21, ;, -48.16, ;, -48.16, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 2578234.40], [-21987654321.12, ;, -21, ;, 48.16, ;, 48.16, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, 2578234.40], [321987654321.12, ;, 21, ;, -130.08, ;, -130.08, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 6043860.00], [-321987654321.12, ;, -21, ;, 130.08, ;, 130.08, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, 6043860.00], [4321987654321.12, ;, 21, ;, -130.08, ;, -130.08, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 5014029.28], [-4321987654321.12, ;, -21, ;, 130.08, ;, 130.08, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, 5014029.28], [54321987654321.12, ;, 21, ;, 197.60, ;, 197.60, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 14388626.40], [-54321987654321.12, ;, -21, ;, -197.60, ;, -197.60, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, 14388626.40], [654321987654321.12, ;, 21, ;, 197.60, ;, 197.60, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 1965227.04], [-654321987654321.12, ;, -21, ;, -197.60, ;, -197.60, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, 1965227.04], [7654321987654321.12, ;, 21, ;, 197.60, ;, 197.60, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 7654321987654321.12, ;, 20961492.00], [-7654321987654321.12, ;, -21, ;, -197.60, ;, -197.60, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -654321987654321.12, ;, -7654321987654321.12, ;, 20961492.00], [-1.21, ;, -1, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, 1.21], [-1.22, ;, -1, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, 1.22], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [-1.24, ;, -1, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, 1.24], [-1.25, ;, -1, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, 1.25], [-1.26, ;, -1, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, 1.26], [-1.27, ;, -1, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, 1.27], [-1.28, ;, -1, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, 1.28], [-1.29, ;, -1, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, 1.29], [-1.20, ;, -1, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, 1.20]]')
		select('Table', 'cell:30 - 2|Num1,19(48.16)')
		assert_p('Table', 'RowCount', '40')
		select('Table', 'cell:30 - 2|Num1,21(130.08)')
		assert_p('Table', 'Text', '130.08', '30 - 2|Num1,21')
		select('Table', 'cell:30 - 2|Num1,22(-130.08)')
		rightclick('Table', '30 - 2|Num1,22')
		select_menu('Edit Record')
###		select('Table1', 'cell:30 - 2|Num1,22(-130.08)')
		select('Table', 'cell:Data,8(5014029.28)')
		assert_p('Table', 'Content', '''[[NumA, 1, 25, 4321987654321.12,      4,321,987,654,321.12, 2020202020342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, 21,  , 0015], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, -130.08, �0, cd30], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, -130.08, �0, cd30], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, 5014029.28, ��0, 1de2cd30], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, 5014029.28, ��0, 1de2cd30], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, 5014029.28, ��0, 1de2cd30], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, 5014029.28, ��0, 1de2cd30], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, 5014029.28, ��0, 1de2cd30], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, 87654321.12,    
u�0, 000000020a75e130], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, 987654321.12,    ���0, 00000016fee0e530], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, 1987654321.12,    .GW�0, 0000002e4757cd30], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, 21987654321.12,   ���0, 000001fff0a1ed30], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, 321987654321.12,   H���0, 00001d48dbf9cd30], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 5014029.28, ��0, 1de2cd30]]''')
		select('Table', 'cell:Data,0(4321987654321.12)')
		assert_p('Table', 'Text', '21', 'Data,2')
		select('Table', 'cell:Data,12(5014029.28)')
		assert_p('Table', 'Content', '''[[NumA, 1, 25, 4321987654321.12,      4,321,987,654,321.12, 2020202020342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, 21,  , 0015], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, -130.08, �0, cd30], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, -130.08, �0, cd30], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, 5014029.28, ��0, 1de2cd30], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, 5014029.28, ��0, 1de2cd30], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, 5014029.28, ��0, 1de2cd30], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, 5014029.28, ��0, 1de2cd30], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, 5014029.28, ��0, 1de2cd30], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, 87654321.12,    
u�0, 000000020a75e130], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, 987654321.12,    ���0, 00000016fee0e530], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, 1987654321.12,    .GW�0, 0000002e4757cd30], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, 21987654321.12,   ���0, 000001fff0a1ed30], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, 321987654321.12,   H���0, 00001d48dbf9cd30], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 5014029.28, ��0, 1de2cd30]]''')
		select('Table', 'cell:Data,12(5014029.28)')
		click('Right')
		select('Table', 'cell:Data,16(-5014029.28)')
		assert_p('Table', 'Content', '[[NumA, 1, 25, -4321987654321.12,     -4,321,987,654,321.12, 202020202d342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, -21, ��, ffeb], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, 130.08, 2�, 32d0], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, 130.08, 2�, 32d0], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, -5014029.28, �2�, e21d32d0], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, -5014029.28, �2�, e21d32d0], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, -5014029.28, �2�, e21d32d0], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, -5014029.28, �2�, e21d32d0], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, -5014029.28, �2�, e21d32d0], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, -87654321.12, �������, fffffffdf58a1ed0], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, -987654321.12, �����, ffffffe9011f1ad0], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, -1987654321.12, ���Ѹ�2�, ffffffd1b8a832d0], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, -21987654321.12, ��� ^�, fffffe000f5e12d0], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, -321987654321.12, ���$2�, ffffe2b7240632d0], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 5014029.28, ��0, 1de2cd30]]')
		select('Table', 'cell:Data,16(-5014029.28)')
		click('Right')
		select('Table', 'cell:Data,20(987654321.12)')
		assert_p('Table', 'Content', '''[[NumA, 1, 25, 54321987654321.12,     54,321,987,654,321.12, 2020202035342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, 21,  , 0015], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, 197.60, M0, 4d30], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, 197.60, M0, 4d30], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, 14388626.40, U�M0, 55c34d30], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, 14388626.40, U�M0, 55c34d30], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, 14388626.40, U�M0, 55c34d30], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, 14388626.40, U�M0, 55c34d30], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, 14388626.40, U�M0, 55c34d30], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, 87654321.12,    
u�0, 000000020a75e130], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, 987654321.12,    ���0, 00000016fee0e530], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, 1987654321.12,    .GW�0, 0000002e4757cd30], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, 21987654321.12,   ���0, 000001fff0a1ed30], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, 321987654321.12,   H���0, 00001d48dbf9cd30], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, 54321987654321.12,  L�U�M0, 00134c8e55c34d30], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, 54321987654321.12,  L�U�M0, 00134c8e55c34d30], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, 54321987654321.12,  L�U�M0, 00134c8e55c34d30], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 14388626.40, U�M0, 55c34d30]]''')
		select('Table', 'cell:Data,20(987654321.12)')
		click('Right')
		select('Table', 'cell:Data,22(-1987654321.12)')
		assert_p('Table', 'Content', '[[NumA, 1, 25, -54321987654321.12,    -54,321,987,654,321.12, 2020202d35342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, -21, ��, ffeb], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, -197.60, ��, b2d0], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, -197.60, ��, b2d0], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, -14388626.40, �<��, aa3cb2d0], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, -14388626.40, �<��, aa3cb2d0], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, -14388626.40, �<��, aa3cb2d0], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, -14388626.40, �<��, aa3cb2d0], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, -14388626.40, �<��, aa3cb2d0], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, -87654321.12, �������, fffffffdf58a1ed0], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, -987654321.12, �����, ffffffe9011f1ad0], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, -1987654321.12, ���Ѹ�2�, ffffffd1b8a832d0], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, -21987654321.12, ��� ^�, fffffe000f5e12d0], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, -321987654321.12, ���$2�, ffffe2b7240632d0], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, -54321987654321.12, ��q�<��, ffecb371aa3cb2d0], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, -54321987654321.12, ��q�<��, ffecb371aa3cb2d0], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, -54321987654321.12, ��q�<��, ffecb371aa3cb2d0], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 14388626.40, U�M0, 55c34d30]]')
		select('Table', 'cell:Data,22(-1987654321.12)')
		click('Right')
		select('Table', 'cell:Data,22(1987654321.12)')
		assert_p('Table', 'Content', '''[[NumA, 1, 25, 654321987654321.12,    654,321,987,654,321.12, 2020203635342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, 21,  , 0015], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, 197.60, M0, 4d30], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, 197.60, M0, 4d30], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, -1965227.04, �IM0, f4494d30], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, -1965227.04, �IM0, f4494d30], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, -1965227.04, �IM0, f4494d30], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, -1965227.04, �IM0, f4494d30], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, -1965227.04, �IM0, f4494d30], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, 87654321.12,    
u�0, 000000020a75e130], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, 987654321.12,    ���0, 00000016fee0e530], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, 1987654321.12,    .GW�0, 0000002e4757cd30], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, 21987654321.12,   ���0, 000001fff0a1ed30], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, 321987654321.12,   H���0, 00001d48dbf9cd30], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, 54321987654321.12,  L�U�M0, 00134c8e55c34d30], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, 654321987654321.12,  �v<�IM0, 00e8763cf4494d30], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, 654321987654321.12,  �v<�IM0, 00e8763cf4494d30], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 1965227.04, ���, 0bb6b2d0]]''')
		select('Table', 'cell:Data,22(1987654321.12)')
		click('Right')
		select('Table', 'cell:Data,22(-1987654321.12)')
		assert_p('Table', 'Content', '[[NumA, 1, 25, -654321987654321.12,   -654,321,987,654,321.12, 20202d3635342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, -21, ��, ffeb], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, -197.60, ��, b2d0], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, -197.60, ��, b2d0], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, 1965227.04, ���, 0bb6b2d0], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, 1965227.04, ���, 0bb6b2d0], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, 1965227.04, ���, 0bb6b2d0], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, 1965227.04, ���, 0bb6b2d0], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, 1965227.04, ���, 0bb6b2d0], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, -87654321.12, �������, fffffffdf58a1ed0], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, -987654321.12, �����, ffffffe9011f1ad0], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, -1987654321.12, ���Ѹ�2�, ffffffd1b8a832d0], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, -21987654321.12, ��� ^�, fffffe000f5e12d0], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, -321987654321.12, ���$2�, ffffe2b7240632d0], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, -54321987654321.12, ��q�<��, ffecb371aa3cb2d0], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, -654321987654321.12, ������, ff1789c30bb6b2d0], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, -654321987654321.12, ������, ff1789c30bb6b2d0], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 1965227.04, ���, 0bb6b2d0]]')
		select('Table', 'cell:Data,22(-1987654321.12)')
		click('Right')
		select('Table', 'cell:Data,22(1987654321.12)')
		assert_p('Table', 'Content', r'''[[NumA, 1, 25, 7654321987654321.12,  7,654,321,987,654,321.12, 20372c3635342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, 21,  , 0015], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, 197.60, M0, 4d30], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, 197.60, M0, 4d30], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, -20961492.00, �M0, 830f4d30], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, -20961492.00, �M0, 830f4d30], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, -20961492.00, �M0, 830f4d30], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, -20961492.00, �M0, 830f4d30], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, -20961492.00, �M0, 830f4d30], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, 87654321.12,    
u�0, 000000020a75e130], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, 987654321.12,    ���0, 00000016fee0e530], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, 1987654321.12,    .GW�0, 0000002e4757cd30], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, 21987654321.12,   ���0, 000001fff0a1ed30], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, 321987654321.12,   H���0, 00001d48dbf9cd30], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, 4321987654321.12,  ���0, 000189151de2cd30], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, 54321987654321.12,  L�U�M0, 00134c8e55c34d30], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, 654321987654321.12,  �v<�IM0, 00e8763cf4494d30], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, 7654321987654321.12, 
�\��M0, 0a9f5c87830f4d30], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 20961492.00, |��, 7cf0b2d0]]''')
		select('Table', 'cell:Data,22(1987654321.12)')
		click('Right')
		select('Table', 'cell:Data,22(-1987654321.12)')
		assert_p('Table', 'Content', '[[NumA, 1, 25, -7654321987654321.12, -7,654,321,987,654,321.12, 2d372c3635342c3332312c3938372c3635342c3332312e3132], [sep0, 26, 1, ;, ;, 3b], [Num0, 27, 2, -21, ��, ffeb], [sep1, 29, 1, ;, ;, 3b], [Num1, 30, 2, -197.60, ��, b2d0], [sep2, 32, 1, ;, ;, 3b], [Num2, 33, 2, -197.60, ��, b2d0], [sep3, 35, 1, ;, ;, 3b], [Num3, 36, 4, 20961492.00, |��, 7cf0b2d0], [sep4, 40, 1, ;, ;, 3b], [Num4, 41, 4, 20961492.00, |��, 7cf0b2d0], [sep5, 45, 1, ;, ;, 3b], [Num5, 46, 4, 20961492.00, |��, 7cf0b2d0], [sep6, 50, 1, ;, ;, 3b], [Num6, 51, 4, 20961492.00, |��, 7cf0b2d0], [sep7, 55, 1, ;, ;, 3b], [Num7, 56, 4, 20961492.00, |��, 7cf0b2d0], [sep8, 60, 1, ;, ;, 3b], [Num8, 61, 8, -87654321.12, �������, fffffffdf58a1ed0], [sep9, 69, 1, ;, ;, 3b], [Num9, 70, 8, -987654321.12, �����, ffffffe9011f1ad0], [sep10, 78, 1, ;, ;, 3b], [Num10, 79, 8, -1987654321.12, ���Ѹ�2�, ffffffd1b8a832d0], [sep11, 87, 1, ;, ;, 3b], [Num11, 88, 8, -21987654321.12, ��� ^�, fffffe000f5e12d0], [sep12, 96, 1, ;, ;, 3b], [Num12, 97, 8, -321987654321.12, ���$2�, ffffe2b7240632d0], [sep13, 105, 1, ;, ;, 3b], [Num13, 106, 8, -4321987654321.12, ��v��2�, fffe76eae21d32d0], [sep14, 114, 1, ;, ;, 3b], [Num14, 115, 8, -54321987654321.12, ��q�<��, ffecb371aa3cb2d0], [sep15, 123, 1, ;, ;, 3b], [Num15, 124, 8, -654321987654321.12, ������, ff1789c30bb6b2d0], [sep16, 132, 1, ;, ;, 3b], [Num16, 133, 8, -7654321987654321.12, �`�x|��, f560a3787cf0b2d0], [sep17, 141, 1, ;, ;, 3b], [Num17, 142, 4, 20961492.00, |��, 7cf0b2d0]]')
	close()

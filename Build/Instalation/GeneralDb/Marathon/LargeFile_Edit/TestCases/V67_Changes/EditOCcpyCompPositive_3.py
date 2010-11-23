useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		if commonBits.isRecordEditor():
			select_menu('Record Layouts>>Load Cobol Copybook')
			select('FileChooser', commonBits.cobolTestDir() + 'cpyCompPositive.cbl')

			select('ComputerOptionCombo', 'Open Cobol Little Endian (Intel)')
			select('BmKeyedComboBox1', 'Other')
			select('BmKeyedComboBox', 'Fixed Length Binary')
##			select('BmKeyedComboBox1', '9')
##			select('BmKeyedComboBox', '2')
			click('Go')

		click('Open')
		select('FileChooser', commonBits.cobolTestDir() + 'cpyCompPositive.bin')
		commonBits.setOpenCobolLayout(select, 'cpyCompPositive')

		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,5(-123.45)')
##		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [23.45, ;, 23, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, -23, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, 23.45], [123.45, ;, 123, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, -123, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, 123.45], [4567.89, ;, 4567, ;, -19.63, ;, -19.63, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, -4567, ;, 19.63, ;, 19.63, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, 4567.89], [34567.89, ;, -30969, ;, -166.19, ;, -166.19, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, 30969, ;, 166.19, ;, 166.19, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, 34567.89], [234567.89, ;, -27577, ;, -50.99, ;, -50.99, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89], [-234567.89, ;, 27577, ;, 50.99, ;, 50.99, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, 234567.89]]')
		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [23.45, ;, 23, ;, 3.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, 23, ;, 3.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [123.45, ;, 23, ;, 3.45, ;, 23.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, 23, ;, 3.45, ;, 23.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [4567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [34567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 34567.89], [-234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 34567.89]]')
		select('Table', 'cell:29 - 2|Num1,5(-123.45)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select_menu('Window>>cpyCompPositive.bin>>Table: ')
		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
#		select('Table2', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,4(-130.08)')
##		assert_p('Table', 'Content', '[[91234567.89, ;, 8455, ;, -63.79, ;, -63.79, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 5335221.97], [-91234567.89, ;, -8455, ;, 63.79, ;, 63.79, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, 5335221.97], [987654321.12, ;, 26801, ;, -68.64, ;, -68.64, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, -188156.96], [-987654321.12, ;, -26801, ;, 68.64, ;, 68.64, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -188156.96], [1987654321.12, ;, 12977, ;, -130.08, ;, -130.08, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 11969364.96], [-1987654321.12, ;, -12977, ;, 130.08, ;, 130.08, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, 11969364.96], [21987654321.12, ;, -1359, ;, -48.16, ;, -48.16, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, -2578234.40], [-21987654321.12, ;, 1359, ;, 48.16, ;, 48.16, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -2578234.40], [321987654321.12, ;, -19791, ;, -130.08, ;, -130.08, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, -6043860.00]]')
		assert_p('Table', 'Content', '[[91234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 1234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 34567.89], [-91234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 1234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 34567.89], [987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 54321.12], [-987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 54321.12], [1987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 54321.12], [-1987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 54321.12], [21987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 54321.12], [-21987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 54321.12], [321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 54321.12]]')
		select('Table', 'cell:29 - 2|Num1,4(-130.08)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select_menu('Window>>cpyCompPositive.bin>>Table: ')
		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,3(197.60)')
##		assert_p('Table', 'Content', '[[-321987654321.12, ;, 19791, ;, 130.08, ;, 130.08, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -6043860.00], [4321987654321.12, ;, -3407, ;, -130.08, ;, -130.08, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 5014029.28], [-4321987654321.12, ;, 3407, ;, 130.08, ;, 130.08, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, 5014029.28], [54321987654321.12, ;, 4785, ;, 197.60, ;, 197.60, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 14388626.40], [-54321987654321.12, ;, -4785, ;, -197.60, ;, -197.60, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, 14388626.40], [654321987654321.12, ;, -27983, ;, 197.60, ;, 197.60, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, -1965227.04], [-654321987654321.12, ;, 27983, ;, -197.60, ;, -197.60, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, -1965227.04], [7654321987654321.12, ;, 4785, ;, 197.60, ;, 197.60, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, -20961492.00], [-7654321987654321.12, ;, -4785, ;, -197.60, ;, -197.60, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, -20961492.00]]')
		assert_p('Table', 'Content', '[[-321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 54321.12], [4321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 54321.12], [-4321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 54321.12], [54321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321.12], [-54321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321.12], [654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 54321.12], [-654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 54321.12], [7654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 7654321987654321.12, ;, 54321.12], [-7654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 7654321987654321.12, ;, 54321.12]]')
		select('Table', 'cell:29 - 2|Num1,3(197.60)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select_menu('Window>>cpyCompPositive.bin>>Table: ')
		select('Table', 'rows:[30,31,32,33,34,35,36,37,38,39],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[30,31,32,33,34,35,36,37,38,39],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,4(-1.25)')
##		assert_p('Table', 'Content', '[[-1.21, ;, -1, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, 1.21], [-1.22, ;, -1, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, 1.22], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [-1.24, ;, -1, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, 1.24], [-1.25, ;, -1, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, 1.25], [-1.26, ;, -1, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, 1.26], [-1.27, ;, -1, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, 1.27], [-1.28, ;, -1, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, 1.28], [-1.29, ;, -1, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, 1.29], [-1.20, ;, -1, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, 1.20]]')
		assert_p('Table', 'Content', '[[-1.21, ;, 1, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21, ;, 1.21], [-1.22, ;, 1, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22, ;, 1.22], [-1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.24, ;, 1, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24, ;, 1.24], [-1.25, ;, 1, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25, ;, 1.25], [-1.26, ;, 1, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26, ;, 1.26], [-1.27, ;, 1, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27, ;, 1.27], [-1.28, ;, 1, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28, ;, 1.28], [-1.29, ;, 1, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29, ;, 1.29], [-1.20, ;, 1, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20, ;, 1.20]]')
	close()

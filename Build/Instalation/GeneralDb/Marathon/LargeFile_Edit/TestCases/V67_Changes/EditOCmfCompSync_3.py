useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		if commonBits.isRecordEditor():
			select_menu('Record Layouts>>Load Cobol Copybook')
			select('FileChooser', commonBits.cobolTestDir() + 'mfCompSync.cbl')
	
			select('ComputerOptionCombo', 'Open Cobol Micro Focus (Intel)')
			select('BmKeyedComboBox1', 'Other')
			select('BmKeyedComboBox', 'Fixed Length Binary')
#			select('BmKeyedComboBox1', '9')
#			select('BmKeyedComboBox', '2')
			click('Go')


		click('Open')
		select('FileChooser', commonBits.cobolTestDir() + 'mfCompSync.bin')
		commonBits.setCobolLayout(select, 'mfCompSync', 'Open Cobol Micro Focus (Intel)')

		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,5(-123.45)')
##		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [23.45, ;, 23, ;, 3.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, -23, ;, -3.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, 23.45], [123.45, ;, 23, ;, 3.45, ;, 23.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, -23, ;, -3.45, ;, -23.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, 123.45], [4567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, -67, ;, -7.89, ;, -67.89, ;, -567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, 4567.89], [34567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, -67, ;, -7.89, ;, -67.89, ;, -567.89, ;, -4567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, 34567.89], [234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 34567.89], [-234567.89, ;, -67, ;, -7.89, ;, -67.89, ;, -567.89, ;, -4567.89, ;, -34567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, 34567.89]]')
		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [23.45, ;, 23, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, -23, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, 23.45], [123.45, ;, 123, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, -123, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, 123.45], [4567.89, ;, -41, ;, -19.63, ;, -19.63, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, 41, ;, 19.63, ;, 19.63, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, 4567.89], [34567.89, ;, 7, ;, -166.19, ;, -166.19, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, -7, ;, 166.19, ;, 166.19, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, 34567.89], [234567.89, ;, 71, ;, -50.99, ;, -50.99, ;, 66795.73, ;, 66795.73, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 66795.73], [-234567.89, ;, -71, ;, 50.99, ;, 50.99, ;, -66795.73, ;, -66795.73, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, 66795.73]]')
		select('Table', 'cell:29 - 2|Num1,5(-123.45)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11],columns:[27 - 1|Num0]')
		select_menu('Window>>mfCompSync.bin>>Table: ')
		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
#		select('Table2', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,4(-130.08)')
##		assert_p('Table', 'Content', '[[91234567.89, ;, 67, ;, 7.89, ;, 67.89, ;, 567.89, ;, 4567.89, ;, 34567.89, ;, 234567.89, ;, 1234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 34567.89], [-91234567.89, ;, -67, ;, -7.89, ;, -67.89, ;, -567.89, ;, -4567.89, ;, -34567.89, ;, -234567.89, ;, -1234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, 34567.89], [987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 54321.12], [-987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, 54321.12], [1987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 54321.12], [-1987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, 54321.12], [21987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 54321.12], [-21987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, 54321.12], [321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 54321.12]]')
		assert_p('Table', 'Content', '[[91234567.89, ;, 7, ;, -63.79, ;, -63.79, ;, -33487.15, ;, -33487.15, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, -33487.15], [-91234567.89, ;, -7, ;, 63.79, ;, 63.79, ;, 33487.15, ;, 33487.15, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -33487.15], [987654321.12, ;, -79, ;, -68.64, ;, -68.64, ;, -20384.80, ;, -20384.80, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, -20384.80], [-987654321.12, ;, 79, ;, 68.64, ;, 68.64, ;, 20384.80, ;, 20384.80, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -20384.80], [1987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, 57541.60, ;, 57541.60, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 57541.60], [-1987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, -57541.60, ;, -57541.60, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, 57541.60], [21987654321.12, ;, -79, ;, -48.16, ;, -48.16, ;, -61652.00, ;, -61652.00, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, -61652.00], [-21987654321.12, ;, 79, ;, 48.16, ;, 48.16, ;, 61652.00, ;, 61652.00, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -61652.00], [321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -4062.24, ;, -4062.24, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, 3129282266.08, ;, 3129282266.08, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, -4062.24]]')
		select('Table', 'cell:29 - 2|Num1,4(-130.08)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[12,13,14,15,16,17,18,19,20],columns:[27 - 1|Num0]')
		select_menu('Window>>mfCompSync.bin>>Table: ')
		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,3(197.60)')
##		assert_p('Table', 'Content', '[[-321987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, 54321.12], [4321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 54321.12], [-4321987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, 54321.12], [54321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321.12], [-54321987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, 54321.12], [654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 654321987654321.12, ;, 54321.12], [-654321987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -654321987654321.12, ;, -654321987654321.12, ;, 54321.12], [7654321987654321.12, ;, 21, ;, 1.12, ;, 21.12, ;, 321.12, ;, 4321.12, ;, 54321.12, ;, 654321.12, ;, 7654321.12, ;, 87654321.12, ;, 987654321.12, ;, 1987654321.12, ;, 21987654321.12, ;, 321987654321.12, ;, 4321987654321.12, ;, 54321987654321.12, ;, 654321987654321.12, ;, 7654321987654321.12, ;, 54321.12], [-7654321987654321.12, ;, -21, ;, -1.12, ;, -21.12, ;, -321.12, ;, -4321.12, ;, -54321.12, ;, -654321.12, ;, -7654321.12, ;, -87654321.12, ;, -987654321.12, ;, -1987654321.12, ;, -21987654321.12, ;, -321987654321.12, ;, -4321987654321.12, ;, -54321987654321.12, ;, -654321987654321.12, ;, -7654321987654321.12, ;, 54321.12]]')
		assert_p('Table', 'Content', '[[-321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 4062.24, ;, 4062.24, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, -3129282266.08, ;, -3129282266.08, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -4062.24], [4321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -19135.52, ;, -19135.52, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 906957161.44, ;, 906957161.44, ;, -1307511879892.00, ;, -1307511879892.00, ;, -1307511879892.00, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, -19135.52], [-4321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 19135.52, ;, 19135.52, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -906957161.44, ;, -906957161.44, ;, 1307511879892.00, ;, 1307511879892.00, ;, 1307511879892.00, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -19135.52], [54321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, -39779.36, ;, -39779.36, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, -4881874091.04, ;, -4881874091.04, ;, 841742079296.48, ;, 841742079296.48, ;, 841742079296.48, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, -39779.36], [-54321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, 39779.36, ;, 39779.36, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, 4881874091.04, ;, 4881874091.04, ;, -841742079296.48, ;, -841742079296.48, ;, -841742079296.48, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -39779.36], [654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 48038.88, ;, 48038.88, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, 2617964823.52, ;, 2617964823.52, ;, 1300041685599.20, ;, 1300041685599.20, ;, 1300041685599.20, ;, -66253952724958.24, ;, -66253952724958.24, ;, 654321987654321.12, ;, 654321987654321.12, ;, 48038.88], [-654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -48038.88, ;, -48038.88, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, -2617964823.52, ;, -2617964823.52, ;, -1300041685599.20, ;, -1300041685599.20, ;, -1300041685599.20, ;, 66253952724958.24, ;, 66253952724958.24, ;, -654321987654321.12, ;, -654321987654321.12, ;, 48038.88], [7654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 10028.00, ;, 10028.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -5174922247.20, ;, -5174922247.20, ;, 1017370891584.48, ;, 1017370891584.48, ;, 1017370891584.48, ;, -272013356517751.84, ;, -272013356517751.84, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 10028.00], [-7654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -10028.00, ;, -10028.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 5174922247.20, ;, 5174922247.20, ;, -1017370891584.48, ;, -1017370891584.48, ;, -1017370891584.48, ;, 272013356517751.84, ;, 272013356517751.84, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, 10028.00]]')
		select('Table', 'cell:29 - 2|Num1,3(197.60)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
##		select('Table', 'rows:[21,22,23,24,25,26,27,28,29],columns:[27 - 1|Num0]')
		select_menu('Window>>mfCompSync.bin>>Table: ')
		select('Table', 'rows:[30,31,32,33,34,35,36,37,38,39],columns:[27 - 1|Num0]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[30,31,32,33,34,35,36,37,38,39],columns:[27 - 1|Num0]')
		select('Table', 'cell:29 - 2|Num1,4(-1.25)')
		assert_p('Table', 'Content', '[[-1.21, ;, -1, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, 1.21], [-1.22, ;, -1, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, 1.22], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [-1.24, ;, -1, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, 1.24], [-1.25, ;, -1, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, 1.25], [-1.26, ;, -1, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, 1.26], [-1.27, ;, -1, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, 1.27], [-1.28, ;, -1, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, 1.28], [-1.29, ;, -1, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, 1.29], [-1.20, ;, -1, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, 1.20]]')
	close()

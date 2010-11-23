useFixture(default)

def test():
	from Modules import commonBits
	import time
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select_menu('File>>File Copy Menu')
		click('*2')
		select('FileChooser', commonBits.cobolTestDir() + 'mfComp.bin')
		select('FileChooser1', commonBits.cobolTestDir() + 'zzBsComp.bin')
		select('FileChooser2', commonBits.cobolTestDir() + 'mfComp.cbl')
		select('ComputerOptionCombo', 'Open Cobol Micro Focus (Intel)')
		select('ComputerOptionCombo1', 'Open Cobol bs2000 Little Endian (Intel)')
##		select('BmKeyedComboBox1', '2')

		select('BmKeyedComboBox', 'Fixed Length Binary')
##		select('BmKeyedComboBoxxxxx', '2')
		click('Right')
		select('TabbedPane', '')
		click('Copy2')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
		select('FileChooser', commonBits.cobolTestDir() + 'zzBsComp.bin')

		if commonBits.isRecordEditor():
			select_menu('Record Layouts>>Load Cobol Copybook')
			select('FileChooser', commonBits.cobolTestDir() + 'bsComp.cbl')
			select('TextField', '')
##			select('FileChooser', commonBits.cobolTestDir() + 'mfCmp.cbl')
			select('TextField', '')
			select('ComputerOptionCombo', 'Open Cobol Micro Focus (Intel)')
##			select('BmKeyedComboBox1', '9')
			select('BmKeyedComboBox1', 'Other')
##			click('Go')
			select('FileChooser', commonBits.cobolTestDir() + 'mfComp.cbl')
			click('Go')
			select('FileChooser', commonBits.cobolTestDir() + 'bsComp.cbl')
			select('TextField', '')
			select('ComputerOptionCombo', 'Open Cobol bs2000 Little Endian (Intel)')
##			select('BmKeyedComboBox1', '9')
			select('BmKeyedComboBox1', 'Other')
			click('Go')
			click('BasicInternalFrameTitlePane$NoFocusButton2')
##			click('BasicInternalFrameTitlePane$NoFocusButton2')


# -----------------------------------------------------------------------------------
# Do Compare
# -----------------------------------------------------------------------------------

		select_menu('File>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.cobolTestDir() + 'mfComp.bin')
		commonBits.setCobolLayout(select,  'mfComp', 'Open Cobol Micro Focus (Intel)')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.cobolTestDir() + 'zzBsComp.bin')
		commonBits.setCobolLayout(select, 'bsComp', 'Open Cobol bs2000 Little Endian (Intel)')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0(-1)')
		select('Table', 'bsComp', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0(0)')
		select('Table1', 'cell:Field,2(Num0)')
		assert_p('Table1', 'Content', '[[NumA, NumA], [sep0, sep0], [Num0, Num0], [sep1, sep1], [Num1, Num1], [sep2, sep2], [Num2, Num2], [sep3, sep3], [Num3, Num3], [sep4, sep4], [Num4, Num4], [sep5, sep5], [Num5, Num5], [sep6, sep6], [Num6, Num6], [sep7, sep7], [Num7, Num7], [sep8, sep8], [Num8, Num8], [sep9, sep9], [Num9, Num9], [sep10, sep10], [Num10, Num10], [sep11, sep11], [Num11, Num11], [sep12, sep12], [Num12, Num12], [sep13, sep13], [Num13, Num13], [sep14, sep14], [Num14, Num14], [sep15, sep15], [Num15, Num15], [sep16, sep16], [Num16, Num16], [sep17, sep17], [Num17, Num17]]')
##		select('Table1', '')
		select('Table', 'cell:Record,0(mfComp)')
		select('Table', 'cell:Record,0(mfComp)')
		doubleclick('Table', 'Record,0')
		assert_p('Table', 'Content', '[[mfComp, bsComp]]')
		select('Table', 'cell:Record,0(mfComp)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')

# --------------------------------------------------------------------
# ---   Check in editor
#---------------------------------------------------------------------

		click('Open')


		select('FileChooser', commonBits.cobolTestDir() + 'zzBsComp.bin')
		commonBits.setCobolLayout(select, 'bsComp', 'Open Cobol bs2000 Little Endian (Intel)')
		commonBits.doEdit(click)
		select('Table', 'cell:30 - 2|Num1,5(-123.45)')
		assert_p('Table', 'Text', '-19.63', '30 - 2|Num1,6')
		select('Table', 'cell:30 - 2|Num1,9(166.19)')
		assert_p('Table', 'RowCount', '40')
		select('Table', 'cell:30 - 2|Num1,11(50.99)')
		assert_p('Table', 'Content', '[[1.23, ;, 1, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23, ;, 1.23], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [23.45, ;, 23, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45, ;, 23.45], [-23.45, ;, -23, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, -23.45, ;, 23.45], [123.45, ;, 123, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45, ;, 123.45], [-123.45, ;, -123, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, -123.45, ;, 123.45], [4567.89, ;, -41, ;, -19.63, ;, -19.63, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89, ;, 4567.89], [-4567.89, ;, 41, ;, 19.63, ;, 19.63, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, -4567.89, ;, 4567.89], [34567.89, ;, 7, ;, -166.19, ;, -166.19, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89, ;, 34567.89], [-34567.89, ;, -7, ;, 166.19, ;, 166.19, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, -34567.89, ;, 34567.89], [234567.89, ;, 71, ;, -50.99, ;, -50.99, ;, 66795.73, ;, 66795.73, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 234567.89, ;, 66795.73], [-234567.89, ;, -71, ;, 50.99, ;, 50.99, ;, -66795.73, ;, -66795.73, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, -234567.89, ;, 66795.73], [91234567.89, ;, 7, ;, -63.79, ;, -63.79, ;, -33487.15, ;, -33487.15, ;, 5335221.97, ;, 5335221.97, ;, 5335221.97, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, 91234567.89, ;, -33487.15], [-91234567.89, ;, -7, ;, 63.79, ;, 63.79, ;, 33487.15, ;, 33487.15, ;, -5335221.97, ;, -5335221.97, ;, -5335221.97, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -91234567.89, ;, -33487.15], [987654321.12, ;, -79, ;, -68.64, ;, -68.64, ;, -20384.80, ;, -20384.80, ;, -188156.96, ;, -188156.96, ;, -188156.96, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, 987654321.12, ;, -20384.80], [-987654321.12, ;, 79, ;, 68.64, ;, 68.64, ;, 20384.80, ;, 20384.80, ;, 188156.96, ;, 188156.96, ;, 188156.96, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -987654321.12, ;, -20384.80], [1987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, 57541.60, ;, 57541.60, ;, 11969364.96, ;, 11969364.96, ;, 11969364.96, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 1987654321.12, ;, 57541.60], [-1987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, -57541.60, ;, -57541.60, ;, -11969364.96, ;, -11969364.96, ;, -11969364.96, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, -1987654321.12, ;, 57541.60], [21987654321.12, ;, -79, ;, -48.16, ;, -48.16, ;, -61652.00, ;, -61652.00, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, -2578234.40, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, 21987654321.12, ;, -61652.00], [-21987654321.12, ;, 79, ;, 48.16, ;, 48.16, ;, 61652.00, ;, 61652.00, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -61652.00], [321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -4062.24, ;, -4062.24, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, 3129282266.08, ;, 3129282266.08, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, -4062.24], [-321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 4062.24, ;, 4062.24, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, -3129282266.08, ;, -3129282266.08, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -4062.24], [4321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -19135.52, ;, -19135.52, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 906957161.44, ;, 906957161.44, ;, -1307511879892.00, ;, -1307511879892.00, ;, -1307511879892.00, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, -19135.52], [-4321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 19135.52, ;, 19135.52, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -906957161.44, ;, -906957161.44, ;, 1307511879892.00, ;, 1307511879892.00, ;, 1307511879892.00, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -19135.52], [54321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, -39779.36, ;, -39779.36, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, -4881874091.04, ;, -4881874091.04, ;, 841742079296.48, ;, 841742079296.48, ;, 841742079296.48, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, -39779.36], [-54321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, 39779.36, ;, 39779.36, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, 4881874091.04, ;, 4881874091.04, ;, -841742079296.48, ;, -841742079296.48, ;, -841742079296.48, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -39779.36], [654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 48038.88, ;, 48038.88, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, 2617964823.52, ;, 2617964823.52, ;, 1300041685599.20, ;, 1300041685599.20, ;, 1300041685599.20, ;, -66253952724958.24, ;, -66253952724958.24, ;, 654321987654321.12, ;, 654321987654321.12, ;, 48038.88], [-654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -48038.88, ;, -48038.88, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, -2617964823.52, ;, -2617964823.52, ;, -1300041685599.20, ;, -1300041685599.20, ;, -1300041685599.20, ;, 66253952724958.24, ;, 66253952724958.24, ;, -654321987654321.12, ;, -654321987654321.12, ;, 48038.88], [7654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 10028.00, ;, 10028.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -5174922247.20, ;, -5174922247.20, ;, 1017370891584.48, ;, 1017370891584.48, ;, 1017370891584.48, ;, -272013356517751.84, ;, -272013356517751.84, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 10028.00], [-7654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -10028.00, ;, -10028.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 5174922247.20, ;, 5174922247.20, ;, -1017370891584.48, ;, -1017370891584.48, ;, -1017370891584.48, ;, 272013356517751.84, ;, 272013356517751.84, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, 10028.00], [-1.21, ;, -1, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, -1.21, ;, 1.21], [-1.22, ;, -1, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, -1.22, ;, 1.22], [-1.23, ;, -1, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, -1.23, ;, 1.23], [-1.24, ;, -1, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, -1.24, ;, 1.24], [-1.25, ;, -1, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, -1.25, ;, 1.25], [-1.26, ;, -1, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, -1.26, ;, 1.26], [-1.27, ;, -1, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, -1.27, ;, 1.27], [-1.28, ;, -1, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, -1.28, ;, 1.28], [-1.29, ;, -1, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, -1.29, ;, 1.29], [-1.20, ;, -1, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, -1.20, ;, 1.20]]')
		select('Table', 'rows:[19,20,21,22,23,24,25,26,27,28,29],columns:[1 - 25|NumA]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[19,20,21,22,23,24,25,26,27,28,29],columns:[1 - 25|NumA]')
		select('Table', 'cell:30 - 2|Num1,3(-130.08)')
		assert_p('Table', 'Content', '[[-21987654321.12, ;, 79, ;, 48.16, ;, 48.16, ;, 61652.00, ;, 61652.00, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, 2578234.40, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -21987654321.12, ;, -61652.00], [321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -4062.24, ;, -4062.24, ;, -6043860.00, ;, -6043860.00, ;, -6043860.00, ;, 3129282266.08, ;, 3129282266.08, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, 321987654321.12, ;, -4062.24], [-321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 4062.24, ;, 4062.24, ;, 6043860.00, ;, 6043860.00, ;, 6043860.00, ;, -3129282266.08, ;, -3129282266.08, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -321987654321.12, ;, -4062.24], [4321987654321.12, ;, -79, ;, -130.08, ;, -130.08, ;, -19135.52, ;, -19135.52, ;, 5014029.28, ;, 5014029.28, ;, 5014029.28, ;, 906957161.44, ;, 906957161.44, ;, -1307511879892.00, ;, -1307511879892.00, ;, -1307511879892.00, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, 4321987654321.12, ;, -19135.52], [-4321987654321.12, ;, 79, ;, 130.08, ;, 130.08, ;, 19135.52, ;, 19135.52, ;, -5014029.28, ;, -5014029.28, ;, -5014029.28, ;, -906957161.44, ;, -906957161.44, ;, 1307511879892.00, ;, 1307511879892.00, ;, 1307511879892.00, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -4321987654321.12, ;, -19135.52], [54321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, -39779.36, ;, -39779.36, ;, 14388626.40, ;, 14388626.40, ;, 14388626.40, ;, -4881874091.04, ;, -4881874091.04, ;, 841742079296.48, ;, 841742079296.48, ;, 841742079296.48, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, 54321987654321.12, ;, -39779.36], [-54321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, 39779.36, ;, 39779.36, ;, -14388626.40, ;, -14388626.40, ;, -14388626.40, ;, 4881874091.04, ;, 4881874091.04, ;, -841742079296.48, ;, -841742079296.48, ;, -841742079296.48, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -54321987654321.12, ;, -39779.36], [654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 48038.88, ;, 48038.88, ;, -1965227.04, ;, -1965227.04, ;, -1965227.04, ;, 2617964823.52, ;, 2617964823.52, ;, 1300041685599.20, ;, 1300041685599.20, ;, 1300041685599.20, ;, -66253952724958.24, ;, -66253952724958.24, ;, 654321987654321.12, ;, 654321987654321.12, ;, 48038.88], [-654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -48038.88, ;, -48038.88, ;, 1965227.04, ;, 1965227.04, ;, 1965227.04, ;, -2617964823.52, ;, -2617964823.52, ;, -1300041685599.20, ;, -1300041685599.20, ;, -1300041685599.20, ;, 66253952724958.24, ;, 66253952724958.24, ;, -654321987654321.12, ;, -654321987654321.12, ;, 48038.88], [7654321987654321.12, ;, -79, ;, 197.60, ;, 197.60, ;, 10028.00, ;, 10028.00, ;, -20961492.00, ;, -20961492.00, ;, -20961492.00, ;, -5174922247.20, ;, -5174922247.20, ;, 1017370891584.48, ;, 1017370891584.48, ;, 1017370891584.48, ;, -272013356517751.84, ;, -272013356517751.84, ;, 7654321987654321.12, ;, 7654321987654321.12, ;, 10028.00], [-7654321987654321.12, ;, 79, ;, -197.60, ;, -197.60, ;, -10028.00, ;, -10028.00, ;, 20961492.00, ;, 20961492.00, ;, 20961492.00, ;, 5174922247.20, ;, 5174922247.20, ;, -1017370891584.48, ;, -1017370891584.48, ;, -1017370891584.48, ;, 272013356517751.84, ;, 272013356517751.84, ;, -7654321987654321.12, ;, -7654321987654321.12, ;, 10028.00]]')

	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_Receipt.txt')
		if commonBits.version()  == 'MsAccess':
			select('ComboBox2', 'Comma Delimited, names on the first line')
		else:
			select('ComboBox1', 'CSV')
##		select('ComboBox2', 'Comma Delimited, names on the first line')
		click('Edit1')
		#select('Table', 'cell:1|FHSTDREC01TAR05/03/200519:00:09,5(RSTAR211853      96010019809740000000220132400000000000015057000000001000000001EQBD180 2005-03-04-20.30.05.620098000000001)')
		assert_p('Table', 'Content', '[[RHTAR297853      96010019809740000112204.03.200520.28.10000005965IN                              000000000000000000000000000                    00013776000000002800000002800                    NNY0000028 L96010082005-03-04-20.34.46.709398000000001000000000], [RDTAR211853      9601001980974000000022013240000000000001EN00000002800000100000000049200EQBD180 2005-03-04-20.30.05.644995000000014		#select('Table', 'cell:1|FHSTDREC01TAR05/03/200519:00:09,5(RSTAR211853      96010019809740000000220132400000000000015057000000001000000001EQBD180 2005-03-04-20.30.05.620098000000001)')
		select_menu('Edit>>Change Layout')
		#select('Table', 'cell:1|FHSTDREC01TAR05/03/200519:00:09,5(RSTAR211853      96010019809740000000220132400000000000015057000000001000000001EQBD180 2005-03-04-20.30.05.620098000000001)')
		#select('ComboBox1', 'Ams')
		select('ComboBox2', 'ams Receipt')
		click('Go')
		if commonBits.version()  != 'MsAccess':
			select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
			assert_p('Table', 'Content', '[[RH, TAR, 297853, 9601, 112204.03., 200520.2, 8.100, 0, 5965IN, , ,           0000000000000000, 0, 00                    00013776000000002800000002800                    NNY0000028 L96010082005-03-04-20.34.46.709398000000001000000000], [RD, TAR, 211853, 9601, 2201324, 0, 1, EN00, 2800, 100000, 00004920, 0EQBD180 2005-03-04-20.30., 5.644995, 000000014			select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
##			select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
#		select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
		assert_p('Table', 'Content', '[[RH, TAR, 297853, 9601, 112204.03., 200520.2, 8.100, 0, 5965IN, , ,           0000000000000000, 0, 00                    00013776000000002800000002800                    NNY0000028 L96010082005-03-04-20.34.46.709398000000001000000000], [RD, TAR, 211853, 9601, 2201324, 0, 1, EN00, 2800, 100000, 00004920, 0EQBD180 2005-03-04-20.30., 5.644995, 000000014')
		assert_p('Table', 'Content', '[[RH, TAR, 297853, 9601, 112204.03., 200520.2, 8.100, 0, 5965IN, , ,           0000000000000000, 0, 00                    00013776000000002800000002800                    NNY0000028 L96010082005-03-04-20.34.46.709398000000001000000000], [RD, TAR, 211853, 9601, 2201324, 0, 1, EN00, 2800, 100000, 00004920, 0EQBD180 2005-03-04-20.30., 5.644995, 000000014')
#		select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
		if commonBits.version()  != 'MsAccess':
			select('Table', 'cell:114 - 9|Act Recv Qty Rs,4(3)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
		select('ComboBox1', 'Ams')
		select('ComboBox2', 'ams Receipt')
		click('Edit1')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

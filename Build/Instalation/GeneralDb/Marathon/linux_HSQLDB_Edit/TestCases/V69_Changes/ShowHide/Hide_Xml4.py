useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml')
		click('Edit1')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,6')
		select_menu('Expand Tree')
		select('LayoutCombo', 'item')
		select_menu('Edit>>Show / Hide Fields')
		select('Table', 'cell:Show,4(true)')
		select('Table', 'cell:Show,5(true)')
		select('Table', 'cell:Show,6(true)')
		select('Table', 'cell:Show,7(true)')
		click('Go')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , ], [, , * Location Download, , , , , , ], [, , ******************************, , , , , , ], [, , , , 173, , , , ], [, , , , 3, x(3), , True, ], [, , , , 41, , , , ], [, , , , 4, 9(4), true, True, ], [, , , , 2, XX, , True, ], [, , , , 35, X(35), , True, ], [, , , , 128, , , , ], [, , , , 1, X, , True, ]]')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'display-length,7')
		select_menu('Show Column>>position')
##		assert_p('JTreeTable', 'Content', '[[, , , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , , ], [, , * Location Download, , , , , , , ], [, , ******************************, , , , , , , ], [, , , , 1, 173, , , , ], [, , , , 1, 3, x(3), , True, ], [, , , , 4, 41, , , , ], [, , , , 4, 4, 9(4), true, True, ], [, , , , 8, 2, XX, , True, ], [, , , , 10, 35, X(35), , True, ], [, , , , 45, 128, , , , ], [, , , , 173, 1, X, , True, ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , ], [, , ******************************, , , , , , , ], [, , * Location Download, , , , , , , ], [, , ******************************, , , , , , , ], [, , , , 173, 1, , , , ], [, , , , 3, 1, x(3), , True, ], [, , , , 41, 4, , , , ], [, , , , 4, 4, 9(4), true, True, ], [, , , , 2, 8, XX, , True, ], [, , , , 35, 10, X(35), , True, ], [, , , , 128, 45, , , , ], [, , , , 1, 173, X, , True, ]]')

##		select('JTreeTable', '')
		rightclick('JTreeTable', 'display-length,7')
		select_menu('Show Column>>name')
###		assert_p('JTreeTable', 'Content', '[[, , , , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , , , ], [, , * Location Download, , , , , , , , ], [, , ******************************, , , , , , , , ], [, , , , Ams-Vendor, 1, 173, , , , ], [, , , , Brand, 1, 3, x(3), , True, ], [, , , , Location-Details, 4, 41, , , , ], [, , , , Location-Number, 4, 4, 9(4), true, True, ], [, , , , Location-Type, 8, 2, XX, , True, ], [, , , , Location-Name, 10, 35, X(35), , True, ], [, , , , Full-Address, 45, 128, , , , ], [, , , , Location-Active, 173, 1, X, , True, ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , ], [, , ******************************, , , , , , , , ], [, , * Location Download, , , , , , , , ], [, , ******************************, , , , , , , , ], [, , , , 173, Ams-Vendor, 1, , , , ], [, , , , 3, Brand, 1, x(3), , True, ], [, , , , 41, Location-Details, 4, , , , ], [, , , , 4, Location-Number, 4, 9(4), true, True, ], [, , , , 2, Location-Type, 8, XX, , True, ], [, , , , 35, Location-Name, 10, X(35), , True, ], [, , , , 128, Full-Address, 45, , , , ], [, , , , 1, Location-Active, 173, X, , True, ]]')

#		select('JTreeTable', 'cell: ,6(null)')
#		click('MetalInternalFrameTitlePane', 395, 7)
#		click('MetalInternalFrameTitlePane', 388, 8)
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('JTreeTable', 'cell: ,6(null)')
#		select('JTreeTable', 'cell: ,6(null)')
		rightclick('JTreeTable', 'name,6')
		select_menu('Show Column>>level')
		select('JTreeTable', 'cell: ,6(null)')
###		assert_p('JTreeTable', 'Content', '[[, , , , , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , , , , ], [, , * Location Download, , , , , , , , , ], [, , ******************************, , , , , , , , , ], [, , , , 01, Ams-Vendor, 1, 173, , , , ], [, , , , 03, Brand, 1, 3, x(3), , True, ], [, , , , 03, Location-Details, 4, 41, , , , ], [, , , , 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 05, Location-Type, 8, 2, XX, , True, ], [, , , , 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 03, Full-Address, 45, 128, , , , ], [, , , , 03, Location-Active, 173, 1, X, , True, ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , ], [, , ******************************, , , , , , , , , ], [, , * Location Download, , , , , , , , , ], [, , ******************************, , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, , , , ], [, , , , 3, 03, Brand, 1, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, , , , ], [, , , , 4, 05, Location-Number, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, XX, , True, ], [, , , , 35, 05, Location-Name, 10, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, , , , ], [, , , , 1, 03, Location-Active, 173, X, , True, ]]')

		select('JTreeTable', 'cell: ,6(null)')
		rightclick('JTreeTable', 'name,7')
		select_menu('Show Column>>storage-length')
		select('JTreeTable', 'cell: ,6(null)')
##		assert_p('JTreeTable', 'Content', '[[, , , , , , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 01, Ams-Vendor, 1, 173, 173, , , , ], [, , , , 03, Brand, 1, 3, 3, x(3), , True, ], [, , , , 03, Location-Details, 4, 41, 41, , , , ], [, , , , 05, Location-Number, 4, 4, 4, 9(4), true, True, ], [, , , , 05, Location-Type, 8, 2, 2, XX, , True, ], [, , , , 05, Location-Name, 10, 35, 35, X(35), , True, ], [, , , , 03, Full-Address, 45, 128, 128, , , , ], [, , , , 03, Location-Active, 173, 1, 1, X, , True, ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, 2, XX, , True, ], [, , , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')

		select('JTreeTable', 'cell: ,6(null)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

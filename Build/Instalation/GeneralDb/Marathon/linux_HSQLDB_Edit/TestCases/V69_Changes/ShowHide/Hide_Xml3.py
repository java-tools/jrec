useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml')
		commonBits.doEdit(click)
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,6')
		select_menu('Expand Tree')
		select('LayoutCombo', 'item')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, 2, XX, , True, ], [, , , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select_menu('Edit>>Show / Hide Fields')
		select('Table', 'cell:Show,4(true)')
		select('Table', 'cell:Show,5(true)')
		select('Table', 'cell:Show,6(true)')
		select('Table', 'cell:Show,7(true)')
		click('Go')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , ], [, , * Location Download, , , , , , ], [, , ******************************, , , , , , ], [, , , , 173, , , , ], [, , , , 3, x(3), , True, ], [, , , , 41, , , , ], [, , , , 4, 9(4), true, True, ], [, , , , 2, XX, , True, ], [, , , , 35, X(35), , True, ], [, , , , 128, , , , ], [, , , , 1, X, , True, ]]')
		select_menu('Edit>>Show / Hide Fields')
		select('Table', 'cell:Show,5(false)')
		select('Table', 'cell:Show,6(false)')
		click('Go')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , ], [, , ******************************, , , , , , , , ], [, , * Location Download, , , , , , , , ], [, , ******************************, , , , , , , , ], [, , , , 173, Ams-Vendor, 1, , , , ], [, , , , 3, Brand, 1, x(3), , True, ], [, , , , 41, Location-Details, 4, , , , ], [, , , , 4, Location-Number, 4, 9(4), true, True, ], [, , , , 2, Location-Type, 8, XX, , True, ], [, , , , 35, Location-Name, 10, X(35), , True, ], [, , , , 128, Full-Address, 45, , , , ], [, , , , 1, Location-Active, 173, X, , True, ]]')
		select_menu('Edit>>Show / Hide Fields')
		select('Table', 'cell:Show,4(false)')
		click('Go')
##		assert_p('JTreeTable', 'Content', '[[, , , , , , , AMSLOCATIONTEST1.cbl, , , , ], [, , ******************************, , , , , , , , , ], [, , * Location Download, , , , , , , , , ], [, , ******************************, , , , , , , , , ], [, , , , 01, Ams-Vendor, 1, 173, , , , ], [, , , , 03, Brand, 1, 3, x(3), , True, ], [, , , , 03, Location-Details, 4, 41, , , , ], [, , , , 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 05, Location-Type, 8, 2, XX, , True, ], [, , , , 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 03, Full-Address, 45, 128, , , , ], [, , , , 03, Location-Active, 173, 1, X, , True, ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , ], [, , ******************************, , , , , , , , , ], [, , * Location Download, , , , , , , , , ], [, , ******************************, , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, , , , ], [, , , , 3, 03, Brand, 1, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, , , , ], [, , , , 4, 05, Location-Number, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, XX, , True, ], [, , , , 35, 05, Location-Name, 10, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, , , , ], [, , , , 1, 03, Location-Active, 173, X, , True, ]]')

##		select('JTreeTable', '')
		rightclick('JTreeTable', 'name,7')
		select_menu('Show Column>>storage-length')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, 2, XX, , True, ], [, , , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

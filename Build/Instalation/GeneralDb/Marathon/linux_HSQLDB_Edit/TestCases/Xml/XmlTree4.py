useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml')
		commonBits.doEdit(click)

		select('LayoutCombo', 'copybook')
		select('JTreeTable', 'cell:filename,0(AMSLOCATIONTEST1.cbl)')
		select('JTreeTable', 'cell:Xml~Namespace,0(null)')
		
		###assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , ], [, , ******************************, , , , ], [, , * Location Download, , , , ], [, , ******************************, , , , ], [, , , , 173, , ]]')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Xml~Prefix,3(******************************)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , ], [, , ******************************, , , , ], [, , * Location Download, , , , ], [, , ******************************, , , , ], [, , , , 173, , ]]')
		select('JTreeTable', 'cell:Xml~Prefix,2(* Location Download)')
		assert_p('JTreeTable', 'Text', '* Location Download', 'Xml~Prefix,2')
		select('JTreeTable', 'cell:Xml~Prefix,3(******************************)')
		assert_p('JTreeTable', 'RowCount', '5')
		select('JTreeTable', 'cell:Xml~Prefix,3(******************************)')
		rightclick('JTreeTable', 'Xml~Prefix,4')
		select_menu('Expand Tree')
		select('LayoutCombo', 'item')
		select('JTreeTable', 'cell:name,6(Location-Details)')
		assert_p('JTreeTable', 'Text', 'Location-Details', 'name,6')
		select('JTreeTable', 'cell:name,5(Brand)')
		assert_p('JTreeTable', 'RowCount', '9')
		select('JTreeTable', 'cell:name,7(Full-Address)')
		assert_p('JTreeTable', 'Text', 'Full-Address', 'name,7')
		select('JTreeTable', 'cell:name,8(Location-Active)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:name,8(Location-Active)')
		rightclick('JTreeTable', 'Xml~Prefix,6')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Xml~Prefix,7(null)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [, , , , 2, 05, Location-Type, 8, 2, XX, , True, ], [, , , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:name,7(Location-Number)')
		assert_p('JTreeTable', 'Text', 'Location-Number', 'name,7')
		select('JTreeTable', 'cell:name,8(Location-Type)')
		assert_p('JTreeTable', 'RowCount', '12')
		select('JTreeTable', 'cell:name,8(Location-Type)')
		rightclick('JTreeTable', 'Xml~Prefix,10')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:name,11(Address-Lines)')
		assert_p('JTreeTable', 'Text', 'Address-Lines', 'name,11')
		select('JTreeTable', 'cell:name,11(Address-Lines)')
		rightclick('JTreeTable', 'Xml~Prefix,6')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:name,10(State)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 115, 05, Address-Lines, 45, 115, , , , ], [, , , , 10, 05, Postcode, 160, 10, 9(10), true, True, ], [, , , , 3, 05, State, 170, 3, XXX, , True, ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:display-length,7(128)')
		rightclick('JTreeTable', 'display-length,7')
		select('JTreeTable', 'cell:display-length,8(115)')
		rightclick('JTreeTable', 'display-length,8')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:name,9(Address-1)')
		assert_p('JTreeTable', 'Text', 'Address-2', 'name,10')
		select('JTreeTable', 'cell:name,10(Address-2)')
		assert_p('JTreeTable', 'RowCount', '15')
		select('JTreeTable', 'cell:name,11(Address-3)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 115, 05, Address-Lines, 45, 115, , , , ], [, , , , 40, 07, Address-1, 45, 40, X(40), , True, ], [, , , , 40, 07, Address-2, 85, 40, X(40), , True, ], [, , , , 35, 07, Address-3, 125, 35, X(35), , True, ], [, , , , 10, 05, Postcode, 160, 10, 9(10), true, True, ], [, , , , 3, 05, State, 170, 3, XXX, , True, ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:display-length,7(128)')
		rightclick('JTreeTable', 'display-length,7')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:name,7(Full-Address)')
		assert_p('JTreeTable', 'RowCount', '9')
		select('JTreeTable', 'cell:name,6(Location-Details)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:name,7(Full-Address)')
		rightclick('JTreeTable', 'name,7')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:name,8(Address-Lines)')
		assert_p('JTreeTable', 'Text', 'Address-1', 'name,9')
		select('JTreeTable', 'cell:name,10(Address-2)')
		assert_p('JTreeTable', 'RowCount', '15')
		select('JTreeTable', 'cell:name,11(Address-3)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 115, 05, Address-Lines, 45, 115, , , , ], [, , , , 40, 07, Address-1, 45, 40, X(40), , True, ], [, , , , 40, 07, Address-2, 85, 40, X(40), , True, ], [, , , , 35, 07, Address-3, 125, 35, X(35), , True, ], [, , , , 10, 05, Postcode, 160, 10, 9(10), true, True, ], [, , , , 3, 05, State, 170, 3, XXX, , True, ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
	close()

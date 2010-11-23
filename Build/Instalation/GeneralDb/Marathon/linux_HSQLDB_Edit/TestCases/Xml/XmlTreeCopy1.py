useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml')
		commonBits.doEdit(click)

		select('LayoutCombo', 'copybook')
		#rightclick('JTreeTable', 'Namespace,0')
		#select_menu('Expand Tree')
		rightclick('JTreeTable', 'Xml~Prefix,4')
		select_menu('Expand Tree')
		select('LayoutCombo', 'item')
		select('JTreeTable', 'cell:name,7(Full-Address)')
		assert_p('JTreeTable', 'Text', 'Full-Address', 'name,7')
		select('JTreeTable', 'cell:name,8(Location-Active)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:name,6(Location-Details)')
		assert_p('JTreeTable', 'RowCount', '9')
		select('JTreeTable', 'cell:Xml~Prefix,7(null)')
		select('JTreeTable', 'cell:display-length,7(128)')
		rightclick('JTreeTable', 'Xml~Namespace,7')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Xml~Prefix,8(null)')
		select_menu('Edit>>Copy Record#{s#}')
		select('JTreeTable', 'cell:Xml~Prefix,5(null)')
		rightclick('JTreeTable', 'Xml~Prefix,5')
		select_menu('Paste Record#{s#}')
		select('JTreeTable', 'cell:Xml~Prefix,6(null)')
		rightclick('JTreeTable', 'Xml~Prefix,6')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:name,6(Address-Lines)')
		assert_p('JTreeTable', 'Text', 'Address-1', 'name,7')
		select('JTreeTable', 'cell:name,7(Address-1)')
		assert_p('JTreeTable', 'RowCount', '13')
		select('JTreeTable', 'cell:name,9(Address-3)')
		assert_p('JTreeTable', 'Text', 'cell:name,9(Address-3)')
		select('JTreeTable', 'cell:name,8(Address-2)')
		rightclick('JTreeTable', 'level,8')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:name,12(Location-Active)')
		rightclick('JTreeTable', 'name,12')
		select_menu('Paste Record#{s#} Prior')
		select('JTreeTable', 'cell:name,9(Address-2)')
#		assert_p('JTreeTable', 'Xml~Text', 'Address-2', 'name,9')
		select('JTreeTable', 'cell:name,10(Location-Active)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 115, 05, Address-Lines, 45, 115, , , , ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 40, 07, Address-2, 85, 40, X(40), , True, ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')
		select('JTreeTable', 'cell:name,5(Brand)')
		click('Copy1')
		select('JTreeTable', 'cell:name,10(Location-Active)')
		click('Paste1')
		select('JTreeTable', 'cell:display-length,4(173)')
		rightclick('JTreeTable', 'display-length,4')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:name,11(Brand)')
#		assert_p('JTreeTable', 'Xml~Text', 'Brand', 'name,11')
		select('JTreeTable', 'cell:name,10(Location-Active)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ], [, , , , 115, 05, Address-Lines, 45, 115, , , , ], [, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 40, 07, Address-2, 85, 40, X(40), , True, ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ], [, , , , 3, 03, Brand, 1, 3, x(3), , True, ]]')
		select('JTreeTable', 'rows:[8,9,10,11],columns:[level]')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'rows:[8,9,10,11],columns:[level]')
		select('Table', 'cell:7|name,6(Address-Lines)')
		#assert_p('Table', 'Xml~Text', 'Address-1', '7|name,7')
		select('Table', 'cell:7|name,7(Address-1)')
		#assert_p('Table', 'Content', '[[item, , , 128, 03, Full-Address, 45, 128, , , , ], [item, , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [item, , , 2, 05, Location-Type, 8, 2, XX, , True, ], [item, , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [/item, , , , , , , , , , , ], [item, , , 128, 03, Full-Address, 45, 128, , , , ], [item, , , 115, 05, Address-Lines, 45, 115, , , , ], [item, , , 40, 07, Address-1, 45, 40, X(40), , True, ], [item, , , 40, 07, Address-2, 85, 40, X(40), , True, ], [item, , , 35, 07, Address-3, 125, 35, X(35), , True, ], [/item, , , , , , , , , , , ], [item, , , 10, 05, Postcode, 160, 10, 9(10), true, True, ], [item, , , 3, 05, State, 170, 3, XXX, , True, ], [/item, , , , , , , , , , , ], [item, , , 40, 07, Address-2, 85, 40, X(40), , True, ], [item, , , 1, 03, Location-Active, 173, 1, X, , True, ], [item, , , 3, 03, Brand, 1, 3, x(3), , True, ]]')
		select('Table', 'cell:7|name,7(Address-1)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml'):
			click('No')
		close()
	close()

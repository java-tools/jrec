useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml')
		commonBits.doEdit(click)

		select('JTreeTable', 'cell:Xml~Text,4(null)')
		rightclick('JTreeTable', 'Xml~Text,4')
		select_menu('Expand Tree')
		select('LayoutCombo', 'item')
		select('JTreeTable', 'rows:[5,6],columns:[Xml~Namespace]')
		click('Delete1')
		select('JTreeTable', 'cell:name,4(Ams-Vendor)')
		assert_p('JTreeTable', 'Text', 'Ams-Vendor', 'name,4')
		select('JTreeTable', 'cell:name,5(Full-Address)')

		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , * Location Download, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ], [, , , , 128, 03, Full-Address, 45, 128, , , , ], [, , , , 1, 03, Location-Active, 173, 1, X, , True, ]]')

		select('JTreeTable', 'rows:[3,4,5],columns:[Xml~Prefix]')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'rows:[3,4,5],columns:[Xml~Prefix]')
		select('Table', 'cell:7|name,1(Ams-Vendor)')
		assert_p('Table', 'Text', 'Ams-Vendor', '7|name,1')
		select('Table', 'cell:7|name,2(Full-Address)')

		assert_p('Table', 'Content', '[[XML Comment, ******************************, , , , , , , , , , ], [item, , , 173, 01, Ams-Vendor, 1, 173, , , , ], [item, , , 128, 03, Full-Address, 45, 128, , , , ], [item, , , 115, 05, Address-Lines, 45, 115, , , , ], [item, , , 40, 07, Address-1, 45, 40, X(40), , True, ], [item, , , 40, 07, Address-2, 85, 40, X(40), , True, ], [item, , , 35, 07, Address-3, 125, 35, X(35), , True, ], [/item, , , , , , , , , , , ], [item, , , 10, 05, Postcode, 160, 10, 9(10), true, True, ], [item, , , 3, 05, State, 170, 3, XXX, , True, ], [/item, , , , , , , , , , , ]]')

		select('Table', 'cell:7|name,3(Address-Lines)')
		assert_p('Table', 'RowCount', '11')
		select('Table', 'cell:7|name,3(Address-Lines)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Xml~Prefix,2(* Location Download)')
		click('Delete1')
		select('JTreeTable', 'cell:Xml~Prefix,1(******************************)')
		assert_p('JTreeTable', 'Content', '[[, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , ******************************, , , , , , , , , , ], [, , , , 173, 01, Ams-Vendor, 1, 173, , , , ]]')
		select('JTreeTable', 'cell:Xml~Prefix,2(******************************)')
		assert_p('JTreeTable', 'Text', 'cell:Xml~Prefix,2(******************************)')
		select('JTreeTable', 'cell:Xml~Prefix,2(******************************)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml'):
			click('No')
		close()
	close()

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
		select('JTreeTable', 'cell:Xml~Text,6(null)')
		rightclick('JTreeTable', 'Xml~Text,6')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:Xml~Text,6(null)')
		select('LayoutCombo', 'item')
		select('JTreeTable', 'cell:Xml~Prefix,0(null)')
		rightclick('JTreeTable', 'Xml~Prefix,0')
		select_menu('Paste Record#{s#} Prior')
		select('JTreeTable', 'cell:name,0(Location-Details)')
		assert_p('JTreeTable', 'Text', '', 'name,1')
		select('JTreeTable', 'cell:level,0(03)')
		assert_p('JTreeTable', 'Content', '[[, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ]]')
		select('JTreeTable', 'cell:level,0(03)')
		select_menu('View>>Table View #{Selected Records#}')
##		select('JTreeTable', 'cell:level,0(03)')
		select('Table', 'cell:7|name,1(Location-Number)')
		assert_p('Table', 'Text', 'Location-Number', '7|name,1')
		select('Table', 'cell:7|name,2(Location-Type)')
		assert_p('Table', 'Content', '[[item, , , 41, 03, Location-Details, 4, 41, , , , ], [item, , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [item, , , 2, 05, Location-Type, 8, 2, XX, , True, ], [item, , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [/item, , , , , , , , , , , ]]')
		select('Table', 'cell:7|name,0(Location-Details)')
		assert_p('Table', 'RowCount', '5')
		select('Table', 'cell:7|name,0(Location-Details)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Xml~Prefix,1(null)')
		rightclick('JTreeTable', 'Xml~Prefix,1')
		select_menu('Paste Record#{s#}')
		select('JTreeTable', 'cell:name,2(Location-Details)')
		assert_p('JTreeTable', 'Text', 'Location-Details', 'name,2')
		select('JTreeTable', 'cell:level,2(03)')
		assert_p('JTreeTable', 'Content', '[[, , , , 41, 03, Location-Details, 4, 41, , , , ], [, , , , AMSLOCATIONTEST1.cbl, , , , , , , , ], [, , , , 41, 03, Location-Details, 4, 41, , , , ]]')
		select('JTreeTable', 'cell:display-length,2(41)')
		assert_p('JTreeTable', 'RowCount', '3')
		select('JTreeTable', 'cell:display-length,2(41)')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'cell:display-length,2(41)')
		select('Table', 'cell:7|name,1(Location-Number)')
		assert_p('Table', 'Text', 'Location-Number', '7|name,1')
		select('Table', 'cell:7|name,2(Location-Type)')
		assert_p('Table', 'Content', '[[item, , , 41, 03, Location-Details, 4, 41, , , , ], [item, , , 4, 05, Location-Number, 4, 4, 9(4), true, True, ], [item, , , 2, 05, Location-Type, 8, 2, XX, , True, ], [item, , , 35, 05, Location-Name, 10, 35, X(35), , True, ], [/item, , , , , , , , , , , ]]')
		select('Table', 'cell:7|name,0(Location-Details)')
		assert_p('Table', 'RowCount', '5')
		select('Table', 'cell:7|name,0(Location-Details)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest1.xml'):
			click('No')
		close()
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest2.xml')
		commonBits.doEdit(click)
		select('JTreeTable', 'cell:Tree,4(null)')
		select('LayoutCombo', 'item')
		select_menu('Data>>Add Attributes')
		
		select('Table', 'Test1', 'Atrribute Name,0')
		
		select('Table', 'Test2', 'Atrribute Name,1')
		select('Table', 'cell:Atrribute Name,1()')
		click('Add')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Fully Expand Tree')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,5')
		select_menu('Edit Record')
		select('Table', 'cell:Data,5(Brand)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 3, 3], [level, 6, , 03, 03], [name, 7, , Brand, Brand], [position, 8, , 1, 1], [storage-length, 9, , 3, 3], [picture, 10, , x(3), x(3)], [numeric, 11, , , ], [Test1, 12, , , ], [Test2, 13, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , , ]]')
		select('Table', 'cell:Field,10(Test1)')
		assert_p('Table', 'Text', '13', 'Start,11')
		select('Table', 'cell:Field,11(Test2)')
		assert_p('Table', 'Text', 'Test2', 'Field,11')
		select('Table', 'Test1', 'Data,10')
		select('Table', 'Test2', 'Data,11')
		select('Table', 'cell:Data,11(Test2)')
		assert_p('Table', 'Text', 'Test2', 'Data,11')
		select('Table', 'cell:Data,10(Test1)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 3, 3], [level, 6, , 03, 03], [name, 7, , Brand, Brand], [position, 8, , 1, 1], [storage-length, 9, , 3, 3], [picture, 10, , x(3), x(3)], [numeric, 11, , , ], [Test1, 12, , Test1, Test1], [Test2, 13, , Test2, Test2], [Xml~End, 1, , True, True], [Following~Text, 2, , , ]]')
		click('Right')
		select('Table', 'cell:Data,5(Location-Details)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 41, 41], [level, 6, , 03, 03], [name, 7, , Location-Details, Location-Details], [position, 8, , 4, 4], [storage-length, 9, , 41, 41], [picture, 10, , , ], [numeric, 11, , , ], [Test1, 12, , , ], [Test2, 13, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'AmsLocationTest2.xml'):
			click('Yes')
		close()

		commonBits.doEdit(click)
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Expand Tree')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Edit Record')
		click('Right')
		select('Table', 'cell:Data,9(Test1)')
		assert_p('Table', 'Text', 'Test1', 'Data,9')
		select('Table', 'cell:Data,10(Test2)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 3, 3], [level, 6, , 03, 03], [name, 7, , Brand, Brand], [position, 8, , 1, 1], [storage-length, 9, , 3, 3], [picture, 10, , x(3), x(3)], [Test1, 11, , Test1, Test1], [Test2, 12, , Test2, Test2], [numeric, 13, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , , ]]')
		select('Table', '', 'Data,10')
		select('Table', '', 'Data,9')
		select('Table', 'cell:Data,8(x(3))')
		assert_p('Table', 'Text', 'x(3)', 'Data,8')
		select('Table', 'cell:Data,7(3)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 3, 3], [level, 6, , 03, 03], [name, 7, , Brand, Brand], [position, 8, , 1, 1], [storage-length, 9, , 3, 3], [picture, 10, , x(3), x(3)], [Test1, 11, , , ], [Test2, 12, , , ], [numeric, 13, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Save')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.doEdit(click)
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Xml~Text,4')
		select_menu('Edit Record')
		select('Table', 'cell:Data,7(173)')
		assert_p('Table', 'Content', '[[Xml~Name, 0, , item, item], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [display-length, 5, , 173, 173], [level, 6, , 01, 01], [name, 7, , Ams-Vendor, Ams-Vendor], [position, 8, , 1, 1], [storage-length, 9, , 173, 173], [picture, 10, , , ], [numeric, 11, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
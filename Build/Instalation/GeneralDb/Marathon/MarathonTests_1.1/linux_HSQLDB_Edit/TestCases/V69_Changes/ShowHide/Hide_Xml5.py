useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'IVM0034_Map.XML')

		commonBits.doEdit(click)
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Edit Record')
		click('Right')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 2, 2], [Col, 6, , 15, 15], [LENGTH, 7, , 47, 47], [INITIAL, 8, , Basic Stock Contract - Header Enquiry Selection, Basic Stock Contract - Header Enquiry Selection], [ATTRB, 9, , (ASKIP,BRT), (ASKIP,BRT)], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,21')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 6, 6], [Col, 6, , 8, 8], [LENGTH, 7, , 8, 8], [INITIAL, 8, , , ], [ATTRB, 9, , (ASKIP,NORM), (ASKIP,NORM)], [id, 10, , CONTNO1, CONTNO1], [Type, 11, , Fld, Fld], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		select_menu('Edit>>Show / Hide Fields')
		##click('MetalInternalFrameTitlePane', 181, 17)
		select('Table', 'cell:Show,4(true)')
		select('Table', 'cell:Show,5(true)')
		select('Table', 'cell:Show,6(true)')
		select('Table', 'cell:Show,7(true)')
		select('Table', 'cell:Show,8(true)')
		click('Go')
		commonBits.doSleep()


		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 6, 6], [Type, 11, , Fld, Fld], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		select_menu('Edit>>Show / Hide Fields')
		assert_p('Table', 'Content', '[[Xml~Name, true], [Xml~Prefix, true], [Xml~Namespace, true], [Row, true], [Col, false], [LENGTH, false], [INITIAL, false], [ATTRB, false], [id, false], [Type, true], [Xml~End, true], [Following~Text, true]]')
		select('Table', 'cell:Show,5(false)')
		select('Table', 'cell:Show,6(false)')
		select('Table', 'cell:Show,7(false)')
		click('Go')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 6, 6], [LENGTH, 7, , 8, 8], [INITIAL, 8, , , ], [ATTRB, 9, , (ASKIP,NORM), (ASKIP,NORM)], [Type, 11, , Fld, Fld], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
##		select('Table', '')
		rightclick('Table', 'Data,6')
		select_menu('Show Field Fields>>Col')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 6, 6], [Col, 6, , 8, 8], [LENGTH, 7, , 8, 8], [INITIAL, 8, , , ], [ATTRB, 9, , (ASKIP,NORM), (ASKIP,NORM)], [Type, 11, , Fld, Fld], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
##		select('Table', '')
		rightclick('Table', 'Data,5')
		select_menu('Show Field Fields>>id')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 6, 6], [Col, 6, , 8, 8], [LENGTH, 7, , 8, 8], [INITIAL, 8, , , ], [ATTRB, 9, , (ASKIP,NORM), (ASKIP,NORM)], [id, 10, , CONTNO1, CONTNO1], [Type, 11, , Fld, Fld], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
##commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

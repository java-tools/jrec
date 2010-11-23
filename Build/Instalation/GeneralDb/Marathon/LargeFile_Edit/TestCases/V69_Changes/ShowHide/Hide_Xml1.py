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
		click('ScrollPane$ScrollBar', 5, 65)
#		select('JTreeTable', '')
		rightclick('JTreeTable', 'id,3')
		select_menu('Edit Record')
#		select('Table', '')
		rightclick('Table', 'Data,4')
		select_menu('Hide Column')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 1, 1], [LENGTH, 7, , 23, 23], [INITIAL, 8, ,  REPLENISHMENT ORDERING,  REPLENISHMENT ORDERING], [ATTRB, 9, , (ASKIP,BRT), (ASKIP,BRT)], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
##		select('Table', '')
		rightclick('Table', 'Data,5')
		select_menu('Hide Column')
##		select('Table', '')
		rightclick('Table', 'Data,5')
		select_menu('Hide Column')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 1, 1], [LENGTH, 7, , 23, 23], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		select('Table', 'cell:Data,4(23)')
		select('Table', 'cell:Data,4(23)')
		rightclick('Table', 'Data,4')
		select_menu('Show Field Fields>>INITIAL')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 1, 1], [LENGTH, 7, , 23, 23], [INITIAL, 8, ,  REPLENISHMENT ORDERING,  REPLENISHMENT ORDERING], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
##		select('Table', '')
		rightclick('Table', 'Data,6')
		select_menu('Show Field Fields>>ATTRB')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 1, 1], [LENGTH, 7, , 23, 23], [INITIAL, 8, ,  REPLENISHMENT ORDERING,  REPLENISHMENT ORDERING], [ATTRB, 9, , (ASKIP,BRT), (ASKIP,BRT)], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
##		select('Table', '')
		rightclick('Table', 'Data,3')
		select_menu('Show Field Fields>>Col')
		assert_p('Table', 'Content', '''[[Xml~Name, 0, , Field, Field], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Row, 5, , 1, 1], [Col, 6, , 26, 26], [LENGTH, 7, , 23, 23], [INITIAL, 8, ,  REPLENISHMENT ORDERING,  REPLENISHMENT ORDERING], [ATTRB, 9, , (ASKIP,BRT), (ASKIP,BRT)], [id, 10, , , ], [Type, 11, , , ], [Xml~End, 1, , True, True], [Following~Text, 2, , 
, 
]]''')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

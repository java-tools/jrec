useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'recent')
		select('TabbedPane', 'Xml')
		select('FilePane$3', 'recent')
		select('FilePane$3', 'recent')
		doubleclick('FilePane$3', '4')
		select('FilePane$3', 'recent')
		assert_p('JTreeTable', 'Content', r'''[[, false, , ], [, , , ], [, , , 
], [, , , 
], [, , , E:\Downloads\StAX.txt], [, , , 1335], [, , , Cp1252], [, , , 
], [, , , E:\Work\RecordEdit\Changes\Version061a\Changes061a.dcf], [, , , 0], [, , , Cp1252], [, , , 
], [, , , E:\Work\MyAccessDBs\RecordEditor\SAR4180B.xml], [, , , 0], [, , , Cp1252], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
], [, , , 
]]''')
		select('FilePane$3', 'recent')
		click('Edit4')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', r'''[[, , UTF-8, 1.0, , ], [, , <!-- Exception scanning External DTD Subset.  True contents of DTD cannot be determined.  Processing will continue as XMLInputFactory.IS_VALIDATING == false. -->, , , ], [, , , , , 
], [, , , , , 
], [, , , , , E:\Downloads\StAX.txt], [, , , , , 1335], [, , , , , Cp1252], [, , , , , 
], [, , , , , E:\Work\RecordEdit\Changes\Version061a\Changes061a.dcf], [, , , , , 0], [, , , , , Cp1252], [, , , , , 
], [, , , , , E:\Work\MyAccessDBs\RecordEditor\SAR4180B.xml], [, , , , , 0], [, , , , , Cp1252], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
], [, , , , , 
]]''')
##		select('LineTree.FileDisplay_JTbl', '')
		rightclick('LineTree.FileDisplay_JTbl', 'Tree,4')
		select_menu('Edit Record')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', r'[[Xml~Name, 0, , PATH, PATH], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , E:\Downloads\StAX.txt, E:\Downloads\StAX.txt]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '''[[Xml~Name, 0, , /PATH, /PATH], [Xml~End, 1, , , ], [Following~Text, 2, , 
, 
]]''')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Xml~Name, 0, , CARET, CARET], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , 1335, 1335]]')
		click('Right')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Xml~Name, 0, , ENCODING, ENCODING], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , Cp1252, Cp1252]]')
		click('Right')
		click('Right')
		click('Right')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', r'[[Xml~Name, 0, , PATH, PATH], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , E:\Work\RecordEdit\Changes\Version061a\Changes061a.dcf, E:\Work\RecordEdit\Changes\Version061a\Changes061a.dcf]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

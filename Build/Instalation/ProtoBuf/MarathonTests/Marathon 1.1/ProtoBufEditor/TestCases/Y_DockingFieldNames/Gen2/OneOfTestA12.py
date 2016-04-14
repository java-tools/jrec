useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.7.0_51'

	if window(commonBits.applicationName()):
		select('File_Txt', commonBits.stdCopybookDir() + 'oneOfTestA.bin')
		select('Proto Definition_Txt', commonBits.stdCopybookDir() + 'oneOfTestA.proto')
		click('Edit1')
		select('TabbedPane', 'Record: ')
		select('LineFrameTree.FileDisplay_JTbl', '123', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Text,0(123)')
		click('New2')

		if window('Record Selection'):
			click('OK')
		close()

		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '21', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '1', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '10.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', 'SALE', 'Data,1')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Data,1(OTHER)')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
##		select('LineTreeChild.FileDisplay_JTbl', '')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Expand Tree')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 21, SALE, 1, 10.0, , , , , , , ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')


		if window('Record Selection'):
			click('OK')
		close()


##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '543', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '1', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '11.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', '10.0', 'Data,4')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,4(10.0)')


		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 543, OTHER, 1, 11.0, 10.0, , , , , , ]]')


		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Insert Record#{s#}')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')

		if window('Record Selection'):
			select('OptionPane.comboBox', 'transfer')
			click('OK')
		close()

##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '654', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '1', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '110.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', '100.0', 'Data,4')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,4(100.0)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[item, 1, , STRING, 654, 654], [type, 2, , ENUM, OTHER, OTHER], [qty, 3, , INT32, 1, 1], [price, 4, , DOUBLE, 110.0, 110.0], [aveCost, 5, , DOUBLE, 100.0, 100.0]]')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,4(100.0)')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 654, OTHER, 1, 110.0, 100.0, , , , , , ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
##		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
##		select_menu('Edit Record')
		select('TabbedPane', 'Record: ')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[name, 1, , STRING, 123, 123], [intFld, 2, , INT32, , ], [strFld, 3, , STRING, , ], [doubleFld, 4, , DOUBLE, , ], [boolFld, 5, , BOOL, , ], [typeOfSale, 6, , ENUM, , ], [intFld2, 7, , INT32, , ], [strFld2, 8, , STRING, , ], [doubleFld2, 9, , DOUBLE, , ], [boolFld2, 10, , BOOL, , ], [typeOfSale2, 11, , ENUM, , ]]')
		click('New2')

		if window('Record Selection'):
			select('OptionPane.comboBox', 'purchase')
			click('OK')
		close()

##		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
##		click('New3')

##		if window('Record Selection'):
##			click('OK')
##		close()

##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '135', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '1', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '110.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', '80.0', 'Data,4')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,4(80.0)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[item, 1, , STRING, 135, 135], [type, 2, , ENUM, OTHER, OTHER], [qty, 3, , INT32, 1, 1], [price, 4, , DOUBLE, 110.0, 110.0], [cost, 5, , DOUBLE, 80.0, 80.0]]')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 135, OTHER, 1, 110.0, 80.0, , , , , , ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')

		if window('Record Selection'):
			select('OptionPane.comboBox', 'transfer')
			click('OK')
		close()

##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '246', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '20', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '220.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', '110.0', 'Data,4')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Data,3(220.0)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[item, 1, , STRING, 246, 246], [type, 2, , ENUM, OTHER, OTHER], [qty, 3, , INT32, 20, 20], [price, 4, , DOUBLE, 220.0, 220.0], [aveCost, 5, , DOUBLE, 110.0, 110.0]]')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 246, OTHER, 20, 220.0, 110.0, , , , , , ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')

		if window('Record Selection'):
			select('OptionPane.comboBox', 'purchase')
			click('OK')
		close()

##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '886644', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '30', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '3330.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', '2000.0', 'Data,4')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,4(2000.0)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[item, 1, , STRING, 886644, 886644], [type, 2, , ENUM, OTHER, OTHER], [qty, 3, , INT32, 30, 30], [price, 4, , DOUBLE, 3330.0, 3330.0], [cost, 5, , DOUBLE, 2000.0, 2000.0]]')
##		click('DisplayFrame$TabButton', 62, 23)
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 886644, OTHER, 30, 3330.0, 2000.0, , , , , , ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')

		if window('Record Selection'):
			click('OK')
		close()

##		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '951', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', '1', 'Data,2')
		select('LineFrameTree.FileDisplay_JTbl1', '10.0', 'Data,3')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Data,2(1)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[item, 1, , STRING, 951, 951], [type, 2, , ENUM, OTHER, OTHER], [qty, 3, , INT32, 1, 1], [value, 4, , DOUBLE, 10.0, 10.0]]')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, , , , , , , , , , ], [, , 951, OTHER, 1, 10.0, , , , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window(r'Save Changes to file: ' +  commonBits.stdCopybookDir() + 'oneOfTestA.bin'):
			click('No')
		close()
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window(commonBits.applicationName()):
		select('File_Txt', commonBits.sampleDir() + 'xTestExt01b.bin')
		select('Proto Definition_Txt', commonBits.stdCopybookDir() + 'Extension01.proto')
		assert_p('Type of Definition_Txt', 'Text', 'Proto Definition')
		commonBits.doSleep()

		assert_p('Proto Definition_Txt', 'Text',  commonBits.stdCopybookDir() + 'Extension01.proto')
		assert_p('Proto Definition_Txt', 'Text',  commonBits.stdCopybookDir() + 'Extension01.proto')
		commonBits.doSleep()

		assert_p('Proto File_Txt', 'Text', 'Extension01.proto')

		assert_p('Primary Message_Txt', 'Text', 'Message')

		click('Edit1')
		select('TabbedPane', 'Record: ')
		select('LineFrameTree.FileDisplay_JTbl', '123', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl', 'n 123', 'Data,1')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,0(123)')
##		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[A, 1, , UINT64, 123, 123], [name, 1, , STRING, n 123, n 123]]')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[A, 1, , UINT64, 123, 123], [name, 2, , STRING, n 123, n 123]]')
		click('New2')
		select('TabbedPane', 'Record:')
		select('LineFrameTree.FileDisplay_JTbl1', '456', 'Data,0')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,0(456)')
		assert_p('LineFrameTree.FileDisplay_JTbl1', 'Content', '[[B, 1, , UINT64, 456, 456]]')
		select('LineFrameTree.FileDisplay_JTbl1', 'cell:Text,0(456)')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Fully Expand Tree')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, n 123], [, , , ]]')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, n 123], [, , 456, ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')
		click('BasicInternalFrameTitlePane$NoFocusButton1')
		click('MetalInternalFrameTitlePane', 302, 11)
		assert_p('TextArea3', 'Text', '''

Only one record allowed in the file''')
		click('Save')
		click('MetalInternalFrameTitlePane', 511, 11)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Edit1')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 123, n 123], [, , 456, ]]')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		click('New1')
		click('Delete2')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('TextArea1', 'Text', '''

Only one record allowed in the file

Only one record allowed in the file''')
	close()

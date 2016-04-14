useFixture(default)

def test():
	from Modules import commonBits
##	import time
	java_recorded_version = '1.6.0_22'

	if window(commonBits.applicationName()):
		select('File_Txt', commonBits.sampleDir() + 'testExt02.bin')
		select('Proto Definition_Txt', commonBits.stdCopybookDir() + 'Extension02.proto')

##		select('Proto Definition_Txt', commonBits.stdCopybookDir() + 'Extension02.proto')
		select('File Structure_Txt', 'Delimited Messages')

##		time.sleep(0.5)
		assert_p('Proto File_Txt', 'Text', 'Extension02.proto')

	
		assert_p('Primary Message_Txt', 'Text', 'Message')
		click('Edit1')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 234, ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , 345, Msg 2], [, , , ], [, , , ]]')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 234, , ], [, , 456, Event 1, [\'ttt\',\'uu\',\'vvv\',\'www\']], [, , this is a note, , ], [, , , , ], [, , note 1, , ], [, , Note 2, , ], [, , 345, Msg 2, ], [, , 1331, Msg2 Event, []], [, , blaaa .., , ]]')
##		select('LineTreeChild.FileDisplay_JTbl', '')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Edit Record')
		select('TabbedPane', 'Record: ')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,0(234)')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[A, 1, , UINT64, 234, 234], [name, 2, , STRING, , ]]')
		click('Down')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[B, 1, , UINT64, 456, 456], [eventName, 2, , STRING, Event 1, Event 1], [qtab, 2, , STRING, [\'ttt\',\'uu\',\'vvv\',\'www\'], [\'ttt\',\'uu\',\'vvv\',\'www\']]]')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,2([\'ttt\',\'uu\',\'vvv\',\'www\'])')
		click('ArrowButton')
##		select('LineFrameTree.FileDisplay_JTbl', '')
		assert_p('Table', 'Content', '[[0, ttt], [1, uu], [2, vvv], [3, www]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Down')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,0(this is a note)')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[text, 1, , STRING, this is a note, this is a note]]')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,0(this is a note)')
		click('Up')
		click('Right')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[text, 1, , STRING, note 1, note 1]]')
		click('Right')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[text, 1, , STRING, Note 2, Note 2]]')

##		if window(r'Save Changes to file: C:\Users\BruceTst/.RecordEditor/ProtoBuf\SampleFiles/testExt02.bin'):
##			click('No')
##		close()
	close()

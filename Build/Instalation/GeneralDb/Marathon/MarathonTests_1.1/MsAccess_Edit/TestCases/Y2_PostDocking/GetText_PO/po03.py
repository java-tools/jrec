useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'po/multiline_context.po')
		click(commonBits.fl('Edit') + '1')

		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')
		select_menu(commonBits.fl('Show Column') + '>>msgctxt')
		select('PoList.FileDisplay_JTbl', 'cell:4|comments,1(Test quotes)')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '''[[Noun. A person that changes his or her ways., convert, bekeerling, Test multiline context], [Verb. Converting from "something" to "something else"., convert, omskakel, Test quotes], [Verb.
Converting from "something" to "something else"., convert, omskakel, Test quotes, newlines and multiline.]]''')
		select('PoList.FileDisplay_JTbl', 'cell:4|comments,1(Test quotes)')

		select('PoList.FileDisplay_JTbl', 'cell:4|comments,2(Test quotes, newlines and multiline.)')
		select('PoList.FileDisplay_JTbl', 'cell:4|comments,2(Test quotes, newlines and multiline.)')
##		select('PoList.FileDisplay_JTbl', 'cell:4|comments,2(Test quotes, newlines and multiline.)')
##		keystroke('PoList.FileDisplay_JTbl', 'F8', '4|comments,2')
##		keystroke('TextArea1', 'Context Menu')
##		keystroke('TextArea2', 'Context Menu')
##		keystroke('TextArea2', 'F8')
##		keystroke('SplitPane', 'Context Menu')
##		keystroke('SplitPane', 'F8')
		select('Table', 'cell:' + commonBits.fl('Line') + ',0(1)')
		rightclick('Table', commonBits.fl('Line') + ',0')
		select_menu(commonBits.fl('Edit Record'))
		select('TabbedPane', 'Single PO Record')
		assert_p('TextArea4', 'Text', 'Noun. A person that changes his or her ways.')
		click('Right')
		assert_p('TextArea4', 'Text', 'Verb. Converting from "something" to "something else".')
		click('Right')

##		assert_p('TextArea4', 'Text', '''Verb.
##Converting from "something" to "something else".''')


		assert_p('TextArea4', 'Text', 'Verb.\nConverting from "something" to "something else".')
		
		assert_p('TextArea4', 'Text', '''Verb.
Converting from "something" to "something else".''')
	close()

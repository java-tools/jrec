useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'po/escapes_cr_in_msgid_and_msgstr.po')
		click(commonBits.fl('Edit') + '1')

		assert_p('TextArea', 'Text', '''msgid with _
_ escape
''')
		assert_p('TextArea1', 'Text', '''msgstr with _
_ escape
''')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('File_Txt',  commonBits.sampleDir() + 'po/flags.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,1(test2)')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,1(test2)')
##		select('PoList.FileDisplay_JTbl1', 'test3', '3|msgstr,2')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,2(test3)')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,2(test3)')
		select('Table1', 'cell:' + commonBits.fl('Line') + ',2(3)')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line]]')
		select('Table1', 'cell:' + commonBits.fl('Line') + ',2(3)')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
##		select('PoList.FileDisplay_JTbl', '')

		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')
		select_menu(commonBits.fl('Show Column') + '>>fuzzy')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[test1, test1, fuzzy then no-c-format on separate lines, Y], [test2, test2, no-c-format then fuzzy on separate lines, Y], [test3, test3, fuzzy then no-c-format on same line, Y], [test4, test4, no-c-format then fuzzy on same line, Y]]')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[test1, test1, fuzzy then no-c-format on separate lines, Y], [test2, test2, no-c-format then fuzzy on separate lines, Y], [test3, test3, fuzzy then no-c-format on same line, Y], [test4, test4, no-c-format then fuzzy on same line, Y]]')
##		select('Table', '')

##		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')
		rightclick('Table', commonBits.fl('Line') + ',0')
		select_menu(commonBits.fl('Edit Record'))
		select('TabbedPane', 'Single PO Record')
		assert_p('TextArea14', 'Text', '''
no-c-format''')
		assert_p('TextArea11', 'Text', 'fuzzy then no-c-format on separate lines')
		assert_p('TextArea8', 'Text', 'test1')
		assert_p('Fuzzy', 'Text', 'true')
		click('Right')
		assert_p('TextArea14', 'Text', '''no-c-format
''')
		assert_p('Fuzzy', 'Text', 'true')
		assert_p('TextArea11', 'Text', 'no-c-format then fuzzy on separate lines')
		click('Right')
		assert_p('TextArea14', 'Text', 'no-c-format')
		assert_p('Fuzzy', 'Text', 'true')
		click('Right')
		assert_p('TextArea14', 'Text', 'no-c-format')
		assert_p('Fuzzy', 'Text', 'true')
		assert_p('TextArea11', 'Text', 'no-c-format then fuzzy on same line')
		click(commonBits.fl('Fuzzy/Blank'))
		select('TabbedPane', 'Fuzzy/Blank')
		select('Table1', 'cell:' + commonBits.fl('Line') + ',2(3)')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line]]')
	close()

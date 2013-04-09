useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/content_end_with_eol.po')
		click(commonBits.fl('Edit') + '1')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '''[[ends with escaped EOL: 
, ends with escaped EOL: 
, ]]''')
		assert_p('TextArea', 'Text', '''ends with escaped EOL: 
''')
		assert_p('TextArea1', 'Text', '''ends with escaped EOL: 
''')


		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')
		select_menu(commonBits.fl('Edit Record'))


		select('TabbedPane', 'Single PO Record')
##		assert_p('TextArea3', 'Text', '''ends with escaped EOL: 
##''')
		assert_p('TextArea4', 'Text', '''ends with escaped EOL: 
''')
		assert_p('TextArea5', 'Text', '''ends with escaped EOL: 
''')

		assert_p('Fuzzy', 'Text', 'false')
		assert_p('obsolete', 'Text', 'false')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('File_Txt', commonBits.sampleDir() + 'po/escapes_comment_fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')
		assert_p('TextArea4', 'Text', r'''# SOME DESCRIPTIVE TITLE.
FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.
src\resources\valid\escapse_comment.po''')
		assert_p('TextArea3', 'Text', 'test')
		assert_p('TextArea5', 'Text', 'test')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')


		rightclick('PoList.FileDisplay_JTbl1', '2|msgid,0')
		select_menu(commonBits.fl('Show Column') + '>>fuzzy')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', r'''[[test, test, # SOME DESCRIPTIVE TITLE.
FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.
src\resources\valid\escapse_comment.po, Y]]''')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0(test)')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		assert_p('PoList.FileDisplay_JTbl', 'Content', r'''[[test, test, # SOME DESCRIPTIVE TITLE.
FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.
src\resources\valid\escapse_comment.po]]''')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		select_menu('Record Layouts>>Load Copybooks from Xml')
		select_menu('Record Layouts>>Load Copybooks from Xml')
#		select('File Name', r'C:\Users\BruceTst\RecordEditor_HSQL\CopyBook\Xml\asd')
##
		if commonBits.isWindowsLook():
			select('File name', commonBits.paramDir() + r'Xml\asd')
		else: 
			select('File Name', commonBits.paramDir() + r'Xml\asd')
##		select('File name', commonBits.paramDir() + r'Xml\asd')


##		select('File name', r'C:\Users\BruceTst\RecordEditor_HSQL\Xml\xx')
		click('Load From Directory')
#		assert_p('TextArea', 'Text', r'C:\Users\BruceTst\RecordEditor_HSQL\CopyBook\Xml\asd is not a directory !!!')
		assert_p('TextArea', 'Text', commonBits.paramDir() + r'Xml\asd is not a directory !!!')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Load Copybooks from Cobol Directory')
#		select('File Name', r'C:\Users\BruceTst\RecordEditor_HSQL\CopyBook\Xml\sdf')
##		select('File name', commonBits.paramDir() + r'Xml\sdf')

		if commonBits.isWindowsLook():
			select('File name', commonBits.paramDir() + r'Xml\sdf')
		else: 
			select('File Name', commonBits.paramDir() + r'Xml\sdf')

		click('Load')
#		assert_p('TextArea', 'Text', r'C:\Users\BruceTst\RecordEditor_HSQL\CopyBook\Xml\sdf is not a directory !!!')
		assert_p('TextArea', 'Text', commonBits.paramDir() + r'Xml\sdf is not a directory !!!')
##		select('File Name', r'C:\Users\BruceTst\RecordEditor_HSQL\CopyBook\Xml\*')
		click('Load')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

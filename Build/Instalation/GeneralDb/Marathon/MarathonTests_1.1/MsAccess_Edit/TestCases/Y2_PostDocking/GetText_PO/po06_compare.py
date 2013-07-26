useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/tst1Fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		click('Export')
		select('File Name_Txt', commonBits.sampleDir() + 'po/z_tst1Fuzzy.po')
		click(commonBits.fl('Save File'))
		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare Menu'))
		click('*1')
		select('Old File_Txt', commonBits.sampleDir() + 'po/tst1Fuzzy.po')
		select('New File_Txt', commonBits.sampleDir() + 'po/z_tst1Fuzzy.po')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click(commonBits.fl('Compare'))
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()

useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg543')
		select('RecordList.Description_Txt', '%')
		select('TabbedPane', 'Extras')

		click('Delete3')

		if window('Delete: zx3e3xzFLDg543'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

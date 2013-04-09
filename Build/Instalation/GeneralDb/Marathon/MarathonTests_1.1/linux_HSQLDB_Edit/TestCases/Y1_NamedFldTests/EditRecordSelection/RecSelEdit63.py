useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg543')
		select('RecordList.Description_Txt', '%')
		select('TabbedPane', 'Extras')

		click('Delete3')

		if window('Delete: zx33xzFLDg543'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

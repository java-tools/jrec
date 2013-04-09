useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg543')
		select('RecordList.Description_Txt', '%')
		##select('TabbedPane', 'Extras')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3e3xzFLDg543')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

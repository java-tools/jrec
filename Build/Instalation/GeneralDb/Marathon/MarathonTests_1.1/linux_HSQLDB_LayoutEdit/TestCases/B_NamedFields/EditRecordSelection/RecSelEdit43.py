useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg543')
		select('RecordList.Description_Txt', '%')
		select('TabbedPane', commonBits.fl('Extras'))

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg543')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

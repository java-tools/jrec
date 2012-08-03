useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLD1')
		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLD1')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3xzFLD2')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLD2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3xzFLD3')

		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLD3')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

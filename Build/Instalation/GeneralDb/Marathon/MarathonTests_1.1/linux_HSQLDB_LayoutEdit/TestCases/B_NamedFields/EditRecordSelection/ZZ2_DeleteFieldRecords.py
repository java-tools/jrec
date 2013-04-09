useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLD1')
		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLD1')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLD2')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLD2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLD3')

		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLD3')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

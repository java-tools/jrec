useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxzxzFLD1')
		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLD1')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLD2')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLD2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLD3')

		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLD3')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

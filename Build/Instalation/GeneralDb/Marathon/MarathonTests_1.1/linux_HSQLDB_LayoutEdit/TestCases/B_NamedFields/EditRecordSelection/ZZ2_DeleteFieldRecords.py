useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLD1')
		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zxxxzFLD1'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLD2')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zxxxzFLD2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLD3')

		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zxxxzFLD3'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

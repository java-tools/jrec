useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLD1')
		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zx3xzFLD1'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3xzFLD2')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zx3xzFLD2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3xzFLD3')

		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zx3xzFLD3'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
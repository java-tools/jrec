useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxzxzFLD1')
		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zxzxzFLD1'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLD2')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zxzxzFLD2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLD3')

		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zxzxzFLD3'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

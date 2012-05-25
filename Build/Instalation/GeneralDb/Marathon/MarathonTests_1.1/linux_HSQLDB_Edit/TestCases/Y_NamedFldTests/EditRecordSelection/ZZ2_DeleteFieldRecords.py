useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		click('*')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLD1')
		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zx33xzFLD1'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLD2')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zx33xzFLD2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLD3')

		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zx33xzFLD3'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

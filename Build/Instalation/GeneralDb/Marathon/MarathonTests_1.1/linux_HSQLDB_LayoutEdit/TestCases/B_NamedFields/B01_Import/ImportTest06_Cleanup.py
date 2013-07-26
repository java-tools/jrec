useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'ZZ_Tst5%')

		select('RecordList.Description_Txt', '%')

		click('Delete')

		if window('Delete: ZZ_Tst5'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'ZZ_Tst4%')

		select('RecordList.Description_Txt', '%%')

		click('Delete1')
		click('Delete')

		if window('Delete: ZZ_Tst4'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'ZZ_Tst3%')

		select('RecordList.Description_Txt', '%')

		click('Delete')

		if window('Delete: ZZ_Tst3'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg56')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zxxxzFLDg56'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg567')

		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zxxxzFLDg567'):
			click('Yes')
		close()


	close()

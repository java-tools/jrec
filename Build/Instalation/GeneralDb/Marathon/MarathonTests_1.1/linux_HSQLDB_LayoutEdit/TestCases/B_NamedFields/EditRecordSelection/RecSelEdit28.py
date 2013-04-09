useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg56')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg56')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg567')

		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg567')):
			click('Yes')
		close()


	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg543')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3g3xzFLDg543')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg5432')

##		select('RecordDef.Record Name_Txt', 'zx3g3xzFLDg5432%')
		select('RecordList.Description_Txt', '%%')
##		select('RecordDef.Record Name_Txt', 'zx3g3xzFLDg5432')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3g3xzFLDg5432')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

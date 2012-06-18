useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg543')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zx3g3xzFLDg543'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3g3xzFLDg5432')

##		select('RecordDef.Record Name_Txt', 'zx3g3xzFLDg5432%')
		select('RecordList.Description_Txt', '%%')
##		select('RecordDef.Record Name_Txt', 'zx3g3xzFLDg5432')
		click('Delete3')

		if window('Delete: zx3g3xzFLDg5432'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

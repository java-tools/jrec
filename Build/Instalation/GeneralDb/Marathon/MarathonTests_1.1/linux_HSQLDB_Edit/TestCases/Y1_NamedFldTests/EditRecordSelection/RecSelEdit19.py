useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg89')
		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zx33xzFLDg89'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg88')
		select('RecordList.Description_Txt', '%%')

		click('Delete3')

		if window('Delete: zx33xzFLDg88'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

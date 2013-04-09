useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxxxzFLDg89')
		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg89')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg88')
		select('RecordList.Description_Txt', '%%')

		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg88')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

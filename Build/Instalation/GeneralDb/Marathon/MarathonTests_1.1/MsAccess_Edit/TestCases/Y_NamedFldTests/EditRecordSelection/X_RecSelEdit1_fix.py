useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')
		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		select('RecordSelectionJTbl', 'rows:[0,1,2],columns:[Field Value]')
		click('Delete2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

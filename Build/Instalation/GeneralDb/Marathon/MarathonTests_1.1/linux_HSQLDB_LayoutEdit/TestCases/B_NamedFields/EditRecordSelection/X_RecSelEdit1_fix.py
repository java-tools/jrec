useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')
		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		select('RecordSelectionJTbl', 'rows:[0,1,2],columns:[' + commonBits.fl('Field Value') + ']')
		commonBits.delete2(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

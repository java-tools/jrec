useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg654')

		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg654')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg65')

		select('RecordList.Description_Txt', '%%')

#		select('TabbedPane', 'Child Records')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg65')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

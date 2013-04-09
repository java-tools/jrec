useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		##select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg1')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
##		commonBits.delete3(click)
##		if window('Delete: zxzxzFLDg1'):
##			click('Yes')
##		close()

##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

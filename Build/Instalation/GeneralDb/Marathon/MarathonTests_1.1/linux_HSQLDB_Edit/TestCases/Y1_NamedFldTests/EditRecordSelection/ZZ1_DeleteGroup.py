useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		click('*')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx33xzFLDg1')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx33xzFLDg1'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

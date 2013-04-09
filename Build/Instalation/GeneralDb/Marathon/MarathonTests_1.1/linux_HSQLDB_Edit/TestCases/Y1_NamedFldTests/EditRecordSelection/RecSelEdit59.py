useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg654')

		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , zx33xzFLD3], [, zx33xzFLD2, , , , , zx33xzFLD1], [, zx33xzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx33xzFLDg654'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg65')

		select('RecordList.Description_Txt', '%%')

#		select('TabbedPane', 'Child Records')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , zx33xzFLD3], [, zx33xzFLD2, , , , , zx33xzFLD1], [, zx33xzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx33xzFLDg65'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg654')

		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx3e3xzFLDg654'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg65')

		select('RecordList.Description_Txt', '%%')

#		select('TabbedPane', 'Child Records')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx3e3xzFLDg65'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

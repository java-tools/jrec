useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'xx11xx')

		select('RecordList.Description_Txt', '%')


		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		click('Delete3')

		if window('Delete: xx11xx'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'xx11xx11')

##		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		click('Delete3')

		if window('Delete: xx11xx11'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()


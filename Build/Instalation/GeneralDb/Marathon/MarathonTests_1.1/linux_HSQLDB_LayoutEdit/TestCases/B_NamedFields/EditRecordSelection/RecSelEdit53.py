useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx3e3xzFLDg654')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , , , , ]]')
#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 12, =, 12], [Or, , fld 12, =, 21], [Or, , fld 13, =, 13]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 31, =, 13]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 12, =, 12], [, ,   , Or  ,   , fld 12, =, 21], [, ,   , Or  ,   , fld 13, =, 13], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 31, =, 13]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg56')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field Value,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 22], [Or, , fld 11, =, 33]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 22], [, ,   , Or  ,   , fld 11, =, 33], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()


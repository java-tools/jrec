useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zxzxzFLD2)')
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		click('Save1')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD2]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2aaa')
		click(commonBits.fl('Save As'))




		if window('Input'):
			click('Cancel')
		close()

		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD2aaa]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2(zx3xzFLD2)')
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',2')
##		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ], [, zx3xzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0()')
		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

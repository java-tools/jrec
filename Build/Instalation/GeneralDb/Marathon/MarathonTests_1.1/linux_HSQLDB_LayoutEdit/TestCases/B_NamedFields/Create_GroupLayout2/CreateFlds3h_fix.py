useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zx3xzFLD2)')
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0()')
		commonBits.save1(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

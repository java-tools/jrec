useFixture(default)

def test():
	from Modules import commonBits
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD2]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2aaa')
		click(commonBits.fl('Save As'))




		if window('Input'):
			click('Cancel')
		close()

		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD2aaa]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2(zxzxzFLD2)')
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',2')
##		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',2()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ], [, zxzxzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

		time.sleep(1)
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0()')
		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

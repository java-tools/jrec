useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		commonBits.delete2(click)
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('RecordDef.System_Txt', 'Unkown')

		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zx3xzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2aaa')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2asd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2asd], [, zx3xzFLD2asd, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1()')
		commonBits.delete2(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		commonBits.delete2(click)
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

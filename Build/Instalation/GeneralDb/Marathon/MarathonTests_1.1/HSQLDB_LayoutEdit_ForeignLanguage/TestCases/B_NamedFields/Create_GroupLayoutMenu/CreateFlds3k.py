useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

 
	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		commonBits.delete2(click)
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records')
)

		select('RecordDef.System_Txt', 'Unkown')

		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2aaa')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2asd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2asd], [, zxzxzFLD2asd, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1()')
		commonBits.delete2(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',0()')
		commonBits.delete2(click)
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

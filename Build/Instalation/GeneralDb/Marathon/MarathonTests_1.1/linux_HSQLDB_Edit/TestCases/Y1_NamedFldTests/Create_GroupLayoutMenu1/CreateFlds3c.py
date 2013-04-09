useFixture(default)

def test():
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		select_menu('Record Layouts>>Edit Layout')
		select_menu('Record Layouts>>Edit Layout')

		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		
		select('RecordList.Description_Txt', '%')


		select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell: ,0(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Right')

		time.sleep(0.6)
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD3')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

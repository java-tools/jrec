useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxxxzFLDg88')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu( commonBits.fl('Edit Record Selections'))
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [' + commonBits.fl('Or') + ', , fld 11, =, 121]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [' + commonBits.fl('Or') + ', , fld 32, =, 32]]')
		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 31, =, 31], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 32, =, 32]]')
		select('TabbedPane', 'zxxxzFLD3')
		select('RecordSelectionJTbl2', 'cell:' + commonBits.fl('Field Value') + ',0(31)')
		
		if  commonBits.isTstLanguage():
			click( commonBits.fl('Delete') + '2')
		else:
			click('Delete4')

		select('TabbedPane', commonBits.fl('Summary'))
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 11, =, 11], [, ,   , ' + commonBits.fl('Or') + '  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , ' + commonBits.fl('And') + '  ,   ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

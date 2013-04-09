useFixture(default)


###
###                  Check Carefully if fails
###                 *------------------------* 
###
###  This test was added after problems with running in Embeded mode (closing Connection)
###  It runs multiple updates and looks for queries not being refereshed ....
###

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Layout Definitions'):
		select('TextArea', '')
		click('BaseHelpPanel', 111, 115)
		click('*')
		commonBits.new1(click)
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane', commonBits.fl('Fields'))
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane', commonBits.fl('Fields'))
		select('TextField2', 'xwz 1')
		click(commonBits.fl('Insert'))
		click(commonBits.fl('Insert'))
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '4', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'z', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', '5', commonBits.fl('Position') + ',1')
		select('RecordFieldsJTbl', '5', commonBits.fl('Length') + ',1')
		select('RecordFieldsJTbl', 'f1', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', '10', commonBits.fl('Position') + ',2')
		select('RecordFieldsJTbl', '5', commonBits.fl('Length') + ',2')
		select('RecordFieldsJTbl', 'f2', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(f2)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(f1)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ], [5, 5, f1, , 0, 0, 0, , , ], [10, 5, f2, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(z)')
		commonBits.save1(click)
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xwz 2')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(f2)')
		##commonBits.delete2(click)
		commonBits.delete2(click)

		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		commonBits.delete2(click)
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '4', commonBits.fl('Position') + ',1')
		select('RecordFieldsJTbl', '44', commonBits.fl('Length') + ',1')
		select('RecordFieldsJTbl', 'f', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '5', commonBits.fl('Position') + ',2')
		select('RecordFieldsJTbl', '5', commonBits.fl('Length') + ',2')
		select('RecordFieldsJTbl', '5', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2()')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '6', commonBits.fl('Position') + ',3')
		select('RecordFieldsJTbl', '6', commonBits.fl('Length') + ',3')
		select('RecordFieldsJTbl', '6', commonBits.fl('FieldName') + ',3')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		assert_p('RecordFieldsJTbl', 'RowCount', '4')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',3()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ], [4, 44, f, , 0, 0, 0, , , ], [5, 5, 5, , 0, 0, 0, , , ], [6, 6, 6, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',3(6)')
		commonBits.delete2(click)
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(5)')
		commonBits.delete2(click)
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(f)')
		commonBits.delete2(click)
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(z)')
		assert_p('RecordFieldsJTbl', 'ColumnCount', '10')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'RowCount', '1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(z)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(z)')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xwz 3')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xwz 4')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xwz 5')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		select('TextField', 'xwz 5')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		select('TextField1', '%')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		commonBits.delete3(click)

		if window( commonBits.fl('Delete: xwz 5')):
			click('Yes')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		select('TextField', 'xwz 1')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		commonBits.delete3(click)

		if window( commonBits.fl('Delete: xwz 1')):
			click('Yes')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		select('TextField', 'xwz 2')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		commonBits.delete3(click)

		if window( commonBits.fl('Delete: xwz 2')):
			click('Yes')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		keystroke('TextField1', 'Down')
		select('TextField', 'xwz 3')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		commonBits.delete3(click)

		if window( commonBits.fl('Delete: xwz 3')):
			click('Yes')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		select('TextField', 'xwz 4')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		commonBits.delete3(click)

		if window( commonBits.fl('Delete: xwz 4')):
			click('Yes')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		select('TabbedPane',  commonBits.fl('Fields')
)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		##click('MetalInternalFrameTitlePane', 129, 14)

	close()
	##time.sleep(2)

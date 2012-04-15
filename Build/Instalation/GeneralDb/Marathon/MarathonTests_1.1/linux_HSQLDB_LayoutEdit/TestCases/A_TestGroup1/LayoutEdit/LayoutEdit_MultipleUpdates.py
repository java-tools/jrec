useFixture(default)


###
###                  Check Carefully if fails
###                 *------------------------* 
###
###  This test was added after problems with running in Embeded mode (closing Connection)
###  It runs multiple updates and looks for queries not being refereshed ....
###

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Layout Definitions'):
		select('TextArea', '')
		click('BaseHelpPanel', 111, 115)
		click('*')
		click('New1')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField2', 'xwz 1')
		click('Insert')
		click('Insert')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '4', 'Length,0')
		select('RecordFieldsJTbl', 'z', 'FieldName,0')
		select('RecordFieldsJTbl', '5', 'Position,1')
		select('RecordFieldsJTbl', '5', 'Length,1')
		select('RecordFieldsJTbl', 'f1', 'FieldName,1')
		select('RecordFieldsJTbl', '10', 'Position,2')
		select('RecordFieldsJTbl', '5', 'Length,2')
		select('RecordFieldsJTbl', 'f2', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:FieldName,2(f2)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:FieldName,1(f1)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ], [5, 5, f1, , 0, 0, 0, , , ], [10, 5, f2, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,0(z)')
		click('Save1')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xwz 2')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,2(f2)')
		click('Delete2')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Delete2')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('Insert')
		select('RecordFieldsJTbl', '4', 'Position,1')
		select('RecordFieldsJTbl', '44', 'Length,1')
		select('RecordFieldsJTbl', 'f', 'FieldName,1')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Insert')
		select('RecordFieldsJTbl', '5', 'Position,2')
		select('RecordFieldsJTbl', '5', 'Length,2')
		select('RecordFieldsJTbl', '5', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:FieldName,2()')
		click('Insert')
		select('RecordFieldsJTbl', '6', 'Position,3')
		select('RecordFieldsJTbl', '6', 'Length,3')
		select('RecordFieldsJTbl', '6', 'FieldName,3')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		assert_p('RecordFieldsJTbl', 'RowCount', '4')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ], [4, 44, f, , 0, 0, 0, , , ], [5, 5, 5, , 0, 0, 0, , , ], [6, 6, 6, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,3(6)')
		click('Delete2')
		select('RecordFieldsJTbl', 'cell:FieldName,2(5)')
		click('Delete2')
		select('RecordFieldsJTbl', 'cell:FieldName,1(f)')
		click('Delete2')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		select('RecordFieldsJTbl', 'cell:FieldName,0(z)')
		assert_p('RecordFieldsJTbl', 'ColumnCount', '10')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'RowCount', '1')
		select('RecordFieldsJTbl', 'cell:FieldName,0(z)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 4, z, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,0(z)')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xwz 3')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xwz 4')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xwz 5')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'xwz 5')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: xwz 5'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'xwz 1')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: xwz 1'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'xwz 2')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: xwz 2'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		keystroke('TextField1', 'Down')
		select('TextField', 'xwz 3')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: xwz 3'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'xwz 4')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: xwz 4'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		##click('MetalInternalFrameTitlePane', 129, 14)
		assert_p('TextArea', 'Text', '''
Invalid Record Name 

Invalid Record Name 

Invalid Record Name 

Invalid Record Name 

Invalid Record Name ''')

	close()
	##time.sleep(2)

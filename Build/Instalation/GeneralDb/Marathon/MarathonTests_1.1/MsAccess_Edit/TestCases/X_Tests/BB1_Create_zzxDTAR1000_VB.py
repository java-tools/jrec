useFixture(default)

def test():
	java_recorded_version = '1.7.0_03'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		click('New2')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField2', 'zzxDTAR1000 VB')
		select('BmKeyedComboBox2', 'Group of Binary Records')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('BmKeyedComboBox3', 'Mainframe')
		click('Insert')
		click('Insert')
		select('ChildRecordsJTbl', 'DTAR020', 'Child Record,0')
		select('ChildRecordsJTbl', 'DTAR1000 VB', 'Child Record,1')
		select('TabbedPane', 'Extras')
		select('BmKeyedComboBox5', 'Mainframe VB (rdw based) Binary')
##		click('BaseHelpPanel', 475, 141)
		click('Save1')
		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, DTAR020, , , , , ], [, DTAR1000 VB, , , , , ]]')
		assert_p('BmKeyedComboBox3', 'Text', 'Mainframe')
		assert_p('BmKeyedComboBox2', 'Text', 'Group of Binary Records')
		assert_p('TextField2', 'Text', 'zzxDTAR1000 VB')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

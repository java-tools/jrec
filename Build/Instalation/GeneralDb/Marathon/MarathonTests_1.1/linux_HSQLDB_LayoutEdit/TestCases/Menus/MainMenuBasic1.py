useFixture(default)

def test():
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*')
		select('TextField', 'ams PO Download%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		assert_p('TextField2', 'Text', 'ams PO Download')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*1')
		assert_p('BmKeyedComboBox', 'Text', '6')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		click('*2')

##		click('Label8')
##		assert_p('Label8', 'Text', 'Layout Name')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*3')
		assert_p('Label2', 'Text', 'Description')
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*6')
		assert_p('SplitCombo', 'Text', 'No Split')
		assert_p('ComputerOptionCombo', 'Text', 'Intel')
		assert_p('BmKeyedComboBox', 'Text', '0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*7')
		select('ComboBox', 'cb2xml XML Copybook (DB)')
		assert_p('ComboBox', 'Text', 'cb2xml XML Copybook (DB)')
		assert_p('SplitCombo', 'Text', 'No Split')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*8')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*9')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

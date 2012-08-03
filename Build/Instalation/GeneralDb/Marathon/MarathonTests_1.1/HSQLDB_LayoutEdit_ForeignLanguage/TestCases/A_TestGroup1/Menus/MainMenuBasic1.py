useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*')
		select('TextField', 'ams PO Download%')
		###select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField1', '%')
		###select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		assert_p('TextField2', 'Text', 'ams PO Download')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*1')
		if commonBits.isVersion80():
			assert_p('BmKeyedComboBox', 'Text',  commonBits.fl('Record Layout'))
		else:
			assert_p('BmKeyedComboBox', 'Text', 'XML')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		click('*2')

##		click('Label8')
##		assert_p('Label8', 'Text', 'Layout Name')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*3')
		assert_p('Label2', 'Text',  commonBits.fl('Table Name'))
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*6')
		assert_p('SplitCombo', 'Text',  commonBits.fl('No Split'))
		assert_p('ComputerOptionCombo', 'Text',  commonBits.fl('Intel'))
		assert_p('BmKeyedComboBox', 'Text',  commonBits.fl('Default'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*7')
		select('ManagerCombo',  commonBits.fl('cb2xml XML Copybook (DB)'))
		assert_p('ManagerCombo', 'Text',  commonBits.fl('cb2xml XML Copybook (DB)'))
		assert_p('SplitCombo', 'Text',  commonBits.fl('No Split'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*8')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*9')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

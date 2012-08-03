useFixture(RecordEditor)

def test():
	from Modules import commonBits
	from datetime import datetime
	import time
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'ams PO Download')
		select('TextField1', '%')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')

		time.sleep(1.0)
		click(commonBits.fl('Save As'))

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams PO Download')
			click('OK')
		close()

		time.sleep(1.0)

##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')
		select('TabbedPane', commonBits.fl('Extras')
)
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', commonBits.fl('Text IO (Unicode)')
)
#		select('BmKeyedComboBox5', '90')
		time.sleep(0.5)
		commonBits.save1(click)
		time.sleep(1)
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		time.sleep(1)

		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'ams Store')
		select('TextField1', '%')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
		select('TabbedPane', commonBits.fl('Fields')
)

		time.sleep(0.5)

		click(commonBits.fl('Save As'))

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams Store')
			click('OK')
		close()

		time.sleep(1.0)

#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		select('TabbedPane', commonBits.fl('Extras')
)
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', commonBits.fl('Text IO (Unicode)')
)

		time.sleep(0.5)
		commonBits.save1(click)

		time.sleep(0.5)
		commonBits.save1(click)

		time.sleep(0.5)

		commonBits.closeWindow(click)
		time.sleep(0.5)
	close()


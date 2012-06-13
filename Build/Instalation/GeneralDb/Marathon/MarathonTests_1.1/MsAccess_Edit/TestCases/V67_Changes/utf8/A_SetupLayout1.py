useFixture(default)

def test():
	from Modules import commonBits
	from datetime import datetime
	import time
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams PO Download')
		select('TextField1', '%')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')

		time.sleep(1.0)
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams PO Download')
			click('OK')
		close()

		time.sleep(1.0)

##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')
		select('TabbedPane', 'Extras')
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', 'Text IO (Unicode)')
#		select('BmKeyedComboBox5', '90')
		time.sleep(0.5)
		click('Save1')
		time.sleep(1)
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		time.sleep(1)

		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams Store')
		select('TextField1', '%')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')

		time.sleep(0.5)

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams Store')
			click('OK')
		close()

		time.sleep(1.0)

#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', 'Text IO (Unicode)')

		time.sleep(0.5)
		click('Save1')

		time.sleep(0.5)
		click('Save1')

		time.sleep(0.5)

		commonBits.closeWindow(click)
		time.sleep(0.5)
	close()


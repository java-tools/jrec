useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams PO Download')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams PO Download')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('TabbedPane', 'Extras')
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', 'Text IO (Unicode)')
#		select('BmKeyedComboBox5', '90')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		##time.sleep(2)

		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams Store')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'utf8_ams Store')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TextField5', 'utf-8')
		select('BmKeyedComboBox5', 'Text IO (Unicode)')

		click('Save1')

	close()


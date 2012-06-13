useFixture(default)

def test():
	java_recorded_version = '1.7.0_03'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams PO Download')

		select('TextField1', '%')

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zzAms PO Download')
			click('OK')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('Preferences')

		if commonBits.isTstLanguage():
			if window('Record Editor Options Editor'):
				select('TabbedPane', 'Language')

				select('TabbedPane', 'Language')

				select('ComboBox2', 'Tst')

				click('Save')

				if window('Message'):
					click('OK')
				close()

##				click('Button2')
			close()
		else:
			if window('Record Editor Options Editor'):
				select('TabbedPane', commonBits.fl('Language'))

				select('ComboBox2', ' ')
				click('Save')

				if window('Message'):
					click('OK')
				close()

				click('Button2')
			close()
	close()

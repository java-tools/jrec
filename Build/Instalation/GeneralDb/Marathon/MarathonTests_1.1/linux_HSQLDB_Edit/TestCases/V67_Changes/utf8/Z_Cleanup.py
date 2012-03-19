useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'utf8_ams PO Download')
		select('TextField1', '%')
		click('Delete3')

		if window('Delete: utf8_ams PO Download'):
			click('Yes')
		close()

		select('TextField', 'utf8_ams Store')
		select('TextField1', '%')
		click('Delete3')

		if window('Delete: utf8_ams Store'):
			click('Yes')
		close()

	close()

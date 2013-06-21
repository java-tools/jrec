useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'utf8_ams PO Download')
		select('TextField1', '%')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: utf8_ams PO Download')):
			click('Yes')
		close()

		select('TextField', 'utf8_ams Store')
		select('TextField1', '%')
		##click('Delete3')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: utf8_ams Store')):
			click('Yes')
		close()

	close()

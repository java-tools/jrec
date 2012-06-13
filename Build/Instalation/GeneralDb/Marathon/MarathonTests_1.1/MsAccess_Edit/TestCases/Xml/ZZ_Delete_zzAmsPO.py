useFixture(default)

def test():
	java_recorded_version = '1.7.0_03'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'zzAms PO Download')

		select('TextField1', '%')

		select('TabbedPane', 'Child Records')
		click('Delete3')

		if window('Delete: zzAms PO Download'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

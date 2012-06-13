useFixture(default)

def test():
	java_recorded_version = '1.7.0_03'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'zzxDTAR1000 VB')

		select('TextField1', '%')

		click('Delete3')

		if window('Delete: zzxDTAR1000 VB'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

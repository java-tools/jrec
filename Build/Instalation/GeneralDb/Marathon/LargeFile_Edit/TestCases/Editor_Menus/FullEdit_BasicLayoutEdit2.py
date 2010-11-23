useFixture(default)

def test():
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Load Cobol Copybook')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit System Table')
		select('TableListJTbl', 'cell:Details,0(Unkown)')
		assert_p('TableListJTbl', 'Text', 'Unkown', 'Details,0')
		select('TableListJTbl', 'cell:Details,0(Unkown)')
		assert_p('TextField1', 'Text', 'System')
		assert_p('TextField', 'Text', '3')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		from Modules import commonBits

		click(commonBits.fl('Choose File'))

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'ams Store')

		click(commonBits.fl('Edit') + '1')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'NSW North Sydney Ad Support', '10 - 35|Loc Name,2')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

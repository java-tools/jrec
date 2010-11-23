useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		from Modules import commonBits

		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'NSW North Sydney Ad Support', '10 - 35|Loc Name,2')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		select('Table', 'cell:10 - 35|Loc Name,2(NSW North Sydney Ad Support)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

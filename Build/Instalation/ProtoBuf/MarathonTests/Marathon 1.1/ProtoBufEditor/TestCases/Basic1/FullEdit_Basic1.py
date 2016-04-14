useFixture(default)

## Check
def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window(commonBits.applicationName()):
		from Modules import commonBits

		click('FileSearch')
		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.bin')
			click('Open')
		close()

		#commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
		select('LinesTbl', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		assert_p('LinesTbl', 'Text', 'NSW North Sydney Ad Support', '4|Loc_Name,2')
		select('LinesTbl', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		assert_p('LinesTbl', 'Text', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		select('LinesTbl', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

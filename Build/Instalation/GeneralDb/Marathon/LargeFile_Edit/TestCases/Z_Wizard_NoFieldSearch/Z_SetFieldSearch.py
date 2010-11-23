useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Edit>>Edit Startup Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Layout Wizard Options')
			select('Table3', 'Y', ' Value ,0')
			select('Table3', 'cell: Value ,0(N)')
			assert_p('Table3', 'Text', 'Y', ' Value ,0')
			select('Table3', 'cell: Value ,0(Y)')
			
			click('Save')
			
		close()
	close()

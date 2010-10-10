useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Edit>>Edit Startup Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Layout Wizard Options')
			select('Table3', 'N', ' Value ,0')
			select('Table3', 'cell: Value ,0(N)')
			assert_p('Table3', 'Text', 'N', ' Value ,0')
			select('Table3', 'cell: Value ,0(N)')
			click('Save')

#			if commonBits.isWindowsLook():
#				click('Save')
#			else:
#				click('Button2')
		close()
	close()

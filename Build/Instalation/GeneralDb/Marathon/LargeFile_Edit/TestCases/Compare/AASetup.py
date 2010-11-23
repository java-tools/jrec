useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Edit>>Edit Startup Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Other Options')
			select('Table2', 'Y', ' Value ,2')
			select('Table2', '0', ' Value ,4')
			select('Table2', 'cell: Value ,2(null)')
			keystroke('Table2', 'Down', ' Value ,2')
			select('Table2', 'N', ' Value ,3')
			select('Table2', 'cell: Value ,3(null)')
			keystroke('Table2', 'Down', ' Value ,3')

			select('TabbedPane', 'Looks')
			##click('ScrollPane$ScrollBar', 14, 21)
			select('TabbedPane', 'Looks')
			select('ComboBox2', 'Java - Metal')
			click('Save')
		close()
	close()

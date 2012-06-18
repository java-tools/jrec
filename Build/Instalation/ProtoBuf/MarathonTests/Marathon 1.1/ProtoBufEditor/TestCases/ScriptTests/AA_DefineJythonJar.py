useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Jars')
			select('JarsTab', 'Optional Jars')
			select('Table3', 'cell:Jar,0()')
			select('FileChooser2', r'C:\JavaPrograms\Marathon\Marathon\marathon_3.1.5.2\jython\jython.jar')
			select('Table3', 'cell:Jar,3(null)')
			assert_p('Table3', 'Content', r'[[optional.0, C:\JavaPrograms\Marathon\Marathon\marathon_3.1.5.2\jython\jython.jar, ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ], [, , ]]')
			select('Table3', 'cell:Jar,3(null)')
			click('Save')
			click('Button2')
		close()
	close()

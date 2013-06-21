useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
##		click(commonBits.fl('Choose File'))

##		if window('Open'):
##			select(commonBits.selectPane(), 'Xml')
##			doubleclick(commonBits.selectPane(), '0')
##			select(commonBits.selectPane(), '7zip_pad.xml')
##			click('Open')
##		close()

		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + '7zip_pad.xml')


		#commonBits.doEdit(click)
		#time.sleep(1)
		commonBits.doEdit(click)

		select('LayoutCombo', 'XML Start_Document')

		if commonBits.isWindowsLook() or  commonBits.isNimbusLook():
			rightclick('JTreeTable', commonBits.fl('Tree') + ',1')
			select_menu(commonBits.fl('Expand Tree'))
		else:
			doubleclick('JTreeTable', commonBits.fl('Tree') + ',1')

##		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',2(null)')

		assert_p('JTreeTable', 'RowCount', '9')
		select('JTreeTable', 'cell:Xml~Version,0(1.0)')
		assert_p('JTreeTable', 'Text', 'false', 'Xml~Standalone,0')
		select('JTreeTable', 'cell:Xml~Encoding,6(null)')
		rightclick('JTreeTable', 'Xml~Encoding,6')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:Xml~Encoding,8(null)')
		assert_p('JTreeTable', 'RowCount', '11')
		select('JTreeTable', 'cell:Xml~Version,7(null)')
		rightclick('JTreeTable', 'Xml~Version,7')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:Xml~Encoding,8(null)')
		select('JTreeTable', 'cell:Xml~Version,13(null)')
		rightclick('JTreeTable', 'Xml~Version,13')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:Xml~Encoding,14(null)')
		select('JTreeTable', 'cell:Xml~Version,16(null)')
		assert_p('JTreeTable', 'RowCount', '20')
		select('JTreeTable', 'cell:Xml~Encoding,13()')
		rightclick('JTreeTable', 'Xml~Encoding,13')
		select_menu(commonBits.fl('Collapse Tree'))
		select('JTreeTable', 'cell:Xml~Encoding,13()')
		assert_p('JTreeTable', 'RowCount', '16')
		select('JTreeTable', 'cell:Xml~Version,12(null)')
		select('JTreeTable', 'cell:Xml~Encoding,6(null)')
		rightclick('JTreeTable', 'Xml~Encoding,6')
		select_menu(commonBits.fl('Collapse Tree'))
		select('JTreeTable', 'cell:Xml~Encoding,7(null)')
		assert_p('JTreeTable', 'RowCount', '9')
	close()


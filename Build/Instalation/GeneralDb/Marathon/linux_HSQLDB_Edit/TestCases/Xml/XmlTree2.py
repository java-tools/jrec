useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Xml' + commonBits.fileSep() + 'recentFiles.xml')
		click('Edit1')
		select('LayoutCombo', 'XML Start_Document')
		select('JTreeTable', 'cell:Xml~Encoding,1( iReport 2 file list - Thu Apr 12 15:22:04 EST 2007 )')
		assert_p('JTreeTable', 'Text', '', 'Xml~Encoding,3')
		select('JTreeTable', 'cell:Xml~Encoding,2()')
		assert_p('JTreeTable', 'RowCount', '4')
		select('JTreeTable', 'cell:Xml~Encoding,3(null)')
		rightclick('JTreeTable', 'Xml~Encoding,3')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Xml~Encoding,4(null)')
		assert_p('JTreeTable', 'RowCount', '14')
		select('JTreeTable', 'cell:Xml~Encoding,5(null)')
		rightclick('JTreeTable', 'Xml~Encoding,7')
		select_menu('Edit Record')
		select('Table', 'cell:Data,2(null)')
		assert_p('Table', 'Content', r'[[Xml~Name, 0, , iReportFile, iReportFile], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml, E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml]]')
		select('Table', r'cell:Data,4(E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml)')
##		assert_p('TextArea', 'Text', r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml')
		click('Right')
		select('Table', '''cell:Data,2(
)''')
		select('Table', 'cell:Text,1()')
		select('Table', 'cell:Text,1()')
		click('RightM')
		click('Left')
		click('Left')
		select('Table', r'cell:Data,4(E:\Work\RecordEdit\Jasper\DTAR107_rpt1.jrxml)')
		select('Table', r'cell:Text,4(E:\Work\RecordEdit\Jasper\DTAR107_rpt1.jrxml)')
		assert_p('Table', 'Content', r'[[Xml~Name, 0, , iReportFile, iReportFile], [Xml~Prefix, 3, , , ], [Xml~Namespace, 4, , , ], [Xml~End, 1, , , ], [Following~Text, 2, , E:\Work\RecordEdit\Jasper\DTAR107_rpt1.jrxml, E:\Work\RecordEdit\Jasper\DTAR107_rpt1.jrxml]]')
		select('Table', r'cell:Text,4(E:\Work\RecordEdit\Jasper\DTAR107_rpt1.jrxml)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		##if window(r'Save Changes to file: ' + commonBits.sampleDir() + 'Xml\recentFiles.xml'):
		##	click('No')
		##close()
	close()

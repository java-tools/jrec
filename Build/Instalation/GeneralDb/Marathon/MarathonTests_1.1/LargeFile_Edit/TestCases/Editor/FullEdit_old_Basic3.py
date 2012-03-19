useFixture(default)

def test():
	from Modules import commonBits

	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		select_menu('Record Layouts>>Edit Layout')
		select('Table', 'cell:Record Name,0(ams PO Download)')
		rightclick('Table', 'Record Name,0')
		assert_p('Table', 'Text', 'cell:Record Name,0(ams PO Download)')
		assert_p('TextField2', 'Text', 'ams PO Download')
		select('ChildRecordsJTbl', 'cell:Child Record,0(353)')
		assert_p('ChildRecordsJTbl', 'Text', 'ams PO Download: Detail', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(353)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Create Layout')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Layout Wizard')
		select_menu('Record Layouts>>Load Cobol Copybook')
		click('EditorPane')
		click('BaseHelpPanel', 373, 266)
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
		#click('WindowsInternalFrameTitlePane', 78, 9)
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		##click('BasicInternalFrameTitlePane$NoFocusButton5')
	close()

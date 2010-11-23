useFixture(default)

def test():
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('Table', 'cell:Record Name,0(ams PO Download)')
		doubleclick('Table', 'Record Name,0')
		select('TextField', 'ams PO Down%')
		select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField1', '%')
##		assert_p('Table', 'Content', '[[ams PO Download, ], [ams PO Download: Allocation, Allocation Line], [ams PO Download: Detail, PO Download: Detail], [ams PO Download: Header, PO Download: Header]]')
		assert_p('Table', 'Content', '[[ams PO Download, ], [ams PO Download: Allocation, Allocation Line], [ams PO Download: Detail, PO Download: Detail], [ams PO Download: Header, PO Download: Header]]')
		
		assert_p('Table', 'Text', 'ams PO Download', 'Record Name,0')
		select('TextField', 'ams PO Downl%')
		assert_p('TextField2', 'Text', 'ams PO Download')
		select('ChildRecordsJTbl', 'cell:Field Start,1(0)')
		click('Edit Child')
		assert_p('TextField', 'Text', 'ams PO Download: Header')
		click('Right')
		assert_p('TextArea', 'Text', 'Allocation Line')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

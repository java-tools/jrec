useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		click('Sort')
		select('records', 'ams PO Download: Detail')
		select('records', 'ams PO Download: Detail')
		assert_p('records', 'Content', '[[ams PO Download: Detail, ams PO Download: Header, ams PO Download: Allocation]]')
		select('records', 'ams PO Download: Detail')
		assert_p('fields_JTbl', 'Content', '[[ , true], [ , true], [ , true], [ , true], [ , true]]')
		select('records', 'ams PO Download: Detail')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('View>>Sorted Field Tree')
		select('records', 'ams PO Download: Detail')
		select('records', 'ams PO Download: Detail')
		assert_p('records', 'Content', '[[ams PO Download: Detail, ams PO Download: Header, ams PO Download: Allocation]]')
		select('records', 'ams PO Download: Detail')
		assert_p('fieldSummary_JTbl', 'Content', '[[Record Type, ], [Pack Qty, ], [Pack Cost, ], [APN, ], [Filler, ], [Product, ], [pmg dtl tech key, ], [Case Pack id, ], [Product Name, ]]')
		select('records', 'ams PO Download: Header')
		assert_p('fieldSummary_JTbl', 'Content', '[[Record Type, ], [Sequence Number, ], [Vendor, ], [PO, ], [Entry Date, ], [Filler, ], [beg01 code, ], [beg02 code, ], [Department, ], [Expected Reciept Date, ], [Cancel by date, ], [EDI Type, ], [Add Date, ], [Filler, ], [Department Name, ], [Prcoess Type, ], [Order Type, ]]')
		select('records', 'ams PO Download: Header')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select_menu('Edit>>Show / Hide Fields')
#		select('Table', 'cell:Show,6(true)')
#		select('Table', 'cell:Show,7(true)')
#		click('Go')
	close()

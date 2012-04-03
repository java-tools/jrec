useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select_menu('Record Layouts>>Load Copybook')
		select('FileChooser', commonBits.xmlCopybookDir() + 'yyAms PO Download.Xml')
		select('BmKeyedComboBox1', 'Other')
		click('Go')
		rightclick('TextArea')
		assert_p('TextArea', 'Text', '''

-->> ''' + commonBits.xmlCopybookDir() + '''yyAms PO Download.Xml processed

      Copybook: yyAms PO Download''')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Reload from DB')
		select('FileChooser', commonBits.sampleDir() + 'zzAms_PODownload_20041231.txt')
		select('ComboBox1', 'Other')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'yy%')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
##		click('ScrollPane$ScrollBar', 12, 112)
		click('Reload from DB')
##		select('ComboBox2', 'yyAms PO Download')
		commonBits.setRecordLayout(select, 'yyAms PO Download')

		commonBits.doEdit(click)
		select('Table', 'cell:3 - 4|DC Number 1,0(4534)')
		rightclick('Table', '1 - 2|Record Type,0')
##		select('Table', 'cell:3 - 4|DC Number 1,0(4534)')
		select_menu('Edit Record')
##		select('Table1', 'cell:3 - 4|DC Number 1,0(4534)')
		select('Table', 'cell:Data,3(286225)')
		click('Right')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, D1, D1], [Pack Qty, 3, 9, 7.0000, 000070000], [Pack Cost, 12, 13, 0.0002, 0000000000002], [APN, 25, 13, 2222500000000, 2222500000000], [Filler, 38, 1, , ], [Product, 39, 8, 43314531, 43314531], [pmg dtl tech key, 72, 15, 2075359, 2075359], [Case Pack id, 87, 15, 45614531, 45614531], [Product Name, 101, 50,  DONKEY 24-006607 SHWL WRAP CARD,  DONKEY 24-006607 SHWL WRAP CARD]]')
		click('TextArea')
		assert_p('TextArea', 'Text', 'D100007000000000000000022222500000000 43314531000000054540000007       2075359        45614531       DONKEY 24-006607 SHWL WRAP CARD                   ')
		click('Left')
		select('Table', 'cell:Data,4(040909)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.349, 45349], [Vendor, 8, 10, 6060, 0000006060], [PO, 18, 12, 286225, 286225], [Entry Date, 30, 6, 040909, 040909], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050102, 050102], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,4(040909)')
		click('TextArea')
		assert_p('TextArea', 'Text', 'H1453490000006060286225      040909        00  200 0501020501075965        LADIES KNICFT')
		doubleclick('Right')
		doubleclick('Right')
		select('Table', 'cell:Data,9(5151)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, S1, S1], [DC Number 1, 3, 4, 5043, 5043], [Pack Quantity 1, 7, 8, 1, 00000001], [DC Number 2, 15, 4, 5045, 5045], [Pack Quantity 2, 19, 8, 1, 00000001], [DC Number 4, 39, 4, 5076, 5076], [Pack Quantity 4, 43, 8, 1, 00000001], [DC Number 5, 51, 4, 5079, 5079], [Pack Quantity 5, 55, 8, 1, 00000001], [DC Number 6, 63, 4, 5151, 5151], [Pack Quantity 6, 67, 8, 1, 00000001], [DC Number 7, 75, 4, 5072, 5072], [Pack Quantity 7, 79, 8, 1, 00000001], [DC Number 8, 87, 4, , ], [Pack Quantity 8, 91, 8, 0, 00000000], [DC Number 9, 99, 4, , ], [Pack Quantity 9, 103, 8, 0, 00000000], [DC Number 10, 111, 4, , ], [Pack Quantity 10, 115, 8, 0, 00000000]]')

		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'yy%')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('Table', 'cell:Description,0()')
# 		click('Delete3')
		select('TextField', 'yyAms PO Download')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Child Records')
		click('Delete3')

		if window('Delete: yyAms PO Download'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'yyAms PO Download%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'yyAms PO Download: A%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: yyAms PO Download: Allocation'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'yyAms PO Download: H%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: yyAms PO Download: Header'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TextField', 'yyAms PO Download: %')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: yyAms PO Download: Detail'):
			click('Yes')
		close()
	close()

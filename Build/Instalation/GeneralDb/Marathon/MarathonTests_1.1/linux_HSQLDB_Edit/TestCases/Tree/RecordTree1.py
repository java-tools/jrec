useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		select_menu('View>>Record Based Tree')
#		select('Table', '1', 'Parent Record,0')
#		select('Table', '0', 'Parent Record,2')
		select('Table', 'ams PO Download: Header', 'Parent Record,0')
		select('Table', 'ams PO Download: Detail', 'Parent Record,2')
		select('Table', 'cell:Parent Record,2(0)')
		click('Build')
		select('LayoutCombo', 'ams PO Download: Detail')
		#####

		select('JTreeTable', 'cell:Pack Qty,3(45352.0000)')
		assert_p('JTreeTable', 'Text', 'cell:Pack Qty,3(45352.0000)')
		select('JTreeTable', 'cell:Pack Cost,4(5341294987)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45349.0000, 606028.6225, 40909, , 0,     LADIES KNIC, FT, ], [, , H1, 45350.0000, 6228000.0002, 22227040909, , 0,     LADIES KNIC, FT, ], [, , H1, 45351.0000, 6228000.0002, 22243040909, , 0,     LADIES KNIC, FT, ], [, , H1, 45352.0000, 534129.4915, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45353.0000, 534129.4987, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45354.0000, 534129.5139, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45355.0000, 534130.3662, 41110, , 0,     YOUTH SHOEC, FT, ], [, , H1, 45356.0000, 534130.4100, 41111, , 0,     YOUTH SHOEC, FT, ]]')
		select('JTreeTable', 'cell:Pack Cost,3(5341294915)')
		assert_p('JTreeTable', 'Text', '534129.4987', 'Pack Cost,4')
		select('JTreeTable', 'cell:Pack Cost,5(5341295139)')
		assert_p('JTreeTable', 'RowCount', '8')
		select('JTreeTable', 'cell:Pack Qty,2(45351.0000)')
		rightclick('JTreeTable', 'Pack Qty,2')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Pack Qty,3(6.0000)')
		assert_p('JTreeTable', 'Text', '6.0000', 'Pack Qty,3')
		select('JTreeTable', 'cell:Pack Qty,4(3.0000)')
		assert_p('JTreeTable', 'RowCount', '12')
		select('JTreeTable', 'cell:Pack Qty,5(44.0000)')
##		assert_p('JTreeTable', 'Content', '[[, , H1, 45349.0000, 606028.6225, 40909, , 0,     LADIES KNIC, FT, ], [, , H1, 45350.0000, 62280000002, 22227040909, , 0,     LADIES KNIC, FT, ], [, , H1, 45351.0000, 62280000002, 22243040909, , 0,     LADIES KNIC, FT, ], [, , D1, 6.0000, 148.3200, 0, , 45614531, 2075352, 45614531,  DONKEY 24-006607 SHWL WRAP CARD], [, , D1, 3.0000, 148.3200, 0, , 45614944, 2075353, 45614944,  MILK 24-006607 SHWL WRAP CARD], [, , D1, 44.0000, 148.3200, 0, , 45615071, 2075354, 45615071,  M.ROSE 24-006607 SHWL WRAP CARD], [, , D1, 3.0000, 148.3200, 0, , 45615156, 2075355, 35615156,  AQUA 24-006607 SHWL WRAP CARD], [, , H1, 45352.0000, 5341294915, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45353.0000, 5341294987, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45354.0000, 5341295139, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45355.0000, 5341303662, 41110, , 0,     YOUTH SHOEC, FT, ], [, , H1, 45356.0000, 5341304100, 41111, , 0,     YOUTH SHOEC, FT, ]]')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45349.0000, 606028.6225, 40909, , 0,     LADIES KNIC, FT, ], [, , H1, 45350.0000, 6228000.0002, 22227040909, , 0,     LADIES KNIC, FT, ], [, , H1, 45351.0000, 6228000.0002, 22243040909, , 0,     LADIES KNIC, FT, ], [, , D1, 6.0000, 148.3200, 0, , 45614531, 2075352, 45614531,  DONKEY 24-006607 SHWL WRAP CARD], [, , D1, 3.0000, 148.3200, 0, , 45614944, 2075353, 45614944,  MILK 24-006607 SHWL WRAP CARD], [, , D1, 44.0000, 148.3200, 0, , 45615071, 2075354, 45615071,  M.ROSE 24-006607 SHWL WRAP CARD], [, , D1, 3.0000, 148.3200, 0, , 45615156, 2075355, 35615156,  AQUA 24-006607 SHWL WRAP CARD], [, , H1, 45352.0000, 534129.4915, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45353.0000, 534129.4987, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45354.0000, 534129.5139, 41013, , 0,     WOMENS SHOC, FT, ], [, , H1, 45355.0000, 534130.3662, 41110, , 0,     YOUTH SHOEC, FT, ], [, , H1, 45356.0000, 534130.4100, 41111, , 0,     YOUTH SHOEC, FT, ]]')
		select('JTreeTable', 'cell:Pack Qty,5(44.0000)')
		rightclick('JTreeTable', 'Pack Qty,5')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Pack Qty,6(50090.0000)')
		assert_p('JTreeTable', 'Text', '50440.0000', 'Pack Qty,7')
	close()

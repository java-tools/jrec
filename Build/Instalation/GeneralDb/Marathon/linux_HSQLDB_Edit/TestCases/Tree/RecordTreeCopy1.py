useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		select_menu('View>>Record Based Tree')
		select('Table', 'ams PO Download: Header', 'Parent Record,0')
		select('Table', 'ams PO Download: Detail', 'Parent Record,2')
		select('Table', 'cell:Parent Record,2(0)')
		click('Build')
		select('JTreeTable', 'cell:PO,2(222243)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,3(45.352)')
		rightclick('JTreeTable', 'Sequence Number,3')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:Vendor,0(6060)')
		rightclick('JTreeTable', 'Vendor,0')
		select_menu('Paste Record#{s#} Prior')
		select('JTreeTable', 'cell:Vendor,0(5341)')
		assert_p('JTreeTable', 'Text', '6060', 'Vendor,1')
		select('JTreeTable', 'cell:PO,0(294915)')
		assert_p('JTreeTable', 'RowCount', '9')
		select('JTreeTable', 'cell:Sequence Number,0(45.352)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,0(45.352)')
		select_menu('Window>>Ams_PODownload_20041231.txt>>Table: ')
		select('JTreeTable', 'cell:Sequence Number,0(45.352)')
		select('Table', 'rows:[0,1,2,3,4],columns:[3 - 4|DC Number 1]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4],columns:[3 - 4|DC Number 1]')
		select('Table', 'cell:3 - 5|Sequence Number,0(45.352)')
		assert_p('Table', 'Text', 'cell:3 - 5|Sequence Number,0(45.352)')
		select('Table', 'cell:18 - 12|PO,1(292680000000)')
		assert_p('Table', 'Content', '[[H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [D1, 0.030, 0, 292680000000, 000000, 00 45846, 68, 00, 0000, 007272, 000003, ,    212, 0, 736, , 45], [S1, 50.360, 2504, 300000003504, 500000, 00350570, 00, 00, 0015, 065000, 000025, 0, 000002, 5, 0760000000, 3, 50], [S1, 51.510, 3518, 2507, 200000, 00151730, 00, 00, 001,    000, 00000, 0, 000000, ,    0000000, 0, ], [H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT]]')
		select('Table', 'cell:18 - 12|PO,1(292680000000)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Ams_PODownload_20041231.txt>>Tree View')
		select('JTreeTable', 'cell:Record Type,2(H1)')
		rightclick('JTreeTable', 'Record Type,2')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:Record Type,8(H1)')
		rightclick('JTreeTable', 'Record Type,8')
		select_menu('Paste Record#{s#}')
		select('JTreeTable', 'cell:Vendor,8(5341)')
		assert_p('JTreeTable', 'Text', 'cell:Vendor,8(5341)')
		select('JTreeTable', 'cell:PO,9(222227)')
		assert_p('JTreeTable', 'RowCount', '10')
		select('JTreeTable', 'cell:Vendor,9(6228)')
		select('JTreeTable', 'cell:Entry Date,9(040909)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT]]')
		select('JTreeTable', 'cell:Vendor,9(6228)')
		assert_p('JTreeTable', 'Text', '6228', 'Vendor,9')
		select('JTreeTable', 'cell:Sequence Number,9(45.350)')
		rightclick('JTreeTable', 'Sequence Number,9')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Vendor,10(6228)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55]]')
		select('JTreeTable', 'cell:Sequence Number,11(0.008)')
		assert_p('JTreeTable', 'Text', '0.108', 'Sequence Number,12')
		select('JTreeTable', 'cell:Sequence Number,12(0.108)')
		assert_p('JTreeTable', 'RowCount', '14')
		select('JTreeTable', 'cell:Sequence Number,0(45.352)')
		rightclick('JTreeTable', 'Sequence Number,0')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Sequence Number,1(0.030)')
		assert_p('JTreeTable', 'Text', '45.349', 'Sequence Number,2')
		select('JTreeTable', 'cell:PO,1(292680000000)')
		assert_p('JTreeTable', 'Text', '292680000000', 'PO,1')
		select('JTreeTable', 'cell:Entry Date,1(000000)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , D1, 0.030, 0, 292680000000, 000000, 00 45846, 68, 00, 0000, 007272, 000003, ,    212, 0, 736, , 45], [, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55]]')
		select('JTreeTable', 'cell:Sequence Number,1(0.030)')
		rightclick('JTreeTable', 'Sequence Number,1')
		select('JTreeTable', 'cell:Sequence Number,1(0.030)')
		assert_p('JTreeTable', 'RowCount', '15')
		select('JTreeTable', 'rows:[10,11],columns:[Record Type]')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'rows:[10,11],columns:[Record Type]')
		select('Table', 'cell:7 - 8|Pack Quantity 1,1(60000006)')
		assert_p('Table', 'Content', '[[H1, 4535, 6, 2280, 222, , 200, 50, 10205010, 7596, 6, LAD, IES KNIC, FT, , , , , ], [D1, 1, 60000006, 2281, 48320000, 0222, 45310000, 5, 45400000, 16, 207, 5348, , 5614, 531, D, ONKEY 24, -6, 607 SHWL], [S1, 5019, 1, 5037, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1, 5095, 1, 51 D, ONKEY 24, -6, 607 SHWL], [S1, 5171, 1, 5177, 1, 5136, 1, 5145, 1, 5096, 1, , 0, , 0, , 0, , 0]]')
		select('Table', 'cell:7 - 8|Pack Quantity 1,1(60000006)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Ams_PODownload_20041231.txt'):
			click('No')
		close()
	close()

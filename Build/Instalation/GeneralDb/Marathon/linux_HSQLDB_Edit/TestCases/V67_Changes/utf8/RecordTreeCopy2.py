useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'utf8a_Ams_PODownload_20041231.txt')
		commonBits.setRecordLayout(select, 'utf8_ams PO Download')
		click('Edit1')
		select_menu('View>>Record Based Tree')
		select('Table', 'ams PO Download: Header', 'Parent Record,0')
		select('Table', 'ams PO Download: Detail', 'Parent Record,2')
#		select('Table', '1', 'Parent Record,0')
#		select('Table', '0', 'Parent Record,2')
		select('Table', 'cell:Parent Record,2(0)')
		click('Build')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Record Type,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Record Type,2(D1)')
		rightclick('JTreeTable', 'Record Type,2')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:Record Type,3(D1)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Record Type,4(D1)')
		assert_p('JTreeTable', 'RowCount', '12')
		select('JTreeTable', 'cell:Record Type,5(D1)')
		rightclick('JTreeTable', 'Record Type,5')
		select_menu('Paste Record#{s#}')
		select('JTreeTable', 'cell:Record Type,1(H1)')
		rightclick('JTreeTable', 'Record Type,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Sequence Number,6(0.016)')
		assert_p('JTreeTable', 'Text', '0.016', 'Sequence Number,6')
		select('JTreeTable', 'cell:Sequence Number,5(0.008)')
		assert_p('JTreeTable', 'RowCount', '13')
		select('JTreeTable', 'cell:Sequence Number,3(0.008)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,5(0.008)')
		rightclick('JTreeTable', 'Sequence Number,5')
		select_menu('Paste Record#{s#} Prior')
		select('JTreeTable', 'cell:Sequence Number,5(0.016)')
		assert_p('JTreeTable', 'Text', '0.016', 'Sequence Number,5')
		select('JTreeTable', 'cell:Sequence Number,6(0.008)')
		assert_p('JTreeTable', 'RowCount', '14')
		select('JTreeTable', 'cell:Sequence Number,4(0.108)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,5(0.016)')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'cell:Sequence Number,5(0.016)')
		select('Table', 'cell:7 - 8|Pack Quantity 1,0(60000006)')
		assert_p('Table', 'Content', '[[D1, 1, 60000006, 2281, 48320000, 0222, 45310000, 5, 45400000, 16, 207, 5348, , 5614, 531, D, ONKEY 24, -6, 607 SHWL], [S1, 5019, 1, 5037, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1, 5095, 1, 51 D, ONKEY 24, -6, 607 SHWL], [S1, 5171, 1, 5177, 1, 5136, 1, 5145, 1, 5096, 1, , 0, , 0, , 0, , 0]]')
		select('Table', 'cell:3 - 4|DC Number 1,1(5019)')
		select('Table', 'cell:7 - 8|Pack Quantity 1,1(1)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Sequence Number,7(0.016)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Vendor,7(6228)')
		rightclick('JTreeTable', 'Vendor,7')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Sequence Number,8(50.190)')
		assert_p('JTreeTable', 'Text', '50.190', 'Sequence Number,8')
		select('JTreeTable', 'cell:Sequence Number,9(51.710)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , S1, 50.190, 1503, 700000001507, 400000, 00150780, 00, 00, 0015, 085000, 000015, 0, 000001, 5, 0930000000, 1, 50], [, , S1, 51.710, 1517, 700000001508, 900000, 00151360, 00, 00, 0015, 145000, 000015, 0, 000001, ,    0000000, 0, ], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,5(0.016)')
		rightclick('JTreeTable', 'Sequence Number,5')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Sequence Number,6(50.190)')
		assert_p('JTreeTable', 'Text', '51.710', 'Sequence Number,7')
		select('JTreeTable', 'cell:Sequence Number,7(51.710)')
		assert_p('JTreeTable', 'RowCount', '18')
		select('JTreeTable', 'cell:PO,7(700000001508)')
		assert_p('JTreeTable', 'Text', '700000001508', 'PO,7')
		select('JTreeTable', 'cell:PO,6(700000001507)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , S1, 50.190, 1503, 700000001507, 400000, 00150780, 00, 00, 0015, 085000, 000015, 0, 000001, 5, 0930000000, 1, 50], [, , S1, 51.710, 1517, 700000001508, 900000, 00151360, 00, 00, 0015, 145000, 000015, 0, 000001, ,    0000000, 0, ], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , S1, 50.190, 1503, 700000001507, 400000, 00150780, 00, 00, 0015, 085000, 000015, 0, 000001, 5, 0930000000, 1, 50], [, , S1, 51.710, 1517, 700000001508, 900000, 00151360, 00, 00, 0015, 145000, 000015, 0, 000001, ,    0000000, 0, ], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,4(0.108)')
		rightclick('JTreeTable', 'Sequence Number,4')
		select_menu('Copy Record#{s#}')
		select('JTreeTable', 'cell:Sequence Number,2(0.016)')
		rightclick('JTreeTable', 'Sequence Number,2')
		select_menu('Paste Record#{s#} Prior')
		select('JTreeTable', 'cell:Record Type,1(H1)')
		rightclick('JTreeTable', 'Record Type,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Sequence Number,2(0.108)')
		assert_p('JTreeTable', 'Text', '0.108', 'Sequence Number,2')
		select('JTreeTable', 'cell:Sequence Number,3(0.016)')
		assert_p('JTreeTable', 'RowCount', '15')
		select('JTreeTable', 'cell:Sequence Number,4(0.008)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Record Type,2(D1)')
		select('JTreeTable', 'rows:[2,3],columns:[Sequence Number]')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'rows:[2,3],columns:[Sequence Number]')
		select('Table', 'cell:7 - 8|Pack Quantity 1,0(80000000)')
		assert_p('Table', 'Text', 'cell:7 - 8|Pack Quantity 1,0(80000000)')
		select('Table', 'cell:3 - 4|DC Number 1,2(5078)')
		assert_p('Table', 'Content', '[[D1, 10, 80000000, 1, 48320000, 6222, 50710000, 5, 45400001, 8, 207, 5350, , 6561, 5071, M, .ROSE 24, -6, 607 SHWL], [S1, 5015, 2, 5019, 5, 5035, 2, 5037, 4, 5052, 2, 5055, 2, 5060, 2, 5070, 2, 5074, 4], [S1, 5078, 5, 5081, 2, 5090, 2, 5091, 4, 5093, 4, 5095, 4, 5129, 4, 5144, 4, 5165, 2], [S1, 5303, 2, 5169, 2, 5171, 3, 5177, 4, 5016, 2, 5089, 4, 5136, 3, 5011, 2, 5046, 2], [S1, 5145, 4, 5096, 3, 5162, 2, 5163, 2, 5164, 2, 5192, 2, 5150, 2, 5175, 2, , 0], [D1, 1, 60000006, 2281, 48320000, 0222, 45310000, 5, 45400000, 16, 207, 5348, , 5614, 531, D, ONKEY 24, -6, 607 SHWL], [S1, 5019, 1, 5037, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1, 5095, 1, 51 D, ONKEY 24, -6, 607 SHWL], [S1, 5171, 1, 5177, 1, 5136, 1, 5145, 1, 5096, 1, , 0, , 0, , 0, , 0]]')
		select('Table', 'cell:3 - 4|DC Number 1,6(5019)')
		assert_p('Table', 'RowCount', '8')
		select('Table', 'cell:3 - 4|DC Number 1,6(5019)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Tree,1(null)')
		rightclick('JTreeTable', 'Tree,1')
		select('JTreeTable', 'cell:Sequence Number,0(45.349)')
		rightclick('JTreeTable', 'Sequence Number,0')
		select_menu('Expand Tree')
		select('JTreeTable', 'rows:[4,5,6,7,8],columns:[Record Type]')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'rows:[4,5,6,7,8],columns:[Record Type]')
		select('Table', 'cell:7 - 8|Pack Quantity 1,0(40000000)')
		assert_p('Table', 'RowCount', '13')
		select('Table', 'cell:3 - 4|DC Number 1,4(5015)')
		assert_p('Table', 'Text', 'cell:3 - 4|DC Number 1,4(5015)')
		select('Table', 'cell:7 - 8|Pack Quantity 1,11(80000000)')
		assert_p('Table', 'Text', 'cell:7 - 8|Pack Quantity 1,11(80000000)')
		select('Table', 'cell:7 - 8|Pack Quantity 1,11(80000000)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'utf8a_Ams_PODownload_20041231.txt'):
			click('No')
		close()
	close()
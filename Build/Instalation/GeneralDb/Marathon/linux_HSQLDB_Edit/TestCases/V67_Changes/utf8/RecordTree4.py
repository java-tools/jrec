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
		select('JTreeTable', 'cell:PO,3(294915)')
		assert_p('JTreeTable', 'Text', '294987', 'PO,4')
		select('JTreeTable', 'cell:PO,4(294987)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:PO,5(295139)')
		assert_p('JTreeTable', 'RowCount', '8')
		select('JTreeTable', 'cell:PO,5(295139)')
		rightclick('JTreeTable', 'Record Type,1')
		select('JTreeTable', 'cell:Record Type,1(H1)')
		rightclick('JTreeTable', 'Record Type,1')
		select_menu('Fully Expand Tree')
		select('JTreeTable', 'cell:PO,2(148320000000)')
		assert_p('JTreeTable', 'Text', '148320000000', 'PO,2')
		select('JTreeTable', 'cell:PO,3(700000001507)')
		assert_p('JTreeTable', 'RowCount', '20')
		select('JTreeTable', 'cell:Sequence Number,4(51.710)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , S1, 50.190, 1503, 700000001507, 400000, 00150780, 00, 00, 0015, 085000, 000015, 0, 000001, 5, 0930000000, 1, 50], [, , S1, 51.710, 1517, 700000001508, 900000, 00151360, 00, 00, 0015, 145000, 000015, 0, 000001, ,    0000000, 0, ], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , S1, 50.190, 1503, 700000001507, 800000, 00150910, 00, 00, 0015, 093000, 000015, 0, 000001, 5, 1770000000, 1, 51], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , S1, 50.150, 2501, 900000005503, 300000, 00250350, 00, 00, 0025, 037000, 000045, 0, 000002, 5, 0550000000, 2, 50], [, , S1, 50.780, 5508, 100000002508, 500000, 00350900, 00, 00, 0025, 091000, 000045, 0, 000004, 5, 0950000000, 4, 51], [, , S1, 53.030, 2516, 900000002517, 000000, 00251710, 00, 00, 0035, 177000, 000045, 0, 000002, 5, 0890000000, 4, 51], [, , S1, 51.450, 4509, 600000003515, 400000, 00251620, 00, 00, 0025, 163000, 000025, 0, 000002, 5, 1920000000, 2, 51], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , S1, 50.190, 1503, 700000001507, 800000, 00150910, 00, 00, 0015, 093000, 000015, 0, 000001, 5, 1770000000, 1, 51], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Sequence Number,2(0.016)')
		rightclick('JTreeTable', 'Sequence Number,2')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:Sequence Number,3(0.008)')
		rightclick('JTreeTable', 'Sequence Number,3')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:Sequence Number,4(0.108)')
		rightclick('JTreeTable', 'Sequence Number,4')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:Sequence Number,5(0.008)')
		rightclick('JTreeTable', 'Sequence Number,5')
		select_menu('Collapse Tree')
		select('JTreeTable', 'cell:PO,4(148320000000)')
		assert_p('JTreeTable', 'RowCount', '12')
		select('JTreeTable', 'cell:PO,3(148320000000)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , D1, 0.016, 6228, 148320000000, 000022, 22 02224, 53, 10, 0000, 005454, 000001, ,    207, 5, 348, , 56], [, , D1, 0.008, 0, 148320000000, 000000, 00 62224, 94, 40, 0000, 005454, 000000, ,    207, 5, 349, , 65], [, , D1, 0.108, 0, 148320000000, 000000, 00 62225, 07, 10, 0000, 005454, 000010, ,    207, 5, 350, , 65], [, , D1, 0.008, 0, 148320000000, 000000, 00 52225, 15, 60, 0000, 005454, 000000, ,    207, 5, 351, , 55], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:Tree,2(null)')
		rightclick('JTreeTable', 'Sequence Number,2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		#if window('Save Changes to file: ' + commonBits.sampleDir() + 'utf8a_Ams_PODownload_20041231.txt'):
		#	click('No')
		#close()
	close()

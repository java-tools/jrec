useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		assert_p('LineList.Layouts_Txt', 'Text', 'Prefered')
		click('Filter1')
		select('Filter.RecordSelection_JTbl', 'cell:Include,0(true)')
		select('Filter.RecordSelection_JTbl', 'cell:Include,2(true)')
		assert_p('Filter.RecordSelection_JTbl', 'Content', '[[ams PO Download: Detail, false], [ams PO Download: Header, true], [ams PO Download: Allocation, false]]')
		click('Filter1')


		assert_p('LineList.Layouts_Txt', 'Text', 'Prefered')

		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT, , ], [H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT, , ], [H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT, , ], [H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT, , ], [H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT, , ], [H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT, , ], [H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT, , ], [H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT, , ]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

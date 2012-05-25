useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		assert_p('LineList.Layouts_Txt', 'Text', 'Prefered')
##		select('Table', 'rows:[15,16,17,18,19,20,21,22,23,24,25,26],columns:[Line]')
		select('Table', 'rows:[2,3,4,5,6,7,8,9,10,11,12,13,14],columns:[Line]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('LineList.FileDisplay_JTbl1', 'rows:[2,3,4,5,6,7,8,9,10,11,12,13,14],columns:[3 - 4|DC Number 1,7 - 8|Pack Quantity 1]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[S1, 5043, 1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [D1, 4.0000, 148.3200, 0, , 5614944, 2075360, 5614944,  MILK 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [S1, 5045, 1, 5076, 1, 3331, 49440001, , 0, , 0, , 0, , 0, , 0, , 0], [D1, 48.0000, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [S1, 5036, 3, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [S1, 5151, 4, 5180, 3, 5173, 2, , 0, , 0, , 0, , 0, , 0, , 0], [D1, 4.0000, 148.3200, 0, , 55615156, 2075362, 55615156,  AQUA 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [S1, 5043, 1, 5045, 1, 3331, 51560001, , 0, , 0, , 0, , 0, , 0, , 0], [H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT, , ], [D1, 16.0000, 6228148.3200, 2222, , 2224531, 2075348, 5614531,  DONKEY 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [S1, 5019, 1, 5037, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1, 5095, 1, 51 D, ONKEY 24, -6, 607 SHWL], [S1, 5171, 1, 5177, 1, 5136, 1, 5145, 1, 5096, 1, , 0, , 0, , 0, , 0], [D1, 8.0000, 148.3200, 0, , 62224944, 2075349, 65614944,  MILK 24-006607 SHWL WRAP CARD, , , , , , , , , , ]]')
		assert_p('LineList.Layouts_Txt', 'Text', 'Prefered')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('LineList.FileDisplay_JTbl', 'rows:[2,3,4,5,6,7,8,9,10,11,12,13,14],columns:[3 - 4|DC Number 1,7 - 8|Pack Quantity 1]')
		select_menu('View>>Column View #{Selected Records#}')
#		select('LineList.FileDisplay_JTbl', 'rows:[2,3,4,5,6,7,8,9,10,11,12,13,14],columns:[3 - 4|DC Number 1,7 - 8|Pack Quantity 1]')
		assert_p('LinesAsColumns.FileDisplay_JTbl', 'Content', '[[S1, D1, S1, D1, S1, S1, D1, S1, H1, D1, S1, S1, D1], [5043, 0, 5045, 4, 5036, 5151, 0, 5043, 4535, 1, 5019, 5171, 0], [1, 40000000, 1, 80000000, 3, 4, 40000000, 1, 6, 60000006, 1, 1, 80000000], [5045, 1, 5076, 1, 5043, 5180, 1, 5045, 2280, 2281, 5037, 5177, 1], [1, 48320000, 1, 48320000, 5, 3, 48320000, 1, 222, 48320000, 1, 1, 48320000], [5076, 0561, 3331, 5561, 3331, 5173, 5561, 3331, , 0222, 5078, 5136, 6222], [1, 49440000, 49440001, 50710000, 50710003, 2, 51560000, 51560001, 200, 45310000, 1, 1, 49440000], [5079, 5, , 5, 5065, , 5, , 50, 5, 5085, 5145, 5], [1, 45400000, 0, 45400000, 4, 0, 45400000, 0, 10205010, 45400000, 1, 1, 45400000], [5151, 4, , 48, 5069, , 4, , 7596, 16, 5091, 5096, 8], [1, 207, 0, 207, 4, 0, 207, 0, 6, 207, 1, 1, 207], [5072, 5360, , 5361, 5076, , 5362, , LAD, 5348, 5093, , 5349], [1, , 0, , 4, 0, , 0, IES KNIC, , 1, 0, ], [, 5614, , 5561, 5079, , 5561, , FT, 5614, 5095, , 6561], [0, 944, 0, 5071, 2, 0, 5156, 0, , 531, 1, 0, 4944], [, M, , M, 5094, , A, , , D, 51 D, , M], [0, ILK 24-0, 0, .ROSE 24, 4, 0, QUA 24-0, 0, , ONKEY 24, ONKEY 24, 0, ILK 24-0], [, 660, , -6, 5128, , 660, , , -6, -6, , 660], [0, 7 SHWL W, 0, 607 SHWL, 3, 0, 7 SHWL W, 0, , 607 SHWL, 607 SHWL, 0, 7 SHWL W]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

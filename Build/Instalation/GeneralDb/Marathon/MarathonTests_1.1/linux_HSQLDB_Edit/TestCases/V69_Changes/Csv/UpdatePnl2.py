useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'csvB_DTAR020.bin.csv')

		click('Edit1')
		select_menu('Edit>>Update Csv Columns')
#		select('Table', '')
		rightclick('Table', 'Decimal Places,0')
		select_menu('Add Field Before')
#		select('Table', '')
		rightclick('Table', 'Decimal Places,3')
		select_menu('Move ...>>After  SALE-PRICE')
#		select('Table', '')
		rightclick('Table', 'Decimal Places,3')
		select_menu('Add Field After')
#		select('Table', '')
		rightclick('Table', 'Decimal Places,6')
		select_menu('Add Field After')
		select('Table', 'DEPT-NO', 'Source Column,7')
		select('Table', 'field 1', 'Field Name,0')
		select('Table', 'KEYCODE NO', 'Field Name,1')
		select('Table', 'STORE-NO', 'Source Column,0')
		select('Table', 'cell:Source Column,0(STORE-NO)')
		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[field 1, true, Text, , STORE-NO, ], [KEYCODE NO, true, Text, , , ], [STORE-NO, true, Text, , , ], [DEPT-NO, true, Text, , , ], [5, true, Text, , , ], [QTY-SOLD, true, Text, , , ], [SALE-PRICE, true, Text, , , ], [8, true, Text, , DEPT-NO, ], [DATE, true, Text, , , ]]')
		else:
			assert_p('Table', 'Content', '[[field 1, true, Text, , STORE-NO], [KEYCODE NO, true, Text, , ], [STORE-NO, true, Text, , ], [DEPT-NO, true, Text, , ], [5, true, Text, , ], [QTY-SOLD, true, Text, , ], [SALE-PRICE, true, Text, , ], [8, true, Text, , DEPT-NO], [DATE, true, Text, , ]]')
		select('Table', 'cell:Source Column,0(STORE-NO)')
		click('Apply')
		assert_p('Table', 'Content', '[[20, 69684558, 20, 280, , 1, 19.00, 280, 40118], [20, 69684558, 20, 280, , -1, -19.00, 280, 40118], [20, 69684558, 20, 280, , 1, 5.01, 280, 40118], [20, 69694158, 20, 280, , 1, 19.00, 280, 40118], [20, 69694158, 20, 280, , -1, -19.00, 280, 40118], [20, 69694158, 20, 280, , 1, 5.01, 280, 40118], [20, 63604808, 20, 170, , 1, 4.87, 170, 40118], [20, 62684671, 20, 685, , 1, 69.99, 685, 40118], [20, 62684671, 20, 685, , -1, -69.99, 685, 40118], [20, 64634429, 20, 957, , 1, 3.99, 957, 40118], [20, 66624458, 20, 957, , 1, 0.89, 957, 40118], [20, 63674861, 20, 957, , 10, 2.70, 957, 40118], [20, 65674532, 20, 929, , 1, 3.59, 929, 40118], [59, 64614401, 59, 957, , 1, 1.99, 957, 40118], [59, 64614401, 59, 957, , 1, 1.99, 957, 40118], [59, 61664713, 59, 335, , 1, 17.99, 335, 40118], [59, 61664713, 59, 335, , -1, -17.99, 335, 40118], [59, 68634752, 59, 410, , 1, 8.99, 410, 40118], [59, 60614487, 59, 878, , 1, 5.95, 878, 40118], [59, 63644339, 59, 878, , 1, 12.65, 878, 40118], [59, 60694698, 59, 620, , 1, 3.99, 620, 40118], [59, 60664659, 59, 620, , 1, 3.99, 620, 40118], [59, 62684217, 59, 957, , 1, 9.99, 957, 40118], [59, 67674686, 59, 929, , 1, 3.99, 929, 40118], [59, 61684613, 59, 335, , 1, 12.99, 335, 40118], [59, 64624770, 59, 957, , 1, 2.59, 957, 40118], [166, 69694814, 166, 360, , 1, 2.50, 360, 40118], [166, 69694814, 166, 360, , 1, 2.50, 360, 40118], [166, 69644164, 166, 193, , 1, 21.59, 193, 40118], [166, 62684907, 166, 375, , 1, 13.99, 375, 40118], [166, 62694193, 166, 375, , 1, 13.99, 375, 40118], [166, 62694193, 166, 375, , -1, -13.99, 375, 40118], [166, 62694193, 166, 375, , 1, 11.99, 375, 40118], [166, 63654450, 166, 320, , 1, 13.99, 320, 40118], [166, 62664576, 166, 320, , 1, 9.72, 320, 40118], [166, 63634260, 166, 320, , 1, 5.59, 320, 40118], [166, 64684534, 166, 440, , 1, 14.99, 440, 40118], [166, 64674965, 166, 235, , 1, 19.99, 235, 40118], [166, 64674965, 166, 235, , -1, -19.99, 235, 40118], [166, 64674965, 166, 235, , 1, 12.00, 235, 40118], [166, 60624523, 166, 261, , 1, 12.00, 261, 40118], [166, 66624253, 166, 957, , 1, 3.49, 957, 40118], [166, 66624253, 166, 957, , 1, 3.49, 957, 40118], [166, 64654284, 166, 957, , 1, 3.99, 957, 40118], [166, 60684907, 166, 805, , 1, 5.50, 805, 40118], [166, 63624299, 166, 870, , 1, 10.99, 870, 40118], [166, 63624367, 166, 870, , 1, 11.19, 870, 40118], [166, 62694575, 166, 475, , 1, 14.99, 475, 40118], [166, 69614011, 166, 905, , 1, 6.99, 905, 40118], [166, 62634996, 166, 650, , 1, 9.99, 650, 40118], [166, 67634503, 166, 970, , 1, 24.99, 970, 40118], [166, 65604476, 166, 830, , 1, 19.95, 830, 40118], [166, 62694170, 166, 851, , 1, 16.99, 851, 40118], [166, 63684098, 166, 410, , 1, 1.98, 410, 40118], [166, 63684098, 166, 410, , 1, 1.98, 410, 40118], [166, 63684098, 166, 410, , 1, 1.98, 410, 40118], [166, 64674609, 166, 485, , 1, 29.99, 485, 40118], [166, 62614014, 166, 366, , 1, 14.99, 366, 40118], [166, 61694741, 166, 432, , 1, 9.06, 432, 40118], [166, 62614534, 166, 432, , 1, 9.09, 432, 40118], [166, 64604876, 166, 801, , 1, 29.62, 801, 40118], [166, 66624829, 166, 957, , 1, 1.99, 957, 40118], [166, 62694843, 166, 193, , 1, 13.59, 193, 40118], [166, 62684580, 166, 265, , 1, 19.00, 265, 40118], [166, 62664909, 166, 957, , 1, 3.29, 957, 40118], [166, 62674751, 166, 957, , 1, 1.99, 957, 40118], [166, 62674492, 166, 957, , 1, 1.49, 957, 40118], [166, 62674492, 166, 957, , 1, 1.49, 957, 40118], [166, 62694706, 166, 193, , 1, 13.59, 193, 40118], [166, 69644602, 166, 265, , 1, 19.00, 265, 40118], [166, 63634768, 166, 270, , 1, 12.00, 270, 40118], [166, 62684207, 166, 265, , 1, 19.00, 265, 40118], [166, 69644961, 166, 230, , 1, 9.60, 230, 40118], [166, 69604743, 166, 250, , 1, 29.95, 250, 40118], [166, 63634081, 166, 929, , 1, 3.89, 929, 40118], [166, 69614229, 166, 902, , 1, 15.95, 902, 40118], [166, 62654454, 166, 845, , 1, 5.95, 845, 40118], [166, 64634712, 166, 845, , 1, 3.90, 845, 40118], [166, 62674092, 166, 851, , 1, 15.99, 851, 40118], [166, 67664966, 166, 929, , 1, 0.89, 929, 40118], [166, 67664966, 166, 929, , 1, 0.89, 929, 40118], [166, 64674633, 166, 220, , 1, 15.99, 220, 40118], [166, 64624081, 166, 280, , 1, 26.24, 280, 40118], [166, 69674069, 166, 910, , 1, 10.49, 910, 40118], [166, 62684028, 166, 520, , 1, 29.99, 520, 40118], [166, 64604876, 166, 801, , 1, 29.62, 801, 40118], [166, 68644966, 166, 902, , 1, 12.50, 902, 40118], [166, 68644966, 166, 902, , -1, -12.50, 902, 40118], [166, 68644966, 166, 902, , 1, 0.01, 902, 40118], [166, 62664347, 166, 370, , 1, 8.99, 370, 40118], [166, 62664231, 166, 370, , 1, 8.99, 370, 40118], [166, 62694605, 166, 261, , 1, 25.00, 261, 40118], [166, 69634922, 166, 261, , 1, 19.00, 261, 40118], [166, 63694928, 166, 904, , 1, 11.49, 904, 40118], [166, 60624185, 166, 500, , 1, 8.99, 500, 40118], [166, 60624314, 166, 500, , 1, 8.99, 500, 40118], [166, 69694959, 166, 270, , 1, 11.99, 270, 40118], [166, 69624033, 166, 80, , 1, 18.19, 80, 40118], [166, 62694485, 166, 193, , 1, 17.56, 193, 40118], [166, 60614646, 166, 60, , 1, 6.00, 60, 40118], [166, 63654066, 166, 275, , 1, 24.99, 275, 40118], [166, 62684548, 166, 415, , 1, 39.99, 415, 40118], [166, 62684548, 166, 415, , 1, 39.99, 415, 40118], [166, 69694685, 166, 360, , 1, 6.99, 360, 40118], [166, 63614741, 166, 395, , 1, 27.99, 395, 40118], [166, 60664302, 166, 270, , 1, 9.00, 270, 40118], [166, 60664241, 166, 270, , 1, 9.00, 270, 40118], [166, 66674979, 166, 360, , 1, 4.50, 360, 40118], [166, 62634862, 166, 355, , 1, 11.89, 355, 40118], [166, 62604139, 166, 335, , 1, 7.99, 335, 40118], [166, 62624382, 166, 370, , 1, 18.98, 370, 40118], [166, 62624382, 166, 370, , -1, -18.98, 370, 40118], [166, 69694937, 166, 360, , 1, 2.50, 360, 40118], [166, 62624382, 166, 370, , 1, 18.98, 370, 40118], [166, 62624382, 166, 370, , 1, 18.98, 370, 40118], [166, 62624382, 166, 370, , -1, -18.98, 370, 40118], [166, 62624382, 166, 370, , -1, -18.98, 370, 40118], [166, 62624382, 166, 370, , 1, 18.98, 370, 40118], [166, 62624382, 166, 370, , 1, 18.98, 370, 40118], [166, 61684889, 166, 685, , 1, 4.49, 685, 40118], [166, 68614651, 166, 370, , 1, 3.99, 370, 40118], [166, 62664674, 166, 471, , 1, 24.99, 471, 40118], [166, 61684889, 166, 685, , 1, 4.49, 685, 40118], [166, 60694417, 166, 929, , 1, 0.65, 929, 40118], [166, 65694328, 166, 929, , 1, 0.59, 929, 40118], [166, 63684449, 166, 320, , 1, 16.99, 320, 40118], [166, 62614815, 166, 405, , 1, 20.00, 405, 40118], [166, 62664151, 166, 455, , 1, 25.00, 455, 40118], [166, 64684719, 166, 410, , 1, 9.99, 410, 40118], [166, 69654084, 166, 60, , 1, 6.00, 60, 40118], [166, 69644897, 166, 60, , 1, 5.08, 60, 40118], [166, 68654655, 166, 60, , 1, 5.08, 60, 40118], [166, 68674560, 166, 170, , 1, 5.99, 170, 40118], [166, 62694387, 166, 432, , 1, 7.99, 432, 40118], [166, 62664568, 166, 432, , 1, 5.99, 432, 40118], [166, 69634261, 166, 261, , 1, 12.00, 261, 40118], [166, 69634660, 166, 261, , 1, 12.00, 261, 40118], [166, 69684947, 166, 280, , 1, 22.49, 280, 40118], [166, 60654072, 166, 905, , 1, 4.33, 905, 40118], [166, 60654072, 166, 905, , 1, 4.33, 905, 40118], [166, 69624221, 166, 355, , 1, 16.99, 355, 40118], [166, 62654800, 166, 355, , 1, 19.99, 355, 40118], [166, 64644495, 166, 801, , 1, 29.65, 801, 40118], [166, 67664645, 166, 929, , 1, 1.39, 929, 40118], [166, 60614265, 166, 395, , 1, 15.99, 395, 40118], [166, 68604583, 166, 905, , 1, 15.99, 905, 40118], [166, 60614265, 166, 395, , 1, 15.99, 395, 40118], [166, 60614265, 166, 395, , -1, -15.99, 395, 40118], [166, 60614265, 166, 395, , -1, -15.99, 395, 40118], [166, 68604583, 166, 905, , -1, -15.99, 905, 40118], [166, 68604583, 166, 905, , 1, 15.99, 905, 40118], [166, 68604583, 166, 905, , -1, -15.99, 905, 40118], [166, 68604583, 166, 905, , 1, 12.80, 905, 40118], [166, 60614265, 166, 395, , 1, 15.99, 395, 40118], [166, 60614265, 166, 395, , -1, -15.99, 395, 40118], [166, 60614265, 166, 395, , 1, 12.80, 395, 40118], [166, 69664661, 166, 904, , 1, 14.95, 904, 40118], [166, 68664211, 166, 193, , 1, 11.19, 193, 40118], [166, 60614707, 166, 60, , 1, 6.00, 60, 40118], [166, 64604513, 166, 235, , 1, 16.99, 235, 40118], [166, 60624864, 166, 261, , 1, 15.00, 261, 40118], [166, 69644909, 166, 261, , 1, 9.00, 261, 40118], [166, 60604100, 166, 80, , 1, 13.30, 80, 40118], [166, 69634263, 166, 261, , 1, 25.00, 261, 40118], [166, 69634263, 166, 261, , -1, -25.00, 261, 40118], [166, 69634263, 166, 261, , 1, 12.00, 261, 40118], [166, 61674701, 166, 670, , 1, 3.99, 670, 40118], [166, 63654007, 166, 670, , 1, 56.99, 670, 40118], [166, 67624103, 166, 801, , 1, 16.50, 801, 40118], [166, 68614329, 166, 905, , 1, 39.99, 905, 40118], [166, 67644384, 166, 193, , 1, 23.96, 193, 40118], [166, 64644495, 166, 801, , 1, 29.65, 801, 40118], [184, 60684484, 184, 220, , 1, 9.00, 220, 40118], [184, 60684484, 184, 220, , 1, 9.00, 220, 40118], [184, 67674299, 184, 905, , 1, 4.99, 905, 40118], [184, 69664620, 184, 355, , 1, 11.89, 355, 40118], [184, 69664620, 184, 355, , -1, -11.89, 355, 40118], [184, 69664620, 184, 355, , 1, 9.09, 355, 40118], [184, 60674210, 184, 275, , -1, -15.00, 275, 40118], [184, 60664048, 184, 60, , -1, -4.80, 60, 40118], [184, 60614866, 184, 60, , -1, -4.80, 60, 40118], [184, 60664048, 184, 60, , -1, -4.80, 60, 40118], [184, 60664048, 184, 60, , -1, -4.80, 60, 40118], [184, 60614866, 184, 60, , -1, -4.80, 60, 40118], [184, 69654081, 184, 70, , 1, 12.99, 70, 40118], [184, 63664643, 184, 193, , 1, 16.79, 193, 40118], [184, 69654135, 184, 70, , 1, 12.99, 70, 40118], [184, 60644672, 184, 160, , 1, 9.09, 160, 40118], [184, 62654852, 184, 345, , -1, -19.59, 345, 40118], [184, 62674960, 184, 490, , -1, -16.00, 490, 40118], [184, 62674960, 184, 490, , 1, 16.00, 490, 40118], [184, 65674126, 184, 929, , 1, 2.69, 929, 40118], [184, 60634192, 184, 500, , 1, 24.99, 500, 40118], [184, 64634500, 184, 957, , -1, -9.99, 957, 40118], [184, 66624803, 184, 170, , 1, 2.00, 170, 40118], [184, 66624803, 184, 170, , -1, -2.00, 170, 40118], [184, 66624803, 184, 170, , 1, 1.04, 170, 40118], [184, 66624889, 184, 170, , 1, 2.00, 170, 40118], [184, 66624889, 184, 170, , -1, -2.00, 170, 40118], [184, 66624889, 184, 170, , 1, 1.04, 170, 40118], [184, 60624241, 184, 500, , 1, 34.99, 500, 40118], [184, 62644079, 184, 650, , 1, 9.99, 650, 40118], [184, 62664183, 184, 320, , -1, -20.99, 320, 40118], [184, 64654047, 184, 320, , -1, -25.99, 320, 40118], [184, 62694327, 184, 375, , -1, -10.39, 375, 40118], [184, 69604894, 184, 275, , -1, -19.00, 275, 40118], [184, 67644821, 184, 60, , -1, -14.99, 60, 40118], [184, 67644118, 184, 60, , 1, 16.99, 60, 40118], [184, 66664981, 184, 901, , 1, 3.09, 901, 40118], [184, 66684899, 184, 901, , 1, 12.99, 901, 40118], [184, 64634942, 184, 270, , 1, 15.99, 270, 40118], [184, 63654826, 184, 275, , 1, 19.00, 275, 40118], [184, 69604993, 184, 220, , -1, -14.39, 220, 40118], [184, 63624118, 184, 250, , -1, -15.00, 250, 40118], [184, 62684517, 184, 475, , -1, -29.99, 475, 40118], [184, 67634503, 184, 970, , 1, 24.99, 970, 40118], [184, 62694782, 184, 193, , -1, -16.99, 193, 40118], [184, 62694683, 184, 193, , -1, -16.99, 193, 40118], [184, 62694782, 184, 193, , -1, -16.99, 193, 40118], [184, 62694782, 184, 193, , -1, -16.99, 193, 40118], [184, 62684096, 184, 310, , 1, 14.00, 310, 40118], [184, 69644199, 184, 193, , 1, 19.19, 193, 40118], [184, 60634366, 184, 904, , 1, 7.99, 904, 40118], [184, 67654448, 184, 70, , 1, 23.00, 70, 40118], [184, 67654448, 184, 70, , -1, -23.00, 70, 40118], [184, 67654448, 184, 70, , 1, 19.01, 70, 40118], [184, 69654459, 184, 60, , 1, 5.08, 60, 40118], [184, 60664779, 184, 60, , 1, 5.08, 60, 40118], [184, 63674002, 184, 310, , -1, -29.99, 310, 40118], [184, 68604041, 184, 870, , -1, -19.99, 870, 40118], [184, 67634503, 184, 970, , 1, 24.99, 970, 40118], [184, 68644941, 184, 60, , -1, -10.39, 60, 40118], [184, 68644941, 184, 60, , -1, -10.39, 60, 40118], [184, 68644941, 184, 60, , -1, -10.39, 60, 40118], [184, 68644941, 184, 60, , -1, -10.39, 60, 40118], [184, 68644934, 184, 60, , 1, 10.39, 60, 40118], [184, 62674884, 184, 350, , -1, -8.99, 350, 40118], [184, 68654381, 184, 235, , 1, 14.95, 235, 40118], [184, 64644433, 184, 801, , 1, 24.99, 801, 40118], [184, 64604829, 184, 805, , -1, -29.99, 805, 40118], [184, 62634996, 184, 650, , 1, 9.99, 650, 40118], [184, 60624270, 184, 670, , 1, 0.95, 670, 40118], [184, 63664932, 184, 929, , 1, 1.59, 929, 40118], [184, 69684804, 184, 851, , 1, 9.99, 851, 40118], [184, 68664363, 184, 415, , -1, -29.95, 415, 40118], [184, 62694503, 184, 929, , 1, 2.99, 929, 40118], [184, 62644205, 184, 590, , 1, 24.99, 590, 40118], [184, 62604912, 184, 590, , 1, 24.89, 590, 40118], [184, 64634802, 184, 904, , 1, 4.29, 904, 40118], [184, 60664257, 184, 901, , 1, 2.12, 901, 40118], [184, 62684043, 184, 878, , -1, -19.95, 878, 40118], [184, 62684043, 184, 878, , 1, 19.95, 878, 40118], [184, 64644860, 184, 805, , -1, -3.95, 805, 40118], [184, 62644590, 184, 590, , -1, -22.99, 590, 40118], [184, 64664587, 184, 432, , -1, -16.99, 432, 40118], [184, 64604930, 184, 801, , -1, -35.95, 801, 40118], [184, 60604880, 184, 250, , 1, 9.00, 250, 40118], [184, 68674372, 184, 845, , 1, 2.00, 845, 40118], [184, 63684755, 184, 845, , 1, 2.00, 845, 40118], [184, 64684439, 184, 929, , 1, 9.99, 929, 40118], [184, 67624120, 184, 940, , 1, 0.98, 940, 40118], [184, 67624120, 184, 940, , -1, -0.98, 940, 40118], [184, 67624120, 184, 940, , 1, 0.49, 940, 40118], [184, 67624120, 184, 940, , 1, 0.98, 940, 40118], [184, 67624120, 184, 940, , -1, -0.98, 940, 40118], [184, 67624120, 184, 940, , 1, 0.49, 940, 40118], [184, 66614192, 184, 940, , 1, 1.23, 940, 40118], [184, 66614192, 184, 940, , -1, -1.23, 940, 40118], [184, 66614192, 184, 940, , 1, 0.69, 940, 40118], [184, 66614192, 184, 940, , 1, 1.23, 940, 40118], [184, 66614192, 184, 940, , -1, -1.23, 940, 40118], [184, 66614192, 184, 940, , 1, 0.69, 940, 40118], [184, 67624473, 184, 940, , 1, 0.50, 940, 40118], [184, 67624473, 184, 940, , -1, -0.50, 940, 40118], [184, 67624473, 184, 940, , 1, 0.19, 940, 40118], [184, 67614923, 184, 875, , -1, -19.95, 875, 40118], [184, 63604715, 184, 432, , -1, -6.99, 432, 40118], [184, 63604715, 184, 432, , -1, -6.99, 432, 40118], [184, 63604715, 184, 432, , -1, -6.99, 432, 40118], [184, 62684102, 184, 310, , 1, 14.00, 310, 40118], [184, 63654091, 184, 805, , 1, 4.95, 805, 40118], [184, 68694729, 184, 855, , -1, -16.99, 855, 40118], [184, 60684563, 184, 220, , 1, 9.59, 220, 40118], [184, 68664957, 184, 80, , 1, 13.99, 80, 40118], [184, 67604116, 184, 801, , 1, 21.00, 801, 40118], [184, 64664047, 184, 801, , 1, 22.99, 801, 40118], [184, 67634497, 184, 970, , 1, 24.99, 970, 40118], [184, 64664270, 184, 929, , 1, 3.19, 929, 40118], [184, 60634150, 184, 160, , -1, -14.99, 160, 40118], [184, 68684135, 184, 878, , -1, -249.00, 878, 40118], [184, 68684135, 184, 878, , 1, 269.00, 878, 40118], [184, 68684135, 184, 878, , -1, -269.00, 878, 40118], [184, 68684135, 184, 878, , 1, 249.00, 878, 40118], [184, 69624909, 184, 235, , -1, -26.21, 235, 40118], [184, 62634962, 184, 530, , -1, -6.89, 530, 40118], [184, 67614726, 184, 160, , -1, -9.95, 160, 40118], [184, 68654094, 184, 170, , -1, -4.50, 170, 40118], [184, 60674335, 184, 485, , 1, 19.95, 485, 40118], [184, 60674335, 184, 485, , -1, -19.95, 485, 40118], [184, 60674335, 184, 485, , 1, 12.99, 485, 40118], [184, 60644305, 184, 929, , 1, 0.89, 929, 40118], [184, 64604604, 184, 366, , 1, 6.99, 366, 40118], [184, 63694367, 184, 290, , 1, 19.00, 290, 40118], [184, 62644079, 184, 650, , 1, 9.99, 650, 40118], [184, 67674119, 184, 929, , 1, 1.89, 929, 40118], [184, 69614627, 184, 265, , 1, 19.00, 265, 40118], [184, 69614740, 184, 265, , 1, 19.00, 265, 40118], [184, 60614135, 184, 230, , -1, -14.25, 230, 40118], [184, 69674452, 184, 970, , 1, 19.99, 970, 40118], [184, 62694272, 184, 957, , 1, 2.49, 957, 40118], [184, 69614274, 184, 902, , 1, 9.95, 902, 40118], [184, 60634379, 184, 290, , 1, 25.00, 290, 40118], [184, 60674904, 184, 261, , 1, 19.00, 261, 40118], [184, 66614582, 184, 670, , -1, -15.99, 670, 40118], [184, 64654096, 184, 805, , 1, 4.95, 805, 40118], [184, 62634605, 184, 405, , 1, 29.99, 405, 40118], [184, 69694354, 184, 929, , 1, 2.99, 929, 40118], [184, 69634699, 184, 80, , 1, 20.99, 80, 40118], [184, 69634712, 184, 80, , 1, 20.99, 80, 40118], [184, 69664171, 184, 250, , -1, -27.95, 250, 40118], [184, 69694479, 184, 280, , 1, 14.99, 280, 40118], [184, 63634656, 184, 929, , -1, -5.50, 929, 40118], [184, 69664149, 184, 903, , -1, -5.99, 903, 40118], [184, 69664163, 184, 903, , -1, -1.39, 903, 40118], [184, 67674341, 184, 901, , -1, -19.95, 901, 40118], [184, 69644053, 184, 230, , 1, 18.74, 230, 40118], [184, 63674184, 184, 957, , 1, 164.00, 957, 40118], [184, 62604338, 184, 870, , 1, 69.99, 870, 40118], [184, 62644344, 184, 432, , 1, 10.99, 432, 40118], [184, 66644706, 184, 432, , 1, 10.99, 432, 40118], [184, 62644528, 184, 432, , 1, 10.99, 432, 40118], [184, 62644764, 184, 432, , 1, 10.99, 432, 40118], [184, 62664135, 184, 405, , 1, 11.00, 405, 40118], [184, 62664244, 184, 405, , 1, 11.00, 405, 40118], [184, 62664197, 184, 405, , 1, 11.00, 405, 40118], [184, 62654875, 184, 471, , 1, 39.99, 471, 40118], [184, 64614653, 184, 265, , 1, 34.99, 265, 40118], [184, 64614653, 184, 265, , 1, 34.99, 265, 40118], [184, 61694023, 184, 650, , 1, 14.99, 650, 40118], [184, 61694023, 184, 650, , 1, 14.99, 650, 40118], [184, 60694909, 184, 998, , 1, 2.00, 998, 40118], [184, 68614241, 184, 70, , 1, 12.99, 70, 40118], [184, 69654638, 184, 60, , 1, 6.00, 60, 40118], [184, 67634923, 184, 60, , 1, 17.59, 60, 40118], [184, 64614285, 184, 70, , 1, 22.99, 70, 40118], [184, 69644389, 184, 193, , 1, 17.59, 193, 40118], [184, 68614787, 184, 70, , 1, 27.99, 70, 40118], [184, 69644337, 184, 70, , 1, 29.99, 70, 40118], [184, 68634061, 184, 70, , 1, 27.99, 70, 40118], [184, 69644184, 184, 193, , 1, 15.99, 193, 40118], [184, 63624756, 184, 801, , 1, 21.00, 801, 40118], [184, 67604116, 184, 801, , 1, 21.00, 801, 40118], [184, 68654451, 184, 193, , 1, 27.99, 193, 40118], [184, 62614172, 184, 193, , 1, 19.19, 193, 40118], [184, 61614174, 184, 620, , 1, 49.99, 620, 40118], [184, 61614174, 184, 620, , 1, 29.99, 620, 40118], [184, 62644445, 184, 801, , 1, 69.95, 801, 40118], [184, 62634323, 184, 905, , 1, 149.00, 905, 40118], [184, 66674079, 184, 901, , 1, 3.95, 901, 40118], [184, 66674130, 184, 901, , 1, 3.95, 901, 40118], [184, 61604095, 184, 902, , 1, 12.50, 902, 40118], [184, 66664028, 184, 902, , 1, 14.95, 902, 40118], [184, 68654621, 184, 902, , 1, 12.95, 902, 40118], [184, 63694264, 184, 830, , 1, 19.95, 830, 40118], [184, 63604361, 184, 901, , 1, 3.99, 901, 40118], [184, 62634259, 184, 901, , 1, 6.69, 901, 40118], [184, 62634259, 184, 901, , 1, 6.69, 901, 40118], [184, 60684429, 184, 904, , 1, 4.89, 904, 40118], [184, 60684037, 184, 904, , 1, 9.95, 904, 40118], [184, 69694875, 184, 904, , 1, 19.95, 904, 40118], [184, 69694875, 184, 904, , 1, 19.95, 904, 40118], [184, 69694875, 184, 904, , 1, 19.95, 904, 40118], [184, 69694875, 184, 904, , -1, -19.95, 904, 40118], [184, 69694875, 184, 904, , -1, -19.95, 904, 40118], [184, 69694875, 184, 904, , -1, -19.95, 904, 40118], [184, 63604108, 184, 901, , 1, 3.95, 901, 40118], [184, 63694928, 184, 904, , 1, 11.49, 904, 40118], [184, 60634765, 184, 904, , 1, 4.99, 904, 40118], [184, 69664668, 184, 903, , 1, 8.95, 903, 40118]]')
		select('LayoutCombo', 'Full Line')
		assert_p('Table', 'Content', '[[20,69684558,20,280,,1,19.00,280,40118], [20,69684558,20,280,,-1,-19.00,280,40118], [20,69684558,20,280,,1,5.01,280,40118], [20,69694158,20,280,,1,19.00,280,40118], [20,69694158,20,280,,-1,-19.00,280,40118], [20,69694158,20,280,,1,5.01,280,40118], [20,63604808,20,170,,1,4.87,170,40118], [20,62684671,20,685,,1,69.99,685,40118], [20,62684671,20,685,,-1,-69.99,685,40118], [20,64634429,20,957,,1,3.99,957,40118], [20,66624458,20,957,,1,0.89,957,40118], [20,63674861,20,957,,10,2.70,957,40118], [20,65674532,20,929,,1,3.59,929,40118], [59,64614401,59,957,,1,1.99,957,40118], [59,64614401,59,957,,1,1.99,957,40118], [59,61664713,59,335,,1,17.99,335,40118], [59,61664713,59,335,,-1,-17.99,335,40118], [59,68634752,59,410,,1,8.99,410,40118], [59,60614487,59,878,,1,5.95,878,40118], [59,63644339,59,878,,1,12.65,878,40118], [59,60694698,59,620,,1,3.99,620,40118], [59,60664659,59,620,,1,3.99,620,40118], [59,62684217,59,957,,1,9.99,957,40118], [59,67674686,59,929,,1,3.99,929,40118], [59,61684613,59,335,,1,12.99,335,40118], [59,64624770,59,957,,1,2.59,957,40118], [166,69694814,166,360,,1,2.50,360,40118], [166,69694814,166,360,,1,2.50,360,40118], [166,69644164,166,193,,1,21.59,193,40118], [166,62684907,166,375,,1,13.99,375,40118], [166,62694193,166,375,,1,13.99,375,40118], [166,62694193,166,375,,-1,-13.99,375,40118], [166,62694193,166,375,,1,11.99,375,40118], [166,63654450,166,320,,1,13.99,320,40118], [166,62664576,166,320,,1,9.72,320,40118], [166,63634260,166,320,,1,5.59,320,40118], [166,64684534,166,440,,1,14.99,440,40118], [166,64674965,166,235,,1,19.99,235,40118], [166,64674965,166,235,,-1,-19.99,235,40118], [166,64674965,166,235,,1,12.00,235,40118], [166,60624523,166,261,,1,12.00,261,40118], [166,66624253,166,957,,1,3.49,957,40118], [166,66624253,166,957,,1,3.49,957,40118], [166,64654284,166,957,,1,3.99,957,40118], [166,60684907,166,805,,1,5.50,805,40118], [166,63624299,166,870,,1,10.99,870,40118], [166,63624367,166,870,,1,11.19,870,40118], [166,62694575,166,475,,1,14.99,475,40118], [166,69614011,166,905,,1,6.99,905,40118], [166,62634996,166,650,,1,9.99,650,40118], [166,67634503,166,970,,1,24.99,970,40118], [166,65604476,166,830,,1,19.95,830,40118], [166,62694170,166,851,,1,16.99,851,40118], [166,63684098,166,410,,1,1.98,410,40118], [166,63684098,166,410,,1,1.98,410,40118], [166,63684098,166,410,,1,1.98,410,40118], [166,64674609,166,485,,1,29.99,485,40118], [166,62614014,166,366,,1,14.99,366,40118], [166,61694741,166,432,,1,9.06,432,40118], [166,62614534,166,432,,1,9.09,432,40118], [166,64604876,166,801,,1,29.62,801,40118], [166,66624829,166,957,,1,1.99,957,40118], [166,62694843,166,193,,1,13.59,193,40118], [166,62684580,166,265,,1,19.00,265,40118], [166,62664909,166,957,,1,3.29,957,40118], [166,62674751,166,957,,1,1.99,957,40118], [166,62674492,166,957,,1,1.49,957,40118], [166,62674492,166,957,,1,1.49,957,40118], [166,62694706,166,193,,1,13.59,193,40118], [166,69644602,166,265,,1,19.00,265,40118], [166,63634768,166,270,,1,12.00,270,40118], [166,62684207,166,265,,1,19.00,265,40118], [166,69644961,166,230,,1,9.60,230,40118], [166,69604743,166,250,,1,29.95,250,40118], [166,63634081,166,929,,1,3.89,929,40118], [166,69614229,166,902,,1,15.95,902,40118], [166,62654454,166,845,,1,5.95,845,40118], [166,64634712,166,845,,1,3.90,845,40118], [166,62674092,166,851,,1,15.99,851,40118], [166,67664966,166,929,,1,0.89,929,40118], [166,67664966,166,929,,1,0.89,929,40118], [166,64674633,166,220,,1,15.99,220,40118], [166,64624081,166,280,,1,26.24,280,40118], [166,69674069,166,910,,1,10.49,910,40118], [166,62684028,166,520,,1,29.99,520,40118], [166,64604876,166,801,,1,29.62,801,40118], [166,68644966,166,902,,1,12.50,902,40118], [166,68644966,166,902,,-1,-12.50,902,40118], [166,68644966,166,902,,1,0.01,902,40118], [166,62664347,166,370,,1,8.99,370,40118], [166,62664231,166,370,,1,8.99,370,40118], [166,62694605,166,261,,1,25.00,261,40118], [166,69634922,166,261,,1,19.00,261,40118], [166,63694928,166,904,,1,11.49,904,40118], [166,60624185,166,500,,1,8.99,500,40118], [166,60624314,166,500,,1,8.99,500,40118], [166,69694959,166,270,,1,11.99,270,40118], [166,69624033,166,80,,1,18.19,80,40118], [166,62694485,166,193,,1,17.56,193,40118], [166,60614646,166,60,,1,6.00,60,40118], [166,63654066,166,275,,1,24.99,275,40118], [166,62684548,166,415,,1,39.99,415,40118], [166,62684548,166,415,,1,39.99,415,40118], [166,69694685,166,360,,1,6.99,360,40118], [166,63614741,166,395,,1,27.99,395,40118], [166,60664302,166,270,,1,9.00,270,40118], [166,60664241,166,270,,1,9.00,270,40118], [166,66674979,166,360,,1,4.50,360,40118], [166,62634862,166,355,,1,11.89,355,40118], [166,62604139,166,335,,1,7.99,335,40118], [166,62624382,166,370,,1,18.98,370,40118], [166,62624382,166,370,,-1,-18.98,370,40118], [166,69694937,166,360,,1,2.50,360,40118], [166,62624382,166,370,,1,18.98,370,40118], [166,62624382,166,370,,1,18.98,370,40118], [166,62624382,166,370,,-1,-18.98,370,40118], [166,62624382,166,370,,-1,-18.98,370,40118], [166,62624382,166,370,,1,18.98,370,40118], [166,62624382,166,370,,1,18.98,370,40118], [166,61684889,166,685,,1,4.49,685,40118], [166,68614651,166,370,,1,3.99,370,40118], [166,62664674,166,471,,1,24.99,471,40118], [166,61684889,166,685,,1,4.49,685,40118], [166,60694417,166,929,,1,0.65,929,40118], [166,65694328,166,929,,1,0.59,929,40118], [166,63684449,166,320,,1,16.99,320,40118], [166,62614815,166,405,,1,20.00,405,40118], [166,62664151,166,455,,1,25.00,455,40118], [166,64684719,166,410,,1,9.99,410,40118], [166,69654084,166,60,,1,6.00,60,40118], [166,69644897,166,60,,1,5.08,60,40118], [166,68654655,166,60,,1,5.08,60,40118], [166,68674560,166,170,,1,5.99,170,40118], [166,62694387,166,432,,1,7.99,432,40118], [166,62664568,166,432,,1,5.99,432,40118], [166,69634261,166,261,,1,12.00,261,40118], [166,69634660,166,261,,1,12.00,261,40118], [166,69684947,166,280,,1,22.49,280,40118], [166,60654072,166,905,,1,4.33,905,40118], [166,60654072,166,905,,1,4.33,905,40118], [166,69624221,166,355,,1,16.99,355,40118], [166,62654800,166,355,,1,19.99,355,40118], [166,64644495,166,801,,1,29.65,801,40118], [166,67664645,166,929,,1,1.39,929,40118], [166,60614265,166,395,,1,15.99,395,40118], [166,68604583,166,905,,1,15.99,905,40118], [166,60614265,166,395,,1,15.99,395,40118], [166,60614265,166,395,,-1,-15.99,395,40118], [166,60614265,166,395,,-1,-15.99,395,40118], [166,68604583,166,905,,-1,-15.99,905,40118], [166,68604583,166,905,,1,15.99,905,40118], [166,68604583,166,905,,-1,-15.99,905,40118], [166,68604583,166,905,,1,12.80,905,40118], [166,60614265,166,395,,1,15.99,395,40118], [166,60614265,166,395,,-1,-15.99,395,40118], [166,60614265,166,395,,1,12.80,395,40118], [166,69664661,166,904,,1,14.95,904,40118], [166,68664211,166,193,,1,11.19,193,40118], [166,60614707,166,60,,1,6.00,60,40118], [166,64604513,166,235,,1,16.99,235,40118], [166,60624864,166,261,,1,15.00,261,40118], [166,69644909,166,261,,1,9.00,261,40118], [166,60604100,166,80,,1,13.30,80,40118], [166,69634263,166,261,,1,25.00,261,40118], [166,69634263,166,261,,-1,-25.00,261,40118], [166,69634263,166,261,,1,12.00,261,40118], [166,61674701,166,670,,1,3.99,670,40118], [166,63654007,166,670,,1,56.99,670,40118], [166,67624103,166,801,,1,16.50,801,40118], [166,68614329,166,905,,1,39.99,905,40118], [166,67644384,166,193,,1,23.96,193,40118], [166,64644495,166,801,,1,29.65,801,40118], [184,60684484,184,220,,1,9.00,220,40118], [184,60684484,184,220,,1,9.00,220,40118], [184,67674299,184,905,,1,4.99,905,40118], [184,69664620,184,355,,1,11.89,355,40118], [184,69664620,184,355,,-1,-11.89,355,40118], [184,69664620,184,355,,1,9.09,355,40118], [184,60674210,184,275,,-1,-15.00,275,40118], [184,60664048,184,60,,-1,-4.80,60,40118], [184,60614866,184,60,,-1,-4.80,60,40118], [184,60664048,184,60,,-1,-4.80,60,40118], [184,60664048,184,60,,-1,-4.80,60,40118], [184,60614866,184,60,,-1,-4.80,60,40118], [184,69654081,184,70,,1,12.99,70,40118], [184,63664643,184,193,,1,16.79,193,40118], [184,69654135,184,70,,1,12.99,70,40118], [184,60644672,184,160,,1,9.09,160,40118], [184,62654852,184,345,,-1,-19.59,345,40118], [184,62674960,184,490,,-1,-16.00,490,40118], [184,62674960,184,490,,1,16.00,490,40118], [184,65674126,184,929,,1,2.69,929,40118], [184,60634192,184,500,,1,24.99,500,40118], [184,64634500,184,957,,-1,-9.99,957,40118], [184,66624803,184,170,,1,2.00,170,40118], [184,66624803,184,170,,-1,-2.00,170,40118], [184,66624803,184,170,,1,1.04,170,40118], [184,66624889,184,170,,1,2.00,170,40118], [184,66624889,184,170,,-1,-2.00,170,40118], [184,66624889,184,170,,1,1.04,170,40118], [184,60624241,184,500,,1,34.99,500,40118], [184,62644079,184,650,,1,9.99,650,40118], [184,62664183,184,320,,-1,-20.99,320,40118], [184,64654047,184,320,,-1,-25.99,320,40118], [184,62694327,184,375,,-1,-10.39,375,40118], [184,69604894,184,275,,-1,-19.00,275,40118], [184,67644821,184,60,,-1,-14.99,60,40118], [184,67644118,184,60,,1,16.99,60,40118], [184,66664981,184,901,,1,3.09,901,40118], [184,66684899,184,901,,1,12.99,901,40118], [184,64634942,184,270,,1,15.99,270,40118], [184,63654826,184,275,,1,19.00,275,40118], [184,69604993,184,220,,-1,-14.39,220,40118], [184,63624118,184,250,,-1,-15.00,250,40118], [184,62684517,184,475,,-1,-29.99,475,40118], [184,67634503,184,970,,1,24.99,970,40118], [184,62694782,184,193,,-1,-16.99,193,40118], [184,62694683,184,193,,-1,-16.99,193,40118], [184,62694782,184,193,,-1,-16.99,193,40118], [184,62694782,184,193,,-1,-16.99,193,40118], [184,62684096,184,310,,1,14.00,310,40118], [184,69644199,184,193,,1,19.19,193,40118], [184,60634366,184,904,,1,7.99,904,40118], [184,67654448,184,70,,1,23.00,70,40118], [184,67654448,184,70,,-1,-23.00,70,40118], [184,67654448,184,70,,1,19.01,70,40118], [184,69654459,184,60,,1,5.08,60,40118], [184,60664779,184,60,,1,5.08,60,40118], [184,63674002,184,310,,-1,-29.99,310,40118], [184,68604041,184,870,,-1,-19.99,870,40118], [184,67634503,184,970,,1,24.99,970,40118], [184,68644941,184,60,,-1,-10.39,60,40118], [184,68644941,184,60,,-1,-10.39,60,40118], [184,68644941,184,60,,-1,-10.39,60,40118], [184,68644941,184,60,,-1,-10.39,60,40118], [184,68644934,184,60,,1,10.39,60,40118], [184,62674884,184,350,,-1,-8.99,350,40118], [184,68654381,184,235,,1,14.95,235,40118], [184,64644433,184,801,,1,24.99,801,40118], [184,64604829,184,805,,-1,-29.99,805,40118], [184,62634996,184,650,,1,9.99,650,40118], [184,60624270,184,670,,1,0.95,670,40118], [184,63664932,184,929,,1,1.59,929,40118], [184,69684804,184,851,,1,9.99,851,40118], [184,68664363,184,415,,-1,-29.95,415,40118], [184,62694503,184,929,,1,2.99,929,40118], [184,62644205,184,590,,1,24.99,590,40118], [184,62604912,184,590,,1,24.89,590,40118], [184,64634802,184,904,,1,4.29,904,40118], [184,60664257,184,901,,1,2.12,901,40118], [184,62684043,184,878,,-1,-19.95,878,40118], [184,62684043,184,878,,1,19.95,878,40118], [184,64644860,184,805,,-1,-3.95,805,40118], [184,62644590,184,590,,-1,-22.99,590,40118], [184,64664587,184,432,,-1,-16.99,432,40118], [184,64604930,184,801,,-1,-35.95,801,40118], [184,60604880,184,250,,1,9.00,250,40118], [184,68674372,184,845,,1,2.00,845,40118], [184,63684755,184,845,,1,2.00,845,40118], [184,64684439,184,929,,1,9.99,929,40118], [184,67624120,184,940,,1,0.98,940,40118], [184,67624120,184,940,,-1,-0.98,940,40118], [184,67624120,184,940,,1,0.49,940,40118], [184,67624120,184,940,,1,0.98,940,40118], [184,67624120,184,940,,-1,-0.98,940,40118], [184,67624120,184,940,,1,0.49,940,40118], [184,66614192,184,940,,1,1.23,940,40118], [184,66614192,184,940,,-1,-1.23,940,40118], [184,66614192,184,940,,1,0.69,940,40118], [184,66614192,184,940,,1,1.23,940,40118], [184,66614192,184,940,,-1,-1.23,940,40118], [184,66614192,184,940,,1,0.69,940,40118], [184,67624473,184,940,,1,0.50,940,40118], [184,67624473,184,940,,-1,-0.50,940,40118], [184,67624473,184,940,,1,0.19,940,40118], [184,67614923,184,875,,-1,-19.95,875,40118], [184,63604715,184,432,,-1,-6.99,432,40118], [184,63604715,184,432,,-1,-6.99,432,40118], [184,63604715,184,432,,-1,-6.99,432,40118], [184,62684102,184,310,,1,14.00,310,40118], [184,63654091,184,805,,1,4.95,805,40118], [184,68694729,184,855,,-1,-16.99,855,40118], [184,60684563,184,220,,1,9.59,220,40118], [184,68664957,184,80,,1,13.99,80,40118], [184,67604116,184,801,,1,21.00,801,40118], [184,64664047,184,801,,1,22.99,801,40118], [184,67634497,184,970,,1,24.99,970,40118], [184,64664270,184,929,,1,3.19,929,40118], [184,60634150,184,160,,-1,-14.99,160,40118], [184,68684135,184,878,,-1,-249.00,878,40118], [184,68684135,184,878,,1,269.00,878,40118], [184,68684135,184,878,,-1,-269.00,878,40118], [184,68684135,184,878,,1,249.00,878,40118], [184,69624909,184,235,,-1,-26.21,235,40118], [184,62634962,184,530,,-1,-6.89,530,40118], [184,67614726,184,160,,-1,-9.95,160,40118], [184,68654094,184,170,,-1,-4.50,170,40118], [184,60674335,184,485,,1,19.95,485,40118], [184,60674335,184,485,,-1,-19.95,485,40118], [184,60674335,184,485,,1,12.99,485,40118], [184,60644305,184,929,,1,0.89,929,40118], [184,64604604,184,366,,1,6.99,366,40118], [184,63694367,184,290,,1,19.00,290,40118], [184,62644079,184,650,,1,9.99,650,40118], [184,67674119,184,929,,1,1.89,929,40118], [184,69614627,184,265,,1,19.00,265,40118], [184,69614740,184,265,,1,19.00,265,40118], [184,60614135,184,230,,-1,-14.25,230,40118], [184,69674452,184,970,,1,19.99,970,40118], [184,62694272,184,957,,1,2.49,957,40118], [184,69614274,184,902,,1,9.95,902,40118], [184,60634379,184,290,,1,25.00,290,40118], [184,60674904,184,261,,1,19.00,261,40118], [184,66614582,184,670,,-1,-15.99,670,40118], [184,64654096,184,805,,1,4.95,805,40118], [184,62634605,184,405,,1,29.99,405,40118], [184,69694354,184,929,,1,2.99,929,40118], [184,69634699,184,80,,1,20.99,80,40118], [184,69634712,184,80,,1,20.99,80,40118], [184,69664171,184,250,,-1,-27.95,250,40118], [184,69694479,184,280,,1,14.99,280,40118], [184,63634656,184,929,,-1,-5.50,929,40118], [184,69664149,184,903,,-1,-5.99,903,40118], [184,69664163,184,903,,-1,-1.39,903,40118], [184,67674341,184,901,,-1,-19.95,901,40118], [184,69644053,184,230,,1,18.74,230,40118], [184,63674184,184,957,,1,164.00,957,40118], [184,62604338,184,870,,1,69.99,870,40118], [184,62644344,184,432,,1,10.99,432,40118], [184,66644706,184,432,,1,10.99,432,40118], [184,62644528,184,432,,1,10.99,432,40118], [184,62644764,184,432,,1,10.99,432,40118], [184,62664135,184,405,,1,11.00,405,40118], [184,62664244,184,405,,1,11.00,405,40118], [184,62664197,184,405,,1,11.00,405,40118], [184,62654875,184,471,,1,39.99,471,40118], [184,64614653,184,265,,1,34.99,265,40118], [184,64614653,184,265,,1,34.99,265,40118], [184,61694023,184,650,,1,14.99,650,40118], [184,61694023,184,650,,1,14.99,650,40118], [184,60694909,184,998,,1,2.00,998,40118], [184,68614241,184,70,,1,12.99,70,40118], [184,69654638,184,60,,1,6.00,60,40118], [184,67634923,184,60,,1,17.59,60,40118], [184,64614285,184,70,,1,22.99,70,40118], [184,69644389,184,193,,1,17.59,193,40118], [184,68614787,184,70,,1,27.99,70,40118], [184,69644337,184,70,,1,29.99,70,40118], [184,68634061,184,70,,1,27.99,70,40118], [184,69644184,184,193,,1,15.99,193,40118], [184,63624756,184,801,,1,21.00,801,40118], [184,67604116,184,801,,1,21.00,801,40118], [184,68654451,184,193,,1,27.99,193,40118], [184,62614172,184,193,,1,19.19,193,40118], [184,61614174,184,620,,1,49.99,620,40118], [184,61614174,184,620,,1,29.99,620,40118], [184,62644445,184,801,,1,69.95,801,40118], [184,62634323,184,905,,1,149.00,905,40118], [184,66674079,184,901,,1,3.95,901,40118], [184,66674130,184,901,,1,3.95,901,40118], [184,61604095,184,902,,1,12.50,902,40118], [184,66664028,184,902,,1,14.95,902,40118], [184,68654621,184,902,,1,12.95,902,40118], [184,63694264,184,830,,1,19.95,830,40118], [184,63604361,184,901,,1,3.99,901,40118], [184,62634259,184,901,,1,6.69,901,40118], [184,62634259,184,901,,1,6.69,901,40118], [184,60684429,184,904,,1,4.89,904,40118], [184,60684037,184,904,,1,9.95,904,40118], [184,69694875,184,904,,1,19.95,904,40118], [184,69694875,184,904,,1,19.95,904,40118], [184,69694875,184,904,,1,19.95,904,40118], [184,69694875,184,904,,-1,-19.95,904,40118], [184,69694875,184,904,,-1,-19.95,904,40118], [184,69694875,184,904,,-1,-19.95,904,40118], [184,63604108,184,901,,1,3.95,901,40118], [184,63694928,184,904,,1,11.49,904,40118], [184,60634765,184,904,,1,4.99,904,40118], [184,69664668,184,903,,1,8.95,903,40118]]')


	close()

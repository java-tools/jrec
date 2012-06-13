useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'csvB_DTAR020.bin.csv')
		if commonBits.version()  == 'MsAccess':
			select('ComboBox2', 'Comma Delimited, names on the first line')
		else:
			select('ComboBox1', 'CSV')
		click('Edit1')
		select('Table', 'cell:2|STORE-NO,0(20)')
		rightclick('Table', '3|DATE,2')
#		select('Table', 'cell:2|STORE-NO,0(20)')
		select_menu('CSV Options>>Delete Column')
		assert_p('Table', 'Content', '[[69684558, 20, 280, 1, 19.00], [69684558, 20, 280, -1, -19.00], [69684558, 20, 280, 1, 5.01], [69694158, 20, 280, 1, 19.00], [69694158, 20, 280, -1, -19.00], [69694158, 20, 280, 1, 5.01], [63604808, 20, 170, 1, 4.87], [62684671, 20, 685, 1, 69.99], [62684671, 20, 685, -1, -69.99], [64634429, 20, 957, 1, 3.99], [66624458, 20, 957, 1, 0.89], [63674861, 20, 957, 10, 2.70], [65674532, 20, 929, 1, 3.59], [64614401, 59, 957, 1, 1.99], [64614401, 59, 957, 1, 1.99], [61664713, 59, 335, 1, 17.99], [61664713, 59, 335, -1, -17.99], [68634752, 59, 410, 1, 8.99], [60614487, 59, 878, 1, 5.95], [63644339, 59, 878, 1, 12.65], [60694698, 59, 620, 1, 3.99], [60664659, 59, 620, 1, 3.99], [62684217, 59, 957, 1, 9.99], [67674686, 59, 929, 1, 3.99], [61684613, 59, 335, 1, 12.99], [64624770, 59, 957, 1, 2.59], [69694814, 166, 360, 1, 2.50], [69694814, 166, 360, 1, 2.50], [69644164, 166, 193, 1, 21.59], [62684907, 166, 375, 1, 13.99], [62694193, 166, 375, 1, 13.99], [62694193, 166, 375, -1, -13.99], [62694193, 166, 375, 1, 11.99], [63654450, 166, 320, 1, 13.99], [62664576, 166, 320, 1, 9.72], [63634260, 166, 320, 1, 5.59], [64684534, 166, 440, 1, 14.99], [64674965, 166, 235, 1, 19.99], [64674965, 166, 235, -1, -19.99], [64674965, 166, 235, 1, 12.00], [60624523, 166, 261, 1, 12.00], [66624253, 166, 957, 1, 3.49], [66624253, 166, 957, 1, 3.49], [64654284, 166, 957, 1, 3.99], [60684907, 166, 805, 1, 5.50], [63624299, 166, 870, 1, 10.99], [63624367, 166, 870, 1, 11.19], [62694575, 166, 475, 1, 14.99], [69614011, 166, 905, 1, 6.99], [62634996, 166, 650, 1, 9.99], [67634503, 166, 970, 1, 24.99], [65604476, 166, 830, 1, 19.95], [62694170, 166, 851, 1, 16.99], [63684098, 166, 410, 1, 1.98], [63684098, 166, 410, 1, 1.98], [63684098, 166, 410, 1, 1.98], [64674609, 166, 485, 1, 29.99], [62614014, 166, 366, 1, 14.99], [61694741, 166, 432, 1, 9.06], [62614534, 166, 432, 1, 9.09], [64604876, 166, 801, 1, 29.62], [66624829, 166, 957, 1, 1.99], [62694843, 166, 193, 1, 13.59], [62684580, 166, 265, 1, 19.00], [62664909, 166, 957, 1, 3.29], [62674751, 166, 957, 1, 1.99], [62674492, 166, 957, 1, 1.49], [62674492, 166, 957, 1, 1.49], [62694706, 166, 193, 1, 13.59], [69644602, 166, 265, 1, 19.00], [63634768, 166, 270, 1, 12.00], [62684207, 166, 265, 1, 19.00], [69644961, 166, 230, 1, 9.60], [69604743, 166, 250, 1, 29.95], [63634081, 166, 929, 1, 3.89], [69614229, 166, 902, 1, 15.95], [62654454, 166, 845, 1, 5.95], [64634712, 166, 845, 1, 3.90], [62674092, 166, 851, 1, 15.99], [67664966, 166, 929, 1, 0.89], [67664966, 166, 929, 1, 0.89], [64674633, 166, 220, 1, 15.99], [64624081, 166, 280, 1, 26.24], [69674069, 166, 910, 1, 10.49], [62684028, 166, 520, 1, 29.99], [64604876, 166, 801, 1, 29.62], [68644966, 166, 902, 1, 12.50], [68644966, 166, 902, -1, -12.50], [68644966, 166, 902, 1, 0.01], [62664347, 166, 370, 1, 8.99], [62664231, 166, 370, 1, 8.99], [62694605, 166, 261, 1, 25.00], [69634922, 166, 261, 1, 19.00], [63694928, 166, 904, 1, 11.49], [60624185, 166, 500, 1, 8.99], [60624314, 166, 500, 1, 8.99], [69694959, 166, 270, 1, 11.99], [69624033, 166, 80, 1, 18.19], [62694485, 166, 193, 1, 17.56], [60614646, 166, 60, 1, 6.00], [63654066, 166, 275, 1, 24.99], [62684548, 166, 415, 1, 39.99], [62684548, 166, 415, 1, 39.99], [69694685, 166, 360, 1, 6.99], [63614741, 166, 395, 1, 27.99], [60664302, 166, 270, 1, 9.00], [60664241, 166, 270, 1, 9.00], [66674979, 166, 360, 1, 4.50], [62634862, 166, 355, 1, 11.89], [62604139, 166, 335, 1, 7.99], [62624382, 166, 370, 1, 18.98], [62624382, 166, 370, -1, -18.98], [69694937, 166, 360, 1, 2.50], [62624382, 166, 370, 1, 18.98], [62624382, 166, 370, 1, 18.98], [62624382, 166, 370, -1, -18.98], [62624382, 166, 370, -1, -18.98], [62624382, 166, 370, 1, 18.98], [62624382, 166, 370, 1, 18.98], [61684889, 166, 685, 1, 4.49], [68614651, 166, 370, 1, 3.99], [62664674, 166, 471, 1, 24.99], [61684889, 166, 685, 1, 4.49], [60694417, 166, 929, 1, 0.65], [65694328, 166, 929, 1, 0.59], [63684449, 166, 320, 1, 16.99], [62614815, 166, 405, 1, 20.00], [62664151, 166, 455, 1, 25.00], [64684719, 166, 410, 1, 9.99], [69654084, 166, 60, 1, 6.00], [69644897, 166, 60, 1, 5.08], [68654655, 166, 60, 1, 5.08], [68674560, 166, 170, 1, 5.99], [62694387, 166, 432, 1, 7.99], [62664568, 166, 432, 1, 5.99], [69634261, 166, 261, 1, 12.00], [69634660, 166, 261, 1, 12.00], [69684947, 166, 280, 1, 22.49], [60654072, 166, 905, 1, 4.33], [60654072, 166, 905, 1, 4.33], [69624221, 166, 355, 1, 16.99], [62654800, 166, 355, 1, 19.99], [64644495, 166, 801, 1, 29.65], [67664645, 166, 929, 1, 1.39], [60614265, 166, 395, 1, 15.99], [68604583, 166, 905, 1, 15.99], [60614265, 166, 395, 1, 15.99], [60614265, 166, 395, -1, -15.99], [60614265, 166, 395, -1, -15.99], [68604583, 166, 905, -1, -15.99], [68604583, 166, 905, 1, 15.99], [68604583, 166, 905, -1, -15.99], [68604583, 166, 905, 1, 12.80], [60614265, 166, 395, 1, 15.99], [60614265, 166, 395, -1, -15.99], [60614265, 166, 395, 1, 12.80], [69664661, 166, 904, 1, 14.95], [68664211, 166, 193, 1, 11.19], [60614707, 166, 60, 1, 6.00], [64604513, 166, 235, 1, 16.99], [60624864, 166, 261, 1, 15.00], [69644909, 166, 261, 1, 9.00], [60604100, 166, 80, 1, 13.30], [69634263, 166, 261, 1, 25.00], [69634263, 166, 261, -1, -25.00], [69634263, 166, 261, 1, 12.00], [61674701, 166, 670, 1, 3.99], [63654007, 166, 670, 1, 56.99], [67624103, 166, 801, 1, 16.50], [68614329, 166, 905, 1, 39.99], [67644384, 166, 193, 1, 23.96], [64644495, 166, 801, 1, 29.65], [60684484, 184, 220, 1, 9.00], [60684484, 184, 220, 1, 9.00], [67674299, 184, 905, 1, 4.99], [69664620, 184, 355, 1, 11.89], [69664620, 184, 355, -1, -11.89], [69664620, 184, 355, 1, 9.09], [60674210, 184, 275, -1, -15.00], [60664048, 184, 60, -1, -4.80], [60614866, 184, 60, -1, -4.80], [60664048, 184, 60, -1, -4.80], [60664048, 184, 60, -1, -4.80], [60614866, 184, 60, -1, -4.80], [69654081, 184, 70, 1, 12.99], [63664643, 184, 193, 1, 16.79], [69654135, 184, 70, 1, 12.99], [60644672, 184, 160, 1, 9.09], [62654852, 184, 345, -1, -19.59], [62674960, 184, 490, -1, -16.00], [62674960, 184, 490, 1, 16.00], [65674126, 184, 929, 1, 2.69], [60634192, 184, 500, 1, 24.99], [64634500, 184, 957, -1, -9.99], [66624803, 184, 170, 1, 2.00], [66624803, 184, 170, -1, -2.00], [66624803, 184, 170, 1, 1.04], [66624889, 184, 170, 1, 2.00], [66624889, 184, 170, -1, -2.00], [66624889, 184, 170, 1, 1.04], [60624241, 184, 500, 1, 34.99], [62644079, 184, 650, 1, 9.99], [62664183, 184, 320, -1, -20.99], [64654047, 184, 320, -1, -25.99], [62694327, 184, 375, -1, -10.39], [69604894, 184, 275, -1, -19.00], [67644821, 184, 60, -1, -14.99], [67644118, 184, 60, 1, 16.99], [66664981, 184, 901, 1, 3.09], [66684899, 184, 901, 1, 12.99], [64634942, 184, 270, 1, 15.99], [63654826, 184, 275, 1, 19.00], [69604993, 184, 220, -1, -14.39], [63624118, 184, 250, -1, -15.00], [62684517, 184, 475, -1, -29.99], [67634503, 184, 970, 1, 24.99], [62694782, 184, 193, -1, -16.99], [62694683, 184, 193, -1, -16.99], [62694782, 184, 193, -1, -16.99], [62694782, 184, 193, -1, -16.99], [62684096, 184, 310, 1, 14.00], [69644199, 184, 193, 1, 19.19], [60634366, 184, 904, 1, 7.99], [67654448, 184, 70, 1, 23.00], [67654448, 184, 70, -1, -23.00], [67654448, 184, 70, 1, 19.01], [69654459, 184, 60, 1, 5.08], [60664779, 184, 60, 1, 5.08], [63674002, 184, 310, -1, -29.99], [68604041, 184, 870, -1, -19.99], [67634503, 184, 970, 1, 24.99], [68644941, 184, 60, -1, -10.39], [68644941, 184, 60, -1, -10.39], [68644941, 184, 60, -1, -10.39], [68644941, 184, 60, -1, -10.39], [68644934, 184, 60, 1, 10.39], [62674884, 184, 350, -1, -8.99], [68654381, 184, 235, 1, 14.95], [64644433, 184, 801, 1, 24.99], [64604829, 184, 805, -1, -29.99], [62634996, 184, 650, 1, 9.99], [60624270, 184, 670, 1, 0.95], [63664932, 184, 929, 1, 1.59], [69684804, 184, 851, 1, 9.99], [68664363, 184, 415, -1, -29.95], [62694503, 184, 929, 1, 2.99], [62644205, 184, 590, 1, 24.99], [62604912, 184, 590, 1, 24.89], [64634802, 184, 904, 1, 4.29], [60664257, 184, 901, 1, 2.12], [62684043, 184, 878, -1, -19.95], [62684043, 184, 878, 1, 19.95], [64644860, 184, 805, -1, -3.95], [62644590, 184, 590, -1, -22.99], [64664587, 184, 432, -1, -16.99], [64604930, 184, 801, -1, -35.95], [60604880, 184, 250, 1, 9.00], [68674372, 184, 845, 1, 2.00], [63684755, 184, 845, 1, 2.00], [64684439, 184, 929, 1, 9.99], [67624120, 184, 940, 1, 0.98], [67624120, 184, 940, -1, -0.98], [67624120, 184, 940, 1, 0.49], [67624120, 184, 940, 1, 0.98], [67624120, 184, 940, -1, -0.98], [67624120, 184, 940, 1, 0.49], [66614192, 184, 940, 1, 1.23], [66614192, 184, 940, -1, -1.23], [66614192, 184, 940, 1, 0.69], [66614192, 184, 940, 1, 1.23], [66614192, 184, 940, -1, -1.23], [66614192, 184, 940, 1, 0.69], [67624473, 184, 940, 1, 0.50], [67624473, 184, 940, -1, -0.50], [67624473, 184, 940, 1, 0.19], [67614923, 184, 875, -1, -19.95], [63604715, 184, 432, -1, -6.99], [63604715, 184, 432, -1, -6.99], [63604715, 184, 432, -1, -6.99], [62684102, 184, 310, 1, 14.00], [63654091, 184, 805, 1, 4.95], [68694729, 184, 855, -1, -16.99], [60684563, 184, 220, 1, 9.59], [68664957, 184, 80, 1, 13.99], [67604116, 184, 801, 1, 21.00], [64664047, 184, 801, 1, 22.99], [67634497, 184, 970, 1, 24.99], [64664270, 184, 929, 1, 3.19], [60634150, 184, 160, -1, -14.99], [68684135, 184, 878, -1, -249.00], [68684135, 184, 878, 1, 269.00], [68684135, 184, 878, -1, -269.00], [68684135, 184, 878, 1, 249.00], [69624909, 184, 235, -1, -26.21], [62634962, 184, 530, -1, -6.89], [67614726, 184, 160, -1, -9.95], [68654094, 184, 170, -1, -4.50], [60674335, 184, 485, 1, 19.95], [60674335, 184, 485, -1, -19.95], [60674335, 184, 485, 1, 12.99], [60644305, 184, 929, 1, 0.89], [64604604, 184, 366, 1, 6.99], [63694367, 184, 290, 1, 19.00], [62644079, 184, 650, 1, 9.99], [67674119, 184, 929, 1, 1.89], [69614627, 184, 265, 1, 19.00], [69614740, 184, 265, 1, 19.00], [60614135, 184, 230, -1, -14.25], [69674452, 184, 970, 1, 19.99], [62694272, 184, 957, 1, 2.49], [69614274, 184, 902, 1, 9.95], [60634379, 184, 290, 1, 25.00], [60674904, 184, 261, 1, 19.00], [66614582, 184, 670, -1, -15.99], [64654096, 184, 805, 1, 4.95], [62634605, 184, 405, 1, 29.99], [69694354, 184, 929, 1, 2.99], [69634699, 184, 80, 1, 20.99], [69634712, 184, 80, 1, 20.99], [69664171, 184, 250, -1, -27.95], [69694479, 184, 280, 1, 14.99], [63634656, 184, 929, -1, -5.50], [69664149, 184, 903, -1, -5.99], [69664163, 184, 903, -1, -1.39], [67674341, 184, 901, -1, -19.95], [69644053, 184, 230, 1, 18.74], [63674184, 184, 957, 1, 164.00], [62604338, 184, 870, 1, 69.99], [62644344, 184, 432, 1, 10.99], [66644706, 184, 432, 1, 10.99], [62644528, 184, 432, 1, 10.99], [62644764, 184, 432, 1, 10.99], [62664135, 184, 405, 1, 11.00], [62664244, 184, 405, 1, 11.00], [62664197, 184, 405, 1, 11.00], [62654875, 184, 471, 1, 39.99], [64614653, 184, 265, 1, 34.99], [64614653, 184, 265, 1, 34.99], [61694023, 184, 650, 1, 14.99], [61694023, 184, 650, 1, 14.99], [60694909, 184, 998, 1, 2.00], [68614241, 184, 70, 1, 12.99], [69654638, 184, 60, 1, 6.00], [67634923, 184, 60, 1, 17.59], [64614285, 184, 70, 1, 22.99], [69644389, 184, 193, 1, 17.59], [68614787, 184, 70, 1, 27.99], [69644337, 184, 70, 1, 29.99], [68634061, 184, 70, 1, 27.99], [69644184, 184, 193, 1, 15.99], [63624756, 184, 801, 1, 21.00], [67604116, 184, 801, 1, 21.00], [68654451, 184, 193, 1, 27.99], [62614172, 184, 193, 1, 19.19], [61614174, 184, 620, 1, 49.99], [61614174, 184, 620, 1, 29.99], [62644445, 184, 801, 1, 69.95], [62634323, 184, 905, 1, 149.00], [66674079, 184, 901, 1, 3.95], [66674130, 184, 901, 1, 3.95], [61604095, 184, 902, 1, 12.50], [66664028, 184, 902, 1, 14.95], [68654621, 184, 902, 1, 12.95], [63694264, 184, 830, 1, 19.95], [63604361, 184, 901, 1, 3.99], [62634259, 184, 901, 1, 6.69], [62634259, 184, 901, 1, 6.69], [60684429, 184, 904, 1, 4.89], [60684037, 184, 904, 1, 9.95], [69694875, 184, 904, 1, 19.95], [69694875, 184, 904, 1, 19.95], [69694875, 184, 904, 1, 19.95], [69694875, 184, 904, -1, -19.95], [69694875, 184, 904, -1, -19.95], [69694875, 184, 904, -1, -19.95], [63604108, 184, 901, 1, 3.95], [63694928, 184, 904, 1, 11.49], [60634765, 184, 904, 1, 4.99], [69664668, 184, 903, 1, 8.95]]')
#		select('Table', '')
		rightclick('Table', '3|DEPT-NO,5')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DEPT-NO, 3, , 280, 280], [QTY-SOLD, 4, , 1, 1], [SALE-PRICE, 5, , 5.01, 5.01]]')
		assert_p('TextArea', 'Text', '69694158,20,280,1,5.01')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>csvB_DTAR020.bin.csv>>Table: ')
#		select('Table', '')
		rightclick('Table', '1|KEYCODE-NO,3')
		select_menu('CSV Options>>Delete Column')
		assert_p('Table', 'Content', '[[20, 280, 1, 19.00], [20, 280, -1, -19.00], [20, 280, 1, 5.01], [20, 280, 1, 19.00], [20, 280, -1, -19.00], [20, 280, 1, 5.01], [20, 170, 1, 4.87], [20, 685, 1, 69.99], [20, 685, -1, -69.99], [20, 957, 1, 3.99], [20, 957, 1, 0.89], [20, 957, 10, 2.70], [20, 929, 1, 3.59], [59, 957, 1, 1.99], [59, 957, 1, 1.99], [59, 335, 1, 17.99], [59, 335, -1, -17.99], [59, 410, 1, 8.99], [59, 878, 1, 5.95], [59, 878, 1, 12.65], [59, 620, 1, 3.99], [59, 620, 1, 3.99], [59, 957, 1, 9.99], [59, 929, 1, 3.99], [59, 335, 1, 12.99], [59, 957, 1, 2.59], [166, 360, 1, 2.50], [166, 360, 1, 2.50], [166, 193, 1, 21.59], [166, 375, 1, 13.99], [166, 375, 1, 13.99], [166, 375, -1, -13.99], [166, 375, 1, 11.99], [166, 320, 1, 13.99], [166, 320, 1, 9.72], [166, 320, 1, 5.59], [166, 440, 1, 14.99], [166, 235, 1, 19.99], [166, 235, -1, -19.99], [166, 235, 1, 12.00], [166, 261, 1, 12.00], [166, 957, 1, 3.49], [166, 957, 1, 3.49], [166, 957, 1, 3.99], [166, 805, 1, 5.50], [166, 870, 1, 10.99], [166, 870, 1, 11.19], [166, 475, 1, 14.99], [166, 905, 1, 6.99], [166, 650, 1, 9.99], [166, 970, 1, 24.99], [166, 830, 1, 19.95], [166, 851, 1, 16.99], [166, 410, 1, 1.98], [166, 410, 1, 1.98], [166, 410, 1, 1.98], [166, 485, 1, 29.99], [166, 366, 1, 14.99], [166, 432, 1, 9.06], [166, 432, 1, 9.09], [166, 801, 1, 29.62], [166, 957, 1, 1.99], [166, 193, 1, 13.59], [166, 265, 1, 19.00], [166, 957, 1, 3.29], [166, 957, 1, 1.99], [166, 957, 1, 1.49], [166, 957, 1, 1.49], [166, 193, 1, 13.59], [166, 265, 1, 19.00], [166, 270, 1, 12.00], [166, 265, 1, 19.00], [166, 230, 1, 9.60], [166, 250, 1, 29.95], [166, 929, 1, 3.89], [166, 902, 1, 15.95], [166, 845, 1, 5.95], [166, 845, 1, 3.90], [166, 851, 1, 15.99], [166, 929, 1, 0.89], [166, 929, 1, 0.89], [166, 220, 1, 15.99], [166, 280, 1, 26.24], [166, 910, 1, 10.49], [166, 520, 1, 29.99], [166, 801, 1, 29.62], [166, 902, 1, 12.50], [166, 902, -1, -12.50], [166, 902, 1, 0.01], [166, 370, 1, 8.99], [166, 370, 1, 8.99], [166, 261, 1, 25.00], [166, 261, 1, 19.00], [166, 904, 1, 11.49], [166, 500, 1, 8.99], [166, 500, 1, 8.99], [166, 270, 1, 11.99], [166, 80, 1, 18.19], [166, 193, 1, 17.56], [166, 60, 1, 6.00], [166, 275, 1, 24.99], [166, 415, 1, 39.99], [166, 415, 1, 39.99], [166, 360, 1, 6.99], [166, 395, 1, 27.99], [166, 270, 1, 9.00], [166, 270, 1, 9.00], [166, 360, 1, 4.50], [166, 355, 1, 11.89], [166, 335, 1, 7.99], [166, 370, 1, 18.98], [166, 370, -1, -18.98], [166, 360, 1, 2.50], [166, 370, 1, 18.98], [166, 370, 1, 18.98], [166, 370, -1, -18.98], [166, 370, -1, -18.98], [166, 370, 1, 18.98], [166, 370, 1, 18.98], [166, 685, 1, 4.49], [166, 370, 1, 3.99], [166, 471, 1, 24.99], [166, 685, 1, 4.49], [166, 929, 1, 0.65], [166, 929, 1, 0.59], [166, 320, 1, 16.99], [166, 405, 1, 20.00], [166, 455, 1, 25.00], [166, 410, 1, 9.99], [166, 60, 1, 6.00], [166, 60, 1, 5.08], [166, 60, 1, 5.08], [166, 170, 1, 5.99], [166, 432, 1, 7.99], [166, 432, 1, 5.99], [166, 261, 1, 12.00], [166, 261, 1, 12.00], [166, 280, 1, 22.49], [166, 905, 1, 4.33], [166, 905, 1, 4.33], [166, 355, 1, 16.99], [166, 355, 1, 19.99], [166, 801, 1, 29.65], [166, 929, 1, 1.39], [166, 395, 1, 15.99], [166, 905, 1, 15.99], [166, 395, 1, 15.99], [166, 395, -1, -15.99], [166, 395, -1, -15.99], [166, 905, -1, -15.99], [166, 905, 1, 15.99], [166, 905, -1, -15.99], [166, 905, 1, 12.80], [166, 395, 1, 15.99], [166, 395, -1, -15.99], [166, 395, 1, 12.80], [166, 904, 1, 14.95], [166, 193, 1, 11.19], [166, 60, 1, 6.00], [166, 235, 1, 16.99], [166, 261, 1, 15.00], [166, 261, 1, 9.00], [166, 80, 1, 13.30], [166, 261, 1, 25.00], [166, 261, -1, -25.00], [166, 261, 1, 12.00], [166, 670, 1, 3.99], [166, 670, 1, 56.99], [166, 801, 1, 16.50], [166, 905, 1, 39.99], [166, 193, 1, 23.96], [166, 801, 1, 29.65], [184, 220, 1, 9.00], [184, 220, 1, 9.00], [184, 905, 1, 4.99], [184, 355, 1, 11.89], [184, 355, -1, -11.89], [184, 355, 1, 9.09], [184, 275, -1, -15.00], [184, 60, -1, -4.80], [184, 60, -1, -4.80], [184, 60, -1, -4.80], [184, 60, -1, -4.80], [184, 60, -1, -4.80], [184, 70, 1, 12.99], [184, 193, 1, 16.79], [184, 70, 1, 12.99], [184, 160, 1, 9.09], [184, 345, -1, -19.59], [184, 490, -1, -16.00], [184, 490, 1, 16.00], [184, 929, 1, 2.69], [184, 500, 1, 24.99], [184, 957, -1, -9.99], [184, 170, 1, 2.00], [184, 170, -1, -2.00], [184, 170, 1, 1.04], [184, 170, 1, 2.00], [184, 170, -1, -2.00], [184, 170, 1, 1.04], [184, 500, 1, 34.99], [184, 650, 1, 9.99], [184, 320, -1, -20.99], [184, 320, -1, -25.99], [184, 375, -1, -10.39], [184, 275, -1, -19.00], [184, 60, -1, -14.99], [184, 60, 1, 16.99], [184, 901, 1, 3.09], [184, 901, 1, 12.99], [184, 270, 1, 15.99], [184, 275, 1, 19.00], [184, 220, -1, -14.39], [184, 250, -1, -15.00], [184, 475, -1, -29.99], [184, 970, 1, 24.99], [184, 193, -1, -16.99], [184, 193, -1, -16.99], [184, 193, -1, -16.99], [184, 193, -1, -16.99], [184, 310, 1, 14.00], [184, 193, 1, 19.19], [184, 904, 1, 7.99], [184, 70, 1, 23.00], [184, 70, -1, -23.00], [184, 70, 1, 19.01], [184, 60, 1, 5.08], [184, 60, 1, 5.08], [184, 310, -1, -29.99], [184, 870, -1, -19.99], [184, 970, 1, 24.99], [184, 60, -1, -10.39], [184, 60, -1, -10.39], [184, 60, -1, -10.39], [184, 60, -1, -10.39], [184, 60, 1, 10.39], [184, 350, -1, -8.99], [184, 235, 1, 14.95], [184, 801, 1, 24.99], [184, 805, -1, -29.99], [184, 650, 1, 9.99], [184, 670, 1, 0.95], [184, 929, 1, 1.59], [184, 851, 1, 9.99], [184, 415, -1, -29.95], [184, 929, 1, 2.99], [184, 590, 1, 24.99], [184, 590, 1, 24.89], [184, 904, 1, 4.29], [184, 901, 1, 2.12], [184, 878, -1, -19.95], [184, 878, 1, 19.95], [184, 805, -1, -3.95], [184, 590, -1, -22.99], [184, 432, -1, -16.99], [184, 801, -1, -35.95], [184, 250, 1, 9.00], [184, 845, 1, 2.00], [184, 845, 1, 2.00], [184, 929, 1, 9.99], [184, 940, 1, 0.98], [184, 940, -1, -0.98], [184, 940, 1, 0.49], [184, 940, 1, 0.98], [184, 940, -1, -0.98], [184, 940, 1, 0.49], [184, 940, 1, 1.23], [184, 940, -1, -1.23], [184, 940, 1, 0.69], [184, 940, 1, 1.23], [184, 940, -1, -1.23], [184, 940, 1, 0.69], [184, 940, 1, 0.50], [184, 940, -1, -0.50], [184, 940, 1, 0.19], [184, 875, -1, -19.95], [184, 432, -1, -6.99], [184, 432, -1, -6.99], [184, 432, -1, -6.99], [184, 310, 1, 14.00], [184, 805, 1, 4.95], [184, 855, -1, -16.99], [184, 220, 1, 9.59], [184, 80, 1, 13.99], [184, 801, 1, 21.00], [184, 801, 1, 22.99], [184, 970, 1, 24.99], [184, 929, 1, 3.19], [184, 160, -1, -14.99], [184, 878, -1, -249.00], [184, 878, 1, 269.00], [184, 878, -1, -269.00], [184, 878, 1, 249.00], [184, 235, -1, -26.21], [184, 530, -1, -6.89], [184, 160, -1, -9.95], [184, 170, -1, -4.50], [184, 485, 1, 19.95], [184, 485, -1, -19.95], [184, 485, 1, 12.99], [184, 929, 1, 0.89], [184, 366, 1, 6.99], [184, 290, 1, 19.00], [184, 650, 1, 9.99], [184, 929, 1, 1.89], [184, 265, 1, 19.00], [184, 265, 1, 19.00], [184, 230, -1, -14.25], [184, 970, 1, 19.99], [184, 957, 1, 2.49], [184, 902, 1, 9.95], [184, 290, 1, 25.00], [184, 261, 1, 19.00], [184, 670, -1, -15.99], [184, 805, 1, 4.95], [184, 405, 1, 29.99], [184, 929, 1, 2.99], [184, 80, 1, 20.99], [184, 80, 1, 20.99], [184, 250, -1, -27.95], [184, 280, 1, 14.99], [184, 929, -1, -5.50], [184, 903, -1, -5.99], [184, 903, -1, -1.39], [184, 901, -1, -19.95], [184, 230, 1, 18.74], [184, 957, 1, 164.00], [184, 870, 1, 69.99], [184, 432, 1, 10.99], [184, 432, 1, 10.99], [184, 432, 1, 10.99], [184, 432, 1, 10.99], [184, 405, 1, 11.00], [184, 405, 1, 11.00], [184, 405, 1, 11.00], [184, 471, 1, 39.99], [184, 265, 1, 34.99], [184, 265, 1, 34.99], [184, 650, 1, 14.99], [184, 650, 1, 14.99], [184, 998, 1, 2.00], [184, 70, 1, 12.99], [184, 60, 1, 6.00], [184, 60, 1, 17.59], [184, 70, 1, 22.99], [184, 193, 1, 17.59], [184, 70, 1, 27.99], [184, 70, 1, 29.99], [184, 70, 1, 27.99], [184, 193, 1, 15.99], [184, 801, 1, 21.00], [184, 801, 1, 21.00], [184, 193, 1, 27.99], [184, 193, 1, 19.19], [184, 620, 1, 49.99], [184, 620, 1, 29.99], [184, 801, 1, 69.95], [184, 905, 1, 149.00], [184, 901, 1, 3.95], [184, 901, 1, 3.95], [184, 902, 1, 12.50], [184, 902, 1, 14.95], [184, 902, 1, 12.95], [184, 830, 1, 19.95], [184, 901, 1, 3.99], [184, 901, 1, 6.69], [184, 901, 1, 6.69], [184, 904, 1, 4.89], [184, 904, 1, 9.95], [184, 904, 1, 19.95], [184, 904, 1, 19.95], [184, 904, 1, 19.95], [184, 904, -1, -19.95], [184, 904, -1, -19.95], [184, 904, -1, -19.95], [184, 901, 1, 3.95], [184, 904, 1, 11.49], [184, 904, 1, 4.99], [184, 903, 1, 8.95]]')
#		select('Table', '')
		rightclick('Table', '1|STORE-NO,7')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '[[STORE-NO, 1, , 20, 20], [DEPT-NO, 2, , 685, 685], [QTY-SOLD, 3, , 1, 1], [SALE-PRICE, 4, , 69.99, 69.99]]')
		assert_p('TextArea', 'Text', '20,685,1,69.99')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('Table', '')
		rightclick('Table', '4|SALE-PRICE,3')
		select_menu('CSV Options>>Delete Column')
		assert_p('Table', 'Content', '[[20, 280, 1], [20, 280, -1], [20, 280, 1], [20, 280, 1], [20, 280, -1], [20, 280, 1], [20, 170, 1], [20, 685, 1], [20, 685, -1], [20, 957, 1], [20, 957, 1], [20, 957, 10], [20, 929, 1], [59, 957, 1], [59, 957, 1], [59, 335, 1], [59, 335, -1], [59, 410, 1], [59, 878, 1], [59, 878, 1], [59, 620, 1], [59, 620, 1], [59, 957, 1], [59, 929, 1], [59, 335, 1], [59, 957, 1], [166, 360, 1], [166, 360, 1], [166, 193, 1], [166, 375, 1], [166, 375, 1], [166, 375, -1], [166, 375, 1], [166, 320, 1], [166, 320, 1], [166, 320, 1], [166, 440, 1], [166, 235, 1], [166, 235, -1], [166, 235, 1], [166, 261, 1], [166, 957, 1], [166, 957, 1], [166, 957, 1], [166, 805, 1], [166, 870, 1], [166, 870, 1], [166, 475, 1], [166, 905, 1], [166, 650, 1], [166, 970, 1], [166, 830, 1], [166, 851, 1], [166, 410, 1], [166, 410, 1], [166, 410, 1], [166, 485, 1], [166, 366, 1], [166, 432, 1], [166, 432, 1], [166, 801, 1], [166, 957, 1], [166, 193, 1], [166, 265, 1], [166, 957, 1], [166, 957, 1], [166, 957, 1], [166, 957, 1], [166, 193, 1], [166, 265, 1], [166, 270, 1], [166, 265, 1], [166, 230, 1], [166, 250, 1], [166, 929, 1], [166, 902, 1], [166, 845, 1], [166, 845, 1], [166, 851, 1], [166, 929, 1], [166, 929, 1], [166, 220, 1], [166, 280, 1], [166, 910, 1], [166, 520, 1], [166, 801, 1], [166, 902, 1], [166, 902, -1], [166, 902, 1], [166, 370, 1], [166, 370, 1], [166, 261, 1], [166, 261, 1], [166, 904, 1], [166, 500, 1], [166, 500, 1], [166, 270, 1], [166, 80, 1], [166, 193, 1], [166, 60, 1], [166, 275, 1], [166, 415, 1], [166, 415, 1], [166, 360, 1], [166, 395, 1], [166, 270, 1], [166, 270, 1], [166, 360, 1], [166, 355, 1], [166, 335, 1], [166, 370, 1], [166, 370, -1], [166, 360, 1], [166, 370, 1], [166, 370, 1], [166, 370, -1], [166, 370, -1], [166, 370, 1], [166, 370, 1], [166, 685, 1], [166, 370, 1], [166, 471, 1], [166, 685, 1], [166, 929, 1], [166, 929, 1], [166, 320, 1], [166, 405, 1], [166, 455, 1], [166, 410, 1], [166, 60, 1], [166, 60, 1], [166, 60, 1], [166, 170, 1], [166, 432, 1], [166, 432, 1], [166, 261, 1], [166, 261, 1], [166, 280, 1], [166, 905, 1], [166, 905, 1], [166, 355, 1], [166, 355, 1], [166, 801, 1], [166, 929, 1], [166, 395, 1], [166, 905, 1], [166, 395, 1], [166, 395, -1], [166, 395, -1], [166, 905, -1], [166, 905, 1], [166, 905, -1], [166, 905, 1], [166, 395, 1], [166, 395, -1], [166, 395, 1], [166, 904, 1], [166, 193, 1], [166, 60, 1], [166, 235, 1], [166, 261, 1], [166, 261, 1], [166, 80, 1], [166, 261, 1], [166, 261, -1], [166, 261, 1], [166, 670, 1], [166, 670, 1], [166, 801, 1], [166, 905, 1], [166, 193, 1], [166, 801, 1], [184, 220, 1], [184, 220, 1], [184, 905, 1], [184, 355, 1], [184, 355, -1], [184, 355, 1], [184, 275, -1], [184, 60, -1], [184, 60, -1], [184, 60, -1], [184, 60, -1], [184, 60, -1], [184, 70, 1], [184, 193, 1], [184, 70, 1], [184, 160, 1], [184, 345, -1], [184, 490, -1], [184, 490, 1], [184, 929, 1], [184, 500, 1], [184, 957, -1], [184, 170, 1], [184, 170, -1], [184, 170, 1], [184, 170, 1], [184, 170, -1], [184, 170, 1], [184, 500, 1], [184, 650, 1], [184, 320, -1], [184, 320, -1], [184, 375, -1], [184, 275, -1], [184, 60, -1], [184, 60, 1], [184, 901, 1], [184, 901, 1], [184, 270, 1], [184, 275, 1], [184, 220, -1], [184, 250, -1], [184, 475, -1], [184, 970, 1], [184, 193, -1], [184, 193, -1], [184, 193, -1], [184, 193, -1], [184, 310, 1], [184, 193, 1], [184, 904, 1], [184, 70, 1], [184, 70, -1], [184, 70, 1], [184, 60, 1], [184, 60, 1], [184, 310, -1], [184, 870, -1], [184, 970, 1], [184, 60, -1], [184, 60, -1], [184, 60, -1], [184, 60, -1], [184, 60, 1], [184, 350, -1], [184, 235, 1], [184, 801, 1], [184, 805, -1], [184, 650, 1], [184, 670, 1], [184, 929, 1], [184, 851, 1], [184, 415, -1], [184, 929, 1], [184, 590, 1], [184, 590, 1], [184, 904, 1], [184, 901, 1], [184, 878, -1], [184, 878, 1], [184, 805, -1], [184, 590, -1], [184, 432, -1], [184, 801, -1], [184, 250, 1], [184, 845, 1], [184, 845, 1], [184, 929, 1], [184, 940, 1], [184, 940, -1], [184, 940, 1], [184, 940, 1], [184, 940, -1], [184, 940, 1], [184, 940, 1], [184, 940, -1], [184, 940, 1], [184, 940, 1], [184, 940, -1], [184, 940, 1], [184, 940, 1], [184, 940, -1], [184, 940, 1], [184, 875, -1], [184, 432, -1], [184, 432, -1], [184, 432, -1], [184, 310, 1], [184, 805, 1], [184, 855, -1], [184, 220, 1], [184, 80, 1], [184, 801, 1], [184, 801, 1], [184, 970, 1], [184, 929, 1], [184, 160, -1], [184, 878, -1], [184, 878, 1], [184, 878, -1], [184, 878, 1], [184, 235, -1], [184, 530, -1], [184, 160, -1], [184, 170, -1], [184, 485, 1], [184, 485, -1], [184, 485, 1], [184, 929, 1], [184, 366, 1], [184, 290, 1], [184, 650, 1], [184, 929, 1], [184, 265, 1], [184, 265, 1], [184, 230, -1], [184, 970, 1], [184, 957, 1], [184, 902, 1], [184, 290, 1], [184, 261, 1], [184, 670, -1], [184, 805, 1], [184, 405, 1], [184, 929, 1], [184, 80, 1], [184, 80, 1], [184, 250, -1], [184, 280, 1], [184, 929, -1], [184, 903, -1], [184, 903, -1], [184, 901, -1], [184, 230, 1], [184, 957, 1], [184, 870, 1], [184, 432, 1], [184, 432, 1], [184, 432, 1], [184, 432, 1], [184, 405, 1], [184, 405, 1], [184, 405, 1], [184, 471, 1], [184, 265, 1], [184, 265, 1], [184, 650, 1], [184, 650, 1], [184, 998, 1], [184, 70, 1], [184, 60, 1], [184, 60, 1], [184, 70, 1], [184, 193, 1], [184, 70, 1], [184, 70, 1], [184, 70, 1], [184, 193, 1], [184, 801, 1], [184, 801, 1], [184, 193, 1], [184, 193, 1], [184, 620, 1], [184, 620, 1], [184, 801, 1], [184, 905, 1], [184, 901, 1], [184, 901, 1], [184, 902, 1], [184, 902, 1], [184, 902, 1], [184, 830, 1], [184, 901, 1], [184, 901, 1], [184, 901, 1], [184, 904, 1], [184, 904, 1], [184, 904, 1], [184, 904, 1], [184, 904, 1], [184, 904, -1], [184, 904, -1], [184, 904, -1], [184, 901, 1], [184, 904, 1], [184, 904, 1], [184, 903, 1]]')
		select('LayoutCombo', 'Full Line')
		assert_p('Table', 'Content', '[[20,280,1], [20,280,-1], [20,280,1], [20,280,1], [20,280,-1], [20,280,1], [20,170,1], [20,685,1], [20,685,-1], [20,957,1], [20,957,1], [20,957,10], [20,929,1], [59,957,1], [59,957,1], [59,335,1], [59,335,-1], [59,410,1], [59,878,1], [59,878,1], [59,620,1], [59,620,1], [59,957,1], [59,929,1], [59,335,1], [59,957,1], [166,360,1], [166,360,1], [166,193,1], [166,375,1], [166,375,1], [166,375,-1], [166,375,1], [166,320,1], [166,320,1], [166,320,1], [166,440,1], [166,235,1], [166,235,-1], [166,235,1], [166,261,1], [166,957,1], [166,957,1], [166,957,1], [166,805,1], [166,870,1], [166,870,1], [166,475,1], [166,905,1], [166,650,1], [166,970,1], [166,830,1], [166,851,1], [166,410,1], [166,410,1], [166,410,1], [166,485,1], [166,366,1], [166,432,1], [166,432,1], [166,801,1], [166,957,1], [166,193,1], [166,265,1], [166,957,1], [166,957,1], [166,957,1], [166,957,1], [166,193,1], [166,265,1], [166,270,1], [166,265,1], [166,230,1], [166,250,1], [166,929,1], [166,902,1], [166,845,1], [166,845,1], [166,851,1], [166,929,1], [166,929,1], [166,220,1], [166,280,1], [166,910,1], [166,520,1], [166,801,1], [166,902,1], [166,902,-1], [166,902,1], [166,370,1], [166,370,1], [166,261,1], [166,261,1], [166,904,1], [166,500,1], [166,500,1], [166,270,1], [166,80,1], [166,193,1], [166,60,1], [166,275,1], [166,415,1], [166,415,1], [166,360,1], [166,395,1], [166,270,1], [166,270,1], [166,360,1], [166,355,1], [166,335,1], [166,370,1], [166,370,-1], [166,360,1], [166,370,1], [166,370,1], [166,370,-1], [166,370,-1], [166,370,1], [166,370,1], [166,685,1], [166,370,1], [166,471,1], [166,685,1], [166,929,1], [166,929,1], [166,320,1], [166,405,1], [166,455,1], [166,410,1], [166,60,1], [166,60,1], [166,60,1], [166,170,1], [166,432,1], [166,432,1], [166,261,1], [166,261,1], [166,280,1], [166,905,1], [166,905,1], [166,355,1], [166,355,1], [166,801,1], [166,929,1], [166,395,1], [166,905,1], [166,395,1], [166,395,-1], [166,395,-1], [166,905,-1], [166,905,1], [166,905,-1], [166,905,1], [166,395,1], [166,395,-1], [166,395,1], [166,904,1], [166,193,1], [166,60,1], [166,235,1], [166,261,1], [166,261,1], [166,80,1], [166,261,1], [166,261,-1], [166,261,1], [166,670,1], [166,670,1], [166,801,1], [166,905,1], [166,193,1], [166,801,1], [184,220,1], [184,220,1], [184,905,1], [184,355,1], [184,355,-1], [184,355,1], [184,275,-1], [184,60,-1], [184,60,-1], [184,60,-1], [184,60,-1], [184,60,-1], [184,70,1], [184,193,1], [184,70,1], [184,160,1], [184,345,-1], [184,490,-1], [184,490,1], [184,929,1], [184,500,1], [184,957,-1], [184,170,1], [184,170,-1], [184,170,1], [184,170,1], [184,170,-1], [184,170,1], [184,500,1], [184,650,1], [184,320,-1], [184,320,-1], [184,375,-1], [184,275,-1], [184,60,-1], [184,60,1], [184,901,1], [184,901,1], [184,270,1], [184,275,1], [184,220,-1], [184,250,-1], [184,475,-1], [184,970,1], [184,193,-1], [184,193,-1], [184,193,-1], [184,193,-1], [184,310,1], [184,193,1], [184,904,1], [184,70,1], [184,70,-1], [184,70,1], [184,60,1], [184,60,1], [184,310,-1], [184,870,-1], [184,970,1], [184,60,-1], [184,60,-1], [184,60,-1], [184,60,-1], [184,60,1], [184,350,-1], [184,235,1], [184,801,1], [184,805,-1], [184,650,1], [184,670,1], [184,929,1], [184,851,1], [184,415,-1], [184,929,1], [184,590,1], [184,590,1], [184,904,1], [184,901,1], [184,878,-1], [184,878,1], [184,805,-1], [184,590,-1], [184,432,-1], [184,801,-1], [184,250,1], [184,845,1], [184,845,1], [184,929,1], [184,940,1], [184,940,-1], [184,940,1], [184,940,1], [184,940,-1], [184,940,1], [184,940,1], [184,940,-1], [184,940,1], [184,940,1], [184,940,-1], [184,940,1], [184,940,1], [184,940,-1], [184,940,1], [184,875,-1], [184,432,-1], [184,432,-1], [184,432,-1], [184,310,1], [184,805,1], [184,855,-1], [184,220,1], [184,80,1], [184,801,1], [184,801,1], [184,970,1], [184,929,1], [184,160,-1], [184,878,-1], [184,878,1], [184,878,-1], [184,878,1], [184,235,-1], [184,530,-1], [184,160,-1], [184,170,-1], [184,485,1], [184,485,-1], [184,485,1], [184,929,1], [184,366,1], [184,290,1], [184,650,1], [184,929,1], [184,265,1], [184,265,1], [184,230,-1], [184,970,1], [184,957,1], [184,902,1], [184,290,1], [184,261,1], [184,670,-1], [184,805,1], [184,405,1], [184,929,1], [184,80,1], [184,80,1], [184,250,-1], [184,280,1], [184,929,-1], [184,903,-1], [184,903,-1], [184,901,-1], [184,230,1], [184,957,1], [184,870,1], [184,432,1], [184,432,1], [184,432,1], [184,432,1], [184,405,1], [184,405,1], [184,405,1], [184,471,1], [184,265,1], [184,265,1], [184,650,1], [184,650,1], [184,998,1], [184,70,1], [184,60,1], [184,60,1], [184,70,1], [184,193,1], [184,70,1], [184,70,1], [184,70,1], [184,193,1], [184,801,1], [184,801,1], [184,193,1], [184,193,1], [184,620,1], [184,620,1], [184,801,1], [184,905,1], [184,901,1], [184,901,1], [184,902,1], [184,902,1], [184,902,1], [184,830,1], [184,901,1], [184,901,1], [184,901,1], [184,904,1], [184,904,1], [184,904,1], [184,904,1], [184,904,1], [184,904,-1], [184,904,-1], [184,904,-1], [184,901,1], [184,904,1], [184,904,1], [184,903,1]]')

	close()

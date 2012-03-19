useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

#
#	Sript does not work yet !!!
#
	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20050101.txt')
		click('Layout Wizard')
		select('Multiple Records #{fixed length#}', 'true')
		click('Right')
		select('TabbedPane', '')
#		select('Table', '')
		select('TextField1', '01')
		select('TextField2', '1')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'header', 'Record Name,0')
		select('Table', 'detail', 'Record Name,1')
		select('Table', 'store', 'Record Name,2')
		select('Table', 'cell:Record Name,0(header)')
		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[H, header, false, true], [D, detail, false, true], [S, store, false, true]]')
		else:
			assert_p('Table', 'Content', '[[H, header], [D, detail], [S, store]]')
		select('Table', 'cell:Record Name,0(header)')
		click('Right')
		select('TabbedPane', '')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')
		click('Right')


		assert_p('TextArea', 'Text', 'You must define the Fields all Records. Please update - detail')
		select('ComboBox', 'store')

		rightclick('Table', 'D,0')
		rightclick('Table', 'P,0')
		rightclick('Table', 'AB,0')

#		select('Table', 'cell:Start,0(1)')
#		rightclick('Table', 'Field Name,1')
#		select_menu('Generate Field Names')

		click('Right')
		select('TabbedPane', '')

		select('Table', 'cell:Start,0(1)')
		rightclick('Table', 'Field Name,1')
		select_menu('Generate Field Names')

		select('ComboBox', 'detail')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[D, 1, 44, 11840, 0,  , 45872078, 4544, 44,        , 2117093,         , 45872078,        , MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 14, 11984, 0,  , 43372078, 4544, 14,        , 2117152,         , 45872078,        , MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 29, 10120, 0,  , 45874751, 4090, 29,        , 2117337,         , 45872078,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 14, 0, 29440800000000,  , 45874751, 4090, 14,        , 2117347,         , 35874751,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 12, 10256, 0,  , 35874751, 4090, 12,        , 2117354,         , 35874751,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 11, 5848, 0,  , 31191697, 2726, 11,        , 2327928,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 11, 5848, 0,  , 33391826, 2726, 11,        , 2327931,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 28, 3356, 0,  , 31191697, 2726, 28,        , 2327933,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 28, 3356, 0,  , 31191826, 2726, 28,        , 2327936,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 15, 3356, 0,  , 31191697, 2726, 15,        , 2327982,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 15, 3356, 0,  , 35221826, 2726, 15,        , 2327985,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 123, 6723, 0,  , 35116979, 2272, 123,        , 2098136,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 133, 6723, 0,  , 35117204, 2272, 133,        , 2098137,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 81, 6723, 0,  , 31127419, 2272, 81,        , 2098139,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 35333979, 2272, 49,        , 2098158,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 32227204, 2272, 49,        , 2098159,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 27, 6723, 0,  , 32227419, 2272, 27,        , 2098160,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 33336979, 2272, 24,        , 2098161,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 30027204, 2272, 24,        , 2098165,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 14, 6183, 0,  , 35007419, 2272, 14,        , 2098167,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 109, 11106, 0,  , 31129413, 3636, 109,        , 2098293,         , 35929413,        , S4547 PALE BLUE INDIAN PRINT JACKET               ], [D, 1, 97, 11106, 0,  , 31129710, 3636, 97,        , 2098297,         , 35929710,        , S4547 PALE PINK EAGLE PRINT JACKET                ]]')
		else:
			assert_p('Table1', 'Content', '[[D, 1, 44, 11840, 0,  , 45872078, 4544, 44,        , 2, 117093,         , 45872078,        , MT, H5, 033H DUSTY , PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 14, 11984, 0,  , 43372078, 4544, 14,        , 2, 117152,         , 45872078,        , MT, H5, 033H DUSTY , PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 29, 10120, 0,  , 45874751, 4090, 29,        , 2, 117337,         , 45872078,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 14, 0, 29440800000000,  , 45874751, 4090, 14,        , 2, 117347,         , 35874751,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 12, 10256, 0,  , 35874751, 4090, 12,        , 2, 117354,         , 35874751,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 11, 5848, 0,  , 31191697, 2726, 11,        , 2, 327928,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 11, 5848, 0,  , 33391826, 2726, 11,        , 2, 327931,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 28, 3356, 0,  , 31191697, 2726, 28,        , 2, 327933,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 28, 3356, 0,  , 31191826, 2726, 28,        , 2, 327936,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 15, 3356, 0,  , 31191697, 2726, 15,        , 2, 327982,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 15, 3356, 0,  , 35221826, 2726, 15,        , 2, 327985,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 123, 6723, 0,  , 35116979, 2272, 123,        , 2, 98136,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 133, 6723, 0,  , 35117204, 2272, 133,        , 2, 98137,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 81, 6723, 0,  , 31127419, 2272, 81,        , 2, 98139,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 35333979, 2272, 49,        , 2, 98158,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 32227204, 2272, 49,        , 2, 98159,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 27, 6723, 0,  , 32227419, 2272, 27,        , 2, 98160,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 33336979, 2272, 24,        , 2, 98161,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 30027204, 2272, 24,        , 2, 98165,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 14, 6183, 0,  , 35007419, 2272, 14,        , 2, 98167,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 109, 11106, 0,  , 31129413, 3636, 109,        , 2, 98293,         , 35929413,        , S4, 54, 7 PALE BLUE,  INDIAN PRINT JACKET               ], [D, 1, 97, 11106, 0,  , 31129710, 3636, 97,        , 2, 98297,         , 35929710,        , S4, 54, 7 PALE PINK,  EAGLE PRINT JACKET                ]]')

		select('Table', 'cell:Start,0(1)')
		rightclick('Table', 'Field Name,1')
		select_menu('Generate Field Names')

		assert_p('Table', 'Text', '')
		assert_p('Table1', 'Text', '')
		select('ComboBox', 'header')
		assert_p('Table', 'Text', '')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[H, 145357, 4338, 233863, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS PLCFT], [H, 145358, 4338, 233872, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS PLCFT], [H, 145359, 4468, 255906, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS PLCFT], [H, 145360, 4448, 290908, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75967,         , OPTIONS PLCFT], [H, 145361, 4228, 292210, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS PLCFT], [H, 145362, 5220, 211984, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75965,         , LADIES KNICFT], [H, 145363, 5110, 211985, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75966,         , LADIES KNICFT], [H, 145364, 5110, 211987, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 65967,         , LADIES KNICFT], [H, 145365, 13112, 211549, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 85966,         , LADIES KNICFT], [H, 145366, 13312, 211555, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75967,         , LADIES KNICFT], [H, 145367, 12212, 222556, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75965,         , LADIES KNICFT], [H, 145368, 1312, 211617, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 175966,         , LADIES KNICFT]]')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [H1, 2, 6, 6, 0, true], [H2, 8, 10, 6, 0, true], [H3, 18, 12, 6, 0, true], [H4, 30, 6, 6, 0, true], [H5, 36, 8, 0, 0, true], [H6, 44, 2, 6, 0, true], [H7, 46, 2, 0, 0, true], [H8, 48, 3, 6, 0, true], [H9, 51, 1, 0, 0, true], [H10, 52, 2, 6, 0, true], [H11, 54, 2, 6, 0, true], [H12, 56, 2, 6, 0, true], [H13, 58, 2, 6, 0, true], [H14, 60, 2, 6, 0, true], [H15, 62, 6, 6, 0, true], [H16, 68, 8, 0, 0, true], [H17, 76, 13, 0, 0, true]]')
		else:
			assert_p('Table1', 'Content', '[[H, 145357, 4338, 233863, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS,  PLCFT], [H, 145358, 4338, 233872, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS,  PLCFT], [H, 145359, 4468, 255906, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS,  PLCFT], [H, 145360, 4448, 290908, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75967,         , OPTIONS,  PLCFT], [H, 145361, 4228, 292210, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS,  PLCFT], [H, 145362, 5220, 211984, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75965,         , LADIES , KNICFT], [H, 145363, 5110, 211985, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75966,         , LADIES , KNICFT], [H, 145364, 5110, 211987, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 65967,         , LADIES , KNICFT], [H, 145365, 13112, 211549, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 85966,         , LADIES , KNICFT], [H, 145366, 13312, 211555, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75967,         , LADIES , KNICFT], [H, 145367, 12212, 222556, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75965,         , LADIES , KNICFT], [H, 145368, 1312, 211617, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 175966,         , LADIES , KNICFT]]')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [H1, 2, 6, 6, 0, true], [H2, 8, 10, 6, 0, true], [H3, 18, 12, 6, 0, true], [H4, 30, 2, 6, 0, true], [H5, 32, 4, 6, 0, true], [H6, 36, 8, 0, 0, true], [H7, 44, 2, 6, 0, true], [H8, 46, 2, 0, 0, true], [H9, 48, 3, 6, 0, true], [H10, 51, 1, 0, 0, true], [H11, 52, 2, 6, 0, true], [H12, 54, 2, 6, 0, true], [H13, 56, 2, 6, 0, true], [H14, 58, 2, 6, 0, true], [H15, 60, 2, 6, 0, true], [H16, 62, 6, 6, 0, true], [H17, 68, 8, 0, 0, true], [H18, 76, 7, 0, 0, true], [H19, 83, 6, 0, 0, true]]')
		assert_p('TextArea', 'Text', 'You must define the Fields all Records. Please update - detail')
		click('Right')
		assert_p('TextArea', 'Text', 'You must define the field Names in all Records, please update: store')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[S, 15, 015, 15, 019, 35, 033, 13337, 20780001, 5037, 1, 5052, 1, 5055, 1, 5060, 2, 5070, 1, 5074, 1], [S, 15, 078, 15, 081, 15, 085, 15090, 1, 5091, 1, 5093, 1, 5095, 1, 5129, 1, 5144, 1, 5165, 1], [S, 15, 303, 15, 169, 15, 170, 15171, 1, 5177, 1, 5016, 1, 5089, 2, 5136, 1, 5011, 1, 5046, 1], [S, 15, 145, 15, 096, 25, 154, 15162, 1, 5163, 1, 5164, 1, 5192, 1, 5150, 1, 5175, 1,     , 0], [S, 15, 036, 15, 043, 15, 045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5079, 1, 5094, 1, 5128, 1], [S, 15, 151, 15, 180, 15, 072, 15173, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 015, 15, 019, 25, 037, 23533, 47510001, 5070, 1, 5078, 1, 5093, 1, 5095, 1, 5129, 1, 5144, 1], [S, 15, 165, 15, 170, 15, 171, 15016, 2, 5089, 2, 5136, 1, 5011, 1, 5046, 1, 5096, 1, 5154, 1], [S, 15, 162, 15, 163, 15, 164, 15192, 1, 5175, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 083, 15, 133, 15, 155, 13584, 47510001, 5166, 1, 5167, 1, 5049, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15, 038, 15, 140, 15, 174, 15184, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 036, 15, 043, 15, 045, 13533, 47510001, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1, 5180, 1], [S, 15, 072, 15, 173, 1,    , 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 036, 15, 043, 15, 045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1], [S, 15, 180, 1,    , 0,    , 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 036, 15, 043, 15, 045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1], [S, 15, 180, 1,    , 0,    , 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 015, 15, 019, 15, 033, 15037, 1, 5060, 1, 5074, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1], [S, 15, 095, 15, 129, 15, 144, 15169, 1, 5170, 1, 5171, 1, 5177, 1, 5016, 1, 5089, 1, 5136, 1], [S, 15, 046, 15, 145, 15, 096, 15154, 1, 5162, 1, 5164, 1, 5192, 1, 5175, 1,     , 0,     , 0], [S, 15, 015, 15, 019, 15, 033, 15037, 1, 5060, 1, 5074, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1], [S, 15, 095, 15, 129, 15, 144, 15169, 1, 5170, 1, 5171, 1, 5177, 1, 5016, 1, 5089, 1, 5136, 1], [S, 15, 046, 15, 145, 15, 096, 15154, 1, 5162, 1, 5164, 1, 5192, 1, 5175, 1,     , 0,     , 0], [S, 15, 040, 15, 020, 15, 083, 15133, 1, 5135, 1, 5155, 1, 5166, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15, 038, 15, 126, 15, 140, 15174, 1, 5184, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 040, 15, 020, 15, 083, 15133, 1, 5135, 1, 5155, 1, 5166, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15, 038, 15, 126, 15, 140, 15174, 1, 5184, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 015, 25, 019, 65, 033, 25035, 2, 5037, 6, 5052, 2, 5055, 2, 5060, 2, 5070, 1, 5074, 6], [S, 15, 078, 65, 081, 15, 085, 45090, 2, 5091, 5, 5093, 3, 5095, 3, 5129, 6, 5144, 3, 5165, 1], [S, 15, 303, 15, 169, 45, 170, 25171, 2, 5177, 5, 5178, 3, 5016, 2, 5089, 3, 5136, 1, 5011, 1], [S, 15, 046, 35, 145, 45, 096, 65154, 5, 5162, 5, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2], [S, 15, 015, 25, 019, 75, 033, 25035, 2, 5037, 6, 5052, 2, 5055, 2, 5060, 2, 5070, 2, 5074, 6], [S, 15, 078, 75, 081, 25, 085, 45090, 2, 5091, 6, 5093, 3, 5095, 3, 5129, 6, 5144, 3, 5165, 2], [S, 15, 303, 25, 169, 45, 170, 25171, 2, 5177, 6, 5178, 3, 5016, 2, 5089, 3, 5136, 2, 5011, 2], [S, 15, 046, 35, 145, 45, 096, 65154, 5, 5162, 5, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2], [S, 15, 019, 55, 037, 55, 074, 55078, 5, 5085, 4, 5091, 5, 5093, 3, 5095, 3, 5129, 5, 5144, 3], [S, 15, 169, 35, 177, 65, 178, 35089, 3, 5046, 3, 5145, 4, 5096, 5, 5154, 4, 5162, 4, 5163, 3], [S, 15, 040, 25, 020, 25, 059, 15068, 1, 5083, 1, 5084, 2, 5133, 2, 5135, 2, 5155, 2, 5156, 2], [S, 15, 166, 35, 167, 25, 048, 25049, 2, 5139, 2, 5143, 2, 5003, 2, 5002, 2, 5027, 2, 5038, 2], [S, 15, 073, 25, 126, 25, 140, 25174, 3, 5184, 2,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 040, 25, 020, 25, 059, 15068, 1, 5083, 1, 5084, 2, 5133, 2, 5135, 2, 5155, 2, 5156, 2], [S, 15, 166, 25, 167, 25, 048, 15049, 1, 5139, 2, 5143, 2, 5003, 2, 5010, 1, 5062, 1, 5138, 1], [S, 15, 141, 15, 002, 25, 027, 25038, 2, 5073, 2, 5126, 2, 5140, 2, 5174, 2, 5184, 2,     , 0], [S, 15, 040, 15, 020, 15, 068, 15083, 1, 5084, 1, 5133, 1, 5135, 1, 5155, 1, 5156, 1, 5166, 2], [S, 15, 167, 15, 048, 15, 049, 15139, 1, 5143, 1, 5002, 1, 5027, 2, 5038, 2, 5073, 1, 5126, 1], [S, 15, 140, 15, 174, 25, 184, 1, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 036, 25, 043, 35, 045, 25057, 1, 5065, 2, 5069, 2, 5076, 2, 5079, 1, 5094, 2, 5128, 2], [S, 15, 151, 25, 180, 15, 072, 15173, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 036, 15, 043, 25, 045, 25057, 1, 5065, 2, 5069, 2, 5076, 2, 5079, 1, 5094, 2, 5128, 2], [S, 15, 151, 25, 180, 25, 072, 15173, 2,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15, 043, 25, 045, 25, 065, 15076, 2, 5128, 2, 5151, 2, 5180, 1, 5072, 1, 5173, 1,     , 0], [S, 15, 015, 25, 019, 45, 033, 25035, 2, 5037, 4, 5052, 2, 5055, 2, 5060, 2, 5070, 2, 5074, 3], [S, 15, 078, 45, 081, 25, 085, 35090, 2, 5091, 4, 5093, 3, 5095, 4, 5129, 4, 5144, 4, 5165, 2], [S, 15, 303, 25, 169, 25, 170, 25171, 2, 5177, 4, 5016, 4, 5089, 3, 5136, 2, 5011, 3, 5046, 2], [S, 15, 145, 35, 096, 45, 154, 45162, 4, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2,     , 0], [S, 15, 015, 25, 019, 55, 033, 25037, 5, 5060, 2, 5070, 1, 5074, 3, 5078, 5, 5085, 2, 5090, 2], [S, 15, 091, 55, 093, 25, 095, 35129, 6, 5144, 5, 5165, 2, 5169, 2, 5170, 2, 5171, 2, 5177, 5], [S, 15, 178, 25, 016, 45, 089, 25136, 2, 5011, 2, 5046, 2, 5145, 2, 5096, 5, 5154, 3, 5162, 3], [S, 15, 163, 25, 164, 25, 192, 25175, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0]]')
			select('Table', 'cell:Start,2(7)')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [, 2, 2, 6, 0, true], [, 4, 3, 0, 0, true], [, 7, 9, 6, 0, true], [, 16, 3, 0, 0, true], [, 19, 9, 6, 0, true], [, 28, 3, 0, 0, true], [, 31, 12, 6, 0, true], [, 43, 8, 6, 0, true], [, 51, 4, 0, 0, true], [, 55, 8, 6, 0, true], [, 63, 4, 0, 0, true], [, 67, 8, 6, 0, true], [, 75, 4, 0, 0, true], [, 79, 8, 6, 0, true], [, 87, 4, 0, 0, true], [, 91, 8, 6, 0, true], [, 99, 4, 0, 0, true], [, 103, 8, 6, 0, true], [, 111, 4, 0, 0, true], [, 115, 8, 6, 0, true]]')
		else:
			assert_p('Table1', 'Content', '[[S, 15015, 15019, 35033, 13337, 207800015037, 15052, 15055, 15060, 25070, 15074, 1], [S, 15078, 15081, 15085, 15090, 15091, 15093, 15095, 15129, 15144, 15165, 1], [S, 15303, 15169, 15170, 15171, 15177, 15016, 15089, 25136, 15011, 15046, 1], [S, 15145, 15096, 25154, 15162, 15163, 15164, 15192, 15150, 15175, 1, 0], [S, 15036, 15043, 15045, 15057, 15065, 15069, 15076, 15079, 15094, 15128, 1], [S, 15151, 15180, 15072, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15015, 15019, 25037, 23533, 475100015070, 15078, 15093, 15095, 15129, 15144, 1], [S, 15165, 15170, 15171, 15016, 25089, 25136, 15011, 15046, 15096, 15154, 1], [S, 15162, 15163, 15164, 15192, 15175, 1, 0, 0, 0, 0, 0], [S, 15083, 15133, 15155, 13584, 475100015166, 15167, 15049, 15139, 15002, 15027, 1], [S, 15038, 15140, 15174, 15184, 1, 0, 0, 0, 0, 0, 0], [S, 15036, 15043, 15045, 13533, 475100015069, 15076, 15094, 15128, 15151, 15180, 1], [S, 15072, 15173, 1, 0, 0, 0, 0, 0, 0, 0, 0], [S, 15036, 15043, 15045, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15180, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0], [S, 15036, 15043, 15045, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15180, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0], [S, 15015, 15019, 15033, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15095, 15129, 15144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15046, 15145, 15096, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15015, 15019, 15033, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15095, 15129, 15144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15046, 15145, 15096, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15040, 15020, 15083, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15038, 15126, 15140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15040, 15020, 15083, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15038, 15126, 15140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15015, 25019, 65033, 25035, 25037, 65052, 25055, 25060, 25070, 15074, 6], [S, 15078, 65081, 15085, 45090, 25091, 55093, 35095, 35129, 65144, 35165, 1], [S, 15303, 15169, 45170, 25171, 25177, 55178, 35016, 25089, 35136, 15011, 1], [S, 15046, 35145, 45096, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15015, 25019, 75033, 25035, 25037, 65052, 25055, 25060, 25070, 25074, 6], [S, 15078, 75081, 25085, 45090, 25091, 65093, 35095, 35129, 65144, 35165, 2], [S, 15303, 25169, 45170, 25171, 25177, 65178, 35016, 25089, 35136, 25011, 2], [S, 15046, 35145, 45096, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15019, 55037, 55074, 55078, 55085, 45091, 55093, 35095, 35129, 55144, 3], [S, 15169, 35177, 65178, 35089, 35046, 35145, 45096, 55154, 45162, 45163, 3], [S, 15040, 25020, 25059, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15166, 35167, 25048, 25049, 25139, 25143, 25003, 25002, 25027, 25038, 2], [S, 15073, 25126, 25140, 25174, 35184, 2, 0, 0, 0, 0, 0], [S, 15040, 25020, 25059, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15166, 25167, 25048, 15049, 15139, 25143, 25003, 25010, 15062, 15138, 1], [S, 15141, 15002, 25027, 25038, 25073, 25126, 25140, 25174, 25184, 2, 0], [S, 15040, 15020, 15068, 15083, 15084, 15133, 15135, 15155, 15156, 15166, 2], [S, 15167, 15048, 15049, 15139, 15143, 15002, 15027, 25038, 25073, 15126, 1], [S, 15140, 15174, 25184, 1, 0, 0, 0, 0, 0, 0, 0], [S, 15036, 25043, 35045, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15151, 25180, 15072, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15036, 15043, 25045, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15151, 25180, 25072, 15173, 2, 0, 0, 0, 0, 0, 0], [S, 15043, 25045, 25065, 15076, 25128, 25151, 25180, 15072, 15173, 1, 0], [S, 15015, 25019, 45033, 25035, 25037, 45052, 25055, 25060, 25070, 25074, 3], [S, 15078, 45081, 25085, 35090, 25091, 45093, 35095, 45129, 45144, 45165, 2], [S, 15303, 25169, 25170, 25171, 25177, 45016, 45089, 35136, 25011, 35046, 2], [S, 15145, 35096, 45154, 45162, 45163, 35164, 25192, 25150, 25175, 2, 0], [S, 15015, 25019, 55033, 25037, 55060, 25070, 15074, 35078, 55085, 25090, 2], [S, 15091, 55093, 25095, 35129, 65144, 55165, 25169, 25170, 25171, 25177, 5], [S, 15178, 25016, 45089, 25136, 25011, 25046, 25145, 25096, 55154, 35162, 3], [S, 15163, 25164, 25192, 25175, 1, 0, 0, 0, 0, 0, 0]]')
			select('Table', 'cell:Start,2(7)')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [, 2, 5, 6, 0, true], [, 7, 12, 6, 0, true], [, 19, 12, 6, 0, true], [, 31, 12, 6, 0, true], [, 43, 12, 6, 0, true], [, 55, 12, 6, 0, true], [, 67, 12, 6, 0, true], [, 79, 12, 6, 0, true], [, 91, 12, 6, 0, true], [, 103, 12, 6, 0, true], [, 115, 8, 6, 0, true]]')
#		select('Table', '')

		rightclick('Table', 'Field Name,1')
		select_menu('Generate Field Names')


		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [S1, 2, 2, 6, 0, true], [S2, 4, 3, 0, 0, true], [S3, 7, 9, 6, 0, true], [S4, 16, 3, 0, 0, true], [S5, 19, 9, 6, 0, true], [S6, 28, 3, 0, 0, true], [S7, 31, 12, 6, 0, true], [S8, 43, 8, 6, 0, true], [S9, 51, 4, 0, 0, true], [S10, 55, 8, 6, 0, true], [S11, 63, 4, 0, 0, true], [S12, 67, 8, 6, 0, true], [S13, 75, 4, 0, 0, true], [S14, 79, 8, 6, 0, true], [S15, 87, 4, 0, 0, true], [S16, 91, 8, 6, 0, true], [S17, 99, 4, 0, 0, true], [S18, 103, 8, 6, 0, true], [S19, 111, 4, 0, 0, true], [S20, 115, 8, 6, 0, true]]')
		else:
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, 0, 0, true], [S1, 2, 5, 6, 0, true], [S2, 7, 12, 6, 0, true], [S3, 19, 12, 6, 0, true], [S4, 31, 12, 6, 0, true], [S5, 43, 12, 6, 0, true], [S6, 55, 12, 6, 0, true], [S7, 67, 12, 6, 0, true], [S8, 79, 12, 6, 0, true], [S9, 91, 12, 6, 0, true], [S10, 103, 12, 6, 0, true], [S11, 115, 8, 6, 0, true]]')
		click('Right')
		select('TabbedPane', '')
		click('Pnl7SaveDbLayout', 264, 53)
		select('TextField', 'Wizard_xAmsPO')
		click('Right')
		if commonBits.isVersion89():
			select('Table', 'cell:2 - 2|S1,0(14)')
		else:
			select('Table', 'cell:2 - 5|S1,0(14535)')
		rightclick('Table', '1 - 1|Record_Type,0')
##		select('Table', 'cell:2 - 5|S1,0(14535)')
		select_menu('Edit Record')
##		select('Table1', 'cell:2 - 5|S1,0(14535)')
		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, H, H], [H1, 2, 6, 145357, 145357], [H2, 8, 10, 4338, 0000004338], [H3, 18, 12, 233863, 000000233863], [H4, 30, 6, 40929, 040929], [H5, 36, 8, , ], [H6, 44, 2, 0, 00], [H7, 46, 2, , ], [H8, 48, 3, 290, 290], [H9, 51, 1, , ], [H10, 52, 2, 5, 05], [H11, 54, 2, 1, 01], [H12, 56, 2, 3, 03], [H13, 58, 2, 5, 05], [H14, 60, 2, 1, 01], [H15, 62, 6, 75966, 075966], [H16, 68, 8, , ], [H17, 76, 13, OPTIONS PLCFT, OPTIONS PLCFT]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, D, D], [D1, 2, 1, 1, 1], [D2, 3, 5, 44, 00044], [D3, 8, 15, 11840, 000000000011840], [D4, 23, 15, 0, 000000000000000], [D5, 38, 1, , ], [D6, 39, 8, 45872078, 45872078], [D7, 47, 11, 4544, 00000004544], [D8, 58, 7, 44, 0000044], [D9, 65, 7, , ], [D10, 72, 7, 2117093, 2117093], [D11, 79, 8, , ], [D12, 87, 8, 45872078, 45872078], [D13, 95, 7, , ], [D14, 102, 50, MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN, MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, S, S], [S1, 2, 2, 15, 15], [S2, 4, 3, 015, 015], [S3, 7, 9, 15, 000000015], [S4, 16, 3, 019, 019], [S5, 19, 9, 35, 000000035], [S6, 28, 3, 033, 033], [S7, 31, 12, 13337, 000000013337], [S8, 43, 8, 20780001, 20780001], [S9, 51, 4, 5037, 5037], [S10, 55, 8, 1, 00000001], [S11, 63, 4, 5052, 5052], [S12, 67, 8, 1, 00000001], [S13, 75, 4, 5055, 5055], [S14, 79, 8, 1, 00000001], [S15, 87, 4, 5060, 5060], [S16, 91, 8, 2, 00000002], [S17, 99, 4, 5070, 5070], [S18, 103, 8, 1, 00000001], [S19, 111, 4, 5074, 5074], [S20, 115, 8, 1, 00000001]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, S, S], [S1, 2, 2, 15, 15], [S2, 4, 3, 078, 078], [S3, 7, 9, 15, 000000015], [S4, 16, 3, 081, 081], [S5, 19, 9, 15, 000000015], [S6, 28, 3, 085, 085], [S7, 31, 12, 15090, 000000015090], [S8, 43, 8, 1, 00000001], [S9, 51, 4, 5091, 5091], [S10, 55, 8, 1, 00000001], [S11, 63, 4, 5093, 5093], [S12, 67, 8, 1, 00000001], [S13, 75, 4, 5095, 5095], [S14, 79, 8, 1, 00000001], [S15, 87, 4, 5129, 5129], [S16, 91, 8, 1, 00000001], [S17, 99, 4, 5144, 5144], [S18, 103, 8, 1, 00000001], [S19, 111, 4, 5165, 5165], [S20, 115, 8, 1, 00000001]]')
		else:
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, H, H], [H1, 2, 6, 145357, 145357], [H2, 8, 10, 4338, 0000004338], [H3, 18, 12, 233863, 000000233863], [H4, 30, 2, 4, 04], [H5, 32, 4, 929, 0929], [H6, 36, 8, , ], [H7, 44, 2, 0, 00], [H8, 46, 2, , ], [H9, 48, 3, 290, 290], [H10, 51, 1, , ], [H11, 52, 2, 5, 05], [H12, 54, 2, 1, 01], [H13, 56, 2, 3, 03], [H14, 58, 2, 5, 05], [H15, 60, 2, 1, 01], [H16, 62, 6, 75966, 075966], [H17, 68, 8, , ], [H18, 76, 7, OPTIONS, OPTIONS], [H19, 83, 6,  PLCFT,  PLCFT]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, D, D], [D1, 2, 1, 1, 1], [D2, 3, 5, 44, 00044], [D3, 8, 15, 11840, 000000000011840], [D4, 23, 15, 0, 000000000000000], [D5, 38, 1, , ], [D6, 39, 8, 45872078, 45872078], [D7, 47, 11, 4544, 00000004544], [D8, 58, 7, 44, 0000044], [D9, 65, 7, , ], [D10, 72, 1, 2, 2], [D11, 73, 6, 117093, 117093], [D12, 79, 8, , ], [D13, 87, 8, 45872078, 45872078], [D14, 95, 7, , ], [D15, 102, 2, MT, MT], [D16, 104, 2, H5, H5], [D17, 106, 11, 033H DUSTY, 033H DUSTY], [D18, 117, 35, PINK L/S FANCY CREW C\'MERE CARDIGAN, PINK L/S FANCY CREW C\'MERE CARDIGAN]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, S, S], [S1, 2, 5, 15015, 15015], [S2, 7, 12, 15019, 000000015019], [S3, 19, 12, 35033, 000000035033], [S4, 31, 12, 13337, 000000013337], [S5, 43, 12, 207800015037, 207800015037], [S6, 55, 12, 15052, 000000015052], [S7, 67, 12, 15055, 000000015055], [S8, 79, 12, 15060, 000000015060], [S9, 91, 12, 25070, 000000025070], [S10, 103, 12, 15074, 000000015074], [S11, 115, 8, 1, 00000001]]')
			click('Right')
			assert_p('Table', 'Content', '[[Record_Type, 1, 1, S, S], [S1, 2, 5, 15078, 15078], [S2, 7, 12, 15081, 000000015081], [S3, 19, 12, 15085, 000000015085], [S4, 31, 12, 15090, 000000015090], [S5, 43, 12, 15091, 000000015091], [S6, 55, 12, 15093, 000000015093], [S7, 67, 12, 15095, 000000015095], [S8, 79, 12, 15129, 000000015129], [S9, 91, 12, 15144, 000000015144], [S10, 103, 12, 15165, 000000015165], [S11, 115, 8, 1, 00000001]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		if commonBits.isVersion89():
			select('Table', 'cell:2 - 2|S1,0(14)')
		else:
			select('Table', 'cell:2 - 5|S1,0(14535)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
		select('ComboBox1', 'Ams')
		click('Edit1')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'Wizard_xAmsPO')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell:Field,0(Record_Type)')
		assert_p('ChildRecordsJTbl', 'Text', 'Record_Type', 'Field,0')
		select('ChildRecordsJTbl', 'cell:Field Value,0(H)')
		assert_p('ChildRecordsJTbl', 'Text', 'H', 'Field Value,0')
		select('ChildRecordsJTbl', 'cell:Field Value,1(D)')
		assert_p('ChildRecordsJTbl', 'Text', 'D', 'Field Value,1')
		select('ChildRecordsJTbl', 'cell:Field Value,2(S)')
		assert_p('ChildRecordsJTbl', 'Text', 'S', 'Field Value,2')
		select('ChildRecordsJTbl', 'cell:Field,2(Record_Type)')
		assert_p('ChildRecordsJTbl', 'Text', 'Record_Type', 'Field,2')
		select('ChildRecordsJTbl', 'cell:Field,2(Record_Type)')
		click('Delete3')

		if window('Delete: Wizard_xAmsPO'):
			click('Yes')
		close()

#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

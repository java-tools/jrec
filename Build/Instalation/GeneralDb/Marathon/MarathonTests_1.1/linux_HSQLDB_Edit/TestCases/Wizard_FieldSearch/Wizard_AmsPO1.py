useFixture(default)

###
### Needs to be completed and tested !!!!
###

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20050101.txt')
		click('Layout Wizard')
		select('Multiple Records #{fixed length#}', 'true')
		click('Right')
		select('TabbedPane', '')
		assert_p('EditorPane', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    This screen will display the first 60 lines of the file.<br>Indicate the <i>start</i> 
    of the <b>Record-Type field</b> by clicking on the starting column<br>Then 
    click on the start of the Next Field.<br>To remove a position click on it 
    again.
  </body>
</html>
''')
		select('TextField1', '01')
		select('TextField2', '01')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')
		click('Right')
		select('TabbedPane', '')
		keystroke('Right', 'Ctrl+F6')
		select_menu('Window>>File Wizard')
		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[H, , false, true], [D, , false, true], [S, , false, true]]')
		else:
			assert_p('Table', 'Content', '[[H, ], [D, ], [S, ]]')
		select('Table', 'Header', 'Record Name,0')
		select('Table', 'cell:Record Name,0()')
		keystroke('Table', 'Down', 'Record Name,0')
		select('Table', 'Detail', 'Record Name,1')
		select('Table', 'cell:Record Name,1()')
		keystroke('Table', 'Down', 'Record Name,1')
		select('Table', 'Store', 'Record Name,2')
		select('Table', 'cell:Record Name,2()')
		keystroke('Table', 'Up', 'Record Name,2')
		select('Table', 'cell:Record Name,1(Detail)')
		if commonBits.isVersion89():
			assert_p('Table', 'Content', '[[H, Header, false, true], [D, Detail, false, true], [S, Store, false, true]]')
		else:
			assert_p('Table', 'Content', '[[H, Header], [D, Detail], [S, Store]]')
		select('Table', 'cell:Record Name,1(Detail)')
		click('Right')
		select('TabbedPane', '')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')
		click('Right')
		assert_p('TextArea', 'Text', 'You must define the Fields all Records. Please update - Detail')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')

		select('ComboBox', 'Store')
		assert_p('Table', 'Text', 'S', 'A,1')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')

		click('Right')
		select('TabbedPane', '')
		select('Table', 'f1', 'Field Name,1')
		select('Table', 'cell:Field Name,1()')
		keystroke('Table', 'Down', 'Field Name,1')
		select('Table', 'f2', 'Field Name,2')
		select('Table', 'cell:Field Name,2()')
		keystroke('Table', 'Down', 'Field Name,2')
		select('Table', 'f3', 'Field Name,3')
		select('Table', 'cell:Field Name,3()')
		keystroke('Table', 'Down', 'Field Name,3')
		select('Table', 'h4', 'Field Name,4')
		select('Table', 'h3', 'Field Name,3')
		select('Table', 'h2', 'Field Name,2')
		select('Table', 'h1', 'Field Name,1')
		select('Table', 'h5', 'Field Name,5')
		select('Table', 'cell:Field Name,5()')
		keystroke('Table', 'Down', 'Field Name,5')
		select('Table', 'h6', 'Field Name,6')
		select('Table', 'cell:Field Name,6()')
		keystroke('Table', 'Down', 'Field Name,6')
		select('Table', 'h7', 'Field Name,7')
		select('Table', 'cell:Field Name,7()')
		keystroke('Table', 'Down', 'Field Name,7')
		select('Table', 'h8', 'Field Name,8')
		select('Table', 'cell:Field Name,8()')
		select('Table1', 'cell:h1,1(145358)')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[H, 145357, 4338, 233863, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS PLCFT], [H, 145358, 4338, 233872, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS PLCFT], [H, 145359, 4468, 255906, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS PLCFT], [H, 145360, 4448, 290908, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75967,         , OPTIONS PLCFT], [H, 145361, 4228, 292210, 40929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS PLCFT], [H, 145362, 5220, 211984, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75965,         , LADIES KNICFT], [H, 145363, 5110, 211985, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75966,         , LADIES KNICFT], [H, 145364, 5110, 211987, 40929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 65967,         , LADIES KNICFT], [H, 145365, 13112, 211549, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 85966,         , LADIES KNICFT], [H, 145366, 13312, 211555, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75967,         , LADIES KNICFT], [H, 145367, 12212, 222556, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75965,         , LADIES KNICFT], [H, 145368, 1312, 211617, 41005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 175966,         , LADIES KNICFT]]')
		else:
			assert_p('Table1', 'Content', '[[H, 145357, 4338, 233863, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS,  PLCFT], [H, 145358, 4338, 233872, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS,  PLCFT], [H, 145359, 4468, 255906, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75966,         , OPTIONS,  PLCFT], [H, 145360, 4448, 290908, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75967,         , OPTIONS,  PLCFT], [H, 145361, 4228, 292210, 4, 929,         , 0,   , 290,  , 5, 1, 3, 5, 1, 75965,         , OPTIONS,  PLCFT], [H, 145362, 5220, 211984, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75965,         , LADIES , KNICFT], [H, 145363, 5110, 211985, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 75966,         , LADIES , KNICFT], [H, 145364, 5110, 211987, 4, 929,         , 0,   , 200,  , 5, 1, 3, 5, 1, 65967,         , LADIES , KNICFT], [H, 145365, 13112, 211549, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 85966,         , LADIES , KNICFT], [H, 145366, 13312, 211555, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75967,         , LADIES , KNICFT], [H, 145367, 12212, 222556, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 75965,         , LADIES , KNICFT], [H, 145368, 1312, 211617, 4, 1005,         , 0,   , 220,  , 5, 1, 3, 5, 1, 175966,         , LADIES , KNICFT]]')
		select('Table1', 'cell:h1,1(145358)')
		select('ComboBox', 'Detail')
		select('Table', 'd2', 'Field Name,2')
		select('Table', 'cell:Field Name,2()')
		keystroke('Table', 'Down', 'Field Name,2')
		select('Table', 'd3', 'Field Name,3')
		select('Table', 'cell:Field Name,3()')
		keystroke('Table', 'Down', 'Field Name,3')
		select('Table', 'd4', 'Field Name,4')
		select('Table', 'cell:Field Name,4()')
		keystroke('Table', 'Down', 'Field Name,4')
		select('Table', 'd5', 'Field Name,5')
		select('Table', 'cell:Field Name,5()')
		keystroke('Table', 'Down', 'Field Name,5')
		select('Table', 'd6', 'Field Name,6')
		select('Table', 'cell:Field Name,6()')
		keystroke('Table', 'Down', 'Field Name,6')
		select('Table', 'd7', 'Field Name,7')
		select('Table', 'cell:Field Name,7()')
		keystroke('Table', 'Down', 'Field Name,7')
		select('Table', 'd8', 'Field Name,8')
		select('Table', 'cell:Field Name,8()')
		select('Table1', 'cell:d2,1(14)')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[D, 1, 44, 11840, 0,  , 45872078, 4544, 44,        , 2117093,         , 45872078,        , MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 14, 11984, 0,  , 43372078, 4544, 14,        , 2117152,         , 45872078,        , MTH5033H DUSTY PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 29, 10120, 0,  , 45874751, 4090, 29,        , 2117337,         , 45872078,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 14, 0, 29440800000000,  , 45874751, 4090, 14,        , 2117347,         , 35874751,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 12, 10256, 0,  , 35874751, 4090, 12,        , 2117354,         , 35874751,        , MTH5030H BLK L/S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 11, 5848, 0,  , 31191697, 2726, 11,        , 2327928,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 11, 5848, 0,  , 33391826, 2726, 11,        , 2327931,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 28, 3356, 0,  , 31191697, 2726, 28,        , 2327933,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 28, 3356, 0,  , 31191826, 2726, 28,        , 2327936,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 15, 3356, 0,  , 31191697, 2726, 15,        , 2327982,         , 35891697,        , SKY BLUE SKP02 L/WEIGHT PONCHO                    ], [D, 1, 15, 3356, 0,  , 35221826, 2726, 15,        , 2327985,         , 35891826,        , DUSTY PINK SKP02 L/WEIGHT PONCHO                  ], [D, 1, 123, 6723, 0,  , 35116979, 2272, 123,        , 2098136,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 133, 6723, 0,  , 35117204, 2272, 133,        , 2098137,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 81, 6723, 0,  , 31127419, 2272, 81,        , 2098139,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 35333979, 2272, 49,        , 2098158,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 32227204, 2272, 49,        , 2098159,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 27, 6723, 0,  , 32227419, 2272, 27,        , 2098160,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 33336979, 2272, 24,        , 2098161,         , 35926979,        , SA13 CHOC MUSTANG PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 30027204, 2272, 24,        , 2098165,         , 35927204,        , SA13 PALE BLUE MP RAGLEN SWEAT                    ], [D, 1, 14, 6183, 0,  , 35007419, 2272, 14,        , 2098167,         , 35927419,        , SA13 PALE PINK MP PRT RAGLAN SWEAT                ], [D, 1, 109, 11106, 0,  , 31129413, 3636, 109,        , 2098293,         , 35929413,        , S4547 PALE BLUE INDIAN PRINT JACKET               ], [D, 1, 97, 11106, 0,  , 31129710, 3636, 97,        , 2098297,         , 35929710,        , S4547 PALE PINK EAGLE PRINT JACKET                ]]')
		else:
			assert_p('Table1', 'Content', '[[D, 1, 44, 11840, 0,  , 45872078, 4544, 44,        , 2, 117093,         , 45872078,        , MT, H5, 033H DUSTY , PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 14, 11984, 0,  , 43372078, 4544, 14,        , 2, 117152,         , 45872078,        , MT, H5, 033H DUSTY , PINK L/S FANCY CREW C\'MERE CARDIGAN], [D, 1, 29, 10120, 0,  , 45874751, 4090, 29,        , 2, 117337,         , 45872078,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 14, 0, 29440800000000,  , 45874751, 4090, 14,        , 2, 117347,         , 35874751,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 12, 10256, 0,  , 35874751, 4090, 12,        , 2, 117354,         , 35874751,        , MT, H5, 030H BLK L/, S VLVT RIBBON SCOOP C\'MERE W/BROOCH], [D, 1, 11, 5848, 0,  , 31191697, 2726, 11,        , 2, 327928,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 11, 5848, 0,  , 33391826, 2726, 11,        , 2, 327931,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 28, 3356, 0,  , 31191697, 2726, 28,        , 2, 327933,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 28, 3356, 0,  , 31191826, 2726, 28,        , 2, 327936,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 15, 3356, 0,  , 31191697, 2726, 15,        , 2, 327982,         , 35891697,        , SK, Y, BLUE SKP02 , L/WEIGHT PONCHO                    ], [D, 1, 15, 3356, 0,  , 35221826, 2726, 15,        , 2, 327985,         , 35891826,        , DU, ST, Y PINK SKP0, 2 L/WEIGHT PONCHO                  ], [D, 1, 123, 6723, 0,  , 35116979, 2272, 123,        , 2, 98136,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 133, 6723, 0,  , 35117204, 2272, 133,        , 2, 98137,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 81, 6723, 0,  , 31127419, 2272, 81,        , 2, 98139,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 35333979, 2272, 49,        , 2, 98158,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 49, 6723, 0,  , 32227204, 2272, 49,        , 2, 98159,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 27, 6723, 0,  , 32227419, 2272, 27,        , 2, 98160,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 33336979, 2272, 24,        , 2, 98161,         , 35926979,        , SA, 13,  CHOC MUSTA, NG PRT RAGLAN SWEAT                ], [D, 1, 24, 6183, 0,  , 30027204, 2272, 24,        , 2, 98165,         , 35927204,        , SA, 13,  PALE BLUE , MP RAGLEN SWEAT                    ], [D, 1, 14, 6183, 0,  , 35007419, 2272, 14,        , 2, 98167,         , 35927419,        , SA, 13,  PALE PINK , MP PRT RAGLAN SWEAT                ], [D, 1, 109, 11106, 0,  , 31129413, 3636, 109,        , 2, 98293,         , 35929413,        , S4, 54, 7 PALE BLUE,  INDIAN PRINT JACKET               ], [D, 1, 97, 11106, 0,  , 31129710, 3636, 97,        , 2, 98297,         , 35929710,        , S4, 54, 7 PALE PINK,  EAGLE PRINT JACKET                ]]')
		select('Table1', 'cell:d2,1(14)')
		click('Right')
		assert_p('TextArea', 'Text', 'You must define the field Names in all Records, please update: Store')
		select('Table', 's2', 'Field Name,2')
		select('Table', 'cell:Field Name,2()')
		keystroke('Table', 'Down', 'Field Name,2')
		select('Table', 's3', 'Field Name,3')
		select('Table', 'cell:Field Name,3()')
		keystroke('Table', 'Down', 'Field Name,3')
		select('Table', 's4', 'Field Name,4')
		select('Table', 'cell:Field Name,4()')
		keystroke('Table', 'Down', 'Field Name,4')
		select('Table', 's5', 'Field Name,5')
		select('Table', 'cell:Field Name,5()')
		keystroke('Table', 'Down', 'Field Name,5')
		select('Table', 's6', 'Field Name,6')
		select('Table', 'cell:Field Name,6()')
		keystroke('Table', 'Down', 'Field Name,6')
		select('Table', 's7', 'Field Name,7')
		select('Table', 'cell:Field Name,7()')
		keystroke('Table', 'Down', 'Field Name,7')
		select('Table', 's8', 'Field Name,8')
		select('Table', 'cell:Field Name,8()')
		keystroke('Table', 'Down', 'Field Name,8')
		select('Table', 's9', 'Field Name,9')
		select('Table', 'cell:Field Name,9()')
		keystroke('Table', 'Down', 'Field Name,9')
		select('Table', 's10', 'Field Name,10')
		select('Table', 'cell:Field Name,10()')
		select('Table1', 'cell:B,2(15)')
		if commonBits.isVersion89():
			assert_p('Table1', 'Content', '[[S, 15015, 15019, 35033, 13337, 20780001, 5037, 1, 5052, 1, 5055, 1, 5060, 2, 5070, 1, 5074, 1], [S, 15078, 15081, 15085, 15090, 1, 5091, 1, 5093, 1, 5095, 1, 5129, 1, 5144, 1, 5165, 1], [S, 15303, 15169, 15170, 15171, 1, 5177, 1, 5016, 1, 5089, 2, 5136, 1, 5011, 1, 5046, 1], [S, 15145, 15096, 25154, 15162, 1, 5163, 1, 5164, 1, 5192, 1, 5150, 1, 5175, 1,     , 0], [S, 15036, 15043, 15045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5079, 1, 5094, 1, 5128, 1], [S, 15151, 15180, 15072, 15173, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15015, 15019, 25037, 23533, 47510001, 5070, 1, 5078, 1, 5093, 1, 5095, 1, 5129, 1, 5144, 1], [S, 15165, 15170, 15171, 15016, 2, 5089, 2, 5136, 1, 5011, 1, 5046, 1, 5096, 1, 5154, 1], [S, 15162, 15163, 15164, 15192, 1, 5175, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15083, 15133, 15155, 13584, 47510001, 5166, 1, 5167, 1, 5049, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15038, 15140, 15174, 15184, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15036, 15043, 15045, 13533, 47510001, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1, 5180, 1], [S, 15072, 15173, 1, 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15036, 15043, 15045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1], [S, 15180, 1, 0, 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15036, 15043, 15045, 15057, 1, 5065, 1, 5069, 1, 5076, 1, 5094, 1, 5128, 1, 5151, 1], [S, 15180, 1, 0, 0, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15015, 15019, 15033, 15037, 1, 5060, 1, 5074, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1], [S, 15095, 15129, 15144, 15169, 1, 5170, 1, 5171, 1, 5177, 1, 5016, 1, 5089, 1, 5136, 1], [S, 15046, 15145, 15096, 15154, 1, 5162, 1, 5164, 1, 5192, 1, 5175, 1,     , 0,     , 0], [S, 15015, 15019, 15033, 15037, 1, 5060, 1, 5074, 1, 5078, 1, 5085, 1, 5091, 1, 5093, 1], [S, 15095, 15129, 15144, 15169, 1, 5170, 1, 5171, 1, 5177, 1, 5016, 1, 5089, 1, 5136, 1], [S, 15046, 15145, 15096, 15154, 1, 5162, 1, 5164, 1, 5192, 1, 5175, 1,     , 0,     , 0], [S, 15040, 15020, 15083, 15133, 1, 5135, 1, 5155, 1, 5166, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15038, 15126, 15140, 15174, 1, 5184, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15040, 15020, 15083, 15133, 1, 5135, 1, 5155, 1, 5166, 1, 5139, 1, 5002, 1, 5027, 1], [S, 15038, 15126, 15140, 15174, 1, 5184, 1,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15015, 25019, 65033, 25035, 2, 5037, 6, 5052, 2, 5055, 2, 5060, 2, 5070, 1, 5074, 6], [S, 15078, 65081, 15085, 45090, 2, 5091, 5, 5093, 3, 5095, 3, 5129, 6, 5144, 3, 5165, 1], [S, 15303, 15169, 45170, 25171, 2, 5177, 5, 5178, 3, 5016, 2, 5089, 3, 5136, 1, 5011, 1], [S, 15046, 35145, 45096, 65154, 5, 5162, 5, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2], [S, 15015, 25019, 75033, 25035, 2, 5037, 6, 5052, 2, 5055, 2, 5060, 2, 5070, 2, 5074, 6], [S, 15078, 75081, 25085, 45090, 2, 5091, 6, 5093, 3, 5095, 3, 5129, 6, 5144, 3, 5165, 2], [S, 15303, 25169, 45170, 25171, 2, 5177, 6, 5178, 3, 5016, 2, 5089, 3, 5136, 2, 5011, 2], [S, 15046, 35145, 45096, 65154, 5, 5162, 5, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2], [S, 15019, 55037, 55074, 55078, 5, 5085, 4, 5091, 5, 5093, 3, 5095, 3, 5129, 5, 5144, 3], [S, 15169, 35177, 65178, 35089, 3, 5046, 3, 5145, 4, 5096, 5, 5154, 4, 5162, 4, 5163, 3], [S, 15040, 25020, 25059, 15068, 1, 5083, 1, 5084, 2, 5133, 2, 5135, 2, 5155, 2, 5156, 2], [S, 15166, 35167, 25048, 25049, 2, 5139, 2, 5143, 2, 5003, 2, 5002, 2, 5027, 2, 5038, 2], [S, 15073, 25126, 25140, 25174, 3, 5184, 2,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15040, 25020, 25059, 15068, 1, 5083, 1, 5084, 2, 5133, 2, 5135, 2, 5155, 2, 5156, 2], [S, 15166, 25167, 25048, 15049, 1, 5139, 2, 5143, 2, 5003, 2, 5010, 1, 5062, 1, 5138, 1], [S, 15141, 15002, 25027, 25038, 2, 5073, 2, 5126, 2, 5140, 2, 5174, 2, 5184, 2,     , 0], [S, 15040, 15020, 15068, 15083, 1, 5084, 1, 5133, 1, 5135, 1, 5155, 1, 5156, 1, 5166, 2], [S, 15167, 15048, 15049, 15139, 1, 5143, 1, 5002, 1, 5027, 2, 5038, 2, 5073, 1, 5126, 1], [S, 15140, 15174, 25184, 1, 0,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15036, 25043, 35045, 25057, 1, 5065, 2, 5069, 2, 5076, 2, 5079, 1, 5094, 2, 5128, 2], [S, 15151, 25180, 15072, 15173, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15036, 15043, 25045, 25057, 1, 5065, 2, 5069, 2, 5076, 2, 5079, 1, 5094, 2, 5128, 2], [S, 15151, 25180, 25072, 15173, 2,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0], [S, 15043, 25045, 25065, 15076, 2, 5128, 2, 5151, 2, 5180, 1, 5072, 1, 5173, 1,     , 0], [S, 15015, 25019, 45033, 25035, 2, 5037, 4, 5052, 2, 5055, 2, 5060, 2, 5070, 2, 5074, 3], [S, 15078, 45081, 25085, 35090, 2, 5091, 4, 5093, 3, 5095, 4, 5129, 4, 5144, 4, 5165, 2], [S, 15303, 25169, 25170, 25171, 2, 5177, 4, 5016, 4, 5089, 3, 5136, 2, 5011, 3, 5046, 2], [S, 15145, 35096, 45154, 45162, 4, 5163, 3, 5164, 2, 5192, 2, 5150, 2, 5175, 2,     , 0], [S, 15015, 25019, 55033, 25037, 5, 5060, 2, 5070, 1, 5074, 3, 5078, 5, 5085, 2, 5090, 2], [S, 15091, 55093, 25095, 35129, 6, 5144, 5, 5165, 2, 5169, 2, 5170, 2, 5171, 2, 5177, 5], [S, 15178, 25016, 45089, 25136, 2, 5011, 2, 5046, 2, 5145, 2, 5096, 5, 5154, 3, 5162, 3], [S, 15163, 25164, 25192, 25175, 1,     , 0,     , 0,     , 0,     , 0,     , 0,     , 0]]')
		else:
			assert_p('Table1', 'Content', '[[S, 15, 15, 15, 19, 35, 33, 13337, 207800015037, 15052, 15055, 15060, 25070, 15074, 1], [S, 15, 78, 15, 81, 15, 85, 15090, 15091, 15093, 15095, 15129, 15144, 15165, 1], [S, 15, 303, 15, 169, 15, 170, 15171, 15177, 15016, 15089, 25136, 15011, 15046, 1], [S, 15, 145, 15, 96, 25, 154, 15162, 15163, 15164, 15192, 15150, 15175, 1, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15079, 15094, 15128, 1], [S, 15, 151, 15, 180, 15, 72, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15, 15, 15, 19, 25, 37, 23533, 475100015070, 15078, 15093, 15095, 15129, 15144, 1], [S, 15, 165, 15, 170, 15, 171, 15016, 25089, 25136, 15011, 15046, 15096, 15154, 1], [S, 15, 162, 15, 163, 15, 164, 15192, 15175, 1, 0, 0, 0, 0, 0], [S, 15, 83, 15, 133, 15, 155, 13584, 475100015166, 15167, 15049, 15139, 15002, 15027, 1], [S, 15, 38, 15, 140, 15, 174, 15184, 1, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 13533, 475100015069, 15076, 15094, 15128, 15151, 15180, 1], [S, 15, 72, 15, 173, 1, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15, 180, 1, , 0, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15, 180, 1, , 0, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 15, 15, 19, 15, 33, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15, 95, 15, 129, 15, 144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15, 46, 15, 145, 15, 96, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15, 15, 15, 19, 15, 33, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15, 95, 15, 129, 15, 144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15, 46, 15, 145, 15, 96, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15, 40, 15, 20, 15, 83, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15, 38, 15, 126, 15, 140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15, 40, 15, 20, 15, 83, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15, 38, 15, 126, 15, 140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15, 15, 25, 19, 65, 33, 25035, 25037, 65052, 25055, 25060, 25070, 15074, 6], [S, 15, 78, 65, 81, 15, 85, 45090, 25091, 55093, 35095, 35129, 65144, 35165, 1], [S, 15, 303, 15, 169, 45, 170, 25171, 25177, 55178, 35016, 25089, 35136, 15011, 1], [S, 15, 46, 35, 145, 45, 96, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15, 15, 25, 19, 75, 33, 25035, 25037, 65052, 25055, 25060, 25070, 25074, 6], [S, 15, 78, 75, 81, 25, 85, 45090, 25091, 65093, 35095, 35129, 65144, 35165, 2], [S, 15, 303, 25, 169, 45, 170, 25171, 25177, 65178, 35016, 25089, 35136, 25011, 2], [S, 15, 46, 35, 145, 45, 96, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15, 19, 55, 37, 55, 74, 55078, 55085, 45091, 55093, 35095, 35129, 55144, 3], [S, 15, 169, 35, 177, 65, 178, 35089, 35046, 35145, 45096, 55154, 45162, 45163, 3], [S, 15, 40, 25, 20, 25, 59, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15, 166, 35, 167, 25, 48, 25049, 25139, 25143, 25003, 25002, 25027, 25038, 2], [S, 15, 73, 25, 126, 25, 140, 25174, 35184, 2, 0, 0, 0, 0, 0], [S, 15, 40, 25, 20, 25, 59, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15, 166, 25, 167, 25, 48, 15049, 15139, 25143, 25003, 25010, 15062, 15138, 1], [S, 15, 141, 15, 2, 25, 27, 25038, 25073, 25126, 25140, 25174, 25184, 2, 0], [S, 15, 40, 15, 20, 15, 68, 15083, 15084, 15133, 15135, 15155, 15156, 15166, 2], [S, 15, 167, 15, 48, 15, 49, 15139, 15143, 15002, 15027, 25038, 25073, 15126, 1], [S, 15, 140, 15, 174, 25, 184, 1, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 25, 43, 35, 45, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15, 151, 25, 180, 15, 72, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 25, 45, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15, 151, 25, 180, 25, 72, 15173, 2, 0, 0, 0, 0, 0, 0], [S, 15, 43, 25, 45, 25, 65, 15076, 25128, 25151, 25180, 15072, 15173, 1, 0], [S, 15, 15, 25, 19, 45, 33, 25035, 25037, 45052, 25055, 25060, 25070, 25074, 3], [S, 15, 78, 45, 81, 25, 85, 35090, 25091, 45093, 35095, 45129, 45144, 45165, 2], [S, 15, 303, 25, 169, 25, 170, 25171, 25177, 45016, 45089, 35136, 25011, 35046, 2], [S, 15, 145, 35, 96, 45, 154, 45162, 45163, 35164, 25192, 25150, 25175, 2, 0], [S, 15, 15, 25, 19, 55, 33, 25037, 55060, 25070, 15074, 35078, 55085, 25090, 2], [S, 15, 91, 55, 93, 25, 95, 35129, 65144, 55165, 25169, 25170, 25171, 25177, 5], [S, 15, 178, 25, 16, 45, 89, 25136, 25011, 25046, 25145, 25096, 55154, 35162, 3], [S, 15, 163, 25, 164, 25, 192, 25175, 1, 0, 0, 0, 0, 0, 0]]')
			assert_p('Table1', 'Content', '[[S, 15, 15, 15, 19, 35, 33, 13337, 207800015037, 15052, 15055, 15060, 25070, 15074, 1], [S, 15, 78, 15, 81, 15, 85, 15090, 15091, 15093, 15095, 15129, 15144, 15165, 1], [S, 15, 303, 15, 169, 15, 170, 15171, 15177, 15016, 15089, 25136, 15011, 15046, 1], [S, 15, 145, 15, 96, 25, 154, 15162, 15163, 15164, 15192, 15150, 15175, 1, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15079, 15094, 15128, 1], [S, 15, 151, 15, 180, 15, 72, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15, 15, 15, 19, 25, 37, 23533, 475100015070, 15078, 15093, 15095, 15129, 15144, 1], [S, 15, 165, 15, 170, 15, 171, 15016, 25089, 25136, 15011, 15046, 15096, 15154, 1], [S, 15, 162, 15, 163, 15, 164, 15192, 15175, 1, 0, 0, 0, 0, 0], [S, 15, 83, 15, 133, 15, 155, 13584, 475100015166, 15167, 15049, 15139, 15002, 15027, 1], [S, 15, 38, 15, 140, 15, 174, 15184, 1, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 13533, 475100015069, 15076, 15094, 15128, 15151, 15180, 1], [S, 15, 72, 15, 173, 1, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15, 180, 1, , 0, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 15, 45, 15057, 15065, 15069, 15076, 15094, 15128, 15151, 1], [S, 15, 180, 1, , 0, , 0, 0, 0, 0, 0, 0, 0, 0], [S, 15, 15, 15, 19, 15, 33, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15, 95, 15, 129, 15, 144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15, 46, 15, 145, 15, 96, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15, 15, 15, 19, 15, 33, 15037, 15060, 15074, 15078, 15085, 15091, 15093, 1], [S, 15, 95, 15, 129, 15, 144, 15169, 15170, 15171, 15177, 15016, 15089, 15136, 1], [S, 15, 46, 15, 145, 15, 96, 15154, 15162, 15164, 15192, 15175, 1, 0, 0], [S, 15, 40, 15, 20, 15, 83, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15, 38, 15, 126, 15, 140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15, 40, 15, 20, 15, 83, 15133, 15135, 15155, 15166, 15139, 15002, 15027, 1], [S, 15, 38, 15, 126, 15, 140, 15174, 15184, 1, 0, 0, 0, 0, 0], [S, 15, 15, 25, 19, 65, 33, 25035, 25037, 65052, 25055, 25060, 25070, 15074, 6], [S, 15, 78, 65, 81, 15, 85, 45090, 25091, 55093, 35095, 35129, 65144, 35165, 1], [S, 15, 303, 15, 169, 45, 170, 25171, 25177, 55178, 35016, 25089, 35136, 15011, 1], [S, 15, 46, 35, 145, 45, 96, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15, 15, 25, 19, 75, 33, 25035, 25037, 65052, 25055, 25060, 25070, 25074, 6], [S, 15, 78, 75, 81, 25, 85, 45090, 25091, 65093, 35095, 35129, 65144, 35165, 2], [S, 15, 303, 25, 169, 45, 170, 25171, 25177, 65178, 35016, 25089, 35136, 25011, 2], [S, 15, 46, 35, 145, 45, 96, 65154, 55162, 55163, 35164, 25192, 25150, 25175, 2], [S, 15, 19, 55, 37, 55, 74, 55078, 55085, 45091, 55093, 35095, 35129, 55144, 3], [S, 15, 169, 35, 177, 65, 178, 35089, 35046, 35145, 45096, 55154, 45162, 45163, 3], [S, 15, 40, 25, 20, 25, 59, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15, 166, 35, 167, 25, 48, 25049, 25139, 25143, 25003, 25002, 25027, 25038, 2], [S, 15, 73, 25, 126, 25, 140, 25174, 35184, 2, 0, 0, 0, 0, 0], [S, 15, 40, 25, 20, 25, 59, 15068, 15083, 15084, 25133, 25135, 25155, 25156, 2], [S, 15, 166, 25, 167, 25, 48, 15049, 15139, 25143, 25003, 25010, 15062, 15138, 1], [S, 15, 141, 15, 2, 25, 27, 25038, 25073, 25126, 25140, 25174, 25184, 2, 0], [S, 15, 40, 15, 20, 15, 68, 15083, 15084, 15133, 15135, 15155, 15156, 15166, 2], [S, 15, 167, 15, 48, 15, 49, 15139, 15143, 15002, 15027, 25038, 25073, 15126, 1], [S, 15, 140, 15, 174, 25, 184, 1, 0, 0, 0, 0, 0, 0, 0], [S, 15, 36, 25, 43, 35, 45, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15, 151, 25, 180, 15, 72, 15173, 1, 0, 0, 0, 0, 0, 0], [S, 15, 36, 15, 43, 25, 45, 25057, 15065, 25069, 25076, 25079, 15094, 25128, 2], [S, 15, 151, 25, 180, 25, 72, 15173, 2, 0, 0, 0, 0, 0, 0], [S, 15, 43, 25, 45, 25, 65, 15076, 25128, 25151, 25180, 15072, 15173, 1, 0], [S, 15, 15, 25, 19, 45, 33, 25035, 25037, 45052, 25055, 25060, 25070, 25074, 3], [S, 15, 78, 45, 81, 25, 85, 35090, 25091, 45093, 35095, 45129, 45144, 45165, 2], [S, 15, 303, 25, 169, 25, 170, 25171, 25177, 45016, 45089, 35136, 25011, 35046, 2], [S, 15, 145, 35, 96, 45, 154, 45162, 45163, 35164, 25192, 25150, 25175, 2, 0], [S, 15, 15, 25, 19, 55, 33, 25037, 55060, 25070, 15074, 35078, 55085, 25090, 2], [S, 15, 91, 55, 93, 25, 95, 35129, 65144, 55165, 25169, 25170, 25171, 25177, 5], [S, 15, 178, 25, 16, 45, 89, 25136, 25011, 25046, 25145, 25096, 55154, 35162, 3], [S, 15, 163, 25, 164, 25, 192, 25175, 1, 0, 0, 0, 0, 0, 0]]')
		select('Table1', 'cell:B,2(15)')
		click('Right')
		select('TabbedPane', '')
		select('TextField', 'Wizard_AmsPo')
		click('Right')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
#		click('MetalInternalFrameTitlePane', 674, 1)
	close()

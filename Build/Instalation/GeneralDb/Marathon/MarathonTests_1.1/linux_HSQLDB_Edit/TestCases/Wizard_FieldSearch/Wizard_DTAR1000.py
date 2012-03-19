useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		click('Layout Wizard')
		click('Right')
		select('TabbedPane', '')
		assert_p('TextField1', 'Text', 'cp037')
		if commonBits.isVersion89():
			assert_p('TextField', 'Text', '')
		else:
			assert_p('TextField', 'Text', '100')
		assert_p('EditorPane', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    <h3>
      File Structure
    </h3>
    <p>
      This screen lets you select the File structure.<br>For Standard Windows 
      / Unix files use <b>Text IO</b>.<br>For Fixed width files, You can click 
      on the Start of the second record to set the length.
    </p>
  </body>
</html>
''')
		click('Right')
		select('TabbedPane', '')
		if commonBits.isNimbusLook():
			assert_p('Table', 'Background', 'DerivedColor(color=255,255,255 parent=nimbusLightBackground offsets=0.0,0.0,0.0,0 pColor=255,255,255')
		else:
			assert_p('Table', 'Background', '[r=255,g=255,b=255]')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[, 1, 2, 35, 0, true], [, 3, 2, 35, 0, true], [, 5, 2, 0, 0, true], [, 7, 48, 0, 0, true], [, 55, 6, 0, 0, true]]')
		assert_p('Table1', 'Content', '[[1, 20, V , Geelong                                         , NYNNNN], [2, 40, Q , Coffs Harbour                                   , NYNNNN], [3, 40, Q , Mackay                                          , NYNNNN], [4, 20, V , Ballarat                                        , NYNNNN], [5, 20, V , Albury                                          , NYNNNN], [6, 20, V , Wodonga                                         , NYNNNN], [7, 20, V , Shepparton                                      , NYNNNN], [8, 20, V , Bendigo                                         , NYNNNN], [9, 50, S , Centrepoint                                     , NYNNNN], [10, 40, Q , Earlville                                       , NYNNNN], [11, 10, N , Cessnock                                        , NYNNNN], [12, 20, V , Ringwood                                        , NYNNNN], [15, 10, N , Bankstown                                       , NYNNNN], [16, 10, N , Glendale                                        , NYNNNN], [19, 10, N , Penrith                                         , NYNNNN], [20, 40, Q , Indooroopilly                                   , NYNNNN], [21, 50, S , Tea Tree Plaza                                  , NYNNNN], [23, 20, V , Warnambool                                      , NYNNNN], [24, 50, S , Newton                                          , NYNNNN], [25, 50, S , Fulham Gardens                                  , NYNNNN], [26, 50, S , Edwardstown                                     , NYNNNN], [27, 40, Q , Runaway Bay                                     , NYNNNN], [28, 20, V , Launceston                                      , NYNNNN], [30, 20, V , Epping                                          , NYNNNN], [32, 20, V , Camberwell                                      , NYNNNN], [33, 10, Y , Blacktown (Closed)                              , NYNNNN], [35, 10, N , Rockdale                                        , NYNNNN], [36, 60, W , Fremantle                                       , NYNNNN], [21, 10, N , Miranda                                         , NYNNNN], [38, 40, Q , Maroochydore                                    , NYNNNN], [39, 20, V , Colac                                           , NYNNNN], [40, 40, Q , Tweed Heads                                     , NYNNNN], [42, 20, V , Reservoir                                       , NYNNNN], [43, 60, W , Morley                                          , NYNNNN], [44, 50, S , Mt Gambier                                      , NYNNNN], [45, 60, W , Carousel                                        , NYNNNN], [46, 10, N , Dubbo                                           , NYNNNN], [47, 50, S , Sefton Park                                     , NYNNNN], [48, 40, Q , Bundaberg                                       , NYNNNN], [49, 40, Q , Rockhampton                                     , NYNNNN], [52, 10, N , Eastwood                                        , NYNNNN], [54, 20, V , Highpoint City                                  , NYNNNN], [55, 10, N , Leichhardt                                      , NYNNNN], [57, 60, W , Victoria Park                                   , NYNNNN], [58, 20, V , Knox City                                       , NYNNNN], [59, 40, Q , Buranda                                         , NYNNNN], [60, 10, N , St Marys                                        , NYNNNN], [61, 20, V , Waverley Gardens                                , NYNNNN], [62, 40, Q , Castletown                                      , NYNNNN], [64, 20, V , Hoppers Crossing                                , NYNNNN], [65, 60, W , Innaloo                                         , NYNNNN], [67, 20, V , Morwell Mid Valley                              , NYNNNN], [68, 40, Q , Mt Gravatt                                      , NYNNNN], [69, 60, W , Midland                                         , NYNNNN], [70, 10, N , Bass Hill                                       , NYNNNN], [72, 60, W , Geraldton                                       , NYNNNN], [73, 40, Q , Southport                                       , NYNNNN], [74, 10, N , Campbelltown                                    , NYNNNN], [75, 20, V , Sale                                            , NYNNNN], [76, 60, W , Bull Creek                                      , NYNNNN]]')
		select('Table', 'store', 'Field Name,0')
		select('Table', 'region', 'Field Name,1')
		select('Table', 'cell:Field Name,1()')
		keystroke('Table', 'Down', 'Field Name,1')
		select('Table', 'StateChar', 'Field Name,2')
		select('Table', 'cell:Field Name,2()')
		keystroke('Table', 'Down', 'Field Name,2')
		select('Table', 'name', 'Field Name,3')
		select('Table', 'cell:Field Name,3()')
		keystroke('Table', 'Down', 'Field Name,3')
		select('Table', 'flags', 'Field Name,4')
		select('Table', 'cell:Field Name,1(region)')
		assert_p('Table', 'Content', '[[store, 1, 2, 35, 0, true], [region, 3, 2, 35, 0, true], [StateChar, 5, 2, 0, 0, true], [name, 7, 48, 0, 0, true], [flags, 55, 6, 0, 0, true]]')
		select('Table', 'cell:Field Name,1(region)')
		assert_p('Table1', 'Content', '[[1, 20, V , Geelong                                         , NYNNNN], [2, 40, Q , Coffs Harbour                                   , NYNNNN], [3, 40, Q , Mackay                                          , NYNNNN], [4, 20, V , Ballarat                                        , NYNNNN], [5, 20, V , Albury                                          , NYNNNN], [6, 20, V , Wodonga                                         , NYNNNN], [7, 20, V , Shepparton                                      , NYNNNN], [8, 20, V , Bendigo                                         , NYNNNN], [9, 50, S , Centrepoint                                     , NYNNNN], [10, 40, Q , Earlville                                       , NYNNNN], [11, 10, N , Cessnock                                        , NYNNNN], [12, 20, V , Ringwood                                        , NYNNNN], [15, 10, N , Bankstown                                       , NYNNNN], [16, 10, N , Glendale                                        , NYNNNN], [19, 10, N , Penrith                                         , NYNNNN], [20, 40, Q , Indooroopilly                                   , NYNNNN], [21, 50, S , Tea Tree Plaza                                  , NYNNNN], [23, 20, V , Warnambool                                      , NYNNNN], [24, 50, S , Newton                                          , NYNNNN], [25, 50, S , Fulham Gardens                                  , NYNNNN], [26, 50, S , Edwardstown                                     , NYNNNN], [27, 40, Q , Runaway Bay                                     , NYNNNN], [28, 20, V , Launceston                                      , NYNNNN], [30, 20, V , Epping                                          , NYNNNN], [32, 20, V , Camberwell                                      , NYNNNN], [33, 10, Y , Blacktown (Closed)                              , NYNNNN], [35, 10, N , Rockdale                                        , NYNNNN], [36, 60, W , Fremantle                                       , NYNNNN], [21, 10, N , Miranda                                         , NYNNNN], [38, 40, Q , Maroochydore                                    , NYNNNN], [39, 20, V , Colac                                           , NYNNNN], [40, 40, Q , Tweed Heads                                     , NYNNNN], [42, 20, V , Reservoir                                       , NYNNNN], [43, 60, W , Morley                                          , NYNNNN], [44, 50, S , Mt Gambier                                      , NYNNNN], [45, 60, W , Carousel                                        , NYNNNN], [46, 10, N , Dubbo                                           , NYNNNN], [47, 50, S , Sefton Park                                     , NYNNNN], [48, 40, Q , Bundaberg                                       , NYNNNN], [49, 40, Q , Rockhampton                                     , NYNNNN], [52, 10, N , Eastwood                                        , NYNNNN], [54, 20, V , Highpoint City                                  , NYNNNN], [55, 10, N , Leichhardt                                      , NYNNNN], [57, 60, W , Victoria Park                                   , NYNNNN], [58, 20, V , Knox City                                       , NYNNNN], [59, 40, Q , Buranda                                         , NYNNNN], [60, 10, N , St Marys                                        , NYNNNN], [61, 20, V , Waverley Gardens                                , NYNNNN], [62, 40, Q , Castletown                                      , NYNNNN], [64, 20, V , Hoppers Crossing                                , NYNNNN], [65, 60, W , Innaloo                                         , NYNNNN], [67, 20, V , Morwell Mid Valley                              , NYNNNN], [68, 40, Q , Mt Gravatt                                      , NYNNNN], [69, 60, W , Midland                                         , NYNNNN], [70, 10, N , Bass Hill                                       , NYNNNN], [72, 60, W , Geraldton                                       , NYNNNN], [73, 40, Q , Southport                                       , NYNNNN], [74, 10, N , Campbelltown                                    , NYNNNN], [75, 20, V , Sale                                            , NYNNNN], [76, 60, W , Bull Creek                                      , NYNNNN]]')
		select('Table', 'cell:Field Name,1(region)')
		click('Right')
		select('TabbedPane', '')
		select('TextField', 'Wizard_DTAR1000')
		click('Right')
		select('Table', 'rows:[2,3,4,5,6,7,8,9,10],columns:[5 - 2|StateChar]')
		select_menu('View>>Table View #{Selected Records#}')
#		select('Table2', 'rows:[2,3,4,5,6,7,8,9,10],columns:[5 - 2|StateChar]')
		assert_p('Table', 'Content', '[[3, 40, Q, Mackay, NYNNNN], [4, 20, V, Ballarat, NYNNNN], [5, 20, V, Albury, NYNNNN], [6, 20, V, Wodonga, NYNNNN], [7, 20, V, Shepparton, NYNNNN], [8, 20, V, Bendigo, NYNNNN], [9, 50, S, Centrepoint, NYNNNN], [10, 40, Q, Earlville, NYNNNN], [11, 10, N, Cessnock, NYNNNN]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('Table', 'rows:[2,3,4,5,6,7,8,9,10],columns:[5 - 2|StateChar]')
#		select('Table', 'rows:[2,3,4,5,6,7,8,9,10],columns:[5 - 2|StateChar]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
##		select('ComboBox1', 'Mainframe')
		select('ComboBox2', 'DTAR1000 VB')
		click('Edit1')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'Wizard_DTAR1000')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Delete3')

		if window('Delete: Wizard_DTAR1000'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

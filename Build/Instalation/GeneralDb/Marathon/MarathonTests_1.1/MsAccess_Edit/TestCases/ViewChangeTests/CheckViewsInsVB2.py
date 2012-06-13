useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_20'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		commonBits.setRecordLayout(select, 'xDTAR1000 VB')

		click('Edit1')
		select('Table', 'rows:[15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],columns:[3 - 2|REGION-NO]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],columns:[3 - 2|REGION-NO]')
		assert_p('Table', 'Content', '[[20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N]]')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: ')
		select('Table', 'rows:[15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],columns:[3 - 2|REGION-NO]')
		select('Table', 'cell:5 - 50|STORE-NAME,1(Q Coffs Harbour)')
		click('New1')
		select('Table', '1', 'Data,8')
		select('Table', '1', 'Data,0')
		select('Table', '1', 'Data,1')
		select('Table', '1', 'Data,2')
		select('Table', '1', 'Data,3')
		select('Table', '1', 'Data,4')
		select('Table', '1', 'Data,5')
		select('Table', '1', 'Data,6')
		select('Table', '1', 'Data,7')
		select('Table', '1', 'Data,8')
		select('Table', 'cell:Data,8()')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: 1')
		assert_p('Table', 'Content', '[[20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N]]')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: 1')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: ')
		select('Table', 'cell:5 - 50|STORE-NAME,3(Q Mackay)')
		click('New1')
		select('Table', '2', 'Data,8')
		select('Table', '2', 'Data,0')
		select('Table', '2', 'Data,1')
		select('Table', '2', 'Data,2')
		select('Table', '2', 'Data,3')
		select('Table', '2', 'Data,4')
		select('Table', '2', 'Data,5')
		select('Table', '2', 'Data,6')
		select('Table', '2', 'Data,7')
		select('Table', '2', 'Data,8')
		select('Table', 'cell:Data,5(2)')
		click('New1')
		select('Table', '3', 'Data,8')
		select('Table', '3', 'Data,0')
		select('Table', '3', 'Data,1')
		select('Table', '3', 'Data,2')
		select('Table', '3', 'Data,3')
		select('Table', '3', 'Data,4')
		select('Table', '3', 'Data,5')
		select('Table', '3', 'Data,6')
		select('Table', '3', 'Data,7')
		select('Table', '3', 'Data,8')
		select('Table', 'cell:Data,2(3)')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: 1')
##		select('Table2', 'cell:Data,2(3)')
		assert_p('Table', 'Content', '[[20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N]]')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: ')
		select('Table', 'cell:5 - 50|STORE-NAME,6(V Ballarat)')
		click('New1')
		select('Table', '4', 'Data,8')
		select('Table', '4', 'Data,0')
		select('Table', '4', 'Data,1')
		select('Table', '4', 'Data,2')
		select('Table', '4', 'Data,3')
		select('Table', '4', 'Data,4')
		select('Table', '4', 'Data,5')
		select('Table', '4', 'Data,6')
		select('Table', '4', 'Data,7')
		select('Table', '4', 'Data,8')
		select('Table', 'cell:Data,6(4)')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: ')
##		select('Table2', 'cell:Data,6(4)')
		assert_p('Table', 'Content', '[[1, 20, V Geelong, N, Y, N, N, N, N], [2, 40, Q Coffs Harbour, N, Y, N, N, N, N], [1, 1, 1, 1, 1, 1, 1, 1, 1], [3, 40, Q Mackay, N, Y, N, N, N, N], [2, 2, 2, 2, 2, 2, 2, 2, 2], [3, 3, 3, 3, 3, 3, 3, 3, 3], [4, 20, V Ballarat, N, Y, N, N, N, N], [4, 4, 4, 4, 4, 4, 4, 4, 4], [5, 20, V Albury, N, Y, N, N, N, N], [6, 20, V Wodonga, N, Y, N, N, N, N], [7, 20, V Shepparton, N, Y, N, N, N, N], [8, 20, V Bendigo, N, Y, N, N, N, N], [9, 50, S Centrepoint, N, Y, N, N, N, N], [10, 40, Q Earlville, N, Y, N, N, N, N], [11, 10, N Cessnock, N, Y, N, N, N, N], [12, 20, V Ringwood, N, Y, N, N, N, N], [15, 10, N Bankstown, N, Y, N, N, N, N], [16, 10, N Glendale, N, Y, N, N, N, N], [19, 10, N Penrith, N, Y, N, N, N, N], [20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N], [40, 40, Q Tweed Heads, N, Y, N, N, N, N], [42, 20, V Reservoir, N, Y, N, N, N, N], [43, 60, W Morley, N, Y, N, N, N, N], [44, 50, S Mt Gambier, N, Y, N, N, N, N], [45, 60, W Carousel, N, Y, N, N, N, N], [46, 10, N Dubbo, N, Y, N, N, N, N], [47, 50, S Sefton Park, N, Y, N, N, N, N], [48, 40, Q Bundaberg, N, Y, N, N, N, N], [49, 40, Q Rockhampton, N, Y, N, N, N, N], [52, 10, N Eastwood, N, Y, N, N, N, N], [54, 20, V Highpoint City, N, Y, N, N, N, N], [55, 10, N Leichhardt, N, Y, N, N, N, N], [57, 60, W Victoria Park, N, Y, N, N, N, N], [58, 20, V Knox City, N, Y, N, N, N, N], [59, 40, Q Buranda, N, Y, N, N, N, N], [60, 10, N St Marys, N, Y, N, N, N, N], [61, 20, V Waverley Gardens, N, Y, N, N, N, N], [62, 40, Q Castletown, N, Y, N, N, N, N], [64, 20, V Hoppers Crossing, N, Y, N, N, N, N], [65, 60, W Innaloo, N, Y, N, N, N, N], [67, 20, V Morwell Mid Valley, N, Y, N, N, N, N], [68, 40, Q Mt Gravatt, N, Y, N, N, N, N], [69, 60, W Midland, N, Y, N, N, N, N], [70, 10, N Bass Hill, N, Y, N, N, N, N], [72, 60, W Geraldton, N, Y, N, N, N, N], [73, 40, Q Southport, N, Y, N, N, N, N], [74, 10, N Campbelltown, N, Y, N, N, N, N], [75, 20, V Sale, N, Y, N, N, N, N], [76, 60, W Bull Creek, N, Y, N, N, N, N], [77, 50, S Elizabeth, N, Y, N, N, N, N], [78, 10, N Warringah Mall, N, Y, N, N, N, N], [79, 60, W Stirling, N, Y, N, N, N, N], [80, 20, V Sunshine, N, Y, N, N, N, N], [81, 10, N Ashfield, N, Y, N, N, N, N], [82, 20, V Box Hill, N, Y, N, N, N, N], [83, 40, Q Springwood, N, Y, N, N, N, N], [84, 40, Q Brookside, N, Y, N, N, N, N], [85, 10, N Roselands, N, Y, N, N, N, N], [86, 20, V Southland, N, Y, N, N, N, N], [87, 20, V Chadstone, N, Y, N, N, N, N], [88, 20, V Templestowe, N, Y, N, N, N, N], [89, 10, N Erina, N, Y, N, N, N, N], [90, 10, N Carlingford Court, N, Y, N, N, N, N], [91, 10, N Chatswood, N, Y, N, N, N, N], [92, 20, V Malvern, N, Y, N, N, N, N], [93, 10, N Liverpool, N, Y, N, N, N, N], [94, 60, W Whitford City, N, Y, N, N, N, N], [95, 10, N Eastgarden, N, Y, N, N, N, N], [96, 10, N Canberra Civic, N, Y, N, N, N, N], [97, 20, V Fountain Gate, N, Y, N, N, N, N], [98, 20, V Frankston, N, Y, N, N, N, N], [99, 20, V Glen Waverley, N, Y, N, N, N, N], [100, 20, V Waurn Ponds, N, Y, N, N, N, N], [125, 10, N Mt Druitt, N, Y, N, N, N, N], [126, 40, Q Robina, N, Y, N, N, N, N], [127, 50, S Marion, N, Y, N, N, N, N], [128, 60, W Rockingham, N, Y, N, N, N, N], [129, 10, N Macquarie, N, Y, N, N, N, N], [131, 20, V Melbourne City, N, Y, N, N, N, N], [132, 20, V Glenorchy, N, Y, N, N, N, N], [133, 40, Q Browns Plains, N, Y, N, N, N, N], [134, 50, S Reynella, N, Y, N, N, N, N], [135, 40, Q Capalaba, N, Y, N, N, N, N], [136, 10, N Charlestown, N, Y, N, N, N, N], [137, 20, V Dandenong, N, Y, N, N, N, N], [138, 40, Q Cairns Central, N, Y, N, N, N, N], [139, 40, Q Toowoomba, N, Y, N, N, N, N], [140, 40, Q Pacific Fair, N, Y, N, N, N, N], [141, 40, Q The Willows, N, Y, N, N, N, N], [142, 50, S Hollywood Plaza, N, Y, N, N, N, N], [143, 40, Q Hervey Bay, N, Y, N, N, N, N], [144, 10, N Parramatta, N, Y, N, N, N, N], [145, 10, N Port Macquarie, N, Y, N, N, N, N], [146, 40, Q Palmerston, N, Y, N, N, N, N], [149, 20, V Broadmeadows, N, Y, N, N, N, N], [150, 10, N Cowra, N, Y, N, N, N, N], [151, 60, W Perth, N, Y, N, N, N, N], [152, 20, V Mornington, N, Y, N, N, N, N], [153, 20, V Pakenham, N, Y, N, N, N, N], [154, 10, N Shellharbour, N, Y, N, N, N, N], [155, 40, Q Redbank, N, Y, N, N, N, N], [156, 40, Q Morayfield, N, Y, N, N, N, N], [157, 20, V Chirnside Park, N, Y, N, N, N, N], [158, 20, V Brimbank Central, N, Y, N, N, N, N], [159, 50, S Whyalla, N, Y, N, N, N, N], [160, 20, V Watergardens, N, Y, N, N, N, N], [161, 20, V Hobart, N, Y, N, N, N, N], [162, 10, N Tuggeranong, N, Y, N, N, N, N], [163, 10, N Queanbeyan, N, Y, N, N, N, N], [164, 10, N Tuggerah, N, Y, N, N, N, N], [165, 10, N Hurstville, N, Y, N, N, N, N], [166, 40, Q Chermside, N, Y, N, N, N, N], [167, 40, Q Northlakes, N, Y, N, N, N, N], [168, 20, V Airport West, N, Y, N, N, N, N], [169, 10, N Hornsby, N, Y, N, N, N, N], [170, 10, N Bondi, N, Y, N, N, N, N], [171, 10, N Burwood, N, Y, N, N, N, N], [173, 60, W Bunbury, N, Y, N, N, N, N], [174, 40, Q Carindale, N, Y, N, N, N, N], [175, 10, N Tamworth, N, Y, N, N, N, N], [177, 10, N Castle Hill, N, Y, N, N, N, N], [178, 10, N Blacktown, N, Y, N, N, N, N], [179, 20, V Greensborough, N, Y, N, N, N, N], [180, 60, W Joondalup, N, Y, N, N, N, N], [183, 20, V Swan Hill, N, Y, N, N, N, N], [184, 40, Q Brisbane CBD, N, Y, N, N, N, N], [186, 20, B Narre Warren (Closed), N, Y, N, N, N, N], [188, 10, N Macarthur Square, N, Y, N, N, N, N], [189, 20, B Northland Baby Target, N, Y, N, N, N, N], [191, 50, B Golden Grove, N, Y, N, N, N, N], [192, 10, N Wetherill Park, N, Y, N, N, N, N], [193, 60, W Kalgoorlie, N, Y, N, N, N, N], [194, 50, S Mildura, N, Y, N, N, N, N], [195, 40, Q Bribie Island, N, Y, N, N, N, N], [196, 60, W Meadow Springs, N, Y, N, N, N, N], [197, 50, S Unley, N, Y, N, N, N, N]]')
		assert_p('Table', 'Content', '[[1, 20, V Geelong, N, Y, N, N, N, N], [2, 40, Q Coffs Harbour, N, Y, N, N, N, N], [1, 1, 1, 1, 1, 1, 1, 1, 1], [3, 40, Q Mackay, N, Y, N, N, N, N], [2, 2, 2, 2, 2, 2, 2, 2, 2], [3, 3, 3, 3, 3, 3, 3, 3, 3], [4, 20, V Ballarat, N, Y, N, N, N, N], [4, 4, 4, 4, 4, 4, 4, 4, 4], [5, 20, V Albury, N, Y, N, N, N, N], [6, 20, V Wodonga, N, Y, N, N, N, N], [7, 20, V Shepparton, N, Y, N, N, N, N], [8, 20, V Bendigo, N, Y, N, N, N, N], [9, 50, S Centrepoint, N, Y, N, N, N, N], [10, 40, Q Earlville, N, Y, N, N, N, N], [11, 10, N Cessnock, N, Y, N, N, N, N], [12, 20, V Ringwood, N, Y, N, N, N, N], [15, 10, N Bankstown, N, Y, N, N, N, N], [16, 10, N Glendale, N, Y, N, N, N, N], [19, 10, N Penrith, N, Y, N, N, N, N], [20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N], [40, 40, Q Tweed Heads, N, Y, N, N, N, N], [42, 20, V Reservoir, N, Y, N, N, N, N], [43, 60, W Morley, N, Y, N, N, N, N], [44, 50, S Mt Gambier, N, Y, N, N, N, N], [45, 60, W Carousel, N, Y, N, N, N, N], [46, 10, N Dubbo, N, Y, N, N, N, N], [47, 50, S Sefton Park, N, Y, N, N, N, N], [48, 40, Q Bundaberg, N, Y, N, N, N, N], [49, 40, Q Rockhampton, N, Y, N, N, N, N], [52, 10, N Eastwood, N, Y, N, N, N, N], [54, 20, V Highpoint City, N, Y, N, N, N, N], [55, 10, N Leichhardt, N, Y, N, N, N, N], [57, 60, W Victoria Park, N, Y, N, N, N, N], [58, 20, V Knox City, N, Y, N, N, N, N], [59, 40, Q Buranda, N, Y, N, N, N, N], [60, 10, N St Marys, N, Y, N, N, N, N], [61, 20, V Waverley Gardens, N, Y, N, N, N, N], [62, 40, Q Castletown, N, Y, N, N, N, N], [64, 20, V Hoppers Crossing, N, Y, N, N, N, N], [65, 60, W Innaloo, N, Y, N, N, N, N], [67, 20, V Morwell Mid Valley, N, Y, N, N, N, N], [68, 40, Q Mt Gravatt, N, Y, N, N, N, N], [69, 60, W Midland, N, Y, N, N, N, N], [70, 10, N Bass Hill, N, Y, N, N, N, N], [72, 60, W Geraldton, N, Y, N, N, N, N], [73, 40, Q Southport, N, Y, N, N, N, N], [74, 10, N Campbelltown, N, Y, N, N, N, N], [75, 20, V Sale, N, Y, N, N, N, N], [76, 60, W Bull Creek, N, Y, N, N, N, N], [77, 50, S Elizabeth, N, Y, N, N, N, N], [78, 10, N Warringah Mall, N, Y, N, N, N, N], [79, 60, W Stirling, N, Y, N, N, N, N], [80, 20, V Sunshine, N, Y, N, N, N, N], [81, 10, N Ashfield, N, Y, N, N, N, N], [82, 20, V Box Hill, N, Y, N, N, N, N], [83, 40, Q Springwood, N, Y, N, N, N, N], [84, 40, Q Brookside, N, Y, N, N, N, N], [85, 10, N Roselands, N, Y, N, N, N, N], [86, 20, V Southland, N, Y, N, N, N, N], [87, 20, V Chadstone, N, Y, N, N, N, N], [88, 20, V Templestowe, N, Y, N, N, N, N], [89, 10, N Erina, N, Y, N, N, N, N], [90, 10, N Carlingford Court, N, Y, N, N, N, N], [91, 10, N Chatswood, N, Y, N, N, N, N], [92, 20, V Malvern, N, Y, N, N, N, N], [93, 10, N Liverpool, N, Y, N, N, N, N], [94, 60, W Whitford City, N, Y, N, N, N, N], [95, 10, N Eastgarden, N, Y, N, N, N, N], [96, 10, N Canberra Civic, N, Y, N, N, N, N], [97, 20, V Fountain Gate, N, Y, N, N, N, N], [98, 20, V Frankston, N, Y, N, N, N, N], [99, 20, V Glen Waverley, N, Y, N, N, N, N], [100, 20, V Waurn Ponds, N, Y, N, N, N, N], [125, 10, N Mt Druitt, N, Y, N, N, N, N], [126, 40, Q Robina, N, Y, N, N, N, N], [127, 50, S Marion, N, Y, N, N, N, N], [128, 60, W Rockingham, N, Y, N, N, N, N], [129, 10, N Macquarie, N, Y, N, N, N, N], [131, 20, V Melbourne City, N, Y, N, N, N, N], [132, 20, V Glenorchy, N, Y, N, N, N, N], [133, 40, Q Browns Plains, N, Y, N, N, N, N], [134, 50, S Reynella, N, Y, N, N, N, N], [135, 40, Q Capalaba, N, Y, N, N, N, N], [136, 10, N Charlestown, N, Y, N, N, N, N], [137, 20, V Dandenong, N, Y, N, N, N, N], [138, 40, Q Cairns Central, N, Y, N, N, N, N], [139, 40, Q Toowoomba, N, Y, N, N, N, N], [140, 40, Q Pacific Fair, N, Y, N, N, N, N], [141, 40, Q The Willows, N, Y, N, N, N, N], [142, 50, S Hollywood Plaza, N, Y, N, N, N, N], [143, 40, Q Hervey Bay, N, Y, N, N, N, N], [144, 10, N Parramatta, N, Y, N, N, N, N], [145, 10, N Port Macquarie, N, Y, N, N, N, N], [146, 40, Q Palmerston, N, Y, N, N, N, N], [149, 20, V Broadmeadows, N, Y, N, N, N, N], [150, 10, N Cowra, N, Y, N, N, N, N], [151, 60, W Perth, N, Y, N, N, N, N], [152, 20, V Mornington, N, Y, N, N, N, N], [153, 20, V Pakenham, N, Y, N, N, N, N], [154, 10, N Shellharbour, N, Y, N, N, N, N], [155, 40, Q Redbank, N, Y, N, N, N, N], [156, 40, Q Morayfield, N, Y, N, N, N, N], [157, 20, V Chirnside Park, N, Y, N, N, N, N], [158, 20, V Brimbank Central, N, Y, N, N, N, N], [159, 50, S Whyalla, N, Y, N, N, N, N], [160, 20, V Watergardens, N, Y, N, N, N, N], [161, 20, V Hobart, N, Y, N, N, N, N], [162, 10, N Tuggeranong, N, Y, N, N, N, N], [163, 10, N Queanbeyan, N, Y, N, N, N, N], [164, 10, N Tuggerah, N, Y, N, N, N, N], [165, 10, N Hurstville, N, Y, N, N, N, N], [166, 40, Q Chermside, N, Y, N, N, N, N], [167, 40, Q Northlakes, N, Y, N, N, N, N], [168, 20, V Airport West, N, Y, N, N, N, N], [169, 10, N Hornsby, N, Y, N, N, N, N], [170, 10, N Bondi, N, Y, N, N, N, N], [171, 10, N Burwood, N, Y, N, N, N, N], [173, 60, W Bunbury, N, Y, N, N, N, N], [174, 40, Q Carindale, N, Y, N, N, N, N], [175, 10, N Tamworth, N, Y, N, N, N, N], [177, 10, N Castle Hill, N, Y, N, N, N, N], [178, 10, N Blacktown, N, Y, N, N, N, N], [179, 20, V Greensborough, N, Y, N, N, N, N], [180, 60, W Joondalup, N, Y, N, N, N, N], [183, 20, V Swan Hill, N, Y, N, N, N, N], [184, 40, Q Brisbane CBD, N, Y, N, N, N, N], [186, 20, B Narre Warren (Closed), N, Y, N, N, N, N], [188, 10, N Macarthur Square, N, Y, N, N, N, N], [189, 20, B Northland Baby Target, N, Y, N, N, N, N], [191, 50, B Golden Grove, N, Y, N, N, N, N], [192, 10, N Wetherill Park, N, Y, N, N, N, N], [193, 60, W Kalgoorlie, N, Y, N, N, N, N], [194, 50, S Mildura, N, Y, N, N, N, N], [195, 40, Q Bribie Island, N, Y, N, N, N, N], [196, 60, W Meadow Springs, N, Y, N, N, N, N], [197, 50, S Unley, N, Y, N, N, N, N]]')
		select_menu('Window>>DTAR1000_Store_file_std.bin>>Table: 1')
		assert_p('Table', 'Content', '[[20, 40, Q Indooroopilly, N, Y, N, N, N, N], [21, 50, S Tea Tree Plaza, N, Y, N, N, N, N], [23, 20, V Warnambool, N, Y, N, N, N, N], [24, 50, S Newton, N, Y, N, N, N, N], [25, 50, S Fulham Gardens, N, Y, N, N, N, N], [26, 50, S Edwardstown, N, Y, N, N, N, N], [27, 40, Q Runaway Bay, N, Y, N, N, N, N], [28, 20, V Launceston, N, Y, N, N, N, N], [30, 20, V Epping, N, Y, N, N, N, N], [32, 20, V Camberwell, N, Y, N, N, N, N], [33, 10, Y Blacktown (Closed), N, Y, N, N, N, N], [35, 10, N Rockdale, N, Y, N, N, N, N], [36, 60, W Fremantle, N, Y, N, N, N, N], [21, 10, N Miranda, N, Y, N, N, N, N], [38, 40, Q Maroochydore, N, Y, N, N, N, N], [39, 20, V Colac, N, Y, N, N, N, N]]')

	close()

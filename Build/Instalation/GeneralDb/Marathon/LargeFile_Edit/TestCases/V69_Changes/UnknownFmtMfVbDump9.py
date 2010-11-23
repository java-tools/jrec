useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'MF_VBdump_Test.bin')
		select('ComboBox2', 'Unknown Format')
		commonBits.doEdit(click)

		if window(''):
			select('Table', 'cell:J,1(P)')
			assert_p('Table', 'Content', '[[T, A, R, 5, 0, 1, 5, S, T, B, a, n, k, s, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, a, n, k, s, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , U, n, i, t,  , 2, ,,  , 3, 9, -, 4, 1,  , A, l, l, i, n, g, h, a, m,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , C, o, n, d, e, l, l,  , P, a, r, k,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 2, 0, 0,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 1, 9, S, T, P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 5, 8,  , L, e, l, a, n, d,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 7, 5, 0,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 3, S, T, B, l, a, c, k, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , D, o, c, k,  , 2, ,,  , 1, 1,  , M, e, l, i, s, s, a,  , P, l, a, c, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 1, 4, 8,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 5, S, T, R, o, c, k, d, a, l, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, u, i, l, d, i, n, g,  , B, ,,  ,  , P, o, r, t, s, i, d, e,  , D, C,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, -, 8,  , M, c,  , P, h, e, r, s, o, n,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, o, t, a, n, y,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 0, 1, 9,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 7, S, T, M, i, r, a, n, d, a,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , W, e, s, t, f, i, e, l, d,  , S, h, o, p, p, i, n, g, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , C, n, r, .,  , U, r, u, n, g, a,  , P, d, e,  , &,  , T, h, e,  , K, i, n, g, s, w, a, y,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, i, r, a, n, d, a,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 2, 2, 8,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 5, 2, S, T, E, a, s, t, w, o, o, d,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  , O, f, f, s, i, t, e,  , R, e, s, e, r, v, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 1, 1,  , M, e, l, i, s, s, a,  , P, l, a, c, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 1, 4, 8,  ,  ,  ,  ,  ,  , N, S, W, A]]')
			select('Table', 'cell:J,1(P)')
##			assert_p('BmKeyedComboBox', 'Text', '5')
			assert_p('BmKeyedComboBox', 'Text', 'Mainframe VB Dump: includes Block length')
			commonBits.doSleep()

			click('Go')
			commonBits.doSleep()
		close()

		commonBits.doSleep()
		commonBits.doSleep()


		select_menu('Window>>MF_VBdump_Test.bin>>Table: ')


		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		select('LayoutCombo', 'Full Line')
		select('Table', 'cell:Full Line,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
		select('Table', 'cell:Full Line,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		select('LayoutCombo', 'Hex 1 Line')
		assert_p('Table', 'Text', 'e3c1d9f5f0f1f5e2e3c2819592a2a396a6954040404040404040404040404040404040404040404040404040c2819592a2a396a69540404040404040404040404040404040404040404040404040404040404040e49589a340f26b40f3f960f4f140c1939389958788819440e2a3998585a340404040404040404040c396958485939340d78199924040404040404040404040404040404040404040404040f2f2f0f0404040404040d5e2e6c1', '         +         1|Hex (1 Line),0')
		assert_p('Table', 'Text', 'e3c1d9f5f0f1f9e2e3d785959989a38840404040404040404040404040404040404040404040404040404040d785959989a388404040404040404040404040404040404040404040404040404040404040404040f5f840d3859381958440e2a3998585a3404040404040404040404040404040404040404040404040d785959989a38840404040404040404040404040404040404040404040404040404040f2f7f5f0404040404040d5e2e6c1', '         +         1|Hex (1 Line),1')
		assert_p('Table', 'Text', 'e3c1d9f5f0f3f3e2e3c293818392a396a6954040404040404040404040404040404040404040404040404040d4819981a89695874040404040404040404040404040404040404040404040404040404040404040c496839240f26b40f1f140d4859389a2a28140d79381838540404040404040404040404040404040d4819981a8969587404040404040404040404040404040404040404040404040404040f2f1f4f8404040404040d5e2e6c1', '         +         1|Hex (1 Line),2')
		assert_p('Table', 'Text', 'e3c1d9f5f0f3f5e2e3d996839284819385404040404040404040404040404040404040404040404040404040c2a489938489958740c26b4040d79699a3a289848540c4c340404040404040404040404040404040f260f840d48340d7888599a2969540e2a3998585a340404040404040404040404040404040404040c296a38195a84040404040404040404040404040404040404040404040404040404040f2f0f1f9404040404040d5e2e6c1', '         +         1|Hex (1 Line),3')
		assert_p('Table', 'Text', 'e3c1d9f5f0f3f7e2e3d489998195848140404040404040404040404040404040404040404040404040404040e685a2a3868985938440e288969797899587a396a695404040404040404040404040404040404040c395994b40e499a495878140d78485405040e3888540d2899587a2a681a840404040404040404040d489998195848140404040404040404040404040404040404040404040404040404040f2f2f2f8404040404040d5e2e6c1', '         +         1|Hex (1 Line),4')
		assert_p('Table', 'Text', 'e3c1d9f5f0f5f2e2e3c581a2a3a6969684404040404040404040404040404040404040404040404040404040d4819981a896958740d68686a289a38540d985a28599a58540404040404040404040404040404040f1f140d4859389a2a28140d793818385404040404040404040404040404040404040404040404040d4819981a8969587404040404040404040404040404040404040404040404040404040f2f1f4f8404040404040d5e2e6c1', '         +         1|Hex (1 Line),5')
		select('LayoutCombo', 'Hex 2 Lines (Mainframe Style)')
		assert_p('Table', 'Text', '''T A R 5 0 1 5 S T B a n k s t o w n                                                     B a n k s t o w n                                                               U n i t   2 ,   3 9 - 4 1   A l l i n g h a m   S t r e e t                     C o n d e l l   P a r k                                               2 2 0 0             N S W A 
e3c1d9f5f0f1f5e2e3c2819592a2a396a6954040404040404040404040404040404040404040404040404040c2819592a2a396a69540404040404040404040404040404040404040404040404040404040404040e49589a340f26b40f3f960f4f140c1939389958788819440e2a3998585a340404040404040404040c396958485939340d78199924040404040404040404040404040404040404040404040f2f2f0f0404040404040d5e2e6c1''', '         +         1|Hex (2 Lines),0')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		assert_p('Table', 'Text', '''T A R 5 0 1 9 S T P e n r i t h                                                         P e n r i t h                                                                   5 8   L e l a n d   S t r e e t                                                 P e n r i t h                                                         2 7 5 0             N S W A 
e3c1d9f5f0f1f9e2e3d785959989a38840404040404040404040404040404040404040404040404040404040d785959989a388404040404040404040404040404040404040404040404040404040404040404040f5f840d3859381958440e2a3998585a3404040404040404040404040404040404040404040404040d785959989a38840404040404040404040404040404040404040404040404040404040f2f7f5f0404040404040d5e2e6c1''', '         +         1|Hex (2 Lines),1')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		assert_p('Table', 'Text', '''T A R 5 0 3 3 S T B l a c k t o w n                                                     M a r a y o n g                                                                 D o c k   2 ,   1 1   M e l i s s a   P l a c e                                 M a r a y o n g                                                       2 1 4 8             N S W A 
e3c1d9f5f0f3f3e2e3c293818392a396a6954040404040404040404040404040404040404040404040404040d4819981a89695874040404040404040404040404040404040404040404040404040404040404040c496839240f26b40f1f140d4859389a2a28140d79381838540404040404040404040404040404040d4819981a8969587404040404040404040404040404040404040404040404040404040f2f1f4f8404040404040d5e2e6c1''', '         +         1|Hex (2 Lines),2')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		assert_p('Table', 'Text', '''T A R 5 0 3 5 S T R o c k d a l e                                                       B u i l d i n g   B ,     P o r t s i d e   D C                                 2 - 8   M c   P h e r s o n   S t r e e t                                       B o t a n y                                                           2 0 1 9             N S W A 
e3c1d9f5f0f3f5e2e3d996839284819385404040404040404040404040404040404040404040404040404040c2a489938489958740c26b4040d79699a3a289848540c4c340404040404040404040404040404040f260f840d48340d7888599a2969540e2a3998585a340404040404040404040404040404040404040c296a38195a84040404040404040404040404040404040404040404040404040404040f2f0f1f9404040404040d5e2e6c1''', '         +         1|Hex (2 Lines),3')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
##		assert_p('Table', 'Text', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
##		assert_p('Table', 'Text', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		rightclick('Table', '         +         1|Hex (2 Lines),1')
		select_menu('Edit Record')
##		select('Table1', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
		assert_p('Table', 'Content', '[[Data, 1, 1, TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA, T, e3c1d9f5f0f1f9e2e3d785959989a38840404040404040404040404040404040404040404040404040404040d785959989a388404040404040404040404040404040404040404040404040404040404040404040f5f840d3859381958440e2a3998585a3404040404040404040404040404040404040404040404040d785959989a38840404040404040404040404040404040404040404040404040404040f2f7f5f0404040404040d5e2e6c1]]')
		select('CheckBox', 'false')
		select('Table', 'cell:Hex,0([B@db7e08)')
		assert_p('Table', 'Text', '''TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSW
ecdffffeed8998a84444444444444444444444444444d8998a8444444444444444444444444444444444ff4d898984ea988a444444444444444444444444d8998a84444444444444444444444444444ffff444444dee
3195019237559938000000000000000000000000000075599380000000000000000000000000000000005803531540239553000000000000000000000000755993800000000000000000000000000002750000000526''', 'Hex,0')

##		assert_p('Table', 'Content', '[[Data, 1, 1, TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA, T, [B@fc519b]]')
##		assert_p('Table', 'Content', '[[Data, 1, 1, TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA, T, [B@fc519b]]')
		select('Table', 'cell:Hex,0([B@106ef07)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:         +         1|Hex (2 Lines),1([B@150b06b)')
	close()

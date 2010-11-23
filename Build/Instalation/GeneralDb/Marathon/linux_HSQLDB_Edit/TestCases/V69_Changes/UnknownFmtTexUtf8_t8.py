useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Text_utf8_Test.txt')
		select('ComboBox2', 'Unknown Format')
		commonBits.doEdit(click)

		if window(''):
			select('TextField1', 'utf-8')
##			select('BmKeyedComboBox', '90')
			select('BmKeyedComboBox', 'Text IO (Unicode)')
			select('Table', 'cell:J,1(P)')

			assert_p('Table', 'Content', '[[T, A, R, 5, 0, 1, 5, S, T, B, a, n, k, s, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, a, n, k, s, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , U, n, i, t,  , 2, ,,  , 3, 9, -, 4, 1,  , A, l, l, i, n, g, h, a, m,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , C, o, n, d, e, l, l,  , P, a, r, k,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 2, 0, 0,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 1, 9, S, T, P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 5, 8,  , L, e, l, a, n, d,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , P, e, n, r, i, t, h,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 7, 5, 0,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 3, S, T, B, l, a, c, k, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , D, o, c, k,  , 2, ,,  , 1, 1,  , M, e, l, i, s, s, a,  , P, l, a, c, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 1, 4, 8,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 5, S, T, R, o, c, k, d, a, l, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, u, i, l, d, i, n, g,  , B, ,,  ,  , P, o, r, t, s, i, d, e,  , D, C,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, -, 8,  , M, c,  , P, h, e, r, s, o, n,  , S, t, r, e, e, t,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , B, o, t, a, n, y,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 0, 1, 9,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 3, 7, S, T, M, i, r, a, n, d, a,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , W, e, s, t, f, i, e, l, d,  , S, h, o, p, p, i, n, g, t, o, w, n,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , C, n, r, .,  , U, r, u, n, g, a,  , P, d, e,  , &,  , T, h, e,  , K, i, n, g, s, w, a, y,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, i, r, a, n, d, a,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 2, 2, 8,  ,  ,  ,  ,  ,  , N, S, W, A], [T, A, R, 5, 0, 5, 2, S, T, E, a, s, t, w, o, o, d,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  , O, f, f, s, i, t, e,  , R, e, s, e, r, v, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 1, 1,  , M, e, l, i, s, s, a,  , P, l, a, c, e,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , M, a, r, a, y, o, n, g,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , 2, 1, 4, 8,  ,  ,  ,  ,  ,  , N, S, W, A]]')

			select('Table', 'cell:J,1(P)')
##			assert_p('BmKeyedComboBox', 'Text', '90')
##			assert_p('BmKeyedComboBox', 'Text', '90')
			assert_p('BmKeyedComboBox', 'Text', 'Text IO (Unicode)')
			commonBits.doSleep()

			click('Go')
			#commonBits.doSleep()
		close()

		commonBits.doSleep()

		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		select('LayoutCombo', 'Full Line')
		select('Table', 'cell:Full Line,2(TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
		select('Table', 'cell:Full Line,2(TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA)')
		select('LayoutCombo', 'UnknownFormatRecord')
		select('Table', 'cell:1 - 1|Data,3(TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
	close()

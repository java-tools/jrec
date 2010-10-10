useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
		select('Table', '1', '45 - 40|Loc Addr Ln1,0')
		select('Table', '22', '45 - 40|Loc Addr Ln1,1')
		select('Table', '33', '85 - 40|Loc Addr Ln2,2')
		select('Table', 'cell:125 - 35|Loc Addr Ln3,2()')
		select_menu('File>>Compare with Disk')
##		select('Table1', 'cell:125 - 35|Loc Addr Ln3,2()')
		select('Table', 'cell:Loc Name,0(DC - Taras Ave)')
		assert_p('Table', 'Text', 'DC - Taras Ave', 'Loc Name,0')
		select('Table', 'cell:Loc Name,0(DC - Taras Ave)')
###		assert_p('Table', 'Content', '[[, Old, 1, TAR, 5839, DC, DC - Taras Ave, 1, 30-68 Taras Ave, Altona North, 3025, VIC, A], [, New, 1, , , , , , , , , , ], [, Old, 2, TAR, 5850, DC, VIC West Ad Support, 22, Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [, New, 2, , , , , , , , , , ], [, Old, 3, TAR, 5853, DC, NSW North Sydney Ad Support, , 33, , , , A], [, New, 3, , , , , , , , , , ]]')
		assert_p('Table', 'Content', '[[, Old, 1, TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [, New, 1, , , , , 1, , , , , ], [, Old, 2, TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [, New, 2, , , , , 22, , , , , ], [, Old, 3, TAR, 5853, DC, NSW North Sydney Ad Support, , , , , , A], [, New, 3, , , , , , 33, , , , ]]')
		select('Table', 'cell:Loc Name,0(DC - Taras Ave)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:125 - 35|Loc Addr Ln3,2()')
		select('Table', 'cell:125 - 35|Loc Addr Ln3,2()')

##		if window(r'Save Changes to file: C:\Program Files\RecordEdit\HSQLDB\SampleFiles\Ams_LocDownload_20041228.txt'):
##			click('No')
##			click('No')
##		close()
	close()

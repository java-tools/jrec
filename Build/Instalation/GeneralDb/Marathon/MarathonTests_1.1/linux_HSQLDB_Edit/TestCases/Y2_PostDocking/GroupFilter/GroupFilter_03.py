useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Fields.FieldRelationship_JTbl', '4', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('Contains') + ')')
##		click('ScrollPane$ScrollBar', 10, 33)
		select('Fields.FieldRelationship_JTbl', commonBits.fl('>= (Numeric)'), commonBits.fl('Operator') + ',0')
		select('TabbedPane', commonBits.fl('Group Filter'))
		select('Fields.FieldRelationship_JTbl1', '4', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('Contains') + ')')
##		click('ScrollPane$ScrollBar', 8, 92)
		select('Fields.FieldRelationship_JTbl1', commonBits.fl('>= (Numeric)'), commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Value') + ',1()')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Field') + ',0(0)')
		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('Include') + ',1(true)')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Value') + ',2()')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Field') + ',0(-2147483647)')
		click('ArrowButton')
		select_menu('ams PO Download: Allocation>>Pack Quantity 1')

		commonBits.save2(click)

##		click(commonBits.fl('Save') + '1')
##		select('File name', 'xx4')

		if commonBits.isWindowsLook():
			select('File name', 'xx4')
		else:
			select('File Name', 'xx4')


		commonBits.save(click)
		##click(commonBits.fl('Save'))
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
##		click('MetalInternalFrameTitlePane', 644, 18)
		click('Filter1')
		click(commonBits.fl('Load'))

		if window('Open'):
			##select('FilePane$3', 'xx4')
			##select(commonBits.selectPane(), 'xx4')
			##click('Open')

			if commonBits.isWindowsLook():
				select('File name', 'xx4')
			else:
				select('File Name', 'xx4')

			click('Open')

##			select('FileName', 'xx4')
##			doubleclick('FilePane$3', '5')
		close()

		select('TabbedPane', commonBits.fl('Group Filter'))
		assert_p('Fields.FieldRelationship_JTbl1', 'Content', '[[, , 200004, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('>= (Numeric)') + ', 4], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ]]')

		commonBits.filter2(click)
##		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		assert_p('LineList.FileDisplay_JTbl1', 'Content', '[[D1, 4, 40000000, 1, 48320000, 4561, 50710000, 5, 45400000, 44, 207, 5354, , 4561, 5071, M, .ROSE 24, -6, 607 SHWL], [S1, 5009, 5, 5021, 5, 5025, 4, 5026, 4, 5047, 3, 5077, 2, 5127, 5, 5134, 4, 5142, 2], [S1, 5044, 2, 5071, 2, , 0, , 0, , 0, , 0, , 0, , 0, , 0]]')
	close()

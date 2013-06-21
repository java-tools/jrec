useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Filter.RecordSelection_JTbl', 'false', commonBits.fl('Include') + ',1')
		select('TabbedPane', commonBits.fl('Group Filter'))
		select('Filter.RecordSelection_JTbl1', 'false', commonBits.fl('In Group') + ',1')
##		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('In Group') + ',1(false)')

		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('Contains') + ')')
		select('Fields.FieldRelationship_JTbl1', '>', commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl1', '4', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Value') + ',0()')

		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Field') + ',0(-2147483647)')
		click('ArrowButton')
##		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Field') + ',0(0)')
		select_menu('ams PO Download: Allocation>>Pack Quantity 1')
#		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('Contains') + ')')
#		select('Fields.FieldRelationship_JTbl1', '>', commonBits.fl('Operator') + ',0')
#		select('Fields.FieldRelationship_JTbl1', '4', commonBits.fl('Value') + ',0')
#		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Value') + ',0()')

		commonBits.save2(click)

##		click(commonBits.fl('Save') + '1')
##		select('File Name', 'xx5')
		if commonBits.isWindowsLook():
			select('File name', 'xx5')
		else:
			select('File Name', 'xx5')


		
		commonBits.save(click)
##		click(commonBits.fl('Save'))

		commonBits.filter2(click)
#		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')


##		click('BasicInternalFrameTitlePane$NoFocusButton2')


		select('LineList.FileDisplay_JTbl1', 'cell:15 - 4|DC Number 2,0(1)')
		select('LineList.FileDisplay_JTbl1', 'cell:15 - 4|DC Number 2,0(1)')
		assert_p('LineList.FileDisplay_JTbl1', 'Content', '[[D1, 4, 40000000, 1, 48320000, 4561, 50710000, 5, 45400000, 44, 207, 5354, , 4561, 5071, M, .ROSE 24, -6, 607 SHWL], [S1, 5009, 5, 5021, 5, 5025, 4, 5026, 4, 5047, 3, 5077, 2, 5127, 5, 5134, 4, 5142, 2], [S1, 5044, 2, 5071, 2, , 0, , 0, , 0, , 0, , 0, , 0, , 0]]')
		select('LineList.FileDisplay_JTbl1', 'cell:15 - 4|DC Number 2,0(1)')
		click('DisplayFrame$TabButton', 25, 24)
		select('TabbedPane', 'Table:')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Execute Saved Filter'))
		select('File Name', 'xx5')
		click(commonBits.fl('Run Dialog'))
		select('TabbedPane', commonBits.fl('Group Filter'))
		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')
		assert_p('Filter.RecordSelection_JTbl1', 'Content', '[[ams PO Download: Detail, true, true], [ams PO Download: Header, false, true], [ams PO Download: Allocation, true, true]]')
		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')
		assert_p('Fields.FieldRelationship_JTbl1', 'Content', '[[, , 200004, true, ' + commonBits.fl('First') + ', >, 4], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')


		commonBits.filter2(click)
#		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		assert_p('LineList.FileDisplay_JTbl2', 'Content', '[[D1, 4, 40000000, 1, 48320000, 4561, 50710000, 5, 45400000, 44, 207, 5354, , 4561, 5071, M, .ROSE 24, -6, 607 SHWL], [S1, 5009, 5, 5021, 5, 5025, 4, 5026, 4, 5047, 3, 5077, 2, 5127, 5, 5134, 4, 5142, 2], [S1, 5044, 2, 5071, 2, , 0, , 0, , 0, , 0, , 0, , 0, , 0]]')
		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
##		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		select('LineList.FileDisplay_JTbl2', 'cell:15 - 4|DC Number 2,1(5021)')
		click(commonBits.fl('Table:'))
		select('TabbedPane', 'Table:')
		click('Find1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Filter1')
		click(commonBits.fl('Load'))

		if window('Open'):
			##select('FilePane$3', 'xx5')
			if commonBits.isWindowsLook():
				select('File name', 'xx5')
			else:
				select('File Name', 'xx5')

##			select('File Name', 'xx5')
			click('Open')
		close()

##
		select('TabbedPane', commonBits.fl('Group Filter'))
		assert_p('Filter.RecordSelection_JTbl1', 'Content', '[[ams PO Download: Detail, true, true], [ams PO Download: Header, false, true], [ams PO Download: Allocation, true, true]]')
		assert_p('Fields.FieldRelationship_JTbl1', 'Content', '[[, , 200004, true, ' + commonBits.fl('First') + ', >, 4], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', 0, true, ' + commonBits.fl('First') + ', ' + commonBits.fl('Contains') + ', ]]')

		commonBits.filter2(click)
#		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')


##		click('BasicInternalFrameTitlePane$NoFocusButton2')


		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		click(commonBits.fl('Table:') + '2')
		select('TabbedPane', 'Table:')
		click(commonBits.fl('Table:') + '3')
		select('TabbedPane', 'Table:')
		assert_p('LineList.FileDisplay_JTbl3', 'Content', '[[D1, 4, 40000000, 1, 48320000, 4561, 50710000, 5, 45400000, 44, 207, 5354, , 4561, 5071, M, .ROSE 24, -6, 607 SHWL], [S1, 5009, 5, 5021, 5, 5025, 4, 5026, 4, 5047, 3, 5077, 2, 5127, 5, 5134, 4, 5142, 2], [S1, 5044, 2, 5071, 2, , 0, , 0, , 0, , 0, , 0, , 0, , 0]]')
	close()

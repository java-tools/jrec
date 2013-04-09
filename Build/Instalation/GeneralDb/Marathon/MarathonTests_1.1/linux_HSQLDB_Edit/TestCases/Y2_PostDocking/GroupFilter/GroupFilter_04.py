useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('TabbedPane', commonBits.fl('Group Filter'))
		select('Fields.FieldRelationship_JTbl1', '4', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('Contains') + ')')
##		click('ScrollPane$ScrollBar', 11, 38)
		select('Fields.FieldRelationship_JTbl1', commonBits.fl('>= (Numeric)'), commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Operator') + ',0(' + commonBits.fl('>= (Numeric)') + ')')
		select('Filter.RecordSelection_JTbl1', 'false', commonBits.fl('Include') + ',1')
		select('Filter.RecordSelection_JTbl1', 'false', commonBits.fl('Include') + ',2')
##		select('Filter.RecordSelection_JTbl1', 'cell:' + commonBits.fl('Include') + ',2(false)')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Field') + ',0(0)')
		click('ArrowButton')
		select_menu('ams PO Download: Allocation>>Pack Quantity 1')


		commonBits.filter2(click)
##		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		assert_p('LineList.FileDisplay_JTbl1', 'Content', '[[D1, 44.0000, 148.3200, 0, , 45615071, 2075354, 45615071,  M.ROSE 24-006607 SHWL WRAP CARD]]')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Group by function') + ',0(' + commonBits.fl('First') + ')')
		select('Fields.FieldRelationship_JTbl1', commonBits.fl('Any of'), commonBits.fl('Group by function') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Group by function') + ',0(' + commonBits.fl('Any of') + ')')

		commonBits.filter2(click)
##		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		assert_p('LineList.FileDisplay_JTbl2', 'Content', '[[D1, 48.0000, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD], [D1, 108.0000, 148.3200, 0, , 62225071, 2075350, 65615071,  M.ROSE 24-006607 SHWL WRAP CARD], [D1, 44.0000, 148.3200, 0, , 45615071, 2075354, 45615071,  M.ROSE 24-006607 SHWL WRAP CARD]]')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Group by function') + ',0(' + commonBits.fl('Any of') + ')')
		select('Fields.FieldRelationship_JTbl1', commonBits.fl('Sum'), commonBits.fl('Group by function') + ',0')
		select('Fields.FieldRelationship_JTbl1', 'cell:' + commonBits.fl('Group by function') + ',0(' + commonBits.fl('Sum') + ')')

		commonBits.filter2(click)
##		click(commonBits.fl('Filter') + '1')
##		select('TabbedPane1', 'Table:')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Filter Options'))
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		assert_p('LineList.FileDisplay_JTbl3', 'Content', '[[D1, 48.0000, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD], [D1, 108.0000, 148.3200, 0, , 62225071, 2075350, 65615071,  M.ROSE 24-006607 SHWL WRAP CARD], [D1, 44.0000, 148.3200, 0, , 45615071, 2075354, 45615071,  M.ROSE 24-006607 SHWL WRAP CARD], [D1, 30.0000, 292.6800, 0, , 45846680, 2120736, 45615156,  CONCERTO BLACK LEATHER ANKLE BOOT], [D1, 29.0000, 198.3600, 0, , 45846772, 2121104, 5846772,  ANGEL PINK SYNTHETIC MICRO SUEDE SLOUCH BOOT], [D1, 29.0000, 198.3600, 0, , 45847489, 2121627, 45847489,  ANGEL PINK SYNTHETIC MICRO SUEDE SLOUCH BOOT], [D1, 25.0000, 465.4800, 0, , 45848707, 2179843, 45848707A,  ANGEL PINK SYNTHETIC MICRO SUEDE SLOUCH BOOT], [D1, 24.0000, 112.9700, 0, , 46164790, 2203056, 46164790,  ANGEL PINK SYNTHETIC MICRO SUEDE SLOUCH BOOT], [D1, 23.0000, 112.9700, 0, , 46165360, 2203072, 46165360,  CABERETCARAMEL MICRO SUEDE PEEP TOE], [D1, 22.0000, 125.7600, 0, , 15935759, 2203223, 05935759,  JAZZ DUSTY PINK HEELED PEEP TOE]]')
	close()

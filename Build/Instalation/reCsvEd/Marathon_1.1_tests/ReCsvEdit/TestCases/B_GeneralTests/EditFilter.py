useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'XfdDTAR020')
		doubleclick('FilePane$3', '5')
		click('Edit1')
		click('Filter1')
		select('Filter.FieldSelection_JTbl', 'cell:Include,2(true)')
		select('Filter.FieldRelationship_JTbl', 'cell:Field,0(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', 'Field,0')
		select('Filter.FieldRelationship_JTbl', '59', 'Value,0')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,0(59)')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[64614401, 59, 957, 1, 1.99], [64614401, 59, 957, 1, 1.99], [61664713, 59, 335, 1, 17.99], [61664713, 59, 335, -1, -17.99], [68634752, 59, 410, 1, 8.99], [60614487, 59, 878, 1, 5.95], [63644339, 59, 878, 1, 12.65], [60694698, 59, 620, 1, 3.99], [60664659, 59, 620, 1, 3.99], [62684217, 59, 957, 1, 9.99], [67674686, 59, 929, 1, 3.99], [61684613, 59, 335, 1, 12.99], [64624770, 59, 957, 1, 2.59]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

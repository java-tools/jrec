useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')

		if commonBits.version() == 'MsAccess':
			select('Record Layout_Txt', 'Comma Delimited, names on the first line')
		else:
			select('System_Txt', 'CSV')

		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Update Csv Columns'))

		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',5')
		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',1')
		select('FieldChange_JTbl', 'cell:' + commonBits.fl('Type') + ',1(Number)')
		click(commonBits.fl('Apply'))
		click('Filter1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Fields.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',0')
		select('Fields.FieldRelationship_JTbl', '5.95', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[60614487, 59, 40118, 878, 1, 5.95]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',0(Contains)')
		select('Fields.FieldRelationship_JTbl', '>=', commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',0(>=)')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [62684671, 20, 40118, 685, 1, 69.99], [61664713, 59, 40118, 335, 1, 17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		select('Fields.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',1')
		select('Fields.FieldRelationship_JTbl', '59', commonBits.fl('Value') + ',1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95]]')
	close()

useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
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
		select('Fields.FieldRelationship_JTbl', '<', commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',0(>)')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, -1, -17.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [68654655, 166, 40118, 60, 1, 5.08]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		select('Fields.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',1')
		select('Fields.FieldRelationship_JTbl', '59', commonBits.fl('Value') + ',1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, -1, -17.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')

	close()

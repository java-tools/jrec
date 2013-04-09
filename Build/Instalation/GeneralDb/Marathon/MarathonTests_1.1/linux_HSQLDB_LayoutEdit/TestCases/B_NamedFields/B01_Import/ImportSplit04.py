useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Load Cobol Copybook')
		select('Cobol Copybook_Txt', commonBits.cobolDir() + 'XTAR1000_020.cbl')
		select('Binary Format_Txt', 'Open Cobol Little Endian (Intel)')
		select('System_Txt', 'Other')
		select('Split Copybook_Txt', 'On 01 level')
		select('File Structure_Txt', 'Mainframe VB (rdw based) Binary')
		click('Go')

		if window('Message'):
			assert_p('OptionPane.label', 'Text', 'You should define the Record Selections details (Field - Field Value)and check the File Structure on the Extra sceen')
			click('OK')
		close()

		assert_p('ChildRecordsJTbl', 'Content', '[[, XTAR1000_020-01TAR1000-REC, , , , , ], [, XTAR1000_020-01TAR020-REC, , , , , ]]')
		assert_p('RecordDef.Record Type_Txt', 'Text', 'Group of Binary Records')
		select('TabbedPane', 'Extras')
		assert_p('RcdExtra.File Structure_Txt', 'Text', 'Open Cobol VB')
		select('TabbedPane', 'Child Records')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Child Name,0')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, 00001000-STORE-NO, , 35, 0, 0, , , 00001000-STORE-NO], [3, 2, 000R1000-REGION-NO, , 35, 0, 0, , , 000R1000-REGION-NO], [5, 50, XTAR1000-STORE-NAME, , 0, 0, 0, , , XTAR1000-STORE-NAME], [55, 1, XTAR1000-NEW-STORE, , 0, 0, 0, , , XTAR1000-NEW-STORE], [56, 1, XTAR1000-ACTIVE-STORE, , 0, 0, 0, , , XTAR1000-ACTIVE-STORE], [57, 1, XTAR1000-CLOSED-STORE, , 0, 0, 0, , , XTAR1000-CLOSED-STORE], [58, 1, XTAR1000-DC-TYPE, , 0, 0, 0, , , XTAR1000-DC-TYPE], [59, 1, XTAR1000-SRC-TYPE, , 0, 0, 0, , , XTAR1000-SRC-TYPE], [60, 1, XTAR1000-HO-TYPE, , 0, 0, 0, , , XTAR1000-HO-TYPE]]')
		click('Right')
##		select('TabbedPane1', 'Extras')
##		select('TabbedPane1', 'Extras')
##		select('TabbedPane1', 'Child Records')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Fields')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 8, XTAR020-KEYCODE-NO, , 0, 0, 0, , , XTAR020-KEYCODE-NO], [9, 2, XTAR020-STORE-NO, , 31, 0, 0, , , XTAR020-STORE-NO], [11, 4, XTAR020-DATE, , 31, 0, 0, , , XTAR020-DATE], [15, 2, XTAR020-DEPT-NO, , 31, 0, 0, , , XTAR020-DEPT-NO], [17, 5, XTAR020-QTY-SOLD, , 31, 0, 0, , , XTAR020-QTY-SOLD], [22, 6, XTAR020-SALE-PRICE, , 31, 2, 0, , , XTAR020-SALE-PRICE]]')
	close()

useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Load Copybook')
		select('User Selected Copybook_Txt', commonBits.cobolDir() + 'AmsLocation.cbl')
		select('Binary Format_Txt', 'Mainframe')
		assert_p('Copybook Type_Txt', 'Text', 'Cobol Copybook (DB)')
		select('User Selected Copybook_Txt', commonBits.paramDir() + 'CopyBook' + commonBits.fileSep() +'cb2xml' + commonBits.fileSep() + 'AMSLOCATION.xml')
		select('System_Txt', 'CSV')
		assert_p('Copybook Type_Txt', 'Text', 'cb2xml XML Copybook (DB)')
		select('User Selected Copybook_Txt', commonBits.paramDir() + 'CopyBook' + commonBits.fileSep() +'Csv' + commonBits.fileSep() + 'ams PO Download.Txt')
 
		select('Binary Format_Txt', 'Intel')
		assert_p('Copybook Type_Txt', 'Text', 'RecordEditor Tab Copybook (Tab Seperator)')
		select('User Selected Copybook_Txt', commonBits.xmlCopybookDir() + 'ams PO Download.Xml')
		select('System_Txt', 'Ams')
		assert_p('Copybook Type_Txt', 'Text', 'RecordEditor XML Copybook')
##		select('Binary Format_Txt', 'Mainframe')
##		select('System_Txt', 'CSV')
##		select('Binary Format_Txt', 'Intel')
##		select('System_Txt', 'Ams')
	close()

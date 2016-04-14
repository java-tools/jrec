useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'ProtoTest_Address1.bin')
		click('Proto Search')
##		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'addressbook.proto, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbook.protocomp, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbookSD1_PersonPhoneNo.proto, addressbookSD1_PersonPhoneNo.proto, PhoneNumber], [' + commonBits.stdCopybookDir() + 'feeds.proto, feeds.proto, Feed]]')

##		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'addressbook.proto, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbook.protocomp, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbookSD1_PersonPhoneNo.proto, addressbookSD1_PersonPhoneNo.proto, PhoneNumber], [' + commonBits.stdCopybookDir() + 'feeds.proto, feeds.proto, Feed], [' + commonBits.stdCopybookDir() + 'oneOfTest.proto, oneOfTest.proto, TestOneOf], [' + commonBits.stdCopybookDir() + 'oneOfTest.protocomp, oneOfTest.proto, TestOneOf], [' + commonBits.stdCopybookDir() + 'oneOfTestA.proto, oneOfTestA.proto, TestOneOf]]')
		assert_p('Table1', 'Content', '[[' + commonBits.stdCopybookDir() + 'addressbook.proto, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbook.protocomp, addressbook.proto, AddressBook], [' + commonBits.stdCopybookDir() + 'addressbookSD1_PersonPhoneNo.proto, addressbookSD1_PersonPhoneNo.proto, PhoneNumber], [' + commonBits.stdCopybookDir() + 'feeds.proto, feeds.proto, Feed], [' + commonBits.stdCopybookDir() + 'oneOfTest.proto, oneOfTest.proto, TestOneOf], [' + commonBits.stdCopybookDir() + 'oneOfTest.protocomp, oneOfTest.proto, TestOneOf], [' + commonBits.stdCopybookDir() + 'oneOfTestA.proto, oneOfTestA.proto, TestOneOf], [' + commonBits.stdCopybookDir() + 'StoreSalesMap.proto, StoreSalesMap.proto, Deptartment], [' + commonBits.stdCopybookDir() + 'StoreSalesMap.protocomp, StoreSalesMap.proto, Deptartment]]')



		assert_p('TextField', 'Text', commonBits.sampleDir() + 'ProtoTest_Address1.bin')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileNameTxtFld1', 'Text', commonBits.stdCopybookDir() + 'addressbook.protocomp')
		assert_p('ComboBox2', 'Text', 'addressbook.proto')
		assert_p('ComboBox2', 'Content', '[[addressbook.proto]]')
		assert_p('ComboBox3', 'Text', 'AddressBook')
		assert_p('ComboBox3', 'Content', '[[Person, AddressBook]]')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox', 'Text', 'Single Message')
	close()

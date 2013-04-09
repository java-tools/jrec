useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/tst1Fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		click('Export')
		select('File Name_Txt', commonBits.sampleDir() + 'po/Z_tst1Fuzzy.po')
		select('Edit Output File_Chk', 'true')
		click(commonBits.fl('Save File'))
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')


		click(commonBits.fl('Fuzzy/Blank'))
		select('TabbedPane', 'Fuzzy/Blank')
		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,1(test1)')
		select('TextArea2', 'test1aaa')
##		select('PoList.FileDisplay_JTbl1', 'test3', '2|msgid,3')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
		click(commonBits.fl('Fuzzy/Blank'))
		select('TabbedPane', 'Fuzzy/Blank')
		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
		select('TextArea2', 'test3aa')
#		select('PoList.FileDisplay_JTbl1', 'test3', '3|msgstr,3')
#		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,3(test3)')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,3(test3)')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1aaa, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,3(test3)')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1aaa, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')


		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare with Disk'))
		assert_p('Table', 'Content', '''[[, Old, 3, , test1, test1, fuzzy then no-c-format on separate lines, , , , , 
no-c-format, , , , Y, ], [, New, 3, , test1aaa, , , , , , , , , , , , ], [, Old, 5, , test3, test3, fuzzy then no-c-format on same line, , , , , no-c-format, , , , Y, ], [, New, 5, , test3aa, , , , , , , , , , , , ]]''')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>Z_tst1Fuzzy.po>>' + commonBits.fl('PO List'))
		click('Save')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>tst1Fuzzy.po>>' + commonBits.fl('PO List'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('File_Txt', commonBits.sampleDir() + 'po/Z_tst1Fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1aaa, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
	
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1aaa, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')

		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,4(test3)')
		select('TextArea', 'test3aa')
##		select('PoList.FileDisplay_JTbl', 'test4', '3|msgstr,5')

		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,5(test4)')
		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,5(test4)')
#		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,2(test1)')
##		select('TextArea', 'test2')
#		select('PoList.FileDisplay_JTbl', 'test2', '3|msgstr,3')
#		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,3(test2)')
#		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,3(test2)')

		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,2(test1)')
		select('TextArea', 'test1aa')
#		select('TextArea2', 'test1aa')
###		select('PoList.FileDisplay_JTbl1', 'test3', '2|msgid,3')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,3(test3)')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		select('PoList.FileDisplay_JTbl', 'cell:3|msgstr,3(test3)')
#		select('TextArea2', 'test3aa')

	

		click(commonBits.fl('Fuzzy/Blank'))
		select('TabbedPane', 'Fuzzy/Blank')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1aaa, test1aa, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3aa, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare with Disk'))
		assert_p('Table', 'Content', '''[[, Old, 3, , test1aaa, test1, fuzzy then no-c-format on separate lines, , , , , 
no-c-format, , , , Y, ], [, New, 3, , , test1aa, , , , , , , , , , , ], [, Old, 5, , test3aa, test3, fuzzy then no-c-format on same line, , , , , no-c-format, , , , Y, ], [, New, 5, , , test3aa, , , , , , , , , , , ]]''')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		click('Save')

		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1aaa, test1aa, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3aa, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1aaa, test1aa, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3aa, test3aa, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')

	close()

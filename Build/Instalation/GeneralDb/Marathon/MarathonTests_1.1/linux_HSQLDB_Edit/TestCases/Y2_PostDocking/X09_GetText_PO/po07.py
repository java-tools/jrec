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
		click(commonBits.fl('Save File'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('File_Txt', commonBits.sampleDir() + 'po/Z_tst1Fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
##		select('PoList.FileDisplay_JTbl1', '')

		rightclick('PoList.FileDisplay_JTbl1', '2|msgid,0')
		select_menu(commonBits.fl('Show Column') + '>>fuzzy')

##		select('PoList.FileDisplay_JTbl1', 'cell:13|fuzzy,1(Y)')
		select('PoList.FileDisplay_JTbl1', 'false', '13|fuzzy,1')


		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare with Disk'))
		
##		select('Table', 'cell:msgid,1()')
		assert_p('Table', 'Content', '''[[, Old, 3, , test1, test1, fuzzy then no-c-format on separate lines, , , , , 
no-c-format, , , , Y, ], [, New, 3, , , , , , , , , , , , , , ]]''')
		select('Table', 'cell:msgid,1()')
##		click('MetalInternalFrameTitlePane', 1425, 11)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')
		click('BaseDisplay$HeaderToolTips', '2|msgid')




		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')


		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		select('Table', 'cell:' + commonBits.fl('Show') + ',12(false)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',13(false)')
		click(commonBits.fl('Go'))



		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, , , ], [Red Hat Middleware, LLC., , , Y, ], [test1, test1, fuzzy then no-c-format on separate lines, , ], [test2, test2, no-c-format then fuzzy on separate lines, Y, ], [test3, test3, fuzzy then no-c-format on same line, Y, ], [test4, test4, no-c-format then fuzzy on same line, Y, ], [obsolete1, obsolete1, Simple obsolete entry (obsolete1), , Y], [obsolete2, , Plural obsolete entry (obsolete2), , Y], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), , Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , Y], [plural1, , Plural obsolete entry (plural 1), , ], [convert, omskakel, Test quotes, newlines and multiline., , ], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, , , ], [blank msgstr, , , , ]]')

##		assert_p('PoList.FileDisplay_JTbl', 'Content', '''[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, , , ], [Red Hat Middleware, LLC., , , Y, ], [test1, test1, fuzzy then no-c-format on separate lines, , ], [test2, test2, no-c-format then fuzzy on separate lines, Y, ], [test3, test3, fuzzy then no-c-format on same line, Y, ], [test4, test4, no-c-format then fuzzy on same line, Y, ], [obsolete1, obsolete1, Simple obsolete entry (obsolete1), , Y], [obsolete2, , Plural obsolete entry (obsolete2), , Y], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), , Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , Y], [plural1, , Plural obsolete entry (plural 1), , ], [convert, omskakel, Test quotes, newlines and multiline., , ], [msgid with _
##_ escape
##, msgstr with _
##_ escape
##, , , ], [blank msgstr, , , , ]]''')


		select('PoList.FileDisplay_JTbl', 'cell:13|fuzzy,4()')

		select('PoList.FileDisplay_JTbl', 'false', '13|fuzzy,4')
		##select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')
		select('PoList.FileDisplay_JTbl', 'false', '14|obsolete,7')
		select('PoList.FileDisplay_JTbl', 'false', '14|obsolete,9')
		


		select('Table', 'cell:' + commonBits.fl('Line') + ',6(7)')
##		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')
		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare with Disk'))
##		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')
		assert_p('Table', 'Content', '[[, Old, 3, , test1, test1, fuzzy then no-c-format on separate lines, , , , , \nno-c-format, , , , Y, ], [, New, 3, , , , , , , , , , , , , , ], [, Old, 5, , test3, test3, fuzzy then no-c-format on same line, , , , , no-c-format, , , , Y, ], [, New, 5, , , , , , , , , , , , , , ], [, Old, 8, , obsolete2, , Plural obsolete entry (obsolete2), obsolete2_plural, obsolete2-0, Extracted comment, SomeFile.java:1, , , , , , Y], [, New, 8, , , , , , , , , , , , , , ], [, Old, 10, , <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , , , , , , , , , Y], [, New, 10, , , , , , , , , , , , , , ]]')
		
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')
		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')

		

		select_menu(commonBits.fl('Window') + '>>Z_tst1Fuzzy.po>>' + commonBits.fl('PO List'))
##		select('PoList.FileDisplay_JTbl', 'false', '14|obsolete,6')
##		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6(Y)')
		select('PoList.FileDisplay_JTbl', 'true', '14|obsolete,6')

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, , , ], [Red Hat Middleware, LLC., , , Y, ], [test1, test1, fuzzy then no-c-format on separate lines, , ], [test2, test2, no-c-format then fuzzy on separate lines, Y, ], [test3, test3, fuzzy then no-c-format on same line, , ], [test4, test4, no-c-format then fuzzy on same line, Y, ], [obsolete1, obsolete1, Simple obsolete entry (obsolete1), , Y], [obsolete2, , Plural obsolete entry (obsolete2), , ], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), , Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , ], [plural1, , Plural obsolete entry (plural 1), , ], [convert, omskakel, Test quotes, newlines and multiline., , ], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, , , ], [blank msgstr, , , , ]]')
		

		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6()')
		click('Save')
		click('Save')
		


		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Compare Menu'))
		click('*1')
##		select('Old File_Txt', r'C:\Users\BruceTst2/.RecordEditor/HSQLDB\SampleFiles/po/Z_tst1Fuzzy.po')
		select('New File_Txt', commonBits.sampleDir() + 'po/Z_tst1Fuzzy.po')
		select('Old File_Txt', commonBits.sampleDir() + 'po/tst1Fuzzy.po')


		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')

		

		click(commonBits.fl('Compare'))
		assert_p('Table', 'Content', '''[[, Old, 3, , test1, test1, fuzzy then no-c-format on separate lines, , , , , 
no-c-format, , , , Y, ], [, New, 3, , , , , , , , , , , , , , ], [, Old, 5, , test3, test3, fuzzy then no-c-format on same line, , , , , no-c-format, , , , Y, ], [, New, 5, , , , , , , , , , , , , , ], [, Old, 7, , obsolete1, obsolete1, Simple obsolete entry (obsolete1), , , Extracted comment, SomeFile.java:1, , , , , , Y], [, New, 7, , , , , , , , , , , , , , ], [, Old, 8, , obsolete2, , Plural obsolete entry (obsolete2), obsolete2_plural, obsolete2-0, Extracted comment, SomeFile.java:1, , , , , , Y], [, New, 8, , , , , , , , , , , , , , ], [, Old, 10, , <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , , , , , , , , , Y], [, New, 10, , , , , , , , , , , , , , ]]''')



		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6()')
##		select('PoList.FileDisplay_JTbl', 'cell:14|obsolete,6()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Edit') + '1')
##		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test2, test2, no-c-format then fuzzy on separate lines], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		click(commonBits.fl('PO List'))
		
##		select('TabbedPane', 'PO List')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		select('Table', 'true', commonBits.fl('Show') + ',12')
		select('Table', 'true', commonBits.fl('Show') + ',13')
##		select('Table', 'cell:' + commonBits.fl('Show') + ',12(true)')
##		select('Table', 'cell:' + commonBits.fl('Show') + ',13(false)')
		click(commonBits.fl('Go'))

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, , , ], [Red Hat Middleware, LLC., , , Y, ], [test1, test1, fuzzy then no-c-format on separate lines, , ], [test2, test2, no-c-format then fuzzy on separate lines, Y, ], [test3, test3, fuzzy then no-c-format on same line, , ], [test4, test4, no-c-format then fuzzy on same line, Y, ], [obsolete1, obsolete1, Simple obsolete entry (obsolete1), , ], [obsolete2, , Plural obsolete entry (obsolete2), , ], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), , Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , ], [plural1, , Plural obsolete entry (plural 1), , ], [convert, omskakel, Test quotes, newlines and multiline., , ], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, , , ], [blank msgstr, , , , ]]')

	close()

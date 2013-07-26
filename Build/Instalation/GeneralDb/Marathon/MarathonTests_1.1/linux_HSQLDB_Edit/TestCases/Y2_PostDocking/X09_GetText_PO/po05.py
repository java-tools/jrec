useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'po/tst1Fuzzy.po')
		click(commonBits.fl('Edit') + '1')
		select('TabbedPane', 'Fuzzy/Blank')
		select('TabbedPane', 'Fuzzy/Blank')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,2(test2)')
##		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,2(test2)')
##		select('PoList.FileDisplay_JTbl1', 'test1', '3|msgstr,1')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,1(test1)')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,1(test1)')
##		select('PoList.FileDisplay_JTbl1', 'cell:3|msgstr,0()')
##		select('PoList.FileDisplay_JTbl1', 'test1', '2|msgid,1')
		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,1(test1)')
		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,1(test1)')
		assert_p('PoList.FileDisplay_JTbl1', 'Content', '[[Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [blank msgstr, , ]]')
		select('PoList.FileDisplay_JTbl1', 'cell:2|msgid,1(test1)')
		click(commonBits.fl('PO List'))
		select('TabbedPane', 'PO List')



		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\n, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')


##		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, ], [Red Hat Middleware, LLC., , ], [test1, test1, fuzzy then no-c-format on separate lines], [test2, test2, no-c-format then fuzzy on separate lines], [test3, test3, fuzzy then no-c-format on same line], [test4, test4, no-c-format then fuzzy on same line], [obsolete1, obsolete1, Simple obsolete entry (obsolete1)], [obsolete2, , Plural obsolete entry (obsolete2)], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3)], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry], [plural1, , Plural obsolete entry (plural 1)], [convert, omskakel, Test quotes, newlines and multiline.], [msgid with _\r_ escape\r, msgstr with _\r_ escape\n, ], [blank msgstr, , ]]')

		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		select('Table', 'cell:' + commonBits.fl('Show') + ',12(false)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',13(false)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',0(false)')


		click(commonBits.fl('Go'))

		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[, HIBERNATE - Relational Persistence for Idiomatic Java, HIBERNATE - Relational Persistence for Idiomatic Java, , , ], [, Red Hat Middleware, LLC., , , Y, ], [, test1, test1, fuzzy then no-c-format on separate lines, Y, ], [, test2, test2, no-c-format then fuzzy on separate lines, Y, ], [, test3, test3, fuzzy then no-c-format on same line, Y, ], [, test4, test4, no-c-format then fuzzy on same line, Y, ], [, obsolete1, obsolete1, Simple obsolete entry (obsolete1), , Y], [, obsolete2, , Plural obsolete entry (obsolete2), , Y], [xyz, obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), , Y], [, <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , Y], [, plural1, , Plural obsolete entry (plural 1), , ], [Verb.\nConverting from "something" to "something else"., convert, omskakel, Test quotes, newlines and multiline., , ], [, msgid with _\r_ escape\n, msgstr with _\r_ escape\n, , , ], [, blank msgstr, , , , ]]')



##		select('Table', '')
##		rightclick('Table', commonBits.fl('Line') + ',0')
##		select_menu(commonBits.fl('Edit Record'))
##		select('TabbedPane', 'Single PO Record')
##		assert_p('TextArea6', 'Text', 'HIBERNATE - Relational Persistence for Idiomatic Java')
##		click('Right')
	close()

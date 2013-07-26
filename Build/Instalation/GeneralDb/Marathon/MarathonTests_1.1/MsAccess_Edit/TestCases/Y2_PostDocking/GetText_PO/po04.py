useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/obsolete.po')
		click(commonBits.fl('Edit') + '1')
##		select('PoList.FileDisplay_JTbl', '')
		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')
		select_menu(commonBits.fl('Show Column') + '>>obsolete')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[obsolete1, obsolete1, Simple obsolete entry (obsolete1), Y], [obsolete2, , Plural obsolete entry (obsolete2), Y], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, Y]]')
		select('PoList.FileDisplay_JTbl', 'cell:2|msgid,1(obsolete2)')
		assert_p('TextArea4', 'Text', 'obsolete2-0')
		doubleclick('TextArea3')
		click('ArrowButton')
		assert_p('Table', 'Content', '[[0, obsolete2-0], [1, obsolete2-1], [2, obsolete2-2]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('TextArea2', 'Text', 'obsolete2_plural')
		assert_p('TextArea1', 'Text', 'obsolete2')
		assert_p('TextArea', 'Text', 'Plural obsolete entry (obsolete2)')
##		select('PoList.FileDisplay_JTbl', 'obsolete1', '2|msgid,0')
##		select('PoList.FileDisplay_JTbl', '')

		rightclick('PoList.FileDisplay_JTbl', '2|msgid,0')

		select_menu(commonBits.fl('Show Column') + '>>msgstrPlural')
		select('PoList.FileDisplay_JTbl', 'cell:6|msgstrPlural,1(obsolete2-0)')
		select('PoList.FileDisplay_JTbl', 'cell:6|msgstrPlural,1(obsolete2-0)')
		select('PoList.FileDisplay_JTbl', 'cell:6|msgstrPlural,1(obsolete2-0)')
		click('ArrowButton')
		assert_p('Table', 'Content', '[[0, obsolete2-0], [1, obsolete2-1], [2, obsolete2-2]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('PoList.FileDisplay_JTbl', 'cell:6|msgstrPlural,2()')
		click('ArrowButton')
		click(commonBits.fl('Add Row After'))
		click(commonBits.fl('Add Row After'))

###		select('LineList.FileDisplay_JTbl', '20sss', '2|STORE-NO,0')
##		select('Table', 'cell:' + commonBits.fl('Data') + ',0()')
##		select('Table', '111', commonBits.fl('Data') + ',0')

		rightclick('Table', commonBits.fl('Data') + ',0')
		rightclick('Table', commonBits.fl('Data') + ',0')
		select('Table', 'cell:' + commonBits.fl('Data') + ',0()')
		select('TextArea', '111')


		select('Table', 'cell:' + commonBits.fl('Data') + ',1()')
		select('TextArea', '222')
		click(commonBits.fl('Add Row After'))

		select('Table', 'cell:' + commonBits.fl('Data') + ',2()')
		select('TextArea', '3333')

		rightclick('Table', commonBits.fl('Data') + ',1')
		select('Table', 'cell:' + commonBits.fl('Data') + ',1()')
		select('TextArea', '222')
		assert_p('Table', 'Content', '[[0, 111], [1, 222], [2, 3333]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '[[obsolete1, obsolete1, Simple obsolete entry (obsolete1), , Y], [obsolete2, , Plural obsolete entry (obsolete2), obsolete2-0, Y], [obsolete3, obsolete3, Simple obsolete entry with context (obsolete3), 111, Y], [<ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter resources., <ulink url="http://www.redhat.com/support/resources/networking/firewall.html">http://www.redhat.com/support/resources/networking/firewall.html</ulink> &mdash; Provides links to a variety of up-to-date packet filter , Multiline obsolete entry, , Y]]')
		select('PoList.FileDisplay_JTbl', 'cell:6|msgstrPlural,2(111)')
		click('ArrowButton')
		assert_p('Table', 'Content', '[[0, 111], [1, 222], [2, 3333]]')

##		if window(rcommonBits.fl('Save Changes to file: C:\Users\BruceTst2/.RecordEditor/HSQLDB\SampleFiles/po/obsolete.po')):
##			click('No')
##		close()
	close()

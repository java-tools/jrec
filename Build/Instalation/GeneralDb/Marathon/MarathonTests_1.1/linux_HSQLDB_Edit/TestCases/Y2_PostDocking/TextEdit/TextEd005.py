useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv4DTAR020_tst1.bin.csv')
		click('Edit1')
		select_menu('View>>Text View')
		select('TabbedPane', 'Document View')
		rightclick('Document View')
		select_menu('Show Child Record')
		select('TabbedPane', 'Document View')
		click('Find')
##		click('MetalInternalFrameTitlePane', 206, 15)
		select('Find.Search For_Txt', '-1')
		select('Find.Replace With_Txt', '-12')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=3, 4, 0')
		click('Replace')
		assert_p('LineFrame.FileDisplay_JTbl', 'Text', 'DATE', 'Field,2')
		assert_p('LineFrame.Record_Txt', 'Text', '3')
		assert_p('TextArea', 'Text', '''63604808,20,40118,170,1,4.87
69684558,20,40118,280,1,19.00
69684558,20,40118,280,-12,-19.00
69694158,20,40118,280,1,5.01
62684671,20,40118,685,1,69.99
62684671,20,40118,685,-1,-69.99
61664713,59,40118,335,1,17.99
61664713,59,40118,335,-1,-17.99
61684613,59,40118,335,1,12.99
68634752,59,40118,410,1,8.99
60694698,59,40118,620,1,3.99
60664659,59,40118,620,1,3.99
60614487,59,40118,878,1,5.95
68654655,166,40118,60,1,5.08
69624033,166,40118,80,1,18.19
60604100,166,40118,80,1,13.30
68674560,166,40118,170,1,5.99''')
		click('Find >>')
		click('Find >>')
		click('Replace Find')
		assert_p('TextArea', 'Text', '''63604808,20,40118,170,1,4.87
69684558,20,40118,280,1,19.00
69684558,20,40118,280,-12,-19.00
69694158,20,40118,280,1,5.01
62684671,20,40118,685,1,69.99
62684671,20,40118,685,-12,-69.99
61664713,59,40118,335,1,17.99
61664713,59,40118,335,-1,-17.99
61684613,59,40118,335,1,12.99
68634752,59,40118,410,1,8.99
60694698,59,40118,620,1,3.99
60664659,59,40118,620,1,3.99
60614487,59,40118,878,1,5.95
68654655,166,40118,60,1,5.08
69624033,166,40118,80,1,18.19
60604100,166,40118,80,1,13.30
68674560,166,40118,170,1,5.99''')
		assert_p('LineFrame.FileDisplay_JTbl', 'Text', 'DATE', 'Field,2')
		keystroke('Replace Find', 'Context Menu')
		assert_p('LineFrame.Record_Txt', 'Text', '8')
		click('Replace Find')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Table:')
		select('TabbedPane', 'Table:')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -12, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -12, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -12, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')

##		if window(r'Save Changes to file: C:\Users\BruceTst/RecordEditor_HSQL\SampleFiles/csv4DTAR020_tst1.bin.csv'):
##			click('No')
##		close()
	close()

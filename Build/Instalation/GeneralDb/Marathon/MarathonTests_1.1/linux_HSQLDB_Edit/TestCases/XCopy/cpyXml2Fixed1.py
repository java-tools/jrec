useFixture(default)
###
###   Not Currently Working
###
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.doEdit(click)

		click('SaveAs')
		##select('ComboBox1', 'XML')
		select('TabbedPane', 'Xml')
		select('FileChooser', commonBits.sampleDir() + 'zXmlDTAR020.bin$.xml')
		click('save file')
#commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>File Copy Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'zXmlDTAR020.bin$.xml')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020_CopyFromXml.bin')
		commonBits.setRecordLayout(select, 'DTAR020')

		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Record,2(DTAR020)')
#		select('Table1', 'cell:Equivalent Field,3()')
		select('Table', 'cell:Record,2(DTAR020)')
#		select('Table1', 'KEYCODE-NO', 'Equivalent Field,3')
#		select('Table1', 'STORE-NO', 'Equivalent Field,4')
#		select('Table1', 'DATE', 'Equivalent Field,5')
#		select('Table1', 'DEPT-NO', 'Equivalent Field,6')
#		select('Table1', 'cell:Equivalent Field,6(DEPT-NO)')
#		select('Table1', 'cell:Equivalent Field,6(DEPT-NO)')
#		click('MetalScrollButton4', 5)
#		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
#		select('Table1', 'SALE-PRICE', 'Equivalent Field,8')
#		select('Table1', '', 'Equivalent Field,9')
#		select('Table1', '', 'Equivalent Field,10')
#		select('Table1', 'cell:Equivalent Field,10()')
		click('Right')
		select('TabbedPane', '')
		click('Copy2')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		##click('MetalInternalFrameTitlePane', 778, 12)
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>File Copy Menu')
		select_menu('File>>Compare Menu')
		click('*2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Menu>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'DTAR020_CopyFromXml.bin')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('JTableHeader', 'Text', 'STORE-NO', 'STORE-NO')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')


### ------------------------------------------------------------------------
### ---  Edit file created via copy
### ------------------------------------------------------------------------

		click('Open')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020_CopyFromXml.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		commonBits.doEdit(click)


		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9],columns:[9 - 2|STORE-NO]')
		select_menu('View>>Table View #{Selected Records#}')
#		select('Table2', 'rows:[0,1,2,3,4,5,6,7,8,9],columns:[9 - 2|STORE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,3(20)')
		assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99]]')
		select('Table', 'cell:9 - 2|STORE-NO,3(20)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9],columns:[9 - 2|STORE-NO]')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9],columns:[9 - 2|STORE-NO]')
		select_menu('Window>>DTAR020_CopyFromXml.bin>>Table: ')
		select('Table', 'rows:[11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28],columns:[1 - 8|KEYCODE-NO,9 - 2|STORE-NO]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28],columns:[1 - 8|KEYCODE-NO,9 - 2|STORE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,5(59)')
		assert_p('Table', 'Content', '[[65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50], [69644164, 166, 40118, 193, 1, 21.59]]')
##		assert_p('Table', 'Content', '[[64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50], [69644164, 166, 40118, 193, 1, 21.59]]')

### ------------------------------------------------------------------------
### ---  Compare files
### ------------------------------------------------------------------------
		select_menu('File>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'DTAR020_CopyFromXml.bin')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('JTableHeader', 'Text', 'DEPT-NO', 'DEPT-NO')
 
	close()

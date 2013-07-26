useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/TstDupFilter01.po')
		click('Edit1')
##		select('TabbedPane', 'Fuzzy/Blank')
##		select('Table', '')

		select('TabbedPane', 'PO List')

		rightclick('Table', 'Line,1')


		select_menu('Show Duplicate Rows')
		click('Generate Duplicate View')
#		select('TabbedPane', 'Duplicates')
		select('TabbedPane', 'Duplicates')
		select('TabbedPane', 'Duplicates')
		select('Table2', 'cell:Line,0(1)')
		select('Table2', 'cell:Line,0(1)')
##		assert_p('PoList.FileDisplay_JTbl2', 'Content', '''[[<= (Text), `!<= (Text)!`, ---------------------------------------------------------------------------------
##
##Use: Combobox_Entry 
## id:    Panel:  
##], [<= (Text), `!<= (Text) 1!`, Use: Combobox_Entry 
## id:    Panel:  
##
## Duplicate (3)]]''')
		assert_p('PoList.FileDisplay_JTbl2', 'Content', '''[[<= (Text), `!<= (Text) 1!`, Use: Combobox_Entry 
 id:    Panel:  

 Duplicate (3)], [<= (Text), `!<= (Text)!`, ---------------------------------------------------------------------------------

Use: Combobox_Entry 
 id:    Panel:  
]]''')


##		assert_p('TextArea3', 'Text', '''---------------------------------------------------------------------------------
##
##Use: Combobox_Entry 
## id:    Panel:  
##''')
		assert_p('TextArea6', 'Text', ''' Use: Combobox_Entry 
 id:    Panel:  
 Duplicate (3)
''')

		assert_p('TextArea7', 'Text', '<= (Text)')
##		assert_p('TextArea5', 'Text', '`!<= (Text)!`')
		assert_p('TextArea8', 'Text', '`!<= (Text) 1!`')

		click('PO List')
		select('TabbedPane', 'PO List')
		assert_p('PoList.FileDisplay_JTbl', 'Content', '''[[<= (Text), `!<= (Text) 1!`, Use: Combobox_Entry 
 id:    Panel:  

 Duplicate (3)], [<All>, `!<All>!`, Use: Combobox_Entry 
 id:    Panel:  
Combo- Layout Selection], [< (Numeric), `!< (Numeric)!`, ---------------------------------------------------------------------------------

Use: Combobox_Entry 
 id:    Panel:  
], [<All>, `!<All>!`, Use: Combobox_Entry 
 id:    Panel:  
Combo- Layout Selection], [<None>, `!<None>!`, Use: Combobox_Entry 
 id:    Panel:  
Combo- Csv_Quote], [<= (Text), `!<= (Text)!`, ---------------------------------------------------------------------------------

Use: Combobox_Entry 
 id:    Panel:  
]]''')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

#	if window(r'Save Changes to file: C:\Users\BruceTst\RecordEditor_HSQL\SampleFiles\po\TstDupFilter01.po'):
#		click('No')
#	close()

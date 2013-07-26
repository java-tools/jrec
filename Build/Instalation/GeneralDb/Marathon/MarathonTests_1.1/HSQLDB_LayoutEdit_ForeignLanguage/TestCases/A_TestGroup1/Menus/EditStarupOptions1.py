useFixture(default)

def test():
	from Modules import commonBits

	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		select_menu( commonBits.fl('Edit') + '>>' +  commonBits.fl('Edit Options'))

		if window('Record Editor Options Editor'):
			if commonBits.isVersion80():
				select('TabbedPane',  commonBits.fl('Looks'))
				select('LooksTab',  commonBits.fl('Initial Program Size'))

				##select('Table', 'cell:Parameter Description,1(Space to be left at the bottom of the screen.)')
				##assert_p('Label74', 'Text', 'Space to be left at the bottom of the screen.')
				##assert_p('Label75', 'Text', 'Space to be left at the top of the screen.')
				##select('TabbedPane6', 'Screen Properties')
				assert_p('Label94', 'Text',  commonBits.fl('Size of the program when it opens'))
				assert_p('Label96', 'Text',  commonBits.fl('Space to be left at the top of the screen.'))
				assert_p('Label98', 'Text',  commonBits.fl('Space to be left at the Right of the screen.'))
			
 ##				assert_p('Label88', 'Text', 'Screen Height')
				
 ##				assert_p('Label89', 'Text', 'Screen Width')
 ##				assert_p('Retrieve Screen size', 'Text', 'Retrieve Screen size')
#				assert_p('EditorPane24', 'Text', '''<html>''')
#				assert_p('EditorPane20', 'Text', '''<html>''')
				

				

				##assert_p('Table', 'Text', 'Space to be left at the bottom of the screen.', 'Parameter Description,0')
				##select('Table', 'cell:Parameter Description,2(Space to be left at the top of the screen.)')
				##assert_p('Table', 'Content', '[[spaceAtBottomOfScreen, Space to be left at the bottom of the screen., ], [spaceAtTopOfScreen, Space to be left at the top of the screen., ], [spaceAtLeftOfScreen, Space to be left at the left of the screen., ], [spaceAtRightOfScreen, Space to be left at the Right of the screen., ]]')
				##select('Table', 'cell:Parameter Description,3(Space to be left at the top of the screen.)')

			else:
				select('TabbedPane', 'Properties')
				select('Table', 'cell:Parameter Description,0(Space to be left at the bottom of the screen.)')
				assert_p('Table', 'Text', 'Space to be left at the bottom of the screen.', 'Parameter Description,0')
				select('Table', 'cell:Parameter Description,1(Space to be left at the top of the screen.)')
				assert_p('Table', 'Content', '[[spaceAtBottomOfScreen, Space to be left at the bottom of the screen., ], [spaceAtTopOfScreen, Space to be left at the top of the screen., ], [spaceAtLeftOfScreen, Space to be left at the left of the screen., ], [spaceAtRightOfScreen, Space to be left at the Right of the screen., ]]')
				select('Table', 'cell:Parameter Description,1(Space to be left at the top of the screen.)')
				select('TabbedPane1', 'Directories')
				select('TabbedPane1', 'Other Options')
				select('Table2', 'cell:Parameter Description,0(This User written class will be invoked when the <b>RecordEditor</b starts.)')
				assert_p('Table2', 'Text', 'Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive', 'Parameter Description,1')
				select('Table2', 'cell:Parameter Description,1(Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive)')
				#assert_p('Table2', 'Content', '[[UserInitilizeClass, This User written class will be invoked when the <b>RecordEditor</b starts., ], [DateFormat, Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive, dd.MMM.yyyy], [SignificantCharInFiles.1, Number of characters to use when looking up record layouts (small), 6], [SignificantCharInFiles.2, Number of characters to use when looking up record layouts (medium), 12], [SignificantCharInFiles.3, Number of characters to use when looking up record layouts (large), 22], [LauchEditorIfMatch, Controls wether Editor is launched Values 1->5. 1->3 correspond to SignificantCharInFiles, ]]')
				select('Table2', 'cell:Parameter Description,1(Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive)')
				#click('Button2')
		close()
	close()

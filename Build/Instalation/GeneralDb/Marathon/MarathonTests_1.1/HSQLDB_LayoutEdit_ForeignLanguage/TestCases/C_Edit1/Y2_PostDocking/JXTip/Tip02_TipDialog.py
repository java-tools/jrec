useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'


	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/TipOfTheDay.properties')
		select('Record Layout_Txt', 'TipDetails')
		click(commonBits.fl('Edit') + '1')
	##	select('Table', '')
		rightclick('Table', commonBits.fl('Line') + ',2')
		select_menu(commonBits.fl('Show in Hints Dialog'))

		if window('Tip of the Day'):
			assert_p('Label2', 'Text', 'Did you know...')
			click('Next >')
			assert_p('EditorPane', 'Text', '''<html>
  <head>
    <style type="text/css">
      <!--
        body { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        a { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        p { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        li { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
      -->
    </style>
    
  </head>
  <body>
    You can <b><font color="blue">sort</font></b> a file by any of

    <ul>
      <li>
        Double clicking on the column heading
      </li>
      <li>
        Click on the sort Button, The editor will display display a <b>sort 
        dialogue</b>
      </li>
      <li>
        Using the <b>Right Click</b> menu.
      </li>
      <li>
        Using the <b>Data &gt;&gt;&gt;&gt; Sort</b> Drop down menu
      </li>
    </ul>
  </body>
</html>
''')
			click('Next >')
			assert_p('EditorPane', 'Text', '''<html>
  <head>
    <style type="text/css">
      <!--
        body { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        a { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        p { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        li { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
      -->
    </style>
    
  </head>
  <body>
    If you want to view a file in a different sequence without changing the 
    order of records in the file, you can

    <ul>
      <li>
        Click <b>ctrl-a</b> to select all records, then select <b>View &gt;&gt;&gt; 
        Table View (Selected Record)</b> to create a new view.
      </li>
      <li>
        <b>Sort</b> this new view in to the required sequence; Only the view\'s 
        record order will be updated, leaving the file in the original 
        sequence.
      </li>
    </ul>
  </body>
</html>
''')
			click('Next >')
			assert_p('EditorPane', 'Text', '''<html>
  <head>
    <style type="text/css">
      <!--
        body { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        a { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        p { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
        li { margin-bottom: 0; font-size: 12pt; margin-right: 0; margin-left: 0; margin-top: 0; font-family: Dialog }
      -->
    </style>
    
  </head>
  <body>
    Do you have <b>Multiple Record Types</b> in the file ???. You can use <b>View 
    &gt;&gt;&gt; Record Based Tree</b> to display the data in a Tree format.
  </body>
</html>
''')
			click('Close')
		close()
	close()


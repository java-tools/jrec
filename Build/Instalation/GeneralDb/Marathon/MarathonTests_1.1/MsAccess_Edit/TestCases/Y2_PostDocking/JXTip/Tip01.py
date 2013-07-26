useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'


	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'po/TipOfTheDay.properties')
		select('Record Layout_Txt', 'TipDetails')
		click(commonBits.fl('Edit') + '1')
		assert_p('TipList.FileDisplay_JTbl', 'Content', '''[[, <html>Do you have a <font color="blue"><b>File</b></font> with out a <b>File Description</b> (Record Layout) ???
<br/>You can use the <font color="blue"><b>File Wizard</b></font> to build the
<b>File Description</b> from the data in the File.
<br/>The <font color="blue"><b>File Wizard</b></font> has a Field-Search functionality
to help identify the fields in the File.
</html>], [, <html>Do you have a <font color="blue"><b>Cobol copybook</b></font> for a file ???
<br>You can use the <b>Record Layouts &gt;&gt;&gt;&gt; Load Cobol Copybook</b>
function to load the Cobol Layout into the <b>RecordEditor</b>
</html>], [, <html>Have you tried <font color="blue"><b>Filter</b></font> function ???. 
<br/>The <font color="blue">filter dialog</font> 
will display all  records that match the entered criteria.
 You can specify multiple criteria (linked by <b>and / or</b>
operators).
<p>The  <font color="blue">Filter function</font> can be a useful alternative to the
find command</p
</html>], [, <html>You can <font color="blue"><b>sort</b></font> a file by any of<ul>
<li>Double clicking on the column heading</li>
<li>Click on the sort Button, The editor will display display a <b>sort dialogue</b></li>
<li>Using the <b>Right Click</b> menu.
<li>Using the <b>Data &gt;&gt;&gt;&gt; Sort</b> Drop down menu
</ul>
</html>], [, <html>If you want to view a file in a different sequence 
without changing the order of records in the file, you can
<ul>
 <li>Click <b>ctrl-a</b> to select all records, then select <b>View &gt;&gt;&gt Table View (Selected Record)</b> to 
create a new view.
 <li><b>Sort</b> this new view in to the required sequence; Only the view\'s record order
will be updated, leaving the file in the original sequence.
</ul>
</html>], [, <html>Do you have <b>Multiple Record Types</b> in the file ???.
You can use <b>View &gt;&gt;&gt; Record Based Tree</b> to display the
data in a Tree format. 
</html>], [, <html>Want to <b>Show / Hide</b> Columns ???. 
You can Use either the <b>Right Click  &gt;&gt;&gt; Show/Hide Column</b>  Menu or the <b>Edit &gt;&gt;&gt; Show / Hide</b>
 menu item.
</html>], [, <html>Want to <b>Fix</b> a column so it does 
not scroll off the screen ???. Use the <b>Right Click &gt;&gt;&gt; Fix Column</b>  Menu Item.
</html>], [, <html>Want to see a file <font color="blue"><b>View</b></font> in a seperate screen ???
<br/>You can use <ul>
 <li><b>Right Click &gt;&gt;&gt; Undock tab</b> on the Tab Header
 <li><b>Window &gt;&gt;&gt; Undock tab</b> command.
</ul>
</html>], [, <html>Want to <font color="blue"><b>compare files</b></font> ???
<br>The <b>Utilities &gt;&gt;&gt; Compare Menu</b> does a <b>formated</b>
file compare where fields can be excluded from the compare.
</html>], [, <html>Want to convert a <b>file</b> to a different <b>format</b> ???,
check out <ul>
<li>The <b>Export</b> functions (CSV, Fixed, Xml etc) see <b>File  &gt;&gt;&gt; Export</b> .
<li><b>Velocity</b> Export Functions. Velocity Templates can be used to create a custom export format
<li><b>Copy</b> Functions (<b>Utilities &gt;&gt;&gt; File Copy Menu</b>
</ul>
</html>], [, <html>Have you tried <font color="blue"><b>Sort Tree\'s</b></font> (<b>View &gt;&gt;&gt; Sort Field Tree</b>) yet ???
<br/><font color="blue"><b>Sort Tree\'s</b></font> will do a Sort sum [<i>like a <b>SQL</b> Select sum(...), ave(..)... Group by ...</i>]
on the file and display the result as a Tree where you can drill down to the individual records.
</html>], [, <html>Do you edit <font color="blue"><b>GetText-PO</b></font> files, You can edit them in the <b>RecordEditor</b>
with the <font color="blue">GetText_PO</font> <b>RecordLayout</b>.], [, <html>Do you edit <font color="blue"><b>SwingX Tip Of The Day</b></font> properties files, You can edit them in the <b>RecordEditor</b>
with the <font color="blue">Tip_of_The_Days_Properties</font> <b>RecordLayout</b>.], [, <html>Have you tried the <b>Docking / Undocking</b> (on the <b>Window</b> Menu and <b>Right Click</b> menus)  yet ???
</html>], [, <html>Have you tried <b>Right Clicking</b> on a panel back ground ???, there are <b>docking / undocking</b> options 
</html>], [, <html>Are you using the same <b>Filter, Sort, Sort Tree</b> or <b>RecordTree</b> all the time ???. You can use the save / load options
to save / load the definition to/from xml files. You can also execute them from the <b>View</b> menu.
</html>], [, <html>Edit <b>Csv</b> files with the RecordEditor ???, you can use the <b>Edit &gt; &gt; &gt; Update Csv Column</b> to move, copy, insert, delete
and rename columns !!!.
</html>], [, <html>Edit <b>Csv</b> files ???, you use the <b>Right Click &gt;&gt;&gt; CSV Options</b> to Add, delete, copy, move columns !!!
</html>], [, <html>Want to see what changes you have made to the file since the last save ???
<br/>Try the <b>Utilities &gt;&gt;&gt; Compare with Disk</b>.
</html>], [, <html>Use Cobol, You can use the <b>Utilities &gt;&gt;&gt;&gt; Cobol Copybook Analysis</b>
to see the structure / field starting poiint of the <b>Cobol Copbook.</b>
</html>], [, <html>Use Cobol, You can use the <b>Utilities &gt;&gt;&gt;&gt; Cobol Copybook Analysis</b>
to see the structure / field starting poiint of the <b>Cobol Copbook.</b>
</html>]]''')
		select('Table', 'cell:' + commonBits.fl('Line') + ',1(2)')
		assert_p('EditorPane', 'Text', '''<html>Do you have a <font color="blue"><b>Cobol copybook</b></font> for a file ???
<br>You can use the <b>Record Layouts &gt;&gt;&gt;&gt; Load Cobol Copybook</b>
function to load the Cobol Layout into the <b>RecordEditor</b>
</html>''')

		commonBits.doSleep()

		assert_p('EditorPane1', 'Text', '<html>\n  <head>\n    \n  </head>\n  <body>\n    Do you have a <font color="blue"><b>Cobol copybook</b></font> for a file \n    ???<br>You can use the <b>Record Layouts &gt;&gt;&gt;&gt; Load Cobol Copybook</b> \n    function to load the Cobol Layout into the <b>RecordEditor</b>\n  </body>\n</html>\n')

##		assert_p('EditorPane1', 'Text', '<html>\n  <head>\n    \n  </head>\n  <body>\n    Do you have a <b><font color="blue">Cobol copybook</font></b> for a file \n    ???<br>You can use the <b>Record Layouts &gt;&gt;&gt;&gt; Load Cobol Copybook</b> \n    function to load the Cobol Layout into the <b>RecordEditor</b>\n  </body>\n</html>\n')

		commonBits.doSleep()
		assert_p('EditorPane1', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    Do you have a <font color="blue"><b>Cobol copybook</b></font> for a file 
    ???<br>You can use the <b>Record Layouts &gt;&gt;&gt;&gt; Load Cobol Copybook</b> 
    function to load the Cobol Layout into the <b>RecordEditor</b>
  </body>
</html>
''')
		select('Table', 'cell:' + commonBits.fl('Line') + ',2(3)')
		commonBits.doSleep()
		assert_p('EditorPane', 'Text', '''<html>Have you tried <font color="blue"><b>Filter</b></font> function ???. 
<br/>The <font color="blue">filter dialog</font> 
will display all  records that match the entered criteria.
 You can specify multiple criteria (linked by <b>and / or</b>
operators).
<p>The  <font color="blue">Filter function</font> can be a useful alternative to the
find command</p
</html>''')
		assert_p('EditorPane1', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    Have you tried <font color="blue"><b>Filter</b></font> function ???.<br>The 
    <font color="blue">filter dialog</font> will display all records that 
    match the entered criteria. You can specify multiple criteria (linked by <b>and 
    / or</b> operators).

    <p>
      The <font color="blue">Filter function</font> can be a useful 
      alternative to the find command
    </p>
  </body>
</html>
''')

		select('Table', 'cell:' + commonBits.fl('Line') + ',3(4)')
		commonBits.doSleep()
		assert_p('EditorPane', 'Text', '''<html>You can <font color="blue"><b>sort</b></font> a file by any of<ul>
<li>Double clicking on the column heading</li>
<li>Click on the sort Button, The editor will display display a <b>sort dialogue</b></li>
<li>Using the <b>Right Click</b> menu.
<li>Using the <b>Data &gt;&gt;&gt;&gt; Sort</b> Drop down menu
</ul>
</html>''')
		assert_p('EditorPane1', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    You can <font color="blue"><b>sort</b></font> a file by any of

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
		select('Table', 'cell:' + commonBits.fl('Line') + ',4(5)')
		commonBits.doSleep()
		assert_p('EditorPane', 'Text', '''<html>If you want to view a file in a different sequence 
without changing the order of records in the file, you can
<ul>
 <li>Click <b>ctrl-a</b> to select all records, then select <b>View &gt;&gt;&gt Table View (Selected Record)</b> to 
create a new view.
 <li><b>Sort</b> this new view in to the required sequence; Only the view\'s record order
will be updated, leaving the file in the original sequence.
</ul>
</html>''')
		assert_p('EditorPane1', 'Text', '''<html>
  <head>
    
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
		select('Table', 'cell:' + commonBits.fl('Line') + ',4(5)')
		rightclick('Table', commonBits.fl('Line') + ',5')
##		select('Table', 'cell:' + commonBits.fl('Line') + ',4(5)')
		select_menu(commonBits.fl('Edit Record'))


		select('TabbedPane', 'Single Tip Record')
		assert_p('EditorPane2', 'Text', '''<html>Do you have <b>Multiple Record Types</b> in the file ???.
You can use <b>View &gt;&gt;&gt; Record Based Tree</b> to display the
data in a Tree format. 
</html>''')
		assert_p('EditorPane3', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    Do you have <b>Multiple Record Types</b> in the file ???. You can use <b>View 
    &gt;&gt;&gt; Record Based Tree</b> to display the data in a Tree format.
  </body>
</html>
''')
		click('Right')
		assert_p('EditorPane2', 'Text', '''<html>Want to <b>Show / Hide</b> Columns ???. 
You can Use either the <b>Right Click  &gt;&gt;&gt; Show/Hide Column</b>  Menu or the <b>Edit &gt;&gt;&gt; Show / Hide</b>
 menu item.
</html>''')
		assert_p('EditorPane3', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    Want to <b>Show / Hide</b> Columns ???. You can Use either the <b>Right 
    Click &gt;&gt;&gt; Show/Hide Column</b> Menu or the <b>Edit &gt;&gt;&gt; Show / Hide</b> 
    menu item.
  </body>
</html>
''')
		click('Right')
		assert_p('EditorPane2', 'Text', '''<html>Want to <b>Fix</b> a column so it does 
not scroll off the screen ???. Use the <b>Right Click &gt;&gt;&gt; Fix Column</b>  Menu Item.
</html>''')
		assert_p('EditorPane3', 'Text', '''<html>
  <head>
    
  </head>
  <body>
    Want to <b>Fix</b> a column so it does not scroll off the screen ???. Use 
    the <b>Right Click &gt;&gt;&gt; Fix Column</b> Menu Item.
  </body>
</html>
''')
		click(commonBits.fl('Single Tip Record'))
		click(commonBits.fl('Tip List'))
		select('TabbedPane', 'Tip List')
		select('Table', 'cell:' + commonBits.fl('Line') + ',4(5)')
		click(commonBits.fl('Single Tip Record'))
		select('TabbedPane', 'Single Tip Record')
	close()

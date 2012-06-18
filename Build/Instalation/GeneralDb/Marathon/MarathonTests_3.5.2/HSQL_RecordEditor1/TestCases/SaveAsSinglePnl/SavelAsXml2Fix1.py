#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleXmlDir() + 'AmsLocationTest2.xml')
        click('Edit')
    close()

    if window('Record Editor'):
        click('Export')

        if frame('Export - AmsLocationTest2.xml:0'):
##            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('Edit Output File', 'true')
            select('Only export Nodes with Data', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.txt:0'):
            assert_content('JTable_22', [ ['copybook', '', '', '', '', '', '', '', 'AMSLOCATIONTEST1.cbl', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '* Location Download', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'item', '', '', '', '', '', '', '173', '01', 'Ams-Vendor', '1', '173', '', ''],
['copybook', 'item', 'item', '', '', '', '', '', '3', '03', 'Brand', '1', '3', 'x(3)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '41', '03', 'Location-Details', '4', '41', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '4', '05', 'Location-Number', '4', '4', '9(4)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '2', '05', 'Location-Type', '8', '2', 'XX', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '35', '05', 'Location-Name', '10', '35', 'X(35)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '128', '03', 'Full-Address', '45', '128', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '115', '05', 'Address-Lines', '45', '115', '', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-1', '45', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-2', '85', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '35', '07', 'Address-3', '125', '35', 'X(35)', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '10', '05', 'Postcode', '160', '10', '9(10)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '3', '05', 'State', '170', '3', 'XXX', ''],
['copybook', 'item', 'item', '', '', '', '', '', '1', '03', 'Location-Active', '173', '1', 'X', '']
])
            select('JTable_22', 'rows:[0],columns:[9 - 11|Level_2]')
            select('JTable_22', 'rows:[0],columns:[9 - 11|Level_2]')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['copybook                                                     AMSLOCATIONTEST1.cbl'],
['copybookXML Comment            ******************************'],
['copybookXML Comment            * Location Download           '],
['copybookXML Comment            ******************************'],
['copybookitem                                                 173                 01Ams-Vendor      1  173         '],
['copybookitem       item                                      3                   03Brand           1  3  x(3)     '],
['copybookitem       item                                      41                  03Location-Details4  41          '],
['copybookitem       itemitem                                  4                   05Location-Number 4  4  9(4) true'],
['copybookitem       itemitem                                  2                   05Location-Type   8  2  XX       '],
['copybookitem       itemitem                                  35                  05Location-Name   10 35 X(35)    '],
['copybookitem       item                                      128                 03Full-Address    45 128         '],
['copybookitem       itemitem                                  115                 05Address-Lines   45 115         '],
['copybookitem       itemitemitem                              40                  07Address-1       45 40 X(40)    '],
['copybookitem       itemitemitem                              40                  07Address-2       85 40 X(40)    '],
['copybookitem       itemitemitem                              35                  07Address-3       12535 X(35)    '],
['copybookitem       itemitem                                  10                  05Postcode        16010 9(10)true'],
['copybookitem       itemitem                                  3                   05State           1703  XXX      '],
['copybookitem       item                                      1                   03Location-Active 1731  X        ']
])
            select('Layouts', 'Fixed')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_29', 'Text', '62 - 20|display-length', '62 - 20|display-length')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_29', 'Text', '84 - 16|name', '84 - 16|name')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_29', 'Text', '103 - 3|storage-length', '103 - 3|storage-length')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_29', 'Text', '103 - 3|storage-length', '103 - 3|storage-length')
            click('Close')
        close()

        if frame('Export - AmsLocationTest2.xml:0'):
##            select('JTable_31', 'rows:[5],columns:[Include]')
##            select('JTable_31', 'false', '{5, Include}')

##            select('JTable_31', 'rows:[6],columns:[Include]')
##            select('JTable_31', 'false', '{6, Include}')
##            select('JTable_31', 'rows:[5],columns:[Include]')

            select('JTable_28', 'rows:[5],columns:[Include]')
            select('JTable_28', 'false', '{5, Include}')

            select('JTable_28', 'rows:[6],columns:[Include]')
            select('JTable_28', 'false', '{6, Include}')
            select('JTable_28', 'rows:[5],columns:[Include]')

            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.txt:0'):
            assert_p('JTable_22', 'Text', '', '{9, 32 - 0|Following~Text}')
##            assert_p('JTable_22', 'Component', '[,0,0,1119x288,alignmentX=0.0,alignmentY=0.0,border=,flags=251658600,maximumSize=,minimumSize=,preferredSize=,autoCreateColumnsFromModel=true,autoResizeMode=AUTO_RESIZE_OFF,cellSelectionEnabled=false,editingColumn=-1,editingRow=-1,gridColor=javax.swing.plaf.ColorUIResource[r=122,g=138,b=153],preferredViewportSize=java.awt.Dimension[width=450,height=400],rowHeight=16,rowMargin=1,rowSelectionAllowed=true,selectionBackground=javax.swing.plaf.ColorUIResource[r=184,g=207,b=229],selectionForeground=sun.swing.PrintColorUIResource[r=51,g=51,b=51],showHorizontalLines=true,showVerticalLines=true]')
            select('JTable_22', 'rows:[0],columns:[9 - 11|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '62 - 16|name', '62 - 16|name')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '78 - 3|position', '78 - 3|position')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '81 - 3|storage-length', '81 - 3|storage-length')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '84 - 5|picture', '84 - 5|picture')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['copybook                                                     '],
['copybookXML Comment            ******************************'],
['copybookXML Comment            * Location Download           '],
['copybookXML Comment            ******************************'],
['copybookitem                                                 Ams-Vendor      1  173         '],
['copybookitem       item                                      Brand           1  3  x(3)     '],
['copybookitem       item                                      Location-Details4  41          '],
['copybookitem       itemitem                                  Location-Number 4  4  9(4) true'],
['copybookitem       itemitem                                  Location-Type   8  2  XX       '],
['copybookitem       itemitem                                  Location-Name   10 35 X(35)    '],
['copybookitem       item                                      Full-Address    45 128         '],
['copybookitem       itemitem                                  Address-Lines   45 115         '],
['copybookitem       itemitemitem                              Address-1       45 40 X(40)    '],
['copybookitem       itemitemitem                              Address-2       85 40 X(40)    '],
['copybookitem       itemitemitem                              Address-3       12535 X(35)    '],
['copybookitem       itemitem                                  Postcode        16010 9(10)true'],
['copybookitem       itemitem                                  State           1703  XXX      '],
['copybookitem       item                                      Location-Active 1731  X        ']
])
            
            click('Close')
        close()

        if frame('Export - AmsLocationTest2.xml:0'):
            select('names on first line', 'true')
            select('space between fields', 'true')
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.txt:0'):
            assert_p('JTable_22', 'Text', '', '{10, 46 - 14|Following~Text}')
            select('JTable_22', 'rows:[0],columns:[10 - 11|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '106 - 16|name', '106 - 16|name')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '132 - 14|storage-length', '132 - 14|storage-length')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Level_1  Level_2     Level_3 Level_4 Level_5 Following~Text Xml~Prefix                     Xml~Namespace name             position storage-length picture numeric'],
['copybook                                                                                                '],
['copybook XML Comment                                        ******************************'],
['copybook XML Comment                                        * Location Download           '],
['copybook XML Comment                                        ******************************'],
['copybook item                                                                                            Ams-Vendor       1        173                           '],
['copybook item        item                                                                                Brand            1        3              x(3)           '],
['copybook item        item                                                                                Location-Details 4        41                            '],
['copybook item        item    item                                                                        Location-Number  4        4              9(4)    true   '],
['copybook item        item    item                                                                        Location-Type    8        2              XX             '],
['copybook item        item    item                                                                        Location-Name    10       35             X(35)          '],
['copybook item        item                                                                                Full-Address     45       128                           '],
['copybook item        item    item                                                                        Address-Lines    45       115                           '],
['copybook item        item    item    item                                                                Address-1        45       40             X(40)          '],
['copybook item        item    item    item                                                                Address-2        85       40             X(40)          '],
['copybook item        item    item    item                                                                Address-3        125      35             X(35)          '],
['copybook item        item    item                                                                        Postcode         160      10             9(10)   true   '],
['copybook item        item    item                                                                        State            170      3              XXX            '],
['copybook item        item                                                                                Location-Active  173      1              X              ']
])
            click('Close')
        close()

        if frame('Export - AmsLocationTest2.xml:0'):
##            select('JTable_31', 'true', '{5, Include}')
##            select('JTable_31', 'true', '{6, Include}')

##            select('JTable_31', 'rows:[6],columns:[Include]')
##            select('JTable_31', 'true', '{6, Include}')
##            select('JTable_31', 'rows:[6],columns:[Include]')

            select('JTable_28', 'true', '{5, Include}')
            select('JTable_28', 'true', '{6, Include}')

            select('JTable_28', 'rows:[6],columns:[Include]')
            select('JTable_28', 'true', '{6, Include}')
            select('JTable_28', 'rows:[6],columns:[Include]')

            
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.txt:0'):
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'Level_5', 'Following~Text', 'Xml~Prefix', 'Xml~Namespace', 'display-length', 'level', 'name', 'position', 'storage-length', 'picture', 'numeric'],
['copybook', '', '', '', '', '', '', '', 'AMSLOCATIONTEST1.cbl', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '* Location Download', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'item', '', '', '', '', '', '', '173', '01', 'Ams-Vendor', '1', '173', '', ''],
['copybook', 'item', 'item', '', '', '', '', '', '3', '03', 'Brand', '1', '3', 'x(3)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '41', '03', 'Location-Details', '4', '41', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '4', '05', 'Location-Number', '4', '4', '9(4)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '2', '05', 'Location-Type', '8', '2', 'XX', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '35', '05', 'Location-Name', '10', '35', 'X(35)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '128', '03', 'Full-Address', '45', '128', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '115', '05', 'Address-Lines', '45', '115', '', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-1', '45', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-2', '85', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '35', '07', 'Address-3', '125', '35', 'X(35)', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '10', '05', 'Postcode', '160', '10', '9(10)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '3', '05', 'State', '170', '3', 'XXX', ''],
['copybook', 'item', 'item', '', '', '', '', '', '1', '03', 'Location-Active', '173', '1', 'X', '']
])
            select('JTable_22', 'rows:[0],columns:[10 - 11|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '106 - 20|display-length', '106 - 20|display-length')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '127 - 5|level', '127 - 5|level')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '133 - 16|name', '133 - 16|name')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Level_1  Level_2     Level_3 Level_4 Level_5 Following~Text Xml~Prefix                     Xml~Namespace display-length       level name             position storage-length picture numeric'],
['copybook                                                                                                 AMSLOCATIONTEST1.cbl'],
['copybook XML Comment                                        ******************************'],
['copybook XML Comment                                        * Location Download           '],
['copybook XML Comment                                        ******************************'],
['copybook item                                                                                            173                  01    Ams-Vendor       1        173                           '],
['copybook item        item                                                                                3                    03    Brand            1        3              x(3)           '],
['copybook item        item                                                                                41                   03    Location-Details 4        41                            '],
['copybook item        item    item                                                                        4                    05    Location-Number  4        4              9(4)    true   '],
['copybook item        item    item                                                                        2                    05    Location-Type    8        2              XX             '],
['copybook item        item    item                                                                        35                   05    Location-Name    10       35             X(35)          '],
['copybook item        item                                                                                128                  03    Full-Address     45       128                           '],
['copybook item        item    item                                                                        115                  05    Address-Lines    45       115                           '],
['copybook item        item    item    item                                                                40                   07    Address-1        45       40             X(40)          '],
['copybook item        item    item    item                                                                40                   07    Address-2        85       40             X(40)          '],
['copybook item        item    item    item                                                                35                   07    Address-3        125      35             X(35)          '],
['copybook item        item    item                                                                        10                   05    Postcode         160      10             9(10)   true   '],
['copybook item        item    item                                                                        3                    05    State            170      3              XXX            '],
['copybook item        item                                                                                1                    03    Location-Active  173      1              X              ']
])
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass

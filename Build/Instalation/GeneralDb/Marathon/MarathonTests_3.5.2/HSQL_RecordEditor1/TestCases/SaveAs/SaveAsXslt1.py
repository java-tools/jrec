#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleXmlDir() + 'TestXml_01.xml')
        click('Edit')
    close()

    if window('Record Editor'):

##        select_menu('File>>Export via Xsl Transform>>TextXml_01_trans1.Xsl')
##        select_menu('File>>SaveAs_xml>>TextXml_01_trans1.Xsl')
        select_menu('File>>Export via Xsl Transform_2>>TextXml_01_trans1.Xsl')

        if frame('Export - TestXml_01.xml:0'):
        ##if frame('Export - TestXml_01.xml:0'):
            select('Edit Output File', 'true')
            click('save file')
        close()

        if frame('Tree View - TestXml_01.xml.xml:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'tr', '{10, Tree}')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[10],columns:[Tree]')
##            assert_p('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'Text', 'tr', '{10, Tree}')
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'My CD Collection'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Title'],
['', '', '', '', '', 'Artist'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Empire Burlesque'],
['', '', '', '', '', 'Bob Dylan'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Hide your heart'],
['', '', '', '', '', 'Bonnie Tyler'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Greatest Hits'],
['', '', '', '', '', 'Dolly Parton'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Still got the blues'],
['', '', '', '', '', 'Gary Moore'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Eros'],
['', '', '', '', '', 'Eros Ramazzotti'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'One night only'],
['', '', '', '', '', 'Bee Gees'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Sylvias Mother'],
['', '', '', '', '', 'Dr.Hook'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Maggie May'],
['', '', '', '', '', 'Rod Stewart'],
['', '', '', '', '', '\n'],
['', '', '', '', '', 'Romanza'],
['', '', '', '', '', 'Andrea Bocelli'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n'],
['', '', '', '', '', '\n']
])

            
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[10],columns:[Tree]')
            click('Close')
##            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[10],columns:[Tree]')
        close()

        if frame('Tree View - TestXml_01.xml:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'cd', '{10, Tree}')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[10],columns:[Tree]')
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', 'ISO-8859-1', '1.0', '', ''],
['', '', ' Edited by XMLSpy\xae ', '', '', ''],
['', '', '', '', '', '\n\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', 'Empire Burlesque'],
['', '', '', '', '', 'Bob Dylan'],
['', '', '', '', '', 'USA'],
['', '', '', '', '', 'Columbia'],
['', '', '', '', '', '10.90'],
['', '', '', '', '', '1985'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', 'Hide your heart'],
['', '', '', '', '', 'Bonnie Tyler'],
['', '', '', '', '', 'UK'],
['', '', '', '', '', 'CBS Records'],
['', '', '', '', '', '9.90'],
['', '', '', '', '', '1988'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', 'Greatest Hits'],
['', '', '', '', '', 'Dolly Parton'],
['', '', '', '', '', 'USA'],
['', '', '', '', '', 'RCA'],
['', '', '', '', '', '9.90'],
['', '', '', '', '', '1982'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', 'Still got the blues'],
['', '', '', '', '', 'Gary Moore'],
['', '', '', '', '', 'UK'],
['', '', '', '', '', 'Virgin records'],
['', '', '', '', '', '10.20'],
['', '', '', '', '', '1990'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t'],
['', '', '', '', '', '\n\t\t']
])
        close()

##        window_closed('Record Editor')
    close()

    pass

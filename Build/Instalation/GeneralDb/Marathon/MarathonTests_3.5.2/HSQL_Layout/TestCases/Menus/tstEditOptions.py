#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Layout Definitions'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Properties')
            assert_p('net.sf.RecordEditor.editProperties.EditPropertiesPnl_11', 'Enabled', 'true')
            assert_p('lbl:Space to be left at the bottom of the screen.', 'Text', 'Space to be left at the bottom of the screen.')
            assert_p('lbl:Space to be left at the top of the screen.', 'Text', 'Space to be left at the top of the screen.')
            assert_p('lbl:Space to be left at the left of the screen.', 'Text', 'Space to be left at the left of the screen.')
            assert_p('lbl:Space to be left at the Right of the screen.', 'Text', 'Space to be left at the Right of the screen.')
            select('JTabbedPane_10', 'Directories')
            assert_p('lbl:Directory holding the help files', 'Text', 'Directory holding the help files')
            assert_p('JEditorPane_14', 'Text', '\r\n    <html>\r\n        <head>\r\n        </head>\r\n        <body>\r\n            <h1>\r\n                Directories\r\n            </h1>\r\n            The properties on this panel are for the various directories used by the\r\n            <b>\r\n                RecordEditor\r\n            </b>\r\n        </body>\r\n    </html>\n')
            assert_p('lbl:Velocity Template directory (Copybooks)', 'Text', 'Velocity Template directory (Copybooks)')
            select('JTabbedPane_10', 'Other Options')
            assert_p('JEditorPane_14', 'Text', '\r\n    <html>\r\n        <head>\r\n        </head>\r\n        <body>\r\n            <h1>\r\n                Other Properties\r\n            </h1>\r\n            This panels lists the other non database Properties\r\n        </body>\r\n    </html>\n')
            assert_p('JTextField_17', 'Text', 'dd.MMM.yyyy')
            assert_p('lbl:Date Format', 'Text', 'Date Format')
            assert_p('lbl:Significant chars 2', 'Text', 'Significant chars 2')
            window_closed('Record Editor Options Editor')
        close()

##        window_closed('Record Layout Definitions')
    close()

    pass
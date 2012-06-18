#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Protocol Buffer Editor'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Properties')
            select('JTabbedPane_10', 'Behaviour')
##            select('Delete Selected with delete key', 'false')
            select('Show all export panels', 'true')
            select('Delete Selected Rows using the delete key', 'false')
            select('Warn the user before deleteing Selected Rows using the delete key', 'false')
            select('Delete Selected Rows using the delete key', 'true')
            select('Warn the user before deleteing Selected Rows using the delete key', 'true')


            click('Save')
##            select('JTabbedPane_12', 'File Options')
##            click('JButton_35')
##            window_closed('Record Editor Options Editor')
        close()

##        window_closed('Record Editor')
    close()

    pass

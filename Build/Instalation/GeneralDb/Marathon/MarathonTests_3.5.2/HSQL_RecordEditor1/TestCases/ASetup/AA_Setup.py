#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Properties')
            select('JTabbedPane_10', 'Other Options')

            select('Bring log to Front', 'false')
            select('Show all export panels', 'true')

            select('Warn on Structure change', 'false')

            select('Test Mode', 'true')

##            select('Delete Selected with delete key', 'true')
            select('Delete Selected Rows using the delete key', 'false')
            select('Warn the user before deleteing Selected Rows using the delete key', 'true')

            
            select('Load In background', 'false')
            select('JTabbedPane_9', 'Looks')

            select('Look and Feel', 'Default')
            click('Save')
            ##click('JButton_35')
            window_closed('Record Editor Options Editor')
        close()

    close()

    pass

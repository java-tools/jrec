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

            select('Delete Selected Rows using the delete key', 'false')
            select('Warn the user before deleteing Selected Rows using the delete key', 'true')

            click('Save')
        close()

    close()

    pass

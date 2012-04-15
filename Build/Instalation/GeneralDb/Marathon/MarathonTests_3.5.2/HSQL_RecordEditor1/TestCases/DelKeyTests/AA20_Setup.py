#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Properties')
            select('JTabbedPane_10', 'Behaviour')
            ##select('Warn user with delete key', 'false')
            select('Warn the user before deleteing Selected Rows using the delete key', 'true')
            select('Warn the user before deleteing Selected Rows using the delete key', 'false')

            click('Save')
        close()

    close()

    pass

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
            select('Default to prefered layout', 'true')
            click('Save')
            window_closed('Record Editor Options Editor')
        close()

##        window_closed('Record Editor')
    close()

    pass

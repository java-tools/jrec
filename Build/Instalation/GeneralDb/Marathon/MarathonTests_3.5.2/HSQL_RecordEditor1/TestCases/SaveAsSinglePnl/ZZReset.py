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
            select('Show all export panels', 'true')

            select('JTabbedPane_10', 'Test')
            select('Use New Tree Expansion_2', 'false')
            click('Save')
            ##click('JButton_51')
            window_closed('Record Editor Options Editor')
        close()

##        window_closed('Record Editor')
    close()

    pass

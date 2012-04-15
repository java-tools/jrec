#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Properties')
            select('JTabbedPane_10', 'Test')
            select('Test Mode', 'true')
            select('Warn on Structure change', 'false')
            select('Load In background', 'false')
            select('Use New Tree Expansion_2', 'false')
            select('Search: All Fields', 'false')
            select('Add names to JComponents for use by testing tools_Chk', 'false')

            select('JTabbedPane_10', 'Behaviour')
            select('Bring log to Front', 'false')
            select('Default to prefered layout', 'false')
            select('Show all export panels', 'true')
            select('Delete Selected Rows using the delete key', 'false')


            select('JTabbedPane_9', 'Looks')

            select('Look and Feel', 'Default')
            click('Save')
            ##click('JButton_35')
            window_closed('Record Editor Options Editor')
        close()

    close()

    pass

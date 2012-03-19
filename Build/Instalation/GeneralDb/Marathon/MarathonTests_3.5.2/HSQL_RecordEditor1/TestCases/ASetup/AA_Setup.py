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
            select('Weather we are running automated Tests (Marathon ?) or not ', 'true')
            select('Bring Log to the Front if Data is written to it', 'false')
            select('Warn the user if Binary-Fields and Structure=Default', 'false')
            select('JTabbedPane_10', 'Big Model Options')
            select('Load File in Background thread', 'false')
            select('JTabbedPane_9', 'Looks')
            select('Look and Feel', 'Default')
            click('Save')
            window_closed('Record Editor Options Editor')
        close()

##        window_closed('Record Editor')
    close()

    pass
#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        click('Preferences')

        if window('Record Editor Options Editor'):
            select('JTabbedPane_9', 'Jars')
            select('JTabbedPane_10', 'Optional Jars')
            select('JTable_17', 'rows:[0],columns:[Jar]')
            select('JTable_17', 'rows:[3],columns:[Jar]')
            select('Jar', 'C:\\JavaPrograms\\Marathon\\Marathon\\marathon_3.1.5.2\\jython\\jython.jar')


            select('JTable_17', 'rows:[0],columns:[Jar]')
            click('Save')
##            click('JButton_36')
            window_closed('Record Editor Options Editor')
        close()
    close()

    pass

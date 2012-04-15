#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'wx1%')
            select('Description', '%')
            click('Delete the Current Record')
        close()

##        window_closed('Record Editor')
    close()
    pass

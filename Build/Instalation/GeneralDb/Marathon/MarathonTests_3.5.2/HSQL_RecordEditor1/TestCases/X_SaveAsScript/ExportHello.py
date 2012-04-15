#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', 'C:\\Users\\mum/RecordEditor_HSQL\\SampleFiles/DTAR020_tst1.bin')
        click('Edit')
    close()

    if window('Record Editor'):
        select_menu('File>>Export via Script>>Hello.js')

        if frame('Export - DTAR020_tst1.bin:0'):
            click('save file')
        close()

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Hello, world!')
            assert_p('OptionPane.button', 'Text', 'OK')
            click('OptionPane.button')
        close()

        if frame('Table:  - DTAR020_tst1.bin:0'):
            click('Close')
        close()

    close()

    pass
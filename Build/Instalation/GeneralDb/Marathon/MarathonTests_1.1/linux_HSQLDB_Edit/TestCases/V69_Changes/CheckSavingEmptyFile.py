useFixture(default)

def test():
	from Modules import commonBits

	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020~~.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		
		click('Edit1')
		select('Table', '11', 'Data,0')
		select('Table', '0.11', 'Data,5')
		select('Table', 'cell:Data,3(0)')
		click('Save1')
		click('Save1')
		if commonBits.isNimbusLook():
			click('InternalFrameTitlePane.closeButton')
			click('InternalFrameTitlePane.closeButton')
		else:
			click('BasicInternalFrameTitlePane$NoFocusButton2')
			click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Edit1')
		assert_p('Table', 'Content', '[[11, 0, 0, 0, 0, 0.11]]')
		click('Delete2')
		click('Save1')
		if commonBits.isNimbusLook():
			click('InternalFrameTitlePane.closeButton')
		else:
			click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Edit1')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, 8, , , ], [STORE-NO, 9, 2, 0, , ], [DATE, 11, 4, 0, , ], [DEPT-NO, 15, 2, 0, , ], [QTY-SOLD, 17, 5, 0, , ], [SALE-PRICE, 22, 6, 0.00, , ]]')
	close()

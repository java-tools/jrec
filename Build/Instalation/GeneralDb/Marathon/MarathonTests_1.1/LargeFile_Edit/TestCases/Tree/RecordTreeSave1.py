useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		click(commonBits.fl('Choose File'))

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_PODownload_20041231.txt')
			click('Open')
		close()

		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Record Based Tree'))
		select('Table', 'ams PO Download: Header', commonBits.fl('Parent Record') + ',0')
		select('Table', 'ams PO Download: Detail', commonBits.fl('Parent Record') + ',2')
		select('Table', 'cell:' + commonBits.fl('Parent Record') + ',2(0)')
		commonBits.save1(click)
		##select('FileChooser', commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')
		commonBits.selectFileName(select, commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')

		click('Save1')




		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Create Record Tree'))
		click(commonBits.fl('Build'))
		select('JTreeTable', 'cell:PO,0(286225)')
		assert_p('JTreeTable', 'Text', '286225', 'PO,0')
		select('JTreeTable', 'cell:PO,0(286225)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:PO,0(286225)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Execute Record Tree'))
		##select('FileChooser',  commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')
		commonBits.selectFileName(select, commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')

		click(commonBits.fl('Run Dialog'))
		select('Table', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')
		assert_p('Table', 'Content', '[[ams PO Download: Detail, 1], [ams PO Download: Header, -1], [ams PO Download: Allocation, 0]]')
		select('Table', 'cell:' + commonBits.fl('Record') + ',1(ams PO Download: Header)')
		click(commonBits.fl('Build'))
		select('JTreeTable', 'cell:PO,0(286225)')
		assert_p('JTreeTable', 'Text', '222227', 'PO,1')
		select('JTreeTable', 'cell:PO,3(294915)')
		assert_p('JTreeTable', 'Text', '294915', 'PO,3')
		select('JTreeTable', 'cell:PO,3(294915)')
		select('JTreeTable', 'cell:PO,5(295139)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:PO,5(295139)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Execute Record Tree'))
		##select('FileChooser', commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')
		commonBits.selectFileName(select, commonBits.userDir() +  'RecordTree'  + commonBits.fileSep() + 'xx')
		click(commonBits.fl('Run')
)
		select('JTreeTable', 'cell:Sequence Number,2(45.351)')
		assert_p('JTreeTable', 'Text', '45.352', 'Sequence Number,3')
		select('JTreeTable', 'cell:PO,5(295139)')
		assert_p('JTreeTable', 'Content', '[[, , H1, 45.349, 6060, 286225, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.350, 6228, 222227, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.351, 6228, 222243, 040909, , 00, , 200, 050102, 050107, , , , LADIES KNI, C, FT], [, , H1, 45.352, 5341, 294915, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.353, 5341, 294987, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.354, 5341, 295139, 041013, , 00, , 475, 041231, 050107, P, , , WOMENS SHO, C, FT], [, , H1, 45.355, 5341, 303662, 041110, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT], [, , H1, 45.356, 5341, 304100, 041111, , 00, , 310, 041231, 050107, P, , , YOUTH SHOE, C, FT]]')
		select('JTreeTable', 'cell:PO,5(295139)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

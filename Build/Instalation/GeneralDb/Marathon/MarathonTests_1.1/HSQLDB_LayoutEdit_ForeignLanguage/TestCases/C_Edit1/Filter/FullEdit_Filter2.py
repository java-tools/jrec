useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'
	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'ams Store')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Table1', 'Loc Nbr', commonBits.fl('Field') + ',0')
		select('Table1', '1', commonBits.fl('Value') + ',0')
		select('Table1', 'Loc Nbr', commonBits.fl('Field') + ',1')
		select('Table1', '2', commonBits.fl('Value') + ',1')
		select('Table1', 'cell:' + commonBits.fl('Value') + ',1()')
		commonBits.filter(click)

		select('Table', 'cell:10 - 35|Loc Name,5(Strathpine - Not Yet Open)')
		assert_p('Table', 'Text', 'Strathpine - Not Yet Open', '10 - 35|Loc Name,5')
		select('Table', 'cell:10 - 35|Loc Name,6(Robina)')
		assert_p('Table', 'Content', '[[TAR, 5129, ST, Macquarie, Maquarie Centre, Dock 2, Talavera Road,  North Ryde, 2113, NSW, A], [TAR, 5162, ST, Tuggeranong, Hyperdome Shopping Centre, Pitman Street, Tuggernong, 2900, NSW, A], [TAR, 5012, ST, Ringwood, Ringwood, Seymour Street, Ringwood, 3134, VIC, A], [TAR, 5152, ST, Mornington, Mornington, Cnr Franklin & Gordon Streets, Momington, 3931, VIC, A], [TAR, 5132, ST, Glenorchy, Derwent Park, Lot 12-13 Riverside Industrial Estate, Derwent park, 7009, TAS, A], [TAR, 5172, ST, Strathpine - Not Yet Open, Chermside Shopping Centre, G ie Rd, , Strathpine, 4032, QLD, A], [TAR, 5126, ST, Robina, Robina Town Centre, Robina Parkway, Robina, 4226, QLD, A], [TAR, 5021, ST, Tea Tree Plaza, Myer Tea Tree Plaza, 976 North East Road, Modbury, 5092, SA, A], [TAR, 5127, ST, Marion, Adelaide Airport, Frank Collopy Court, Adelaide Airport, 5950, SA, A], [TAR, 5142, ST, Hollywood Plaza, Hollywood Plaza Shopping Centre, Winzor Road, Salisbury Downs, 5108, SA, A], [TAR, 5128, ST, Rockingham, Rockingham Shopping Centre., Cnr. Read Street & Council Avenue, Rockingham, 6168, WA, A], [TAR, 5192, ST, Wetherill Park, , , , , , A]]')
		select('Table', 'cell:10 - 35|Loc Name,7(Tea Tree Plaza)')
		assert_p('Table', 'RowCount', '12')
		select('Table', 'cell:10 - 35|Loc Name,7(Tea Tree Plaza)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()


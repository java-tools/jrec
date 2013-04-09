useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'utf8a_Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'utf8_ams Store')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Table', 'false', commonBits.fl('Include') + ',0')
		select('Table', 'false', commonBits.fl('Include') + ',2')
		select('Table', 'false', commonBits.fl('Include') + ',5')
		select('Table', 'false', commonBits.fl('Include') + ',6')
		select('Table', 'false', commonBits.fl('Include') + ',7')
		#select('Table1', 'cell:' + commonBits.fl('Include') + ',7(false)')
		select('Table1', 'Loc Nbr', commonBits.fl('Field') + ',0')
		select('Table1', '1', commonBits.fl('Value') + ',0')
		select('Table1', 'Loc Nbr', commonBits.fl('Field') + ',1')
		select('Table1', '2', commonBits.fl('Value') + ',1')
		select('Table1', 'cell:' + commonBits.fl('Value') + ',1()')
		commonBits.filter(click)

		select('Table', 'cell:45 - 40|Loc Addr Ln1,4(Derwent Park)')
		assert_p('Table', 'Text', 'Derwent Park', '45 - 40|Loc Addr Ln1,4')
		select('Table', 'cell:45 - 40|Loc Addr Ln1,5(Chermside Shopping Centre, G ie Rd)')

##		assert_p('Table', 'Content', '[[5129, Macquarie, Maquarie Centre, NSW, A], [5162, Tuggeranong, Hyperdome Shopping Centre, NSW, A], [5012, Ringwood, Ringwood, VIC, A], [5152, Mornington, Mornington, VIC, A], [5132, Glenorchy, Derwent Park, TAS, A], [5172, Strathpine - Not Yet Open, Chermside Shopping Centre, G ie Rd, QLD, A], [5126, Robina, Robina Town Centre, QLD, A], [5021, Tea Tree Plaza, Myer Tea Tree Plaza, SA, A], [5127, Marion, Adelaide Airport, SA, A], [5142, Hollywood Plaza, Hollywood Plaza Shopping Centre, SA, A], [5128, Rockingham, Rockingham Shopping Centre., WA, A], [5192, Wetherill Park, , , A]]')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[5129, Macquarie, Maquarie Centre, NSW], [5162, Tuggeranong, Hyperdome Shopping Centre, NSW], [5012, Ringwood, Ringwood, VIC], [5152, Mornington, Mornington, VIC], [5132, Glenorchy, Derwent Park, TAS], [5172, Strathpine - Not Yet Open, Chermside Shopping Centre, G ie Rd, QLD], [5126, Robina, Robina Town Centre, QLD], [5021, Tea Tree Plaza, Myer Tea Tree Plaza, SA], [5127, Marion, Adelaide Airport, SA], [5142, Hollywood Plaza, Hollywood Plaza Shopping Centre, SA], [5128, Rockingham, Rockingham Shopping Centre., WA], [5192, Wetherill Park, , ]]')
		else:
			assert_p('Table', 'Content', '[[5129, Macquarie, Maquarie Centre, NSW, A], [5162, Tuggeranong, Hyperdome Shopping Centre, NSW, A], [5012, Ringwood, Ringwood, VIC, A], [5152, Mornington, Mornington, VIC, A], [5132, Glenorchy, Derwent Park, TAS, A], [5172, Strathpine - Not Yet Open, Chermside Shopping Centre, G ie Rd, QLD, A], [5126, Robina, Robina Town Centre, QLD, A], [5021, Tea Tree Plaza, Myer Tea Tree Plaza, SA, A], [5127, Marion, Adelaide Airport, SA, A], [5142, Hollywood Plaza, Hollywood Plaza Shopping Centre, SA, A], [5128, Rockingham, Rockingham Shopping Centre., WA, A], [5192, Wetherill Park, , , A]]')

		select('Table', 'cell:45 - 40|Loc Addr Ln1,7(Myer Tea Tree Plaza)')
		assert_p('Table', 'RowCount', '12')
		select('Table', 'cell:10 - 35|Loc Name,2(Ringwood)')
		rightclick('Table', '10 - 35|Loc Name,2')
		select_menu( commonBits.fl('Edit Record'))
	##	select('Table1', 'cell:10 - 35|Loc Name,2(Ringwood)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',1(Ringwood)')

		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5012, 5012], [Loc Name, 10, 35, Ringwood, Ringwood], [Loc Addr Ln1, 45, 40, Ringwood, Ringwood], [Loc State, 170, 3, VIC, VIC]]')
		else:
			assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5012, 5012], [Loc Name, 10, 35, Ringwood, Ringwood], [Loc Addr Ln1, 45, 40, Ringwood, Ringwood], [Loc State, 170, 3, VIC, VIC], [Loc Actv Ind, 173, 1, A, A]]')

		select('Table', 'cell:' + commonBits.fl('Data') + ',3(VIC)')
		assert_p('Table', 'Text', 'VIC', commonBits.fl('Data') + ',3')
		select('Table', 'cell:' + commonBits.fl('Data') + ',2(Ringwood)')

		if commonBits.isMissingCol():
			assert_p('Table', 'RowCount', '4')
		else:
			assert_p('Table', 'RowCount', '5')
	close()

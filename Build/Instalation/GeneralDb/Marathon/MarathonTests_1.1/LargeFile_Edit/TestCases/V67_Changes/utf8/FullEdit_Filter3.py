useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'utf8a_Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'utf8_ams Store')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Table1', 'Loc Type', commonBits.fl('Field') + ',0')
		select('Table1', ' = ', commonBits.fl('Operator') + ',0')
		select('Table1', 'dc', commonBits.fl('Value') + ',0')
		select('Table1', 'Loc Nbr', commonBits.fl('Field') + ',1')
		select('Table1', '8', commonBits.fl('Value') + ',1')
		select('Table1', 'cell:' + commonBits.fl('Value') + ',1()')
		select('Table', 'false', commonBits.fl('Include') + ',0')
		select('Table', 'false', commonBits.fl('Include') + ',2')
		select('Table', 'false', commonBits.fl('Include') + ',7')
		select('Table', 'false', commonBits.fl('Include') + ',6')
		select('Table', 'false', commonBits.fl('Include') + ',5')
		#select('Table1', 'cell:' + commonBits.fl('Include') + ',5(false)')
		commonBits.filter(click)

		select('Table', 'cell:10 - 35|Loc Name,1(VIC West Ad Support)')

##		assert_p('Table', 'Content', '[[5839, DC - Taras Ave, , VIC, A], [5850, VIC West Ad Support, , VIC, A], [5853, NSW North Sydney Ad Support, , , A], [5866, WA Ad Support, , , A], [5887, QLD Ad Support, , , A], [5888, SA Ad Support, , , A], [5895, VIC East Ad Support, , , A], [5897, Sydney Gate DC, No 2 Sydney Gate, NSW, A], [5958, State Warehouse  WA, Target State Warehouse (WA) FCL, WA, A], [5968, Beverly DC, Beverly DC, SA, A]]')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[5839, DC - Taras Ave, , VIC], [5850, VIC West Ad Support, , VIC], [5853, NSW North Sydney Ad Support, , ], [5866, WA Ad Support, , ], [5887, QLD Ad Support, , ], [5888, SA Ad Support, , ], [5895, VIC East Ad Support, , ], [5897, Sydney Gate DC, No 2 Sydney Gate, NSW], [5958, State Warehouse  WA, Target State Warehouse (WA) FCL, WA], [5968, Beverly DC, Beverly DC, SA]]')
		else:
			assert_p('Table', 'Content', '[[5839, DC - Taras Ave, , VIC, A], [5850, VIC West Ad Support, , VIC, A], [5853, NSW North Sydney Ad Support, , , A], [5866, WA Ad Support, , , A], [5887, QLD Ad Support, , , A], [5888, SA Ad Support, , , A], [5895, VIC East Ad Support, , , A], [5897, Sydney Gate DC, No 2 Sydney Gate, NSW, A], [5958, State Warehouse  WA, Target State Warehouse (WA) FCL, WA, A], [5968, Beverly DC, Beverly DC, SA, A]]')

		select('Table', 'cell:10 - 35|Loc Name,7(Sydney Gate DC)')
		assert_p('Table', 'Text', 'cell:10 - 35|Loc Name,7(Sydney Gate DC)')
		select('Table', 'cell:10 - 35|Loc Name,5(SA Ad Support)')
		assert_p('Table', 'RowCount', '10')
		select('Table', 'cell:10 - 35|Loc Name,7(Sydney Gate DC)')
		rightclick('Table', '10 - 35|Loc Name,7')
		select_menu( commonBits.fl('Edit Record'))
	##	select('Table1', 'cell:10 - 35|Loc Name,7(Sydney Gate DC)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',1(Sydney Gate DC)')

##		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5897, 5897], [Loc Name, 10, 35, Sydney Gate DC, Sydney Gate DC], [Loc Addr Ln1, 45, 40, No 2 Sydney Gate, No 2 Sydney Gate], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5897, 5897], [Loc Name, 10, 35, Sydney Gate DC, Sydney Gate DC], [Loc Addr Ln1, 45, 40, No 2 Sydney Gate, No 2 Sydney Gate], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5897, 5897], [Loc Name, 10, 35, Sydney Gate DC, Sydney Gate DC], [Loc Addr Ln1, 45, 40, No 2 Sydney Gate, No 2 Sydney Gate], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')

		select('Table', 'cell:' + commonBits.fl('Data') + ',3(NSW)')
		assert_p('Table', 'Text', 'cell:' + commonBits.fl('Data') + ',3(NSW)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',1(Sydney Gate DC)')

		if commonBits.isMissingCol():
			assert_p('Table', 'RowCount', '4')
		else:
			assert_p('Table', 'RowCount', '5')

	close()

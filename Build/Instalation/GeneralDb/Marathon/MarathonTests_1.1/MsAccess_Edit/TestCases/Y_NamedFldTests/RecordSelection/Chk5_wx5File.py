useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'wx5File.txt')
		select('Record Layout_Txt', 'wx5File')
		click('Edit1')
		select('LineList.FileDisplay_JTbl', 'cell:3 - 2|RecordType2,0(HD)')
		rightclick('LineList.FileDisplay_JTbl', '1 - 1|RecordType1,0')
		select('LineList.FileDisplay_JTbl', 'cell:3 - 2|RecordType2,0(HD)')
		rightclick('LineList.FileDisplay_JTbl', '3 - 2|RecordType2,0')
		select_menu('Edit Record')
		select('LineList.FileDisplay_JTbl', 'cell:3 - 2|RecordType2,0(HD)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, H, H], [RecordType2, 3, 2, HD, HD], [RunDate, 5, 8, 20100101, 20100101], [RunNumber, 13, 8, 1, 00000001]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, HD, HD], [Dept, 5, 4, 121, 121], [Product, 9, 8, 12121, 00012121]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 01, 01], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 02, 02], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01 1')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 05, 05], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01 2')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 07, 07], [Field71, 5, 6, 21, 21], [Field72, 11, 9, 2223, 00002223], [Field73, 26, 3, 123, 123]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd07')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 02, 02], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01 1')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 01, 01], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 07, 07], [Field71, 5, 6, 21, 21], [Field72, 11, 9, 1234, 000001234], [Field73, 26, 3, 123, 123]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd07')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 01, 01], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, HD, HD], [Dept, 5, 4, 121, 121], [Product, 9, 8, 12121, 00012121]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProdHead')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 01, 01], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 02, 02], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd01 1')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 05, 05], [Field11, 5, 4, 21, 21], [Field12, 9, 8, 22, 00000022], [Field13, 17, 3, 23, 23]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, P, P], [RecordType2, 3, 2, 07, 07], [Field71, 5, 6, 21, 21], [Field72, 11, 9, 2332, 000002332], [Field73, 26, 3, 123, 123]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwProd07')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[RecordType1, 1, 1, T, T], [RecordType2, 3, 2, TR, TR], [Count, 17, 8, 123, 00000123]]')
		assert_p('LineFrame.Layouts_Txt', 'Text', 'wwTrailer')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('LineList.FileDisplay_JTbl', 'cell:3 - 2|RecordType2,0(HD)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()

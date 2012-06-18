useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		click('Edit1')
		click('Find1')
		assert_p('Label', 'Text', 'Search For')
		assert_p('Label1', 'Text', 'Replace With')
		assert_p('Label2', 'Text', 'Record Layout')
		assert_p('Label3', 'Text', 'Field')
		assert_p('Label5', 'Text', 'Direction')
		assert_p('Label6', 'Text', 'Ignore Case')
		assert_p('Find1', 'Text', 'Find')
		assert_p('Replace', 'Text', 'Replace')
		assert_p('Replace Find', 'Text', 'Replace Find')
		assert_p('Replace All', 'Text', 'Replace All')
		assert_p('ComboBox2', 'Content', '[[Forward, Backward]]')
##		assert_p('ComboBox1', 'Content', '[[Contains,  = , Doesn\'t Contain,  <> , >, >=, <, <= ,  = (Numeric), > (Text), >= (Text), < (Text), <= (Text)]]')
		assert_p('ComboBox1', 'Content', '[[Contains,  = , Doesn\'t Contain,  <> , Starts With, >, >=, <, <= , = (Numeric), > (Text), >= (Text), < (Text), <= (Text)]]')
		assert_p('ComboBox', 'Content', '[[, All Fields, Brand_Id, Loc_Nbr, Loc_Type, Loc_Name, Loc_Addr_Ln1, Loc_Addr_Ln2, Loc_Addr_Ln3, Loc_Postcode, Loc_State, Loc_Actv_Ind]]')
#		assert_p('LayoutCombo', 'Content', '[Locations]]')

		assert_p('LayoutCombo', 'Content', '[[Locations]]')
	close()

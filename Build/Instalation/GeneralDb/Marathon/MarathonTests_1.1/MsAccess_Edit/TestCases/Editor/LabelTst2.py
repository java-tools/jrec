useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click(commonBits.fl('Edit') + '1')
		click('Find1')
		assert_p('Label', 'Text', commonBits.fl('Search For'))
		assert_p('Label1', 'Text', commonBits.fl('Replace With'))
		assert_p('Label2', 'Text', commonBits.fl('Record Layout'))
		assert_p('Label3', 'Text', commonBits.fl('Field'))
		assert_p('Label5', 'Text', commonBits.fl('Direction'))
		assert_p('Label6', 'Text', commonBits.fl('Ignore Case'))

		assert_p(commonBits.fl('Find') + ' >>', 'Text', commonBits.fl('Find') + ' >>')

#		if commonBits.isTstLanguage():
#			assert_p(commonBits.fl('Find'), 'Text', commonBits.fl('Find'))
#		else:
#			assert_p('Find1', 'Text', 'Find')

		assert_p(commonBits.fl('Replace'), 'Text', commonBits.fl('Replace'))
		assert_p(commonBits.fl('Replace Find'), 'Text', commonBits.fl('Replace Find'))
		assert_p(commonBits.fl('Replace All'), 'Text', commonBits.fl('Replace All'))
		assert_p('ComboBox2', 'Content', '[[' + commonBits.fl('Forward') + ', ' + commonBits.fl('Backward') + ']]')
##		assert_p('ComboBox1', 'Content', '[[Contains,  = , Doesn\'t Contain,  <> , >, >=, <, <= ,  = (Numeric), > (Text), >= (Text), < (Text), <= (Text)]]')
		assert_p('ComboBox1', 'Content', '[[' + commonBits.fl('Contains') + ',  = , ' + commonBits.fl('Doesn\'t Contain') + ',  <> , ' + commonBits.fl('Starts With') + ', >, >=, <, <= , ' + commonBits.fl('= (Numeric)') + ', ' + commonBits.fl('> (Text)') + ', ' + commonBits.fl('>= (Text)') + ', ' + commonBits.fl('< (Text)') + ', ' + commonBits.fl('<= (Text)') + ']]')

##		assert_p('ComboBox1', 'Content', '[[' + commonBits.fl('Contains') + ',  = , ' + commonBits.fl('Doesn\'t Contain') + ',  <> , ' + commonBits.fl('Starts With') + ', >, >=, <, <= ,' + commonBits.fl(' = (Numeric)') + ',' + commonBits.fl(' > (Text)') + ',' + commonBits.fl(' >= (Text)') + ',' + commonBits.fl(' < (Text)') + ',' + commonBits.fl(' <= (Text)') + ']]')


		assert_p('ComboBox', 'Content', '[[, ' + commonBits.fl('All Fields') + ', Brand Id, Loc Nbr, Loc Type, Loc Name, Loc Addr Ln1, Loc Addr Ln2, Loc Addr Ln3, Loc Postcode, Loc State, Loc Actv Ind]]')
		assert_p('LayoutCombo', 'Content', '[[ams Store]]')
	close()

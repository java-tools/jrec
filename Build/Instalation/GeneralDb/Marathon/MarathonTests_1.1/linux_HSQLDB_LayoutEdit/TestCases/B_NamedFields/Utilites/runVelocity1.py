useFixture(runVelocity)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window(''):
		assert_p('Label', 'Text', commonBits.fl('Input File'))
		assert_p('Label1', 'Text', commonBits.fl('Template File'))
		assert_p('Label2', 'Text', commonBits.fl('Data Base'))
		assert_p('Label3', 'Text', commonBits.fl('System'))
		assert_p('Label4', 'Text', commonBits.fl('Record Layout'))
		assert_p('Label5', 'Text', commonBits.fl('Description'))
		assert_p('Label6', 'Text', commonBits.fl('Output File'))
		assert_p(commonBits.fl('Choose Output File'), 'Text', commonBits.fl('Choose Output File'))
		assert_p(commonBits.fl('Reload from DB'), 'Text', commonBits.fl('Reload from DB'))
		assert_p(commonBits.fl('Choose Velocity Template'), 'Text', commonBits.fl('Choose Velocity Template'))
		assert_p(commonBits.fl('Choose File'), 'Text', commonBits.fl('Choose File'))
		
	close()

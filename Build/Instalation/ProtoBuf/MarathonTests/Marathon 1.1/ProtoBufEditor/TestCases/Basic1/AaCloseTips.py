useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Tip of the Day'):
		select('Show tips on startup', 'false')
		click('Close')
	close()

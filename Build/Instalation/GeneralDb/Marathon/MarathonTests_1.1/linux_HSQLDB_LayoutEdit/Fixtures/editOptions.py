from net.sf.RecordEditor.editProperties import EditOptions

class Fixture:
	def start_application(self):
		args = []
		EditOptions.main(args)

## "javaw.exe" -jar "C:\JavaPrograms\RecordEdit\HSQL\lib\run.jar" net.sf.RecordEditor.editProperties.EditOptions

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


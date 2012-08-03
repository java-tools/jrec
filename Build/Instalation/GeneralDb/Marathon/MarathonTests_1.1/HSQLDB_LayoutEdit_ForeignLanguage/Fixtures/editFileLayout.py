from net.sf.RecordEditor.editFileLayout import Edit

class Fixture:
	def start_application(self):
		args = []
		Edit.main(args)

##"javaw.exe" -jar "C:\JavaPrograms\RecordEdit\HSQL\lib\run.jar" net.sf.RecordEditor.editFileLayout.Edit

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


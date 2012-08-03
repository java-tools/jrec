from net.sf.RecordEditor.copy import CopyDBLayout

class Fixture:
	def start_application(self):
		args = []
		CopyDBLayout.main(args)
## "javaw.exe" -jar "C:\JavaPrograms\RecordEdit\HSQL\lib\run.jar" net.sf.RecordEditor.copy.CopyDBLayout
	def teardown(self):
		pass

	def setup(self):
		self.start_application()


from net.sf.RecordEditor.edit.util import RunVelocityGui

class Fixture:
	def start_application(self):
		args = []
		RunVelocityGui.main(args)

## "C:\Program Files (x86)\Java\jre7\bin\javaw.exe" -jar "C:\JavaPrograms\RecordEdit\HSQL\lib\run.jar" net.sf.RecordEditor.edit.util.RunVelocityGui


	def teardown(self):
		pass

	def setup(self):
		self.start_application()


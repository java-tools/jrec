from net.sf.RecordEditor.layoutWizard import WizardFileMenu

class Fixture:
	def start_application(self):
		args = []
		WizardFileMenu.main(args)

#"javaw.exe" -jar "C:\JavaPrograms\RecordEdit\HSQLDB/lib/run.jar" net.sf.RecordEditor.layoutWizard.WizardFileMenu


	def teardown(self):
		pass

	def setup(self):
		self.start_application()


from net.sf.RecordEditor import RunCsvEditor

class Fixture:
	def start_application(self):
		args = []
		RunCsvEditor.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


from net.sf.RecordEditor.edit import FullEditor

class Fixture:
	def start_application(self):
		args = []
		FullEditor.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


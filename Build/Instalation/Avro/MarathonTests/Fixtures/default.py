from net.sf.RecordEditor import RunAvroEditor

class Fixture:
	def start_application(self):
		args = []
		RunAvroEditor.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


from net.sf.RecordEditor.diff import CompareDBLayout

class Fixture:
	def start_application(self):
		args = []
		CompareDBLayout.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


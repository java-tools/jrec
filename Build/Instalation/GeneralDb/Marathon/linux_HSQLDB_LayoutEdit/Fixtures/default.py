from net.sf.RecordEditor.layoutEd import LayoutEdit

class Fixture:
	def start_application(self):
		args = []
		LayoutEdit.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


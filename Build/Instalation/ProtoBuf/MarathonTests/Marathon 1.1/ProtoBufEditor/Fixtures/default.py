from net.sf.RecordEditor import RunProtoBufferEditor

class Fixture:
	def start_application(self):
		args = []
		RunProtoBufferEditor.main(args)

	def teardown(self):
		pass

	def setup(self):
		self.start_application()


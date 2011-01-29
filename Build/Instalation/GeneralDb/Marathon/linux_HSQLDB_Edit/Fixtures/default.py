from net.sf.RecordEditor.edit import FullEditor

import time

class Fixture:
	def start_application(self):
		args = []
		FullEditor.main(args)

	def teardown(self):
		pass
##		time.sleep(2)

	def setup(self):
##		time.sleep(1)
		self.start_application()


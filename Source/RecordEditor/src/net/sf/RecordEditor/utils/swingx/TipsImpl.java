package net.sf.RecordEditor.utils.swingx;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JComponent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.SwingUtils;


public class TipsImpl {


	protected static void startTips(JComponent parent, String tipfile, String tipVariable) {
		try {
			Properties tipsProperties = new Properties();
			tipsProperties.load(new FileInputStream(tipfile));

			org.jdesktop.swingx.tips.TipOfTheDayModel model = org.jdesktop.swingx.tips.TipLoader.load(tipsProperties);
			org.jdesktop.swingx.JXTipOfTheDay tips = new org.jdesktop.swingx.JXTipOfTheDay(model);

			long startTip = System.currentTimeMillis() % Math.max(1, model.getTipCount());

			Dimension d = tips.getPreferredSize();

			if (d.height > 0) {
				d.height = d.height + SwingUtils.CHAR_HEIGHT * 3;
				d.width = d.width + SwingUtils.CHAR_WIDTH * 5;

				tips.setPreferredSize(d);
			}

			for (int i = 0; i < startTip; i++) {
				tips.nextTip();
			}
			tips.showDialog(parent, new StartupHandler(tipVariable));
		} catch (IOException ioe) {
			Common.logMsgRaw("IO Error Loading Tips", ioe);
			ioe.printStackTrace();
		}
	}


	private static class StartupHandler implements org.jdesktop.swingx.JXTipOfTheDay.ShowOnStartupChoice {

		private String propertiesVarName;



		public StartupHandler(String propertiesVarName) {
			super();
			this.propertiesVarName = propertiesVarName;
		}

		@Override
		public void setShowingOnStartup(boolean showOnStartup) {
			String s = "N";
			if (showOnStartup) {
				s = "Y";
			}

			Parameters.setProperty(propertiesVarName, s);
		}

		@Override
		public boolean isShowingOnStartup() {
			return true;
		}
	}
}

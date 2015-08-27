package net.sf.RecordEditor.utils.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIDefaults;

import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class TabWithClosePnl extends JPanel {
    protected final JLabel  label    = new JLabel();
    private   final JButton closeBtn = new JButton("X");


    public TabWithClosePnl(String screenName, boolean addCloseBtn) {

        super.setBorder(BorderFactory.createEmptyBorder());
        label.setText(screenName);
        label.setOpaque(true);

        if (BasePanel.NAME_COMPONENTS || Common.TEST_MODE) {
        	label.setName(screenName);
        }
        add(label);

        if (Common.NIMBUS_LAF) {
        	UIDefaults def = new UIDefaults();
        	def.put("Button.contentMargins", new Insets(0,0,0,0));
        	closeBtn.putClientProperty("Nimbus.Overrides", def);

        	closeBtn.setFont(new Font("Monospaced", Font.PLAIN,  (SwingUtils.STANDARD_FONT_HEIGHT * 3) / 4));
        	closeBtn.setPreferredSize(new Dimension(SwingUtils.STANDARD_FONT_HEIGHT * 5 / 4,
        			SwingUtils.STANDARD_FONT_HEIGHT * 3 / 2));
        } else {
        	closeBtn.setFont(new Font("Monospaced", Font.PLAIN,  (SwingUtils.STANDARD_FONT_HEIGHT * 2) / 3));
        	closeBtn.setPreferredSize(new Dimension(SwingUtils.STANDARD_FONT_HEIGHT, SwingUtils.STANDARD_FONT_HEIGHT));
        }
        if (addCloseBtn) {
	        closeBtn.setMargin(new Insets(0,0,0,0));
	
	        add(closeBtn);
        }
    }

    public final void setCloseAction(ActionListener closeAction) {
    	closeBtn.addActionListener( closeAction );
    }

    public final void removeCloseAction(ActionListener closeAction) {
    	closeBtn.removeActionListener( closeAction );
    }

    public final void setBackground(Color bg) {
        super.setBackground(bg);

        if (label != null) {
            label.setBackground(bg);
        }
    }

    public final void setTabname(String name) {

        label.setText(name);

        label.revalidate();

        this.revalidate();
    }
}

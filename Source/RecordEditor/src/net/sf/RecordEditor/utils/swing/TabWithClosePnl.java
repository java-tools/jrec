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

import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class TabWithClosePnl extends JPanel {
    protected final JLabel  label    = new JLabel();
    private   final JButton closeBtn = new JButton("X");


    public TabWithClosePnl(String screenName) {

        super.setBorder(BorderFactory.createEmptyBorder());
        label.setText(screenName);
        label.setOpaque(true);

        if (BasePanel.NAME_COMPONENTS || Common.TEST_MODE) {
        	label.setName(screenName);
        }
        add(label);


        closeBtn.setFont(new Font("Monospaced", Font.PLAIN,  (SwingUtils.STANDARD_FONT_HEIGHT * 2) / 3));
        closeBtn.setPreferredSize(new Dimension(SwingUtils.STANDARD_FONT_HEIGHT, SwingUtils.STANDARD_FONT_HEIGHT));


        closeBtn.setMargin(new Insets(0,0,0,0));

        add(closeBtn);
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

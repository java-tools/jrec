/**
 *
 */
package net.sf.RecordEditor.re.install;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.re.editProperties.CommonCode;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * This screen warns the user they are about to overwrite an existing RecordEditor
 * install !!!
 *
 * @author Bruce Martin
 *
 */
public class InstallPnl4RunSql extends BasicWizardPnl implements IInstallerPnl, ActionListener {

    private String sqlDir = Parameters.getBaseDirectory() + "/SQL/";

	private InstallDetails details;
	private Connection connection = null;

	private static final JEditorPane tips = new JEditorPane(
    		"text/html",
    		LangConversion.convertId(LangConversion.ST_MESSAGE, "IP_TIP02",
    				 "<h2>Create DataBase Tables</h2>"
    			   + "This screen runs the SQL to create an load the backend SQL "
    			   + "tables used by the <b>RecordEditor</b>.<br>"
    			   + "You can either run everything at once (first option) "
    			   + "or run it one step at a time."
    		));

    private JButton bldAllTables    = new JButton(" + ");
    private JButton dropTbls        = new JButton(" + ");
    private JButton bldCreateTables = new JButton(" + ");
    private JButton bldRecordTables = new JButton(" + ");
    private JButton bldtblTables    = new JButton(" + ");

    private JTextArea sqlOutput     = new JTextArea();


	public InstallPnl4RunSql() {
		super(new BaseHelpPanel("IP2_Warn"));

		init_200_layoutScreen();
		init_300_listners();
	}

	private void init_200_layoutScreen() {

		panel.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		panel.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
	                BasePanel.FULL, BasePanel.FULL,
	                new JScrollPane(tips));

		panel.addMenuItem("Build Everything", bldAllTables);
		panel.setGap(BasePanel.GAP0);
		panel.addLine("    or", null);
		panel.setGap(BasePanel.GAP0);
		panel.addMenuItem(        "Drop Tables", dropTbls);
		panel.addMenuItem("Create Tables in DB", bldCreateTables);
		panel.addMenuItem( "Load Common Tables", bldtblTables);
		panel.addMenuItem("Load Record Layouts", bldRecordTables);
		panel.setGap(BasePanel.GAP2);

		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
	                BasePanel.FULL, BasePanel.FULL,
	                new JScrollPane(sqlOutput));
	}

	private void init_300_listners() {

		bldAllTables   .addActionListener(this);
		dropTbls       .addActionListener(this);
		bldCreateTables.addActionListener(this);
		bldRecordTables.addActionListener(this);
		bldtblTables   .addActionListener(this);
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bldAllTables) {
            ap_400_RunSql(true, true, true, true);
        } else {
            ap_400_RunSql(
            		e.getSource() == dropTbls,
            		e.getSource() == bldCreateTables,
                    e.getSource() == bldRecordTables,
                    e.getSource() == bldtblTables);
        }

	}


    /**
     * Run selected SQL files
     *
     * @param createTbls wether to run create tables script
     * @param loadRecords wether to run load records scripts
     * @param loadTables wether to run load tables scripts
     */
    private void ap_400_RunSql(boolean dropTables, boolean createTbls, boolean loadRecords, boolean loadTables) {

        try {
            boolean ok = true;
            //boolean dropSemiColumn = driver.getText().contains("derby");
            String step = "";
            StringBuffer output = new StringBuffer(sqlOutput.getText());
            Connection c = getConnection();
            //System.out.println("drop Semi " + dropSemiColumn + " " + driver.getText());

            Statement statement = c.createStatement();

            if (dropTables) {
                ok = ap_410_RunSqlInAfile(statement, output, "DropTbls.Sql");
                step = "Drop tables DB";
            }

            if (createTbls) {
                ok = ap_410_RunSqlInAfile(statement, output, "Create_RecordEdit.Sql");
                step = "Create Tables in DB";
            }

            if (ok && loadTables) {
                ok = ap_410_RunSqlInAfile(statement, output, "Data_Tables.Sql");
                step = "Load Common Tables";
            }

            if (ok && loadRecords) {
                ok = ap_410_RunSqlInAfile(statement, output, "Data_Record.Sql")
                  && ap_410_RunSqlInAfile(statement, output, "Data_RecordFields.Sql")
                  && ap_410_RunSqlInAfile(statement, output, "Data_SubRecords.Sql");
                step = "Load Record Layouts";
            }

            if (! ok) {
                output.append("\n**************************************************************");
                output.append("\n* Error at step " + step + " processing stopped");
                output.append("\n* Check the log above for the actual errors");
                output.append("\n**************************************************************");
            }


            sqlOutput.setText(output.toString());
            //c.close();

        } catch (Exception e) {
            panel.setMessageRplTxt("Connection Failed: {0}, can not run SQL",  e.getMessage());
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws Exception {

    	if (connection == null) {
	        connection = CommonCode.getConnection("RecordEditor",
	        		details.driver, details.source + details.createExtension,
	        		details.user, details.password, details.jarName);

	        panel.setMessageTxt("Connection Ok ~", connection.getClass().getName());
	    }
	    return connection;
    }

    /**
     * Run the SQL in one file
     *
     * @param statement SQL Statement - used to run the SQL
     * @param output String buffer to hold the sql and any error messages
     * @param filename file name holding the SQL to be run
     *
     * @return wether there where any errors
     */
    private boolean ap_410_RunSqlInAfile(Statement statement, StringBuffer output, String filename) {
        boolean ok = true;
        boolean dropSemiColumn = details.dropSemiColon;
        try {
            Reader reader = new FileReader(sqlDir + filename);
            BufferedReader r = new BufferedReader(reader);
            StringBuffer longSql;
            String sql;

            while ((sql = r.readLine()) != null) {
                if (! "".equals(sql.trim()) && ! sql.trim().startsWith("--")) {
                    output.append("   " + sql + "\n");
                    if (! sql.trim().endsWith(";")) {
                        longSql = new StringBuffer(sql);
                        while ((sql = r.readLine()) != null) {
                            if (! "".equals(sql.trim()) && ! sql.trim().startsWith("--")) {
                                output.append(" " + sql + "\n");
                                longSql.append(" ").append(sql.trim());
                                if (sql.trim().endsWith(";")) {
                                    break;
                                }
                            }
                        }
                        sql = longSql.toString();
                    }
                    if (dropSemiColumn && sql.trim().endsWith(";")) {
                    	sql = sql.trim();
                    	sql = sql.substring(0,sql.length() - 1);
                    }

                    try {
                        statement.execute(sql.trim());
                    } catch (Exception e) {
                    	//System.out.println(dropSemiColumn + " " + sql.trim() + '<');
                        output.append("** Error: " + e.getMessage() + "\n\n");
                        //e.printStackTrace();
                        ok = false;
                    }
                }
            }
            r.close();

            output.append("\n\nFile: " + filename + " proessed\n\n");
        } catch (Exception e) {
            ok = false;
            output.append("\nError: " + e.getMessage() + "\non file "
                    + filename + "\n\n");
        }
        return ok;
    }



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.install.BasicWizardPnl#skip()
	 */
	@Override
	public boolean skip() {
		return (! details.existingDefinition);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
	 */
	@Override
	public void setValues(InstallDetails detail) throws Exception {
		this.details = detail;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public InstallDetails getValues() throws Exception {
		return details;
	}
}

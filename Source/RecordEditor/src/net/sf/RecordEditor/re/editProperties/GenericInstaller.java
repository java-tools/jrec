/*
 * @Author Bruce Martin
 * Created on 26/01/2007 for version 0.60
 *
 * Purpose:
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Better HSQL support
 *   - Save properties on windows close
 *   - Remove call to BasePanel.done() as it is no longer needed
 */
package net.sf.RecordEditor.re.editProperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.JarFileSelect;

/**
 * This program will<ol compact>
 *  <li>Create <b>RecordEditor</b> Properties file with DB details</li>
 *  <li>Add the JDBC driver jar to the JDBC file</li>
 *  <li>Add <b>RecordEditor</b> tables to the Database
 * </ol>
 *
 * @author Bruce Martin
 *
 */
public class GenericInstaller implements ActionListener {

    public  static final int TIP_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 17;
    private static final int HSQL_IDX   = 2;
//    private static final int SQLITE_IDX = 11;

    private Properties properties = Parameters.readProperties();

    private String sqlDir = Parameters.getBaseDirectory() + "/SQL/";
    private JFrame frame = new JFrame("RecordEditor - Generic DB installer");
    private JPanel secondPanel;

    private static final String[] DB_LIST = {
            "Other", "Microsoft Access", "HSQL", "HSQL Imbedded", "My SQL", "Oracle",
            "H2 Mixed Mode", "H2", "H2 Imbeded",
            "Derby", "Derby Imbedded", "SQLite", "Postgres"
    };

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JEditorPane tips;
    private JEditorPane tipsRunSql;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox database    = new JComboBox(DB_LIST);

    //private JTextField sourceName = new JTextField();
    private JTextField  driver      = new JTextField();
    private JTextField  source      = new JTextField();
    private JTextField  user        = new JTextField();
    private JTextField  password    = new JTextField();
    private JTextField  commit      = new JTextField();
    private JTextField  checkpoint  = new JTextField();
    private JTextField  createExt   = new JTextField();
    private JTextField  dbIndex     = new JTextField("0");
    private JarFileSelect jdbcJar   = new JarFileSelect(true, null);

    private JCheckBox dropSemiChk = new JCheckBox();

    private JCheckBox saveInLibDir  = new JCheckBox();
    private JButton   test          = SwingUtils.newButton("Test Conection");
    private JButton   sqlScreen     = SwingUtils.newButton("Run SQL");

    private JButton bldAllTables    = new JButton(" + ");
    private JButton bldCreateTables = new JButton(" + ");
    private JButton bldRecordTables = new JButton(" + ");
    private JButton bldtblTables    = new JButton(" + ");

    private JTextArea sqlOutput     = new JTextArea();

    private  BasePanel pnl = new BasePanel();

    //private JTextArea message       = new JTextArea();

    private JTextField[] screenFields = {
            driver, source, user, password, createExt, commit, checkpoint
    };

    private static final String DESCRIPTION
        = "<h2>Generic Database Setup</h2>"
        + "This program will<ol compact>"
        + "<li>Create <b>RecordEditor</b> Properties file with the Data Base details you enter.</li>"
        + "<li>Add the JDBC driver jar you select to the jar file.</li>"
        + "<li>Add <b>RecordEditor</b> tables to the Database."
        + "</ol><h2>How to use the screen</h2><ol compact>"
        + "<li>Select the database you are using in the <it>Database</it> Combo box, this will "
        + "set the other fields to the default values.</li>"
        + "<li>Update the Database connection details as needed</li>"
        + "<li>Enter the JDBC (Java database Concectivity) Jar for the Database.</li>"
        + "<li>Your Database needs to be running (except for MS Access)</li>"
        + "<li>Test the database connection.</li>"
        + "<li>Run the SQL</li>"
        + "</ol>";

    private static final String DESCRIPTION_RUN_SQL
        = "<h2>Create DataBase Tables</h2>"
        + "This screen runs the SQL to create an load the backend SQL "
        + "tables used by the <b>RecordEditor</b>.<br>"
        + "You can either run everything at once (first option) "
        + "or run it one step at a time.";


    private static final String[][] DB_DETAILS = {
            {"sun.jdbc.odbc.JdbcOdbcDriver", "JDBC:ODBC:RecordLayout", "", "", ""},
            {"org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/recordedit", "sa", "", ""},
            {"org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:" + Parameters.getApplicationDirectory() + "HsqlDatabase/recordedit;readonly=no;", "sa", "", ""},
            {"org.gjt.mm.mysql.Driver", "jdbc:mysql://localhost/recordedit", "", "", ""},
            {"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:oci<version>:@<database>", "...", "...", ""},
            {"org.h2.Driver", "jdbc:h2:" + Parameters.getApplicationDirectory() + "H2RecordEditor;AUTO_SERVER=TRUE", "sa", "", ""},
            {"org.h2.Driver", "jdbc:h2:tcp://localhost/RecordEditor", "sa", "", ""},
            {"org.h2.Driver", "jdbc:h2:" + Parameters.getApplicationDirectory() + "H2RecordEditor", "sa", "", ""},
            {"org.apache.derby.jdbc.ClientDriver","jdbc:derby://localhost:1527/RecordEditor","???","???", ";create=true"},
            {"org.apache.derby.jdbc.EmbeddedDriver","jdbc:derby:" + Parameters.getApplicationDirectory() + "DerbyRecordEditor","???","???", ";create=true"},
            {"org.sqlite.JDBC","jdbc:sqlite:" + Parameters.getApplicationDirectory() + "SQLiteRE.db","sa","", ""},
            {"org.postgresql.Driver","jdbc:postgresql://localhost:5432/RecordEditor","???","???", ""},
    };

    private static boolean[] dropSemiArray = {
    	false, false, false, false,	false, false, false, false, true, true, true, false
    };

    private Connection connection;
    private String currDriver = "";
    private String currSource = "";

    private String currUser = "";
    private String currPassword = "";
    private String currJarName = "";
    /**
     *
     */
    public GenericInstaller() {
        super();

        init_100_ScreenFields();
        init_200_Screen();

        frame.setVisible(true);
    }


    /**
     * Initialise screen fields
     *
     */
    private void init_100_ScreenFields() {
    	String libDir = Parameters.getLibDirectory();
    	if (libDir == null) {
    		libDir = "";
    	}

        tips = new JEditorPane("text/html", DESCRIPTION);
        tips.setEditable(false);
        tipsRunSql = new JEditorPane("text/html", DESCRIPTION_RUN_SQL);
        tipsRunSql.setEditable(false);


        if (properties == null) {
            properties = new Properties();
            System.out.println("No properties");
        } else {
            driver.setText(properties.getProperty("Driver.0"));
            source.setText(properties.getProperty("Source.0"));
            user.setText(properties.getProperty("User.0"));
            password.setText(properties.getProperty("Password.0"));
            jdbcJar.setText(properties.getProperty("JdbcJar.0"));
        }

        database.addActionListener(this);
        test.addActionListener(this);
        sqlScreen.addActionListener(this);
        bldAllTables.addActionListener(this);
        bldCreateTables.addActionListener(this);
        bldRecordTables.addActionListener(this);
        bldtblTables.addActionListener(this);

        saveInLibDir.setSelected(libDir.indexOf("usr/") >= 0);


        //message.setEditable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ap_310_SaveProperties();
                ap_320_SaveJarDetails();

                if (connection != null) {
	                try {
	                	connection.close();
	                } catch (Exception ex) {
					}
                }
            }
        });

    }

    /**
     * Setup the screen
     *
     */
    private void init_200_Screen() {

    	SwingUtils.addTab(tabbedPane, "GenericInstaller", "DB Definition", new JScrollPane(init_210_buildDbDefPanel()));
        secondPanel = init_220_RunSqlPanel();

        pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                tabbedPane);


        pnl.addMessageRE();
        pnl.setHeightRE(BasePanel.GAP3);
        //pnl.done();

        frame.getContentPane().add(pnl);
//        frame.getContentPane().add(new JScrollPane(pnl));
        frame.pack();
        //frame.setVisible(true);
    }


    /**
     * Build the database definition panel
     * @return Db definition panel
     */
    private JPanel init_210_buildDbDefPanel() {
         BasePanel pnl = new BasePanel();

        dropSemiChk.setSelected(false);

        pnl.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP0,
                BasePanel.FULL, BasePanel.FULL,
                tips);

        //pnl.addComponent("Source Name", sourceName);
        pnl.addLineRE("Database Index (0->15)", dbIndex);
        pnl.addLineRE("Database", database);
        pnl.addLineRE("Drop ; from SQL", dropSemiChk);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addLineRE("Driver", driver);
        pnl.addLineRE("Source", source);
        pnl.addLineRE("DB Create Extension", createExt);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addLineRE("User", user);
        pnl.addLineRE("Password", password);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addLineRE("JDBC Jar", jdbcJar);
        pnl.addLineRE("Save Properties in Lib Dir", saveInLibDir, test);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addLineRE("Also ", null, sqlScreen);
        //pnl.setGap(BasePanel.GAP2);

        //pnl.done();

        return pnl;
    }

    /**
     * Build the run SQL panel
     * @return the run SQL panel
     */
    private JPanel init_220_RunSqlPanel() {
        BasePanel pnl = new BasePanel();

        pnl.addComponentRE(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
                BasePanel.FULL, BasePanel.FULL,
                new JScrollPane(tipsRunSql));

        pnl.addMenuItemRE("Build Everything", bldAllTables);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addLineRE("    or", null);
        pnl.setGapRE(BasePanel.GAP0);
        pnl.addMenuItemRE("Create Tables in DB", bldCreateTables);
        pnl.addMenuItemRE("Load Common Tables",  bldtblTables);
        pnl.addMenuItemRE("Load Record Layouts", bldRecordTables);
        pnl.setGapRE(BasePanel.GAP2);

        pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                new JScrollPane(sqlOutput));

        //pnl.done();
        return pnl;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public final void actionPerformed(ActionEvent e) {

        if (e.getSource() == database) {
            ap_100_ChangeDB();
        } else if (e.getSource() == test) {
            ap_200_Test();
        } else if (e.getSource() == sqlScreen) {
            ap_300_Change2SqlScreen();
        } else if (e.getSource() == bldAllTables) {
            ap_400_RunSql(true, true, true);
        } else {
            ap_400_RunSql(e.getSource() == bldCreateTables,
                    e.getSource() == bldRecordTables,
                    e.getSource() == bldtblTables);
        }
    }

    /**
     * update the screen detailsfor the selected database
     */
    private void ap_100_ChangeDB() {
        int idx = database.getSelectedIndex() - 1;

        if (idx >= 0) {
            for (int i = 0; i < DB_DETAILS[idx].length ; i++) {
                screenFields[i].setText(DB_DETAILS[idx][i]);
                //System.out.println(">> " + i + " " + DB_DETAILS[idx][i]);
            }

            dropSemiChk.setSelected(dropSemiArray[idx]);
        }

    }

    /**
     * Test a database connection
     */
    private void ap_200_Test() {

         try {
        	 getConnection();
        } catch (Exception e) {
            pnl.setMessageTxtRE("Connection Failed:", e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Test a database connection
     */
    private void ap_300_Change2SqlScreen() {
    	int idx = -1;
    	try {
    		idx = Integer.parseInt(dbIndex.getText());
    	} catch (Exception e) {
		}

    	if (idx < 0 || idx > 15) {
    		pnl.setMessageTxtRE("Invalid Database Index");
    		dbIndex.requestFocus();
    	} else {
	        ap_310_SaveProperties();
	        ap_320_SaveJarDetails();

	        try {
	        	getConnection();

	            tabbedPane.removeTabAt(0);
	            SwingUtils.addTab(tabbedPane, "GenericInstaller", "Run SQL", secondPanel);
	        } catch (Exception e) {
	            pnl.setMessageRplTxtRE("Connection Failed: {0}, can not run SQL", e.getMessage());
	            //e.printStackTrace();
	        }
    	}
    }

    private Connection getConnection() throws Exception {

    	if (connection == null
    	|| !  currDriver.equals(driver.getText())
        || !  currSource.equals(source.getText())
        || !  currUser.equals( user.getText())
    	|| !  currPassword.equals(password.getText())
    	|| !  currJarName.equals(jdbcJar.getText())
    	) {
    		currDriver = driver.getText();
    		currSource = source.getText();
    		currUser = user.getText();
    		currPassword = password.getText();
    		currJarName = jdbcJar.getText();

	        connection = CommonCode.getConnection("RecordEditor",
	        		currDriver, currSource + createExt.getText(),
	        		currUser, currPassword, currJarName);

	        createExt.setText("");
	        pnl.setMessageTxtRE("Connection Ok ~", connection.getClass().getName());
	    }
	    return connection;
    }

//    private void closeHsqlConnection() {
//
//
//        if (database.getSelectedIndex() == HSQL_IDX + 1) {
//        	String jar =  jdbcJar.getText();
//        	if (jar == null ||  "".equals(jar)) {
//        		Common.closeHSQL();
//        		message.setText("closed DB 1");
//        	} else {
//        		try {
//        			org.hsqldb.jdbc.jdbcConnection xx = (org.hsqldb.jdbc.jdbcConnection) c;
//
//        			CommonCode.getClass(jar, "org.hsqldb.DatabaseManager").getMethod("closeDatabases", int.class).invoke(null, Integer.valueOf(0));
//        			//((org.hsqldb.DatabaseManager) CommonCode.getClass(jar, "org.hsqldb.DatabaseManager").newInstance()).closeDatabases(0);
//        		} catch (Exception e) {
//					e.printStackTrace();
//				}
//        		pnl.etMessageTxt("closed DB 2");
//        	}
//        }
//    }

    /**
     * Save property details to the properties file
     */
    public void ap_310_SaveProperties() {
    	String id = dbIndex.getText();
    	String drop = "";
    	if (dropSemiChk.isSelected()) {
    		drop = "Y";
    	}

        ap_311_SetProperty(Parameters.DB_SOURCE_NAME + id, "DB: " + database.getSelectedItem().toString());
        ap_311_SetProperty(Parameters.DB_DRIVER + id, driver.getText());
        ap_311_SetProperty(Parameters.DB_SOURCE + id, source.getText());
        ap_311_SetProperty(Parameters.DB_USER + id, user.getText());
        ap_311_SetProperty(Parameters.DB_PASSWORD + id, password.getText());
        ap_311_SetProperty(Parameters.DB_JDBC_JAR + id, jdbcJar.getText());
        ap_311_SetProperty(Parameters.DB_DROP_SEMI + id, drop);

        if (database.getSelectedIndex() == HSQL_IDX || database.getSelectedIndex() == HSQL_IDX + 1) {
            ap_311_SetProperty(Parameters.DB_COMMIT + id, "Y");
            ap_311_SetProperty(Parameters.DB_CHECKPOINT + id, "Y");
//        } else if (database.getSelectedIndex() == SQLITE_IDX) {
//            ap_311_SetProperty(Parameters.DB_COMMIT + id, "Y");
        }

        try {
            String propertyFileName = Parameters.getPropertyFileName();
			CommonCode.renameFile(propertyFileName);
            properties.store(
                new FileOutputStream(propertyFileName),
                "RecordEditor");
        } catch (Exception e) {
            e.printStackTrace();
        }

       	String libDir = Parameters.getGlobalPropertyFileName();
       	//System.out.println("--> " + libDir + " " + saveInLibDir.isSelected() );
       	if (saveInLibDir.isSelected() && libDir != null) {
           	try {
               properties.store(
                        new FileOutputStream(libDir),
                        "RecordEditorGlobal");
          	} catch (Exception e) {
                e.printStackTrace();
			}
        }
    }


    /**
     * Set an individual property
     *
     * @param name property name
     * @param val its new value
     */
    public void ap_311_SetProperty(String name, String val) {
        String s = properties.getProperty(name);

        if ((s != null && ! "".equals(s))
        ||  (val != null && ! "".equals(val))) {
            properties.setProperty(name, val);
        	System.out.println(" == Setting property " + name + " ~ " + val);
//        } else {
//           	System.out.println(" == Not Setting property " + name + " ~ " + val + " ~ " + s);
        }
    }


    /**
     * Save jdbc jar details to the jar file
     *
     */
    public void ap_320_SaveJarDetails() {
        String newJar = jdbcJar.getText();
        System.out.println("Saving jar to file ...");
        sqlOutput.setText(sqlOutput.getText() + "\n\nSaving jar to file ..." );
        if ("".equals(newJar)) {
            return;
        }
        String outputFile = CommonCode.USER_JAR_FILE;

        ArrayList<String> jarList = new ArrayList<String>();
        FileWriter writer = null;
        BufferedWriter w = null;
        BufferedReader in = null;
        FileReader inReader = null;
        int i, j;
        
        try {
        	File f = new File(outputFile);
        	f.getParentFile().mkdirs();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        try {
            int jdbcCount = 0;
            try {
	            inReader = new FileReader(outputFile);
	            in = new BufferedReader(inReader);
	            String jar, adjJar;


	            while ((jar = in.readLine()) != null) {
	                 if (! jar.trim().startsWith("#")) {
	                   adjJar = jar;

	                    if ((j = jar.indexOf('\t')) >= 0) {
	                        if (jar.toLowerCase().startsWith("jdbc")) {
	                            jdbcCount += 1;
	                        }

	                        adjJar = jar.substring(j + 1);
	                    }
	                    if (adjJar.equals(newJar)) {
	                        return;
	                    }
	                }
	                jarList.add(jar);
	            }

	            CommonCode.renameFile(outputFile);
            } catch (Exception e) {
				e.printStackTrace();
            } finally {
				try {
					if (in != null) {
						in.close();
					}
					if (inReader != null) {
						inReader.close();
					}
				} catch (IOException ioe) {
				}
			}
            writer = new FileWriter(outputFile);
            w = new BufferedWriter(writer);

            for (i = 0; i < jarList.size(); i++) {
                w.write(jarList.get(i));
                w.newLine();
            }
            w.write("jdbc." + jdbcCount + "\t" + newJar);
            w.newLine();

            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	if (writer != null) {
            		writer.close();
            	}
            	if (w != null) {
            		w.close();
            	}
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        System.out.println("Saving jar to file ... done");
        sqlOutput.setText(sqlOutput.getText() + "\n\nSaving jar to file ... Done\n\n" );
    }

    /**
     * Run selected SQL files
     *
     * @param createTbls wether to run create tables script
     * @param loadRecords wether to run load records scripts
     * @param loadTables wether to run load tables scripts
     */
    private void ap_400_RunSql(boolean createTbls, boolean loadRecords, boolean loadTables) {

        try {
            boolean ok = true;
            boolean dropSemiColumn = driver.getText().contains("derby");
            String step = "";
            StringBuffer output = new StringBuffer(sqlOutput.getText());
            Connection c = getConnection();
            System.out.println("drop Semi " + dropSemiColumn + " " + driver.getText());

            Statement statement = c.createStatement();

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
            pnl.setMessageRplTxtRE("Connection Failed: {0}, can not run SQL",  e.getMessage());
        }
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
        boolean dropSemiColumn = dropSemiChk.isSelected();
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



    /**
     *  Run the installer
     * @param args program arguments
     */
    public static void main(String[] args) {

        new GenericInstaller();
    }
}

/*
 * @Author Bruce Martin
 * Created on 26/01/2007  for version 0.60
 *
 * Purpose:
 * Test a JDBC connection
 */
package net.sf.RecordEditor.editProperties;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Test a JDBC connection
 *
 * @author Bruce Martin
 *
 */
public class TestJdbcConnection {

    private static final int SCREEN_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 55;
    private static final int SCREEN_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 17;

    private String jdbcSourceName;
    private String jdbcDriver;
    private String jdbcSource;
    private String jdbcUser;
    private String jdbcPassword;
    private String jdbcJar;

    private JFrame frame = new JFrame();
    private JTextArea message = new JTextArea();


    /**
     * Test a JDBC connection
     * @param sourceName User Name of the connection
     * @param driver driver name
     * @param source source name
     * @param user user id
     * @param password password of the user
     * @param jarName file name of the jar holding driver
     */
    public void testConnection(final String sourceName,
            final String driver,
            final String source,
            final String user,
            final String password,
            final String jarName) {

        jdbcSourceName  = sourceName;
        jdbcDriver      = driver;
        jdbcSource      = source;
        jdbcUser        = user;
        jdbcPassword    = password;
        jdbcJar         = jarName;

        init_200_TestConnection();
        init_300_Screen();
    }


    /**
     * Test the database connection
     *
     */
    private void init_200_TestConnection() {
        String common = "\n\n   Source: " + jdbcSource
                        + "\n     User: " + jdbcUser
                        + "\n Password: " + jdbcPassword;
        try {
            Connection dbConnection = CommonCode.getConnection(jdbcSourceName,
                    jdbcDriver,
                    jdbcSource,
                    jdbcUser,
                    jdbcPassword,
                    jdbcJar);

     		dbConnection.close();

    		message.setText("Connection succesfull to " + jdbcSourceName + common);

        } catch (Exception e) {
            message.setText("Error Connecting to the Database: " + e.getMessage() + common);
            e.printStackTrace();
        }
    }

    /**
     * build the screen
     *
     */
    private void init_300_Screen() {

        frame.getContentPane().add(new JScrollPane(message));
        frame.pack();
        frame.setBounds(1, 1, SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setVisible(true);
    }

}

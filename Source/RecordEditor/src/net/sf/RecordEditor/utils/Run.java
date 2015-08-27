/*
 * @Author Bruce Martin
 * Created on 20/01/2007 for version 0.60
 *
 * Purpose:
 *   - Read jars from a supplied file
 *   - Combine these with jars supplied to the class
 *   - create URLclass loaded
 *   - Start the supplied class using the class loader just
 *     built
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - start programs via main method
 *
 */
package net.sf.RecordEditor.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.ExternalReferenceConstants;

/**
 * This class will
 *   - Read jars from a supplied file
 *   - Combine these with jars supplied to the class
 *   - create URLclass loaded
 *   - Start the supplied class using the class loader just
 *     built
 *
 * @author Bruce Martin
 *
 */
public final class Run implements ExternalReferenceConstants {
	public static final String FILE_SEPERATOR  =  System.getProperty("file.separator");
    private static final int FILENAME_POS = 9;
    private String libDirectory = null;
    private boolean win = false;



    private static final String[] JARS = {
        "<lib>/JRecord.jar",
        "<lib>/LayoutEdit.jar",
        "<lib>/RecordEdit.jar",
        /*"<lib>/properties.zip"*/
    };


    public Run() throws MalformedURLException {
        ArrayList<String> list = new ArrayList<String>();
        String libDir = getDir();


        readFile(list, libDir + FILE_SEPERATOR + SYSTEM_JARS_FILENAME, true);
        readFile(list, libDir + FILE_SEPERATOR + SYSTEM_JDBC_JARS_FILENAME, false);
        addJars(list, null);

        buildUrls(list, libDir, false, false);
    }

    /**
     * Run class using jars held in the supplied file +
     * those in an array
     * @param filename file that holds the jars
     * @param jars Extra jars supplied to the program
     * @param classToRun class to be run
     * @param args program arguments
     */
	@SuppressWarnings("unchecked")
	public Run(String jarDir, final String filename, String[] jars,
            final String classToRun, final String[] args) {
		libDirectory = jarDir;
        try { 
    		String os = System.getProperty("os.name");
    		String version = System.getProperty("os.name");
    		win = os != null
					&& os.toLowerCase().indexOf("win") >= 0;
    		boolean isWinUac = (win && !"5.1".equals(version));

            ArrayList<String> list = new ArrayList<String>();

            URL[] urls;
            URLClassLoader urlLoader;
            String libDir = getDir();
            String userDir = getUserJars(libDir);


            addJars(list, jars);
            readFile(list, libDir + FILE_SEPERATOR + SYSTEM_JARS_FILENAME, true);
            readFile(list, libDir + FILE_SEPERATOR + SYSTEM_JDBC_JARS_FILENAME, false);
            readFile(list, userDir + FILE_SEPERATOR + USER_JARS_FILENAME, false);
            readExtensions(list, libDir);
            readExtensions(list, userDir);


            urls = buildUrls(list, libDir, isWinUac, true);

            urlLoader = new URLClassLoader(urls);

            @SuppressWarnings("rawtypes")
			Class runClass = urlLoader.loadClass(classToRun);
            System.out.println("Starting Program !!!");
            //String points[] = new String[] {"0", "0", "3", "4"};
            Object[] arguments = new Object[]{args};
            Method mainMethod = runClass.getMethod("main", new Class[] {args.getClass()});
            mainMethod.invoke(null, arguments);
            
//            String v = System.getProperty("java.version");
//            if (! (v.startsWith("1.6") || v.startsWith("1.5"))) {
//            	urlLoader.close();
//            }
          } catch (Exception e) {
            e.printStackTrace();
          }
    }

	private void addJars(ArrayList<String> list, String[] jars) {

        if (jars == null) {
        	jars = JARS;
        }
        for (int i = 0; i < jars.length; i++) {
        	if (jars[i] != null) {
        		list.add(jars[i]);
        	}
        }
	}

	private void readFile(ArrayList<String> list, String fileName, boolean required) {
		String s;
        try {
        	System.out.println(" * Run  Reading File: " + fileName);
            FileReader inReader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(inReader);
            while ((s = in.readLine()) != null) {
                if (s != null && ! s.trim().startsWith("#") && ! "".equals(s.trim())) {
                	System.out.println(s);
                    list.add(s);
                }
            }
            in.close();
        } catch (Exception e) {
        	System.out.println("Error Reading: " + fileName
        			+ " ~ " + e.getClass().getName() + " " + e.getMessage());
        	if (required) {
        		e.printStackTrace();
        	}
        }

	}

	private void readExtensions(ArrayList<String> list, String libDir) {
		String pathname = libDir  + FILE_SEPERATOR + "Extensions";
		File extFileDir = new File(pathname);
		if (extFileDir.isDirectory()) {
			String[] fileNames = extFileDir.list();
			for (String fileName : fileNames) {
				if (fileName.toLowerCase().endsWith(".jar")) {
					list.add(pathname + FILE_SEPERATOR + fileName);
				}
			}
		}
	}

	private URL[] buildUrls(ArrayList<String> list, String libDir, boolean isWinUac, boolean warn) throws MalformedURLException {
		URL[] urls = new URL[list.size()];
		boolean tryUnpack = true;
		String s, packedName;
		int j, missingCount=0;
        StringBuilder missing = new StringBuilder("The Following Jars are Missing:\n");
		Pack200.Unpacker unpacker = null;
		File packedFile;

		System.out.println("\t listSise " + list.size() + " > " + libDir + " >> " + isWinUac);
        for (int i = 0; i < urls.length; i++) {
            s = list.get(i);
            if ((j = s.indexOf('\t')) >= 0) {
                s = s.substring(j + 1);
            }
            s = s.trim();

            if (s.toLowerCase().startsWith("<lib>")) {
                s = libDir + s.substring(5);
            }
            // System.out.println("\t file: " + s + " " + (new File(s)).exists());

            if (! (new File(s)).exists()) {
        		packedName = s.substring(0, s.length() - 4) + ".pack";
            	packedFile = new File(packedName);
            	if (s.toUpperCase().endsWith(".JAR") && (packedFile.exists())) {
        		System.out.println("\t\t Packed file: " + packedName + " " + packedFile.exists() + " " + isWinUac);
            		if (isWinUac && tryUnpack) {
            			try {
             				String[] args  = new String [] {
              					    "CMD.EXE",
              					    "/C",
              					    "RunUnpack.exe"};
            				System.out.println("Unpack Command"  + args[0] + " " + args[2]);
            				//Process p = Runtime.getRuntime().exec(args);

            				ProcessBuilder builder = new ProcessBuilder(args);
            				File holdDirFile = builder.directory();
            				builder.directory(new File(libDirectory));
            				//System.out.println("lib dir: " + libDir + " " + (new File(libDirectory)).getCanonicalPath());
            				Process p = builder.start();
            				Thread.sleep(5000);

               				gobble(p.getInputStream());
               				System.out.println("Errors: ");
               				gobble(p.getErrorStream());

			                int exitVal = p.waitFor();
			                builder.directory(holdDirFile);
			                System.out.println("Exited with error code "+exitVal + " ");
            				System.out.println("Unpack done");
	            			tryUnpack = false;
            			} catch (Exception e) {
							e.printStackTrace();
            			}
            		}
            		if (! (new File(s)).exists()) {
	            		try {
		                	if (unpacker == null) {
		                		unpacker = Pack200.newUnpacker();
		                	}
		                	//System.out.println("\t\t Unpacking " + packedName);
		                	if (packedFile.exists()) {
		        				JarOutputStream jaros = new JarOutputStream(
		        						new BufferedOutputStream(new FileOutputStream(s)));
		                		unpacker.unpack(packedFile, jaros);
		                		jaros.close();
		                	}
	            		} catch (Exception e) {
							e.printStackTrace();
						}
            		}
            	}
            	if (s.indexOf("velocity")    < 0 && s.indexOf("cb2xml") < 0 && s.indexOf("pict.zip") < 0
            	&&  s.indexOf("PoEditor_re") < 0 && s.indexOf("swingx-subset-1.6.4") < 0
            	&& (! (new File(s)).exists())) {
            		missingCount += 1;
            		missing.append("\n").append(s);
            	}
            }

            try {
            	urls[i] = (new File(s)).toURI().toURL();
            } catch (Exception e) {
            	urls[i] = new URL("file:" + s);
			}
        }

        if (missingCount > 0 && warn) {
        	JTextArea missingTxt = new JTextArea();
        	missingTxt.setText(missing.toString()
        			+ "\nthis could mean unpack process in the install failed.\n"
        			+ "\nYou could try running the supplied unpackSubDirectories.jar utility."
        			+ "\nYou can unpack manually using java's unpack200 utility."
        			+ "\n\nWill try to start the application anyway");

        	JOptionPane.showMessageDialog(null, missingTxt, "Missing Jars", JOptionPane.ERROR_MESSAGE);
        }
        return urls;
	}

	private void gobble(InputStream stream) throws IOException {
		BufferedReader in = new BufferedReader(
                new InputStreamReader(stream));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

	}

    /**
     * Get the directory where this class is being run from
     * @return the directory where this class is being run from
     */
    private final String getDir() {

        if (libDirectory == null) {
            String dir = ClassLoader.getSystemResource(
                    "net/sf/RecordEditor/utils/Run.class"
                    ).toString();
            libDirectory = "";

            if (dir.toLowerCase().startsWith("jar:")) {
                int pos = dir.indexOf('!');
//                String dfltEncName = AccessController.doPrivileged(
//                	    new GetPropertyAction("file.encoding")
//                	);
//                 //System.out.println("     dir: " + dir);

                libDirectory = dir.substring(FILENAME_POS, pos);

                pos = libDirectory.lastIndexOf('/');
//                try {
//                	baseDirectory = URLDecoder.decode(baseDirectory.substring(0, pos), dfltEncName);
//                } catch (Exception e) {
//				}
                //System.out.println(">> " + baseDirectory + " >> " + baseDirectory.substring(0, pos));
                libDirectory = URLDecoder.decode(libDirectory.substring(0, pos));
            }
            //System.out.println("base dir: " + dir);
        }

        return libDirectory;
    }

    private final String getUserJars(String libDirectory) {
    	String ret = libDirectory;

    	try {
    		String stdDir = null;
    		String winDir = null;
   		
    		try {
    			Properties p = new Properties();
                p.load(new FileInputStream(libDirectory + "/" + GLOBAL_PROPERTIES_FILE_NAME));
                stdDir = p.getProperty(PROPERTIES_DIR_VAR_NAME);
                winDir = p.getProperty(PROPERTIES_WIN_DIR_VAR_NAME);
            } catch (Exception e) {
                //e.printStackTrace();
            	System.out.println(e);
            }
    		
    		File propertiesFile = new File(libDirectory + "/properties.zip" );
    		if (propertiesFile.exists() && (stdDir == null || (win && winDir == null))) {
    			try {
					URL[] urls = {propertiesFile.toURI().toURL()};
		
			   	 	ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME,
			   	 			Locale.getDefault(),
			   	 			new URLClassLoader(urls));
			   	 	stdDir = getVar(rb, PROPERTIES_DIR_VAR_NAME, stdDir);
			   	 	winDir = getVar(rb, PROPERTIES_WIN_DIR_VAR_NAME, winDir);
	    		} catch (Exception e) {	}
    		}
	   	 	ret = stdDir;
	   	 	
	   	 	if (win && winDir != null && ! Common.exists(stdDir)) {
	   	 		ret = winDir;
	   	 	}
    		
	    } catch (Exception e) {
    		e.printStackTrace();
		}
    	return ret;
    }
    
    private String getVar(ResourceBundle rb, String var, String defaultValue) {
    	 String val = defaultValue;
    			 
    	 if (rb != null) {
	    	 try {
				 String s = rb.getString(var);
				 if (s != null || "".equals(s)) {
					 val = s;
				 }
			} catch (Exception e) {
			}
    	 }
    	 
    	 return expandVar(val);
    }

    private String expandVar(String stdDir) {
    	if (stdDir == null) return null;
    	
    	String ret = stdDir;
        String lcName = stdDir.toLowerCase();

        if (lcName.startsWith("<lib>")) {
            ret = libDirectory  + ret.substring(5);
        } else if (lcName.startsWith("<install>")) {
            ret = libDirectory.substring(0, libDirectory.length()-4)
            	+ ret.substring("<install>".length());
        } else {
			String userHome = System.getProperty("user.home");
			if (lcName.startsWith("<home>")) {
			    ret = userHome + ret.substring(6);
			} else if (lcName.startsWith("<appdata>")) {
				String d = userHome;
				try {
					if (win) {
						String ss = System.getenv("APPDATA");
						if (ss != null && ! "".equals(ss)) {
							d = ss;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
						
						
			    ret = d + ret.substring(9);
			}
		}
        return ret;
    }

    public static String[] getArgs(String[] args) {
    
    	String[] args1 = args;
    	if (args.length < 2) {
     		args1 = new String[0];
     	} else {
    // 		int i = 0;
     		args1 = new String[args.length - 1];
     		
     		for (int i = 0; i < args.length - 1; i++) {
     			args1[i] = args[i+1];
     		}
     	}
    	return args1; 
    }
    
    public static void main(String[] args) throws MalformedURLException {
 //   	System.out.println("\t Upack program");
    	new Run();
    }


    
}

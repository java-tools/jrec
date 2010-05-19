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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

//import sun.security.action.GetPropertyAction;

/*

 private static void invokeMain()
   throws ClassNotFoundException,
          SecurityException,
          NoSuchMethodException,
          IllegalArgumentException,
          IllegalAccessException,
          InvocationTargetException
 {
   Class distanceClass = Class.forName("Distance");
   String points[] = new String[] {"0", "0", "3", "4"};
   Object arguments[] = new Object[] { points };
   Method mainMethod = distanceClass.getMethod("main", new Class[] { points.getClass()});
   Object result = mainMethod.invoke(null, arguments);
 }

 */

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
public final class Run {
	public static final String FILE_SEPERATOR  =  System.getProperty("file.separator");
    private static final int FILENAME_POS = 9;
    private String baseDirectory = null;



    private static final String[] JARS = {
        "<lib>/JRecord.jar",
        "<lib>/LayoutEdit.jar",
        "<lib>/RecordEdit.jar",
        "<lib>/properties.zip"
    };




    /**
     * Run class using jars held in the supplied file +
     * those in an array
     * @param filename file that holds the jars
     * @param jars Extra jars supplied to the program
     * @param classToRun class to be run
     * @param args program arguments
     */
	@SuppressWarnings("unchecked")
	public Run(final String filename, String[] jars,
            final String classToRun, final String[] args) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            StringBuilder missing 
            	= new StringBuilder("The Following Jars are Missing:\n");
            int missingCount = 0;
            String s, packedName;
            int i, j;
            URL[] urls;
            URLClassLoader urlLoader;
            Pack200.Unpacker unpacker = null;
            
            if (jars == null) {
            	jars = JARS;
            }

            try {
                FileReader inReader = new FileReader(getDir() + FILE_SEPERATOR + filename);
                BufferedReader in = new BufferedReader(inReader);
                while ((s = in.readLine()) != null) {
                    if (s != null && ! s.trim().startsWith("#") && ! "".equals(s.trim())) {
                        list.add(s);
                    }
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jars != null) {
                for (i = 0; i < jars.length; i++) {
                	if (jars[i] != null) {
                		list.add(jars[i]);
                	}
                }
            }

            urls = new URL[list.size()];
            for (i = 0; i < urls.length; i++) {
                s = list.get(i).toString();
                if ((j = s.indexOf('\t')) >= 0) {
                    s = s.substring(j + 1);
                }
                s = s.trim();

                if (s.toLowerCase().startsWith("<lib>")) {
                    s = getDir() + s.substring(5);
                }

                if (! (new File(s)).exists()) {
                	if (s.toUpperCase().endsWith(".JAR")) {
                		try {
	                		packedName = s.substring(0, s.length() - 4) + ".pack";
		                	if (unpacker == null) {
		                		unpacker = Pack200.newUnpacker();
		                	}
		                	File packedFile = new File(packedName);
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
                	if (s.indexOf("velocity") < 0 && ! (new File(s)).exists()) {
                		missingCount += 1;
                		missing.append("\n").append(s);
                	}
                }

                urls[i] = new URL("file:" + s);
            }

            if (missingCount > 0) {
            	JTextArea missingTxt = new JTextArea();
            	missingTxt.setText(missing.toString()
            			+ "\nthis could mean unpack process in the install failed.\n"
            			+ "\nYou could try running the supplied unpackSubDirectories.jar utility."
            			+ "\nYou can unpack manually using java's unpack200 utility." 
            			+	"\n\nWill try to start the application anyway");
            	
            	JOptionPane.showMessageDialog(null, missingTxt, "Missing Jars", JOptionPane.ERROR_MESSAGE);
            }
            urlLoader = new URLClassLoader(urls);

            Class runClass = urlLoader.loadClass(classToRun);
            //System.out.println(runClass.getClassLoader() == urlLoader);
            //String points[] = new String[] {"0", "0", "3", "4"};
            Object[] arguments = new Object[]{args};
            Method mainMethod = runClass.getMethod("main", new Class[] {args.getClass()});
            mainMethod.invoke(null, arguments);

            //Class c = urlLoader.loadClass("net.sf.RecordEditor.layoutEd.LayoutEdit");
            //Class c = urlLoader.loadClass(classToRun);
            //prog = (MainProgram) urlLoader.loadClass(classToRun).newInstance();

          } catch (Exception e) {
            e.printStackTrace();
         }
    }

    /**
     * Get the directory where this class is being run from
     * @return the directory where this class is being run from
     */
    private final String getDir() {

        if (baseDirectory == null) {
            String dir = ClassLoader.getSystemResource(
                    "net/sf/RecordEditor/utils/Run.class"
                    ).toString();
            baseDirectory = "";

//           System.out.println(">> " + dir);

            if (dir.toLowerCase().startsWith("jar:")) {
                int pos = dir.indexOf('!');
//                String dfltEncName = AccessController.doPrivileged(
//                	    new GetPropertyAction("file.encoding")
//                	);
//                 //System.out.println("     dir: " + dir);
                baseDirectory = dir.substring(FILENAME_POS, pos);
                pos = baseDirectory.lastIndexOf('/');
//                try {
//                	baseDirectory = URLDecoder.decode(baseDirectory.substring(0, pos), dfltEncName);
//                } catch (Exception e) {
//				}
                //System.out.println(">> " + baseDirectory + " >> " + baseDirectory.substring(0, pos));
                baseDirectory = URLDecoder.decode(baseDirectory.substring(0, pos));
             }
            System.out.println("base dir: " + dir);
        }

        return baseDirectory;
    }
}

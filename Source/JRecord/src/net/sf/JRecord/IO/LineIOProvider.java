/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose:
 *
 * Modification log:
 * On 2006/06/28 by Jean-Francois Gagnon:
 *    - Added a Fujitsu Variable Length Line IO Provider
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Started work on seperating Record section out, so removing
 *     all reference to the Common module and used a new Constants
 *     module
 */
package net.sf.JRecord.IO;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Details.LineProvider;


/**
 * LineIOprovider - This class returns a LineIO class appropriate for
 * the supplied file structure. All the Files Structures are available as
 * Constants.IO_*
 *
 * <pre>
 * <b>Usage:</b>
 *
 *         CopybookLoader loader = <font color="brown"><b>new</b></font> RecordEditorXmlLoader();
 *         ExternalRecord extlayout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, <font color="brown"><b>null</b></font>);
 *
 *         LayoutDetail layout = extlayout.asLayoutDetail();
 *         AbstractLineWriter writer = <b>LineIOProvider.getInstance()</b>.getLineWriter(layout.getFileStructure());
 * </pre>
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class LineIOProvider implements AbstractManager, AbstractLineIOProvider {

	private StandardLineIOProvider standardProvider = new StandardLineIOProvider();
	private HashMap<Integer, AbstractLineIOProvider> providers = new HashMap<Integer, AbstractLineIOProvider>();
	public int START_USER_FILE_STRUCTURES = 1000;
    private static LineIOProvider ioProvider = null;

//	@SuppressWarnings("rawtypes")
//	private LineProvider provider;


    //private  int numberOfEntries = 0;
    private  ArrayList<String> names = new ArrayList<String>(20) ;
    private  ArrayList<String> externalNames = new ArrayList<String>(20) ;
    private  ArrayList<Integer>keys = new ArrayList<Integer>(20) ;



    /**
     * create LineIOprovider class - This class returns IO-Routines
     * appropriate for the Supplied File Structure.
     */
    protected LineIOProvider() {
        this(null);
    }

    /**
	 * @see net.sf.JRecord.Common.AbstractManager#getManagerName()
	 */
	@Override
	public String getManagerName() {
		return "Line_IO_Names";
	}

    /**
     * create LineIOprovider class - This class returns IO-Routines
     * appropriate for the Supplied File Structure with a LineProvider
     *
     * @param lineProvider lineProvider to use. Line providers
     * create Lines.
     */
	public LineIOProvider(@SuppressWarnings("rawtypes") final LineProvider lineProvider) {
        super();

//        provider = lineProvider;
//        if (lineProvider == null) {
//            provider = new DefaultLineProvider();
//        }
        registerNames(standardProvider);
    }



    /**
     * Gets a Record Reader Class that is appropriate for reading the
     * supplied file-structure
     *
     * @param fileStructure File Structure of the required reader
     *
     * @return line reader
     */
    @SuppressWarnings( "rawtypes" )
	public AbstractLineReader getLineReader(int fileStructure) {
        return getLineReader(fileStructure, getLineProvider(fileStructure));
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(net.sf.JRecord.Common.IBasicFileSchema)
	 */
	@Override
	public AbstractLineReader getLineReader(IBasicFileSchema schema) {
		return getProvider(schema.getFileStructure()).getLineReader(schema);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(IBasicFileSchema schema,
			LineProvider lineProvider) {
		return getProvider(schema.getFileStructure()).getLineReader(schema, lineProvider);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(int fileStructure,
			IBasicFileSchema schema, LineProvider lineProvider) {
		return getProvider(fileStructure).getLineReader(schema, lineProvider);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	public AbstractLineReader getLineReader(int fileStructure,
            						   LineProvider lineProvider) {
    	return getProvider(fileStructure).getLineReader(fileStructure, lineProvider);
     }


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int)
	 */
	@Override
	public AbstractLineWriter getLineWriter(int fileStructure) {
		return getLineWriter(fileStructure, null);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int, java.lang.String)
	 */
	@Override
	public AbstractLineWriter getLineWriter(int fileStructure, String charset) {
    	return getProvider(fileStructure).getLineWriter(fileStructure, charset);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#isCopyBookFileRequired(int)
	 */
    public boolean isCopyBookFileRequired(int fileStructure) {
    	return getProvider(fileStructure).isCopyBookFileRequired(fileStructure);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureName(int)
	 */
    public String getStructureName(int fileStructure) {
    	Integer fs = Integer.valueOf(fileStructure);
    	for (int i = 0; i < keys.size(); i++) {
    		if (keys.get(i).equals(fs)) {
    			return externalNames.get(i);
    		}
    	}
    	return "";
    }


	@Override
	public String getStructureNameForIndex(int index) {
		return externalNames.get(index);
	}

    /**
     * Convert a structure-name to a file-Structure identifier
     * @param name Name of the File Structure
     * @return The file Structure
     */
    public int getStructure(String name) {
    	int min = Math.min(keys.size(), externalNames.size());
    	if (name != null) {
	    	for (int i = 0; i < min; i++) {
	    		if (name.equalsIgnoreCase(externalNames.get(i))) {
	    			//System.out.println(" ~~~ getStructure ~ " +  externalNames.get(i) + " " + keys.get(i));
	    			return keys.get(i).intValue();
	    		}
	    	}
    	}
		//System.out.println(" ~~~ getStructure ~ not found " +  name);
    	return Constants.NULL_INTEGER;
    }


//    /**
//     * Get line provider
//     * @return Returns the provider.
//     * @deprecated use getLineProvider(fileStructure)
//     */
//	@SuppressWarnings("rawtypes")
//	public LineProvider getLineProvider() {
//        return provider;
//    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineProvider(int)
	 */
	@SuppressWarnings("rawtypes")
	public LineProvider getLineProvider(int fileStructure) {
    	return getProvider(fileStructure).getLineProvider(fileStructure);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getKey(int)
	 */
	@Override
	public int getKey(int idx) {
		return keys.get(idx).intValue();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getName(int)
	 */
	@Override
	public String getName(int idx) {
		return names.get(idx);
	}

    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureName(int)
	 */
    public String getInternalStructureName(int fileStructure) {
    	Integer fs = Integer.valueOf(fileStructure);
    	for (int i = 0; i < keys.size(); i++) {
    		if (keys.get(i).equals(fs)) {
    			return names.get(i);
    		}
    	}
    	return "";
    }

	/**
	 * @see net.sf.JRecord.Common.AbstractManager#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		return names.size();
	}

	/**
     * Get an instance of LineIOProvider
     * @return a LineIOProvider
     */
    public static LineIOProvider getInstance() {
        if (ioProvider == null) {
            ioProvider = new LineIOProvider();
        }

        return ioProvider;
    }

    /**
     * Set the system lineIOprovider
     * @param newProvider new IO provider
     */
    public static void setInstance(LineIOProvider newProvider) {
        ioProvider = newProvider;
    }

    private AbstractLineIOProvider getProvider(int fileStructure) {
    	AbstractLineIOProvider ret = standardProvider;
    	Integer key = Integer.valueOf(fileStructure);
    	if (providers.containsKey(key)) {
    		ret = providers.get(key);
    	}
    	return ret;
    }

    public void register(AbstractLineIOProvider newProvider) {
    	for (int i =0; i < newProvider.getNumberOfEntries(); i++) {
    		register(newProvider.getKey(i), newProvider);
    	}
    	registerNames(newProvider);
    }


    private void register(int fileStructure, AbstractLineIOProvider newProvider) {

    	Integer key = Integer.valueOf(fileStructure);
    	if (providers.containsKey(key)) {
    		throw new RuntimeException("File Structure " + fileStructure + " is already defined");
    	}

    	providers.put(key, newProvider);
    }

    public final void registerNames(AbstractLineIOProvider newProvider) {
    	for (int i = 0; i < newProvider.getNumberOfEntries(); i++) {
    		names.add(newProvider.getName(i));
    		keys.add(Integer.valueOf(newProvider.getKey(i)));
    		externalNames.add(newProvider.getStructureNameForIndex(i));
    		//System.out.println(" --> " + keys.get(i) + " >> " + names.get(i) + " >> " + externalNames.get(i));
    	}
    }
}

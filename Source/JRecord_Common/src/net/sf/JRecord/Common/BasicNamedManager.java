package net.sf.JRecord.Common;

/**
 * Class to manage other classes (with a name given to the managed classes).
 * Also used to build combo box's etc in the RecordEditor
 * 
 * @author Bruce Martin
 *
 * @param <managedClass> class being managed
 */
public abstract class BasicNamedManager<managedClass> 
   extends BasicManager<managedClass> 
implements AbstractManager {

	private String[] names;

	/**
	 * Create a Named Manager
	 * @param numberOfSystemEntries number of System Entries
	 * @param startOfUserRange first user key
	 * @param numberOfUserEntries number of user entries
	 */
	public BasicNamedManager(int numberOfSystemEntries, int startOfUserRange, final managedClass[] initialArray) {
		super(numberOfSystemEntries, startOfUserRange, initialArray);
		
		names = new String[super.getNumberOfEntries()];
	}

	/**
	 * Register a Class
	 * 
	 * @see BasicManager#register(int, Object)
	 * 
	 * @deprecated use register(id, name, parser)
	 */
	public final void register(int id, managedClass parser) {
		register(id, "",  parser);
	}

	/**
	 * Register a new class with a name
	 * @param id Identifier of Class
	 * @param name User name of the class
	 * @param parser new parser
	 */
	public void register(int id, String name, managedClass parser) {
		super.register(id, parser);
		names[id] = name;
	}
	
	/**
	 * @see net.sf.JRecord.Common.AbstractManager#getName(int)
	 */
	public String getName(int id) {
		return names[id];
	}


}

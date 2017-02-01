package net.sf.RecordEditor.re.editProperties;

/**
 * EditOptions - implement panel specific save and close
 * actions
 * 
 * @author Bruce Martin
 *
 */
public interface ISaveOptions {

	public void save();

	public void close();
	
	public boolean isCloseRequired(); 
}

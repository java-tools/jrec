package net.sf.RecordEditor.re.script;

import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;

/**
 * This class will open a file (with a specified layout)
 * and put the user in edit o it. It use from ScriptData
 * in user written scripts.
 * 
 * @author Bruce Martin
 *
 */
public interface IOpenReFile {

	public abstract AbstractFileDisplayWithFieldHide openFile(String fileName, String layoutName);

}
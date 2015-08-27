/**
 *
 */
package net.sf.RecordEditor.re.openFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.protoGen.cobolOpt.CobolCopybookOption;


/**
 * This class provides an interface to the Cobol Options
 *
 * @author Bruce Martin
 *
 */
public class CobolOptionsManager {

	private static final CobolOptionsManager instance = new CobolOptionsManager();


	private TreeMap<String, List<CobolCopybookOption>> cobolOpts
					= new TreeMap<String, List<CobolCopybookOption>>();

	private CobolOptionsManager() {
		String s = Common.OPTIONS.cobolOptionFile.get();
		File f = new File(s);
		List<CobolCopybookOption> last = null;

		if (f.exists() && f.isFile() && f.length() > 0) {
			try {
				InputStream in = new FileInputStream(f);
				CobolCopybookOption cobOpt;

				while ((cobOpt = CobolCopybookOption.parseDelimitedFrom(in)) != null) {
					String copybookName = cobOpt.getCopybookName();
					if (last == null || (! last.get(0).getCopybookName().equals(copybookName))) {
						last = getOptionList(copybookName);
					}

					addItm(last, cobOpt);
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addItm(List<CobolCopybookOption> list, CobolCopybookOption c) {

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCopybookDir().equals(c.getCopybookDir())) {
				list.set(i, c);
				return;
			}
		}

		list.add(c);
	}

	private List<CobolCopybookOption> getOptionList(String copybookName) {
		List<CobolCopybookOption> list;

		if (cobolOpts.containsKey(copybookName)) {
			list = cobolOpts.get(copybookName);
		} else {
			list = new ArrayList<CobolCopybookOption>(5);
			cobolOpts.put(copybookName, list);
		}

		return list;
	}


	/**
	 * Add / Save Cobol Options for a Cobol-Copybook
	 * @param cobOpt Cobol Options
	 */
	public final void saveCobolOption(CobolCopybookOption cobOpt) {

		addItm(getOptionList(cobOpt.getCopybookName()), cobOpt);

		save();
	}

	/**
	 * Save all the Cobol Options to disk !!!
	 */
	public final void save() {
		String s = Common.OPTIONS.cobolOptionFile.get();
		File f = new File(s + ".tmp");
		File dir = f.getParentFile();

		if (! dir.exists()) {
			dir.mkdirs();
		}

		try {
			//System.out.println("Writing ... " + f.getPath());
			OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
			for (Map.Entry<String, List<CobolCopybookOption>> e : cobolOpts.entrySet()) {
				for (CobolCopybookOption co : e.getValue()) {
					co.writeDelimitedTo(os);
				}
			}
			os.close();

			File bk = new File(s + ".bak");
			File of = new File(s);

			if (of.exists()) {
				if (bk.exists()) {
					bk.delete();
				}

				of.renameTo(bk);
			}
			f.renameTo(of);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CobolCopybookOption getOption(String name, long dateTime) {
		List<CobolCopybookOption> list = cobolOpts.get(name);
		CobolCopybookOption temp, ret = null;

		if (list != null && list.size() > 0) {
			ret = list.get(0);

			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i);
				if (dateTime == temp.getCopybookDateTime()) {
					ret = temp;
					break;
				} else if ((	ret.getCopybookDateTime() > temp.getCopybookDateTime()
					||  ret.getCopybookDateTime() < dateTime)
				&& dateTime < temp.getCopybookDateTime()) {
					ret = temp;
				} else if (ret.getCopybookDateTime() < dateTime
						&& ret.getCopybookDateTime() < temp.getCopybookDateTime()
						&& temp.getCopybookDateTime() <  dateTime) {
					ret = temp;
				}
			}
		}
		return ret;
	}

	public static CobolOptionsManager getInstance() {
		return instance;
	}

}

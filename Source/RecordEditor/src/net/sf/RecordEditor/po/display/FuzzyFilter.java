package net.sf.RecordEditor.po.display;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.FilterDetails;

public class FuzzyFilter {

	public static String TO_WORK_FILTER_XML
	= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	+ "<EditorTask type=\"Filter\" layoutName=\"GetText_PO\">"
	+ "  <layout name=\"GetText_PO\" groupHeader=\"\">"
	+ "    <record name=\"GetText_PO\">"
	+ "      <FieldTest fieldName=\"fuzzy\"  operator=\"Contains\" value=\"Y\" booleanOperator=\"And\"/>"
	+ "      <FieldTest fieldName=\"msgstr\" operator=\"Is Empty\" value=\"\"  booleanOperator=\"Or\"/>"
	+ "      <FieldTest fieldName=\"msgidPlural\"  operator=\"Is Empty\" value=\"\"  booleanOperator=\"And\"/>"
	+ "      <FieldTest fieldName=\"msgstrPlural\" operator=\"Is Empty\" value=\"\"  booleanOperator=\"Or\"/>"
	+ "      <FieldTest fieldName=\"msgstrPlural\" operator=\" &lt;> \"  value=\"\"  booleanOperator=\"And\"/>"
	+ "    </record>"
	+ "  </layout>"
	+ "</EditorTask>";

	private static String  fName = null;
	private static FilterDetails filter;

	private static String getFileName() {
		if (fName == null) {
			try {
				File f = File.createTempFile("FuzzyFilter", "x,l");
				OutputStreamWriter s = new OutputStreamWriter( new FileOutputStream(f), "utf8");

				s.write(TO_WORK_FILTER_XML);
				s.close();
				fName = f.getAbsolutePath();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return fName;
	}


	public final static FileView getFuzzyView(FileView fileView) {
		if (filter == null) {
			try {
				net.sf.RecordEditor.jibx.JibxCall<net.sf.RecordEditor.jibx.compare.EditorTask> jibx
					= new net.sf.RecordEditor.jibx.JibxCall< net.sf.RecordEditor.jibx.compare.EditorTask>(
						 net.sf.RecordEditor.jibx.compare.EditorTask.class);

				net.sf.RecordEditor.jibx.compare.EditorTask saveDetails = jibx.marshal(getFileName());

				filter = new FilterDetails(fileView.getLayout(), FilterDetails.FT_NORMAL);

				filter.updateFromExternalLayout(saveDetails.filter);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				filter = null;
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				filter = null;
				return null;
			}
		}

		return fileView.getFilteredView(filter);
	}
}

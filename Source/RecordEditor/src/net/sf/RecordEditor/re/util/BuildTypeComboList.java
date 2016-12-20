package net.sf.RecordEditor.re.util;

import java.util.ArrayList;

import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeChar;
import net.sf.JRecord.Types.TypeCommaDecimalPoint;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.Types.TypeNum;
import net.sf.RecordEditor.re.jrecord.types.TypeDateWrapper;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;

public class BuildTypeComboList {

	private static int VALID_IDX = 3;

	public static TreeComboRendor getTreeComboRender(TreeComboItem[] items) {
		return new TreeComboRendor(items);
	}

	public static TreeComboItem[] getList(AbsRowList typeList) {
		return getList(false, typeList);
	}

	

	public static TreeComboItem[] getTextTypes(AbsRowList typeList) {
		return getList(true, typeList);
	}

	
	@SuppressWarnings("deprecation")
	private static TreeComboItem[] getList(boolean onlyTextFields, AbsRowList typeList) {
		//TypeList typeList = new TypeList(connectionIdx, false, false);
		TypeManager m = TypeManager.getInstance();
		TreeComboItem[] ret;
		Object fld, valid;
		int key;
		Type t;

		TreeComboItem tc;

		ArrayList<MenuDtls> subMenus = new  ArrayList<MenuDtls>();

		MenuDtls charTypes = new MenuDtls("", new  ArrayList<MenuDtls>());

		MenuDtls numericTypes = new MenuDtls("Numeric Types", subMenus);
		MenuDtls binTypes = null;
		if (! onlyTextFields) {
			binTypes = new MenuDtls("Binary Numeric Types", subMenus);
		}
		MenuDtls leftJustifiedTypes = new MenuDtls("Left Justified Numeric Types", subMenus);
		MenuDtls spacePaddedTypes = new MenuDtls("Right Justified Space padded Types", subMenus);
		MenuDtls commaDecimalTypes = new MenuDtls("Numeric Decimal point=','", subMenus);

		MenuDtls dateTypes = new MenuDtls("Date Types", subMenus);
		MenuDtls checkBoxTypes = new MenuDtls("Checkbox Types", subMenus);
		MenuDtls specialTypes = new MenuDtls("Special Char Types", subMenus);
		MenuDtls userTypes = new MenuDtls("locally defined Types", subMenus);



		String foreignLookUpId = Common.getTblLookupKey(Common.TI_FIELD_TYPE);
//		Type typeChar = m.getType(Type.ftChar);

		int size = typeList.getSize();
		for (int i = 0; i < size; i++) {
			key = ((Integer) typeList.getKeyAt(i));
			fld = typeList.getFieldAt(i);
			valid = typeList.getFieldAt(i, VALID_IDX);
			String s = "";
			if (key == 100 || key == Type.ftChar) {
				System.out.println();
			}
			if (fld != null) {
				s = fld.toString();
			}

			t = m.getType(key);
			if (t != null && (valid == null || Boolean.TRUE.equals(valid) || "".equals(valid) )) {
				tc = new TreeComboItem(
						key,
						LangConversion.convertId(
								LangConversion.ST_EXTERNAL,
								foreignLookUpId + key,
								s),
						s, false);
//				if (key == Type.ftDateDMY) {
//					System.out.print('.');
//				}
				if (onlyTextFields && t.isBinary()) {
					
				} else if (key >= Type.USER_RANGE_START) {
					if (t instanceof TypeDateWrapper) {
						dateTypes.add(tc);
					} else {
						userTypes.add(tc);
					}
				} else {
					switch (key) {
					case Type.ftCsvArray:
					case Type.ftXmlNameTag:
					case Type.ftMultiLineEdit:
					case Type.ftCharNullPadded:
					case Type.ftCharNullTerminated:
					case Type.ftCharRestOfRecord:
	                case Type.ftCharMultiLine:	
					case Type.ftMultiLineChar:
					case Type.ftArrayField:
					case Type.ftRecordEditorType:
						specialTypes.add(tc);
						break;
					case Type.ftFjZonedNumeric:
					case Type.ftZonedNumeric:
					case Type.ftGnuCblZonedNumeric:
					case Type.ftNumOrEmpty:
						numericTypes.add(tc);
						break;
					case Type.ftCheckBoxTrue:
					case Type.ftCheckBoxYN:
					case Type.ftCheckBoxTF:
					case Type.ftCheckBoxBoolean:
					case Type.ftCheckBoxY:
						checkBoxTypes.add(tc);
						break;
					default:
						if (t instanceof TypeDateWrapper) {
							dateTypes.add(tc);
						} else if (s.startsWith("Date ")) {
						} else if (t.isNumeric()) {
							if (t.isBinary()) {
								binTypes.add(tc);
							} else if (t instanceof TypeCommaDecimalPoint) {
								commaDecimalTypes.add(tc);
							} else if (t instanceof TypeChar && ((TypeChar) t).isLeftJustified()) {
								leftJustifiedTypes.add(tc);
							} else if (t instanceof TypeNum && " ".equals(((TypeNum) t).getPadChar())) {
								spacePaddedTypes.add(tc);
							} else {
								numericTypes.add(tc);
							}
						} else {
							charTypes.add(tc);
						}
					}
				}
			}
		}

		int comboSize = charTypes.items.size();

		for (MenuDtls menu : subMenus) {
			if (menu.items.size() > 0) {
				comboSize += 1;
			}
		}
		ret = new TreeComboItem[comboSize];
		int idx = 0;
		int k = 16121;

		for (MenuDtls menu : subMenus) {
			if (menu.items.size() > 0) {
				ret[idx++] = bldMenu(k++, menu.name, menu.items);
			}
		}

//		ret[idx++] = bldMenu(k++, "Numeric Types", numericTypes);
//		ret[idx++] = bldMenu(k++, "Binary Numeric Types", binTypes);
//		ret[idx++] = bldMenu(k++, "Left Justified Numeric Types", leftJustifiedTypes);
//		ret[idx++] = bldMenu(k++, "Right Justified Space padded Types", spacePaddedTypes);
//		ret[idx++] = bldMenu(k++, "Numeric Decimal point='.'", commaDecimalTypes);
//		ret[idx++] = bldMenu(k++, "Date Types", dateTypes);
//		ret[idx++] = bldMenu(k++, "Checkbox Types", checkBoxTypes);
//		ret[idx++] = bldMenu(k++, "Special Char Types", specialTypes);
//
//		if (userTypes.size() > 0) {
//			ret[idx++] = bldMenu(k++, "locally defined Types", userTypes);
//		}

		for (TreeComboItem c : charTypes.items) {
			ret[idx++] = c;
		}

		return ret;
	}


	private static TreeComboItem bldMenu(int key, String name, ArrayList<TreeComboItem> items) {
		TreeComboItem[] itms = new TreeComboItem[items.size()];
		itms = items.toArray(itms);

		return new TreeComboItem(key, name, LangConversion.convert(LangConversion.ST_COMBO, name), itms);
	}


	private static class MenuDtls {
		final ArrayList<TreeComboItem> items = new ArrayList<TreeComboItem>();

		final String name;

		public MenuDtls(String name, ArrayList<MenuDtls> dtls) {
			super();
			this.name = name;
			dtls.add(this);
		}

		/**
		 * @param e
		 * @return
		 * @see java.util.ArrayList#add(java.lang.Object)
		 */
		public boolean add(TreeComboItem e) {
			return items.add(e);
		}

	}
}

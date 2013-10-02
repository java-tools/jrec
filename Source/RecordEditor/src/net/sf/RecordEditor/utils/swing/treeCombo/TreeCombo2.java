package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;


/**
 * TreeCombo Swing utilitity
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TreeCombo2 extends ComboLikeObject {

	private static final TreeComboItem[] EMPTY_TREE = {};

	private TreeComboItem selectedItem = null;
	private HashMap<Integer, TreeComboItem> itemMap = new HashMap<Integer, TreeComboItem>();
//	private final TreeComboItem[] items;


	private final TreeComboPopup popup;

	private boolean toInitListners = true;


	/**
	 * Display an array of Tree-Items in a Tree Combo
	 * @param itms array of Tree-Items
	 */
	public TreeCombo2(TreeComboItem[] itms) {
		this(itms, (JButton[]) null);
	}

	/**
	 * Display an array as a Tree Combo with extra buttons
	 * @param itms items to display in the Tree-Combo
	 * @param btns buttons to add at the End
	 */
	public TreeCombo2(TreeComboItem[] itms, JButton...btns) {
		super(null, btns);
		if (itms == null) {
			itms = EMPTY_TREE;
		}
		popup = new TreeComboPopup(this, itms);
		super.setCurrentPopup(popup);

		if (itms != null) {
			super.setPopupHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT * Math.min(16, itms.length));
		}


		this.valueTxt.addHierarchyBoundsListener(new HierarchyBoundsListener() {
			@Override public void ancestorResized(HierarchyEvent e) {
				hidePopup();
			}

			@Override public void ancestorMoved(HierarchyEvent e) {
				hidePopup();
			}
		});
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.ComboLikeObject#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		this.actionPerformed_classInit();
		super.actionPerformed(e);
	}

	private void actionPerformed_classInit() {

		if (toInitListners) {
			toInitListners = false;
			Container c = this;
			while (c != null) {
				if (c instanceof Window) {
					((Window) c).addWindowListener(new WindowAdapter() {
						@Override public void windowIconified(WindowEvent e) {
							//System.out.println(" ==!! Window Iconfied " + e.paramString());
							hidePopup();
						}

						@Override public void windowDeactivated(WindowEvent e) {
							//System.out.println(" ==!! Window Deactivated " + e.paramString());
							hidePopup();
						}

						@Override public void windowClosing(WindowEvent e) {
							//System.out.println(" ==!! Window Closing " + e.paramString());
							hidePopup();
						}
					});
					break;
				} else if (c instanceof JInternalFrame) {
					((JInternalFrame) c).addInternalFrameListener(new InternalFrameAdapter() {
						@Override public void internalFrameIconified(InternalFrameEvent e) {
							hidePopup();
						}

						@Override public void internalFrameDeactivated(InternalFrameEvent e) {
							hidePopup();
						}

						@Override public void internalFrameClosing(InternalFrameEvent e) {
							hidePopup();
						}
					});
				}
				c = c.getParent();
			}
		}

	}


	public final void setTree(TreeComboItem[] items) {
		popup.setList(items);

		if (items != null) {
			super.setPopupHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT * Math.min(16, items.length));
		}
	}



	/**
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#highlightItem(javax.swing.JPopupMenu, boolean)
	 */
	@Override
	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {
		if (visible) {
			searchChildren(new ArrayList<TreeComboItem>(), popup.items);
		}  else {
			this.popup.deHighLight();
		}
	}




	private boolean searchItem(ArrayList<TreeComboItem> parents, TreeComboItem item) {
		parents.add(item);

		boolean ret = searchChildren(parents, item.getChildren());

		parents.remove(item);

		return ret;

	}

	private boolean searchChildren(ArrayList<TreeComboItem> parents, TreeComboItem[] children) {

		for (int i = 0; i < children.length; i++) {
			TreeComboItem child = children[i];
			//JMenu m;

			if (child.equals(selectedItem)) {
//				MenuElement[] me = new MenuElement[parents.size() * 2 + 2];
//
//				me[0] = menu;
//				for (int j = 0; j < parents.size(); j++) {
//					m =  menuMap.get(parents.get(j));
//					me[j*2+1] = m;
//					me[j*2+2] = m.getPopupMenu();
//				}
//				me[me.length - 1] = menuItemMap.get(child);
//
//				MenuSelectionManager.defaultManager().setSelectedPath(me);
//
//				TreeComboItem[] ti = new TreeComboItem[parents.size() + 1];
				parents.add(child);
				popup.highLight(0, parents);
				return true;
			} else if (child.getChildren() != null && child.getChildren().length > 0) {
				if (searchItem(parents, child)) {
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * @return the selectedItem
	 */
	public TreeComboItem getSelectedItem() {
		return selectedItem;
	}


	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(TreeComboItem selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setText(selectedItem.getEditString());
		}
	}

	public void setSelectedKey(int key) {
		TreeComboItem item = itemMap.get(key);
		if (item == null) {
			item = TreeComboItem.BLANK_ITEM;
		}
		setSelectedItem(item);
	}


	private static class TreeComboPopup extends JPopupMenu {
		//private Vector<TreeComboItem> items = new Vector<TreeComboItem>();
		private TreeComboItem[] items;

		@SuppressWarnings({ "rawtypes"})
		private JList displayList = new JList();
		private JScrollPane scrollPane = new JScrollPane(displayList);

		private TreeComboPopup childPopup = null;

		private final TreeCombo2 parentCombo;

		@SuppressWarnings("unchecked")
		private TreeComboPopup(TreeCombo2 parent, TreeComboItem[] itms) {

			this.parentCombo = parent;

			setList(itms);

			super.add(scrollPane);

			displayList.setCellRenderer(new ComboBoxRenderer());
			displayList.addListSelectionListener(new ListSelectionListener() {
				private int lastIndex = -121;
				@Override
				public void valueChanged(ListSelectionEvent e) {
					int selectedIdx = displayList.getSelectedIndex();
					if (selectedIdx >= 0 && selectedIdx != lastIndex && items != null) {
						TreeComboItem[] childList = items[selectedIdx].getChildren();
						if (childList == null) {
							setChildVisible(false);
							parentCombo.setSelectedItem(items[selectedIdx]);
						} else {
							highLightSelectedItem(selectedIdx, childList);
						}
					}

					lastIndex = selectedIdx;
				}
			});
		}

		public final void highLight(int lvl, List<TreeComboItem> list) {

			TreeComboItem searchItem = list.get(lvl);
			for (int i = 0; i < items.length; i++) {
				if (items[i].equals(searchItem)) {
					TreeComboItem[] childList = items[i].getChildren();
					displayList.setSelectedIndex(i);
					if (childList != null && lvl < list.size()) {
						highLightSelectedItem(i, childList);
						childPopup.highLight(lvl + 1, list);
					}
				}
			}
		}

		private void highLightSelectedItem(int selectedIdx, TreeComboItem[] childList) {
			int selectedLine = selectedIdx - displayList.getFirstVisibleIndex();
//			System.out.println(" --> " + displayList.getFirstVisibleIndex()
//					+ " " + selectedIdx
//					+ " ~ " + displayList.indexToLocation(selectedLine).y);

			childPopup.setList(childList);
			childPopup.show(TreeComboPopup.this,
							TreeComboPopup.this.getWidth(),
							displayList.indexToLocation(selectedLine).y);
		}

		public final void deHighLight() {
			displayList.setSelectedIndex(-1);
			if (childPopup != null) {
				childPopup.deHighLight();
			}
		}

		public final void setList(TreeComboItem[] itms) {

			items = itms;
			displayList.setListData(itms);
			setupDisplayList();
			//displayList.setCellRenderer(new ComboBoxRenderer());
			//displayList.repaint();
			//System.out.println("\t " + displayList.getModel().getSize());
		}

		public final void setChildVisible(boolean b) {
			if (childPopup != null) {
				childPopup.setVisible(b);
			}
		}

		public void show(Component invoker, int x, int y) {

//			if (! (invoker instanceof TreeComboPopup)) {
//				setInvoker(invoker);
//			}
	        if (invoker != null) {
	    	    Point invokerOrigin = invoker.getLocationOnScreen();

	            setLocation(invokerOrigin.x + x, invokerOrigin.y + y);
				//System.out.println(" --- Show " + invoker.getClass().getName());
	        } else {
	            setLocation(x, y);
	        }
	        setVisible(true);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JPopupMenu#setVisible(boolean)
		 */
		@Override
		public void setVisible(boolean b) {
			super.setVisible(b);

			if (! b && childPopup != null) {
				childPopup.setVisible(false);
			}
		}

		private void setupDisplayList() {
			int len = 1;
			if (items != null && items.length > 0) {
				len = items.length;
				if (childPopup == null) {
					for (TreeComboItem tc : items) {
						if (tc.getChildren() != null) {
							childPopup = new TreeComboPopup(parentCombo, null);
							break;
						}
					}
				}
			}

			displayList.setVisibleRowCount( Math.min(15, len) );
		}
	}



	private static class ComboBoxRenderer extends DefaultListCellRenderer {

		private static final ImageIcon FOLDER_ICON = Common.getRecordIcon(Common.ID_MENU_FOLDER);

		/* (non-Javadoc)
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(
				@SuppressWarnings("rawtypes") JList list,
				Object value,
				int index, boolean isSelected, boolean cellHasFocus) {


			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof TreeComboItem) {
				TreeComboItem itm = (TreeComboItem) value;

				if (itm.getChildren() != null) {
					this.setBorder(this.getBorder());
					this.setIcon(FOLDER_ICON);
				}

				this.setText(itm.getEnglish());
			}
			this.setBackground(c.getBackground());
			this.setForeground(c.getForeground());

			return this;
		}

	}

}

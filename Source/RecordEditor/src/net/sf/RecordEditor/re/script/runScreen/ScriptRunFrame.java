/**
 *
 */
package net.sf.RecordEditor.re.script.runScreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayDetails;
import net.sf.RecordEditor.re.display.IChildDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.FormatFileName;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.openFile.RecentFilesList;
import net.sf.RecordEditor.re.script.IEditor;
import net.sf.RecordEditor.re.script.ScriptData;
import net.sf.RecordEditor.re.script.bld.RunScriptSkelPopup;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.OpenSaveAction;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileTreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ScriptRunFrame extends ReFrame implements BasicLayoutCallback, IEditor, IChildDisplay, ChangeListener {

	private static String RESOURCE_PATH;
	private static ArrayList<LanguageDetails> langList = new ArrayList<LanguageDetails>(20);
	private static HashMap<String, LanguageDetails> extLangMap = new HashMap<String, LanguageDetails>(20) ;


//	private JComboBox languageCombo;
//	private FileChooserTxt templateFC = new FileChooserTxt(true);
	private final TreeComboFileSelect templateFC;// = new TreeComboFileSelect(true);
//	private String lastFile = "";
	private JButton runBtn = SwingUtils.newButton("Run !!!", Common.getRecordIcon(Common.ID_SCRIPT_ICON));
	private JTextArea msg = new JTextArea();
	private JTabbedPane tab = new JTabbedPane();
	private JTextArea outputTxt = new JTextArea();

	private Theme currentTheme = null;
	private ArrayList<ScriptEditPane> panes = new ArrayList<ScriptEditPane>();

	private ReFrame activeFrame;
	private final boolean runScript;
	private final int extraScreenCount;

	private static FormatFileName formatFileName = new FormatFileName() {
		@Override
		public String formatLayoutName(String layoutName) {
			return layoutName;
		}
	};
	private static RecentFiles	recent = new RecentFiles(
			Parameters.getApplicationDirectory() + "ScriptFiles.txt",
			formatFileName,
			true, null);
	private RecentFilesList recentList = new RecentFilesList(recent, this);


	private ArrayList<AbstractActiveScreenAction> menuActions = new ArrayList<AbstractActiveScreenAction>(40);

//	private FocusAdapter filenameListner = new FocusAdapter() {
//
//		@Override
//		public void focusLost(FocusEvent e) {
//
//			if (! lastFile.equals(templateFC.getText())) {
//				checkFile(false);
//				lastFile = templateFC.getText();
//			}
//		}
//	};

	private ChangeListener tabChangeListner = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
//       	System.out.println("Tab change requests - start");
//        	templateFC.removeFocusListener(filenameListner);
        	
        	if (isScriptTab()) {
	        	templateFC.removeTextChangeListner(this);
	        	ScriptEditPane activePane = getActivePane();
				File scriptFile = activePane.getScriptFile();
	        	if (scriptFile != null) {
	        		templateFC.setText(scriptFile.getPath());
	        	}
	//        	activePane.tabComponent.requestFocus();
	//        	activePane.getTextArea().requestFocus();
	        	updateMenuStatus();
	//         	System.out.println("Tab change requests - end");
	//           	templateFC.addFocusListener(filenameListner);
	    		templateFC.addTextChangeListner(this);
	    		templateFC.setNotifyOfAllUpdates(true);
        	}
       }
    };

    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:		run();    					        		break;
        	case KeyEvent.VK_ESCAPE:	ScriptRunFrame.this.doDefaultCloseAction();	break;
        	}
        }
    };

	private OpenSaveAction openAction = new OpenSaveAction(this, true) {

		@Override
		public void processFile(File selectedFile) {
			setNewFileName(selectedFile);
		}

		@Override
		public File getDefaultFile() {
			return new File(templateFC.getText());
		}
	};

	private ReAbstractAction newAction = new ReAbstractAction("New", Common.ID_NEW_ICON) {
		@Override
		public void actionPerformed(ActionEvent e) {
			newFile();
		}
	};
	private ActionOnActiveScreen saveAction;
	private OpenSaveAction saveAsAction;
	private ReAbstractAction saveAllAction;


	static {
		ArrayList<LanguageDetails> extLangList = new ArrayList<LanguageDetails>(20);
		String s = ScriptRunFrame.class.getName().replace(".", "/");
		RESOURCE_PATH = '/' + s.substring(0, s.lastIndexOf('/'));

		LanguageDetails[] stdLang = {
//				DEFAULT_LANGUAGE_DEF,
				new LanguageDetails("js",   "JavaScript", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, null),
				new LanguageDetails("bsh",  "Bean Shell", SyntaxConstants.SYNTAX_STYLE_JAVA,   null),
				new LanguageDetails("rb",	"Ruby",		  SyntaxConstants.SYNTAX_STYLE_RUBY,   null),
				new LanguageDetails("py",	"Python",     SyntaxConstants.SYNTAX_STYLE_PYTHON, null),
				new LanguageDetails("java", "Java",       SyntaxConstants.SYNTAX_STYLE_JAVA,   null),
				new LanguageDetails("c",	"c",          SyntaxConstants.SYNTAX_STYLE_C,      null),
				new LanguageDetails("xml",	"xml",        SyntaxConstants.SYNTAX_STYLE_XML,    null),
		};

		for (LanguageDetails l : stdLang) {
			extLangList.add(l);
		}


		for (LanguageDetails l : extLangList) {
			extLangMap.put(l.ext, l);
		}

		@SuppressWarnings("rawtypes")
		Set keySet = TokenMakerFactory.getDefaultInstance().keySet();
		String langKey, ext;
		List<String[]> langDtls = net.sf.RecordEditor.re.script.ScriptMgr.getLanguageExt();
		for (String[] langDtl : langDtls) {
			if (langDtl[0] != null) {
				ext = langDtl[0].toLowerCase();
				try {
					langKey = "text/" + langDtl[1];
					if (extLangMap.containsKey(ext)) {
						extLangMap.get(ext).scriptLangName = langDtl[1];
					} else {
						if (! keySet.contains(langKey)) {

							TokenMakerFactory f = TokenMakerFactory.getDefaultInstance();

							if (f == null) {
								langKey = LanguageDetails.DEFAULT_LANGUAGE_DEF.rSyntax;
							} else if (f.keySet().contains(langKey)) {
								
							} else {
								String className = "org/fife/ui/rsyntaxtextarea/modes/" + langDtl[1] + "TokenMaker.class";
						
								URL resource = ScriptRunFrame.class.getClassLoader().getResource(className);
								if (f instanceof AbstractTokenMakerFactory && resource != null) {
									className = "org.fife.ui.rsyntaxtextarea.modes." + langDtl[1] + "TokenMaker";
									((AbstractTokenMakerFactory) f).putMapping(langKey, className);
								} else {
									langKey = LanguageDetails.DEFAULT_LANGUAGE_DEF.rSyntax;
								}
							}
						}

						LanguageDetails ld = new LanguageDetails(ext, langDtl[1], langKey, langDtl[1]);
						extLangList.add(ld);
						extLangMap.put(ld.ext, ld);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		TreeMap<String, LanguageDetails> map = new TreeMap<String, LanguageDetails>();
		for (LanguageDetails l : extLangList) {
			if (! map.containsKey(l.langName)) {
				map.put(l.langName, l);
			}
		}

		Set<String> langkeys = map.keySet();
		langList.add(LanguageDetails.DEFAULT_LANGUAGE_DEF);
		for (String key : langkeys) {
			langList.add(map.get(key));
		}
	}

	/**
	 * Display a frame where users can run
	 */
	public ScriptRunFrame() {
		this(ReFrame.getActiveFrame());
	}

	/**
	 * Display a frame where users can run
	 */
	public ScriptRunFrame(ReFrame activeFrame) {
		this(activeFrame, true);
	}
	
	public ScriptRunFrame(ReFrame activeFrame, boolean visible) {
		this(activeFrame, "Run Script Screen", visible, true, true);
	}
	
	public ScriptRunFrame(ReFrame activeFrame, String title, boolean visible, boolean runScript, boolean addOutput) {
		super("", title, null);
		this.activeFrame = activeFrame;
		this.runScript = runScript;
		if (activeFrame == null || ! (activeFrame.getDocument() instanceof FileView)) {
			msg.setText(" >>> Warning  >>>  can not retrive File details, this could affect the script !!!");
		}
		templateFC = new TreeComboFileSelect(true, false, true, recent.getFileComboList(), recent.getDirectoryList());
		extraScreenCount = addOutput? 1 : 0;

		init_100(addOutput);
		init_200_layout(visible);
		init_300_listner();
	}

	private void init_100(boolean addOutput) {

//		LanguageDetails languageDetails;
		ScriptEditPane editPane = new ScriptEditPane(tabChangeListner);
		if (addOutput) {
			tab.add("Output", new JScrollPane(outputTxt));
		}
		addTab(editPane);
	
		saveAction = new ActionOnActiveScreen("Save", Common.ID_SAVE_ICON) {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScriptEditPane activePane = getActivePane();
				if (activePane == null) {

				} else if (activePane.isEmpty()) {
					saveAsAction.actionPerformed(null);
				} else {
					saveFile();
				}
			}

			/* (non-Javadoc)
			 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
			 */
			@Override
			public void checkActionEnabled() {
				ScriptEditPane activePane = getActivePane();
				setEnabled(
						   activePane != null
						&& ( (! activePane.isEmpty())
						  || (! "".equals(activePane.getText())) )
				);
			}
		};
		saveAsAction = new OpenSaveAction(this, false) {

			@Override
			public void processFile(File selectedFile) {
				saveAs(selectedFile);
			}


			@Override
			public File getDefaultFile() {
				File scriptFile = null;
				ScriptEditPane activePane = getActivePane();
				if (activePane != null) {
					scriptFile = activePane.getScriptFile();
					if (scriptFile == null) {
						return new File(templateFC.getText());
					}
				}
				return scriptFile;
			}
		};

		saveAllAction = new ReAbstractAction("Save All ...", Common.ID_SAVE_ICON) {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (panes.size() > 0 /*|| ! panes.get(0).isEmpty()*/) {
					StringBuilder msgStr = new StringBuilder();
					int idx = tab.getSelectedIndex();
					File dir = getDefaultdir();
					
					tab.removeChangeListener(tabChangeListner);

					try {
						for (ScriptEditPane p : panes) {
							if (p.isChanged()) {
								try {
									if (p.isEmpty()) {
										tab.setSelectedComponent(p.tabComponent);
										saveAsAction.askUser(dir);
									} else {
										p.saveFile();
									}
									
								} catch (IOException ex) {
									msgStr.append(UtMessages.ERROR_SAVING_FILE.get(
											p.getScriptFile().getPath(), e.toString()));
								}
							}
						}
					} finally {
						tab.addChangeListener(tabChangeListner);
						tab.setSelectedIndex(idx);
					}

					String m = msgStr.toString();
					if (! "".equals(m)) {
						msg.setText(m);
						Common.logMsgRaw(m, null);
					}
				}
			}
		};

	}

	private void init_200_layout(boolean visible) {
		BaseHelpPanel pnl = new BaseHelpPanel();
//		List<String>  langs = net.sf.RecordEditor.re.script.ScriptMgr.getLanguages();
//
//		languageCombo = new JComboBox(
//				langs.toArray(new String[langs.size()])
//		);
		String scriptDir = Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.getWithStar();
		int screenHeight = ReFrame.getDesktopHeight() - 2;
		int desktopWidth = ReFrame.getDesktopWidth();
		JSplitPane msgPnl = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tab,  new JScrollPane(msg));


		templateFC.setText(scriptDir
				+ " ================================== ");

//		pnl.addLine("Script", templateFC, templateFC.getChooseFileButton())
//		   .setGap(BaseHelpPanel.GAP1);
//		pnl.addLine("", null, runBtn)
//		   .setGap(BaseHelpPanel.GAP2);
		
		if (runScript) {
			pnl.addLineRE("Script", templateFC, runBtn)
			   .setGapRE(BaseHelpPanel.GAP2);
		}


		pnl.addMessage(msgPnl);

		pnl.setHeightRE(BaseHelpPanel.FILL);

		pnl.addReKeyListener(listner);
		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_SCRIPT));

		init_210_BuildMenu();

	    JPanel fullPanel = new JPanel();

	    fullPanel.setLayout(new BorderLayout());
	    fullPanel.add("North", init_220_BuildToolBar());
	    fullPanel.add("Center", pnl);

		this.addMainComponent(fullPanel); //pnl);
		this.templateFC.setText(scriptDir);
//		this.lastFile = scriptDir;
		this.setVisible(visible);
		this.setToMaximum(false);
		//this.setSize(this.getWidth(), screenHeight);
		this.setBounds(desktopWidth - this.getWidth() - 1, this.getY(), this.getWidth(), screenHeight);
				
		msgPnl.setDividerLocation(msgPnl.getHeight() - SwingUtils.STANDARD_FONT_HEIGHT * 7);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowWillBeClosing()
	 */
	@Override
	public void windowWillBeClosing() {

		File dir = getDefaultdir();
		
		tab.removeChangeListener(tabChangeListner);
		for (ScriptEditPane p : panes) {
			if (p.isChanged()) {
				try {
					if (p.isEmpty()) {
						tab.setSelectedComponent(p.tabComponent);
						saveAsAction.askUser(dir);
					} else {
						p.checkSave();
					}
				} catch (IOException ex) {
					Common.logMsgRaw(
							UtMessages.ERROR_SAVING_FILE.get(p.getScriptFile().getPath(), ex.toString()),
							null);
				}
			}
		}
		super.windowWillBeClosing();
	}

	private void init_210_BuildMenu() {
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(init_211_BuildFileMenu());
		menuBar.add(init_212_BuildLanguage());
		menuBar.add(init_214_BuildViewMenu());
		menuBar.add(init_215_BuildThemeMenu());
		
		if (activeFrame != null && DisplayDetails.getDisplayDetails(activeFrame) != null) {
			JMenu bldMenu = RunScriptSkelPopup.buildScriptMenu(this, "Build");
			if (bldMenu != null) {
				menuBar.add(bldMenu);
			}
		}

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem(new AboutAction()));
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}

	private JMenu init_211_BuildFileMenu() {
		JMenu fileMenu = new JMenu("File");

		fileMenu.add(newAction);

		fileMenu.add(openAction);

		fileMenu.add(saveAction);


		fileMenu.add(saveAsAction);
		fileMenu.add(saveAllAction);

		fileMenu.add(recentList.getMenu());

		return fileMenu;
	}

	private JMenu init_212_BuildLanguage() {
		JMenu langMenu = new JMenu("Language");

		ButtonGroup bg = new ButtonGroup();

		for (LanguageDetails l : langList) {
			ChangeSyntaxStyleAction item = new ChangeSyntaxStyleAction(l);
	        bg.add(item.menuItem);
	        langMenu.add(item.menuItem);
		}

		return langMenu;
	}

	private JMenu init_214_BuildViewMenu() {
		JMenu viewMenu = new JMenu("View");

	        viewMenu.add(init_2141_CreateCheckBox(new CodeFoldingAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new ViewLineHighlightAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new ViewLineNumbersAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new AnimateBracketMatchingAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new BookmarksAction()));
	        viewMenu.add(new JCheckBoxMenuItem(new WordWrapAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new ToggleAntiAliasingAction()));
	        viewMenu.add(init_2141_CreateCheckBox(new MarkOccurrencesAction()));
	        viewMenu.add(new JCheckBoxMenuItem(new TabLinesAction()));

		return viewMenu;
	}

	private JCheckBoxMenuItem init_2141_CreateCheckBox(AbstractAction a) {
		JCheckBoxMenuItem cb = new JCheckBoxMenuItem(a);
		cb.setSelected(true);
		return cb;
	}

	private JMenu init_215_BuildThemeMenu() {
		JMenu themeMenu = new JMenu("Theme");

		ButtonGroup bg = new ButtonGroup();
        init_2151_addThemeItem("Default", "/default.xml", bg, themeMenu);
        init_2151_addThemeItem("Default (Alternate)", "/default-alt.xml", bg, themeMenu);
        init_2151_addThemeItem("Dark", "/dark.xml", bg, themeMenu);
        init_2151_addThemeItem("Eclipse", "/eclipse.xml", bg, themeMenu);
        init_2151_addThemeItem("IDEA", "/idea.xml", bg, themeMenu);
        init_2151_addThemeItem("Visual Studio", "/vs.xml", bg, themeMenu);

        return themeMenu;
	}

    private void init_2151_addThemeItem(String name, String themeXml, ButtonGroup bg,
            JMenu menu) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(
                new ThemeAction(name, themeXml));
        bg.add(item);
        menu.add(item);
	}

	private JToolBar init_220_BuildToolBar() {
		JToolBar toolBar = new JToolBar();

	    toolBar.add(openAction);
	    toolBar.add(newAction);
	    toolBar.add(saveAction);
	    toolBar.add(saveAsAction);
	    toolBar.add(saveAllAction);

	    if (runScript) {
		    try {
		        toolBar.add(new JSeparator());
		    } catch (Exception e) {
		        toolBar.addSeparator( new Dimension(
		        				SwingUtils.STANDARD_FONT_WIDTH,
		        				toolBar.getHeight()));
	        }
		    toolBar.add(new ReAbstractAction("Run", Common.ID_SCRIPT_ICON) {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					run();
				}
			});
	    }

	    return toolBar;
    }


	private void init_300_listner() {
		if (runScript) {
			runBtn.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					run();
				}
			});
		}

//		templateFC.addFcFocusListener(filenameListner);
		List<FileTreeComboItem> recentComboList = recent.getFileComboList();
		templateFC.setTree(recentComboList.toArray(new FileTreeComboItem[recentComboList.size()]));
		templateFC.addTextChangeListner(this);
		

		tab.addChangeListener(tabChangeListner);
	}

	private void checkFile(boolean reload) {
//      	System.out.println("check File - start");

		String fname = templateFC.getText();
		File f = new File(fname);

		try {
			if (f.exists() && f.isFile()) {
				for (int i = 0; i < panes.size(); i++) {
					if ((! panes.get(i).isEmpty()) && f.equals(panes.get(i).getScriptFile())) {
						if (reload) {
							panes.get(i).readFile(f);
						}
						tab.setSelectedIndex(i);
						checkLanguage(fname);
						return;
					}
				}

				if (panes.size() == 1 && panes.get(0).isEmpty()) {
					panes.get(0).readFile(f);
					updateMenuStatus();
				} else {
					addTab(new ScriptEditPane(tabChangeListner, f));
					tab.setSelectedIndex(tab.getTabCount() - 1 - extraScreenCount);
				}

				recent.putFileLayout(fname, "?");
				//recentList.update();
			}
		} catch (IOException e) {
			String error = e.toString();
			msg.setText(error);
		}
		checkLanguage(fname);
//       	System.out.println("check File - end");
	}

	private void addTab(ScriptEditPane p) {
		panes.add(p);
		//tab.addTab("", p.tabComponent);
		if (tab.getTabCount() == 0) {
			tab.addTab("", p.tabComponent);
		} else {
			tab.insertTab("", null, p.tabComponent, "", tab.getTabCount() - extraScreenCount);
		}
		int newTabIdx = panes.size() - 1;
		tab.setTabComponentAt(newTabIdx, p.tabWithClose);
		new TabCloseListner(p);

		if (currentTheme != null) {
			currentTheme.apply(panes.get(newTabIdx).getTextArea());
		}
		tab.setSelectedIndex(newTabIdx);
	}

	private void checkLanguage(String fname) {
    	String ext = Parameters.getExtensionOnly(fname);
    	if (ext != null && ! "".equals(ext)) {
			setLanguage(ext);
    	}
	}


	public final void setLanguage(String ext) {
		if (isScriptTab()) {
			try {
				LanguageDetails l = extLangMap.get(ext.toLowerCase());
				if (l == null) {
					l = LanguageDetails.DEFAULT_LANGUAGE_DEF;
				}
	
	        	panes.get(tab.getSelectedIndex()).setLanguageDetails(l);
	        	updateMenuStatus();
	
			} catch (Exception e2) {
				// do nothing
			}
		}
	}

	private void run() {

		if (runScript && isScriptTab()) {
			try {
				ScriptEditPane scriptEditPane = panes.get(tab.getSelectedIndex());
				String scriptName = scriptEditPane.getScriptFile().getPath();
				String script = scriptEditPane.getText();
				ScriptData data =  ScriptData.getScriptData( activeFrame, scriptName);
				String s = "";
				String lang = scriptEditPane.getLanguageDetails().scriptLangName;
	//			if (languageCombo.getSelectedItem() != null) {
	//				lang = languageCombo.getSelectedItem().toString();
	//			}
	
				if ("".equals(script)) {
					msg.setText(LangConversion.convert("No Script to Run !!!"));
				} else {

					updateOutput out = new updateOutput();
					new Thread(out).start();
					if (lang == null) {
						(new net.sf.RecordEditor.re.script.ScriptMgr())
							.runScript(scriptName, data, script, out.w);
					} else {
						(new net.sf.RecordEditor.re.script.ScriptMgr())
							.runScript(lang, scriptName, data, script, out.w);
					}
					out.cont = false;
				}
	
				if (data != null && ! "".equals(data.outputFile)) {
					s = "\n " +  LangConversion.convert("Output File:") + " " + data.outputFile;
				}
				msg.setText(LangConversion.convert("Script {0} run  !!!", scriptName) + s);
			} catch (Exception e) {
				String s = LangConversion.convert("Error:") + " " + e.getClass().getName() + " " + e.getMessage();
				msg.setText(s);
				Common.logMsgRaw(s, e);
			}
		}
	}

	/**
	 * Update the status of Menu items
	 */
	private void updateMenuStatus() {
    	for (AbstractActiveScreenAction as : menuActions) {
    		as.checkActionEnabled();
    	}
	}

	private ScriptEditPane getActivePane() {

		if (isScriptTab()) {
			return panes.get(tab.getSelectedIndex());
		}
		return null;
	}



	private void newFile() {
		if (panes.size() == 1 && panes.get(0).isEmpty()) {

		} else {
			addTab(new ScriptEditPane(tabChangeListner));
			tab.setSelectedIndex(tab.getTabCount() - 1 - extraScreenCount);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.BasicLayoutCallback#setRecordLayout(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void setRecordLayout(int layoutId, String layoutName, String filename) {
		setFileName(filename);
	}

	private void saveAs(File selectedFile) {
		if (isScriptTab()) {
			try {
				getActivePane().saveAsFile(selectedFile);
				setNewFileName(selectedFile);
			} catch (IOException e) {
				msg.setText(e.toString());
			}
		}
	}

	private void setNewFileName(File selectedFile) {
		setFileName(selectedFile.getPath());
	}


	public final void setFileName(String filename) {
		setFileNameInt(filename, false);
	}
	
	
	@Override
	public final void loadFileName(String filename) {
		setFileNameInt(filename, true);
	}

	private  void setFileNameInt(String filename, boolean reload) {
//		templateFC.removeFocusListener(filenameListner);
    	templateFC.removeTextChangeListner(this);
		templateFC.setText(filename);
//		lastFile = filename;
		checkFile(reload);
//		templateFC.addFocusListener(filenameListner);
		templateFC.addTextChangeListner(this);
	}




	private void saveFile() {
		if (isScriptTab()) {
			try {
				getActivePane().saveFile();
			} catch (IOException ex) {
				msg.setText(ex.toString());
			}
		}
	}





	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		switch (action) {
		case ReActionHandler.NEW:		newFile();								break;
		case ReActionHandler.OPEN:		openAction.actionPerformed(null);		break;
		case ReActionHandler.SAVE:		saveFile();								break;
		case ReActionHandler.SAVE_AS:	saveAsAction.actionPerformed(null);		break;
		//case ReActionHandler.FIND:											break;

		default: super.executeAction(action);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		switch (action) {
		case ReActionHandler.NEW:
		case ReActionHandler.OPEN:
		case ReActionHandler.SAVE:
		case ReActionHandler.SAVE_AS:
		//case ReActionHandler.FIND:
			return true;
		}
		return super.isActionAvailable(action);
	}





	@Override
	public AbstractFileDisplay getSourceDisplay() {
		return DisplayDetails.getDisplayDetails(activeFrame);
	}


	private File getDefaultdir() {
		File ret;
		String filename = templateFC.getText();
		String defaultDirName = Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.getWithStar();
		if (filename == null || filename.length() == 0) {
			ret = new File(defaultDirName);
		} else {
//			if (filename.endsWith("*")) {
//				filename = filename.substring(0, filename.length() - 1);
//			}
			if (filename.equals(defaultDirName)) {
				String t = filename.substring(0, filename.length() - 1) + "UserScripts" + Common.FILE_SEPERATOR ;
				if ((new File(t)).isDirectory()) {
					filename = t + "*";
				}
			}
			ret = new File(filename);
//			if (! ret.isDirectory()) {
//				ret = ret.getParentFile();
//			}
		}
		return ret;
	}



	@Override
	public void stateChanged(ChangeEvent e) {
//		String filename = templateFC.getText();
		
		checkFile(false);
//		lastFile = filename;
	}
	
	private boolean isScriptTab() {
		return isScriptTab(tab.getSelectedIndex()); 
	}

	private boolean isScriptTab(int idx) {
		return idx < tab.getTabCount() - extraScreenCount;
	}

	private class TabCloseListner implements ActionListener {
		private final ScriptEditPane editPane;


		public TabCloseListner(ScriptEditPane editPane) {
			super();
			this.editPane = editPane;
			editPane.tabWithClose.setCloseAction(this);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editPane.checkSave();

				int idx = panes.indexOf(editPane);

				if (panes.size() > 0) {
					editPane.clear();

					if (panes.size() > 1) {
						panes.remove(idx);
						tab.remove(idx);
						editPane.tabWithClose.removeCloseAction(this);
					}
				}
			} catch (IOException e1) {
				msg.setText(e1.toString());
				e1.printStackTrace();
			}
		}
	}


	private abstract class ActionOnActiveScreen extends ReAbstractAction implements AbstractActiveScreenAction {

		public ActionOnActiveScreen(String name, int iconId) {
			super(name, iconId);

			menuActions.add(this);
			checkActionEnabled();
		}

		public ActionOnActiveScreen(String name) {
			super(name);

			menuActions.add(this);
		}
	}


    private class ThemeAction extends ReAbstractAction {
        private String xml;

        public ThemeAction(String name, String xml) {
        	super(name);

            this.xml = xml;
        }

        public void actionPerformed(ActionEvent e) {
            InputStream in = ScriptRunFrame.class.getResourceAsStream(RESOURCE_PATH + xml);

//            System.out.println(RESOURCE_PATH + " ! " + RESOURCE_PATH + xml + " " + (in == null));
//            System.out.println(ScriptRunFrame.class
//            		.getResource(RESOURCE_PATH + xml)
//            		.getFile());

            try {
                currentTheme = Theme.load(in);

                for (ScriptEditPane p : panes) {
                	currentTheme.apply(p.getTextArea());
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }


	private class ChangeSyntaxStyleAction extends ActionOnActiveScreen  {

        private final LanguageDetails langDef;
        private final JRadioButtonMenuItem menuItem;

        public ChangeSyntaxStyleAction(LanguageDetails style) {
        	super(style.langName);
            //putValue(NAME, name);
            this.langDef = style;
            this.menuItem = new JRadioButtonMenuItem(this);
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().setLanguageDetails(langDef);
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			menuItem.setSelected(
					isScriptTab()
				&&
					langDef.equals(
							getActivePane()
								.getLanguageDetails()));
		}

    }



    private class AboutAction extends AbstractAction {

        public AboutAction() {
            putValue(NAME, "About RSyntaxTextArea...");
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(ScriptRunFrame.this,
                    "<html>" +
                    "<p>The <b>RecordEditor</b> uses <b>RSyntaxTextArea</b> to edit Script Files." +
                    "<p><b>RSyntaxTextArea</b> - A Swing syntax highlighting text component" +
                    "<br>Version 2.0.7" +
                    "<br>Licensed under a modified BSD license",
                    "About RSyntaxTextArea (https://sourceforge.net/projects/rsyntaxtextarea/)",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }


	private class BookmarksAction extends ActionOnActiveScreen {

        public BookmarksAction() {
            super("Bookmarks");
        }

        public void actionPerformed(ActionEvent e) {
			//super.checkActionEnabled();
        	if (isScriptTab()) {
        		getActivePane().getScrollPane().setIconRowHeaderEnabled(
                            !getActivePane().getScrollPane().isIconRowHeaderEnabled());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getScrollPane().setIconRowHeaderEnabled(
                    getActivePane().getScrollPane().isIconRowHeaderEnabled());
			}
		}

    }


	private class CodeFoldingAction extends ActionOnActiveScreen {

        public CodeFoldingAction() {
            super("Code Folding");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getTextArea().setCodeFoldingEnabled(
        				!getActivePane().getTextArea().isCodeFoldingEnabled());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setCodeFoldingEnabled(
						getActivePane().getTextArea().isCodeFoldingEnabled());
			}
		}
    }


    private class MarkOccurrencesAction extends ActionOnActiveScreen {

        public MarkOccurrencesAction() {
            super("Mark Occurrences");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getTextArea().setMarkOccurrences(
        				!getActivePane().getTextArea().getMarkOccurrences());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setMarkOccurrences(
						getActivePane().getTextArea().getMarkOccurrences());
			}
		}

    }


    private class TabLinesAction extends ActionOnActiveScreen {

        public TabLinesAction() {
            super("Tab Lines");
        }

        public void actionPerformed(ActionEvent e) {

        	if (isScriptTab()) {
        		getActivePane().getTextArea().setPaintTabLines(
        				!getActivePane().getTextArea().getPaintTabLines());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setPaintTabLines(
						getActivePane().getTextArea().getPaintTabLines());
			}
		}

    }




    private class ToggleAntiAliasingAction extends ActionOnActiveScreen {

        public ToggleAntiAliasingAction() {
            super("Anti-Aliasing");
        }

        public void actionPerformed(ActionEvent e) {
			//super.checkActionEnabled();

        	if (isScriptTab()) {
        		getActivePane().getTextArea().setAntiAliasingEnabled(
        				!getActivePane().getTextArea().getAntiAliasingEnabled());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();
			
			if (isScriptTab()) {
				getActivePane().getTextArea().setAntiAliasingEnabled(
						getActivePane().getTextArea().getAntiAliasingEnabled());
			}
		}

    }


    private class ViewLineHighlightAction extends ActionOnActiveScreen {

        public ViewLineHighlightAction() {
            super("Current Line Highlight");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getTextArea().setHighlightCurrentLine(
        				!getActivePane().getTextArea().getHighlightCurrentLine());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setHighlightCurrentLine(
						getActivePane().getTextArea().getHighlightCurrentLine());
			}
		}

    }


    private class ViewLineNumbersAction extends ActionOnActiveScreen {

        public ViewLineNumbersAction() {
            super("Line Numbers");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getScrollPane().setLineNumbersEnabled(
        				!getActivePane().getScrollPane().getLineNumbersEnabled());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getScrollPane().setLineNumbersEnabled(
						getActivePane().getScrollPane().getLineNumbersEnabled());
			}
		}

    }


    private class WordWrapAction extends ActionOnActiveScreen {

        public WordWrapAction() {
            super("Word Wrap");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getTextArea().setLineWrap(!getActivePane().getTextArea().getLineWrap());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setLineWrap(getActivePane().getTextArea().getLineWrap());
			}
		}
    }


    private class AnimateBracketMatchingAction extends ActionOnActiveScreen {

        public AnimateBracketMatchingAction() {
            super("Animate Bracket Matching");
        }

        public void actionPerformed(ActionEvent e) {
        	if (isScriptTab()) {
        		getActivePane().getTextArea().setAnimateBracketMatching(
                        !getActivePane().getTextArea().getAnimateBracketMatching());
        	}
        }

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
		 */
		@Override
		public void checkActionEnabled() {
			//super.checkActionEnabled();

			if (isScriptTab()) {
				getActivePane().getTextArea().setAnimateBracketMatching(
                    getActivePane().getTextArea().getAnimateBracketMatching());
			}
		}
    }


	public static final RecentFiles getRecent() {
		return recent;
	}
	
	
	private class updateOutput implements Runnable {
		
		boolean cont = true;
		StringWriter w = null;
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			w = new StringWriter();
			int size = 0;
			while (cont) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				size = writeOutput(size);
			}
			writeOutput(size);
			
			w = null;
		}
		
		
		private int writeOutput(int size) {
			if (w.getBuffer().length() != size) {
				String s = w.toString();
				outputTxt.setText(s);
				size = s.length();
				
				if (isScriptTab()) {
					tab.setSelectedIndex(tab.getTabCount() - 1);
				}
			}
			return size;
		}
		
	}

}

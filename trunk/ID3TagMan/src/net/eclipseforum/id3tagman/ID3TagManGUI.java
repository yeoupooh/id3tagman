package net.eclipseforum.id3tagman;

import java.io.File;
import java.io.FileFilter;

import net.eclipseforum.id3tagman.handler.ITagHandler;
import net.eclipseforum.id3tagman.handler.Javamp3TagHandler;
import net.eclipseforum.id3tagman.handler.TagHandlerException;
import net.eclipseforum.id3tagman.handler.TagPropertyException;
import net.eclipseforum.id3tagman.util.StringConverter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ID3TagManGUI implements SelectionListener {

	private Shell sShell = null;
	private ToolBar toolBar = null;
	private Text textFolder = null;
	private Text textSrcEncoding = null;
	private Text textDestEncoding = null;
	private Table table = null;
	private ToolItem itemBrowse = null;
	private ToolItem itemPreview = null;
	private ToolItem itemConvert = null;
	private ToolItem itemSelectAll = null;
	private ToolItem itemUnselectAll = null;
	private ToolItem itemAbout = null;
	private Label labelStatus = null;

	private TagProperty[] tagProperties = new TagProperty[] {
			new TagProperty(TagProperty.IDs.FileName, "File name", 200),
			new TagProperty(TagProperty.IDs.Title, "Title", 80),
			new TagProperty(TagProperty.IDs.TrackNumber, "Track number", 30),
			new TagProperty(TagProperty.IDs.Artist, "Artist", 80),
			new TagProperty(TagProperty.IDs.Album, "Album", 80),
			new TagProperty(TagProperty.IDs.AlbumTitle, "Album title", 80),
			new TagProperty(TagProperty.IDs.Genre, "Genre", 80),
			new TagProperty(TagProperty.IDs.Authors, "Authors", 80),
			new TagProperty(TagProperty.IDs.Comment, "Comment", 80) };

	// ITagHandler handler = new Jid3libTagHandler();
	// ITagHandler handler = new Myid3TagHandler();
	ITagHandler handler = new Javamp3TagHandler();

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Before this is run, be sure to set up the launch configuration
		 * (Arguments->VM Arguments) for the correct SWT library path in order
		 * to run with the SWT dlls. The dlls are located in the SWT plugin jar.
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 * installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		ID3TagManGUI thisClass = new ID3TagManGUI();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private ToolItem addToolItem(int style, String text, String imageFilename) {
		ToolItem item = new ToolItem(toolBar, style);
		item.setText(text);
		item.addSelectionListener(this);
		try {
			Image image = new Image(sShell.getDisplay(), imageFilename);
			if (image != null) {
				item.setImage(image);
			}
		} catch (SWTException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setImage(new Image(sShell.getDisplay(),
				"images/coffee_cup_128.png"));
		sShell.setText(Constants.APP_NAME + " " + Constants.APP_VERSION);
		sShell.setSize(new Point(1000, 700));
		sShell.setLayout(new GridLayout());

		Menu menu = new Menu(sShell, SWT.BAR);
		MenuItem miFileHeader = new MenuItem(menu, SWT.CASCADE);
		miFileHeader.setText("&File");

		Menu miFile = new Menu(sShell, SWT.DROP_DOWN);
		miFileHeader.setMenu(miFile);

		sShell.setMenuBar(menu);

		toolBar = new ToolBar(sShell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		GridData gridDataToolBar = new GridData();
		gridDataToolBar.horizontalAlignment = GridData.FILL;
		gridDataToolBar.grabExcessHorizontalSpace = true;
		toolBar.setLayoutData(gridDataToolBar);

		itemBrowse = addToolItem(SWT.PUSH, "Browse...",
				"images/folder_full_64.png");
		itemPreview = addToolItem(SWT.CHECK, "Preview",
				"images/light_bulb_64.png");
		itemSelectAll = addToolItem(SWT.PUSH, "Select All",
				"images/accept_page_64.png");
		itemUnselectAll = addToolItem(SWT.PUSH, "Unselect All",
				"images/full_page_64.png");
		itemConvert = addToolItem(SWT.PUSH, "Convert...",
				"images/target_64.png");
		itemAbout = addToolItem(SWT.PUSH, "Coffee?", "images/coffee_cup_64.png");

		textFolder = new Text(sShell, SWT.NONE);
		GridData gridDataTextFolder = new GridData();
		gridDataTextFolder.horizontalAlignment = GridData.FILL;
		gridDataTextFolder.grabExcessHorizontalSpace = true;
		textFolder.setLayoutData(gridDataTextFolder);
		textFolder.pack();

		textSrcEncoding = new Text(sShell, SWT.NONE);
		textSrcEncoding.setLayoutData(gridDataTextFolder);
		textSrcEncoding.setText("iso-8859-1");
		textSrcEncoding.pack();

		textDestEncoding = new Text(sShell, SWT.NONE);
		textDestEncoding.setLayoutData(gridDataTextFolder);
		textDestEncoding.setText("euc-kr");
		textDestEncoding.pack();

		// AddTree();

		table = new Table(sShell, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION);

		for (int i = 0; i < tagProperties.length; i++) {
			TagProperty prop = tagProperties[i];
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText(prop.getText());
			tc.setWidth(prop.getWidth());
		}

		// table.setLinesVisible(true);
		table.setHeaderVisible(true);

		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.pack();

		labelStatus = new Label(sShell, SWT.None);
		GridData gdLabelStatus = new GridData();
		gdLabelStatus.grabExcessHorizontalSpace = true;
		gdLabelStatus.horizontalAlignment = GridData.FILL;
		labelStatus.setLayoutData(gdLabelStatus);
		labelStatus.setText("Ready.");
	}

	private void updateTable() {
		setStatusMessage("Loading files...");

		boolean doConvert = itemPreview.getSelection();
		table.removeAll();

		FileFilter fileFilter = new Mp3FileFilter();

		File[] files = (new File(textFolder.getText())).listFiles(fileFilter);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			TableItem item = new TableItem(table, SWT.NONE);
			item.setData(file);
			System.out.println(file.getName());

			StringConverter conv = new StringConverter(textSrcEncoding
					.getText(), textDestEncoding.getText());

			try {
				handler.load(file);
				for (int j = 0; j < tagProperties.length; j++) {
					TagProperty prop = tagProperties[j];
					String value = null;
					String convValue = null;
					try {
						value = handler.getProperty(prop.getId());
					} catch (TagPropertyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (prop.getId() == TagProperty.IDs.FileName
							|| doConvert == false) {
						convValue = value;
					} else {
						if (value != null) {
							convValue = conv.convert(value);
						}
					}
					if (convValue != null) {
						item.setText(prop.getId().index(), convValue);
						if (convValue != value) {
							item.setChecked(true);
						}
					}
				}
			} catch (TagHandlerException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		setStatusMessage("Files loaded.");
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void widgetSelected(SelectionEvent evt) {
		if (evt.getSource() == itemBrowse) {
			browse();
		} else if (evt.getSource() == itemPreview) {
			preview();
		} else if (evt.getSource() == itemConvert) {
			convert();
		} else if (evt.getSource() == itemSelectAll) {
			checkAll(true);
		} else if (evt.getSource() == itemUnselectAll) {
			checkAll(false);
		} else if (evt.getSource() == itemAbout) {
			about();
		}
	}

	private void about() {
		MessageBox mbox = new MessageBox(sShell);
		mbox.setMessage("made by yeoupooh at gmail.com");
		mbox.open();
	}

	private void browse() {
		DirectoryDialog dlg = new DirectoryDialog(sShell);
		String dir = dlg.open();
		if (dir != null) {
			textFolder.setText(dir);
			updateTable();
		}
	}

	private void preview() {
		File dir = new File(textFolder.getText());
		if (dir.exists() == true) {
			updateTable();
		}
	}

	private void convert() {
		for (int i = 0; i < table.getItemCount(); i++) {
			TableItem item = table.getItem(i);
			if (item.getChecked() == true) {
				File file = (File) item.getData();
				setStatusMessage("Converting..." + file.getName());
				try {
					handler.load(file);
					for (int j = 0; j < tagProperties.length; j++) {
						TagProperty prop = tagProperties[j];
						System.out.println(prop.getId() + "="
								+ item.getText(prop.getId().index()));
						if (item.getText(prop.getId().index()) != null) {
							handler.setProperty(prop.getId(), item.getText(prop
									.getId().index()));
						}
					}
					handler.save();
				} catch (TagHandlerException e) {
					e.printStackTrace();
				} catch (TagPropertyException e) {
					e.printStackTrace();
				}
				setStatusMessage(file.getName() + " is converted.");
			}
		}
	}

	private void checkAll(boolean checked) {
		for (int i = 0; i < table.getItemCount(); i++) {
			table.getItem(i).setChecked(checked);
		}
	}

	private void setStatusMessage(String message) {
		labelStatus.setText(message);
	}
}
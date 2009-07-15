package net.eclipseforum.id3tagman;

import java.io.File;
import java.io.FileFilter;

import net.eclipseforum.id3tagman.handler.ITagHandler;
import net.eclipseforum.id3tagman.handler.Javamp3TagHandler;
import net.eclipseforum.id3tagman.handler.TagHandlerException;
import net.eclipseforum.id3tagman.handler.TagPropertyException;
import net.eclipseforum.id3tagman.util.StringConverter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
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

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText(Constants.APP_NAME + " " + Constants.APP_VERSION);
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(new GridLayout());

		toolBar = new ToolBar(sShell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		GridData gridDataToolBar = new GridData();
		gridDataToolBar.horizontalAlignment = GridData.FILL;
		gridDataToolBar.grabExcessHorizontalSpace = true;
		toolBar.setLayoutData(gridDataToolBar);

		itemBrowse = new ToolItem(toolBar, SWT.PUSH);
		itemBrowse.setText("Browse...");
		// Image icon = new Image(sShell.getDisplay(), "icons/new.gif");
		itemBrowse.addSelectionListener(this);
		// itemPush.setImage(icon);

		itemPreview = new ToolItem(toolBar, SWT.CHECK);
		itemPreview.setText("Preview");
		itemPreview.addSelectionListener(this);

		itemConvert = new ToolItem(toolBar, SWT.PUSH);
		itemConvert.setText("Convert");
		itemConvert.addSelectionListener(this);

		itemSelectAll = new ToolItem(toolBar, SWT.PUSH);
		itemSelectAll.setText("Select All");
		itemSelectAll.addSelectionListener(this);

		itemUnselectAll = new ToolItem(toolBar, SWT.PUSH);
		itemUnselectAll.setText("Unselect All");
		itemUnselectAll.addSelectionListener(this);

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

		// table.setLinesVisible(true);
		table.setHeaderVisible(true);

		for (int i = 0; i < tagProperties.length; i++) {
			TagProperty prop = tagProperties[i];
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText(prop.getText());
			tc.setWidth(prop.getWidth());
		}

		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.pack();
	}

	private void updateTable() {
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
		}
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
			}
		}
	}

	private void checkAll(boolean checked) {
		for (int i = 0; i < table.getItemCount(); i++) {
			table.getItem(i).setChecked(checked);
		}
	}
}
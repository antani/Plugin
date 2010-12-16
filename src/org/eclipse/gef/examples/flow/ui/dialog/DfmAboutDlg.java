package org.eclipse.gef.examples.flow.ui.dialog;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

public class DfmAboutDlg extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button Close;
	private Label label1;

	
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);			
			DfmAboutDlg inst = new DfmAboutDlg(shell, SWT.NULL);
			inst.open("test text");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DfmAboutDlg(Shell parent, int style) {
		super(parent, style);
	}

	public void open(String text) {
		try {
			Display display = Display.getDefault();
			Shell parent = getParent();
			// | SWT.APPLICATION_MODAL		
			dialogShell = new Shell(parent, SWT.SHELL_TRIM);
			dialogShell.setLayout(new FormLayout());
			dialogShell.setText("DFM About - Output(s)");
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(608, 93);
			{
				label1 = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 12);
				label1LData.width = 600;
				label1LData.height = 13;
				label1.setLayoutData(label1LData);
				label1.setText(text);
			}
			{
				Close = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				dialogShell.setDefaultButton(Close);
				FormData CloseLData = new FormData();
				CloseLData.left =  new FormAttachment(0, 1000, 148);
				CloseLData.top =  new FormAttachment(0, 1000, 38);
				CloseLData.width = 40;
				CloseLData.height = 23;
				Close.setLayoutData(CloseLData);
				Close.setText("Close");
				Close.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("Close.widgetSelected, event="+evt);
						dialogShell.dispose();
					}
				});
			}
			Monitor primary = display.getPrimaryMonitor();
		    Rectangle bounds = primary.getBounds();
		    Rectangle rect = dialogShell.getBounds();
		    int x = bounds.x + (bounds.width - rect.width) / 2;
		    int y = bounds.y + (bounds.height - rect.height) / 2;
		    dialogShell.setLocation(x, y);
			//dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

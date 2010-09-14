package org.eclipse.gef.examples.flow.ui.dialog;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DfmAboutDlg extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button Close;
	private Label label1;

	
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DfmAboutDlg inst = new DfmAboutDlg(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DfmAboutDlg(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.setText("DFM About - Output(s)");
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(208, 93);
			{
				label1 = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 12);
				label1LData.width = 181;
				label1LData.height = 13;
				label1.setLayoutData(label1LData);
				label1.setText("DFM Version returns a string value.");
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
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

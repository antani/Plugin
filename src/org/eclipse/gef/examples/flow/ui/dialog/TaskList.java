package org.eclipse.gef.examples.flow.ui.dialog;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;


public class TaskList extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label TaskLbl;
	private Combo TaskCmb;
	private Button closeBtn;
	private String selectedTask;
	/**
	 * @return the selectedTask
	 */
	public String getSelectedTask() {
		return selectedTask;
	}

	/**
	 * @param selectedTask the selectedTask to set
	 */
	public void setSelectedTask(String selectedTask) {
		this.selectedTask = selectedTask;
	}

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			TaskList inst = new TaskList(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TaskList(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(326, 128);
			{
				closeBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData closeBtnLData = new FormData();
				closeBtnLData.left =  new FormAttachment(0, 1000, 129);
				closeBtnLData.top =  new FormAttachment(0, 1000, 51);
				closeBtnLData.width = 40;
				closeBtnLData.height = 23;
				closeBtn.setLayoutData(closeBtnLData);
				closeBtn.setText("Close");
				closeBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("Close.widgetSelected, event="+evt);
						dialogShell.dispose();
					}
				});
			}
			{
				TaskCmb = new Combo(dialogShell, SWT.NONE);
				FormData TaskCmbLData = new FormData();
				TaskCmbLData.left =  new FormAttachment(0, 1000, 138);
				TaskCmbLData.top =  new FormAttachment(0, 1000, 12);
				TaskCmbLData.width = 66;
				TaskCmbLData.height = 21;
				TaskCmb.setLayoutData(TaskCmbLData);
				TaskCmb.setText("NetAppTasks");
				TaskCmb.setItems(new java.lang.String[] {"HelloDfm","ListAllDataset","Task2"});
				selectedTask ="";
				TaskCmb.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("TaskCmb.widgetSelected, event="+evt);
						int selected = TaskCmb.getSelectionIndex();
						if(selected != -1){
							setSelectedTask(TaskCmb.getItem(selected));
							
						}
					}
				});
			}
			{
				TaskLbl = new Label(dialogShell, SWT.NONE);
				FormData TaskLblLData = new FormData();
				TaskLblLData.left =  new FormAttachment(0, 1000, 12);
				TaskLblLData.top =  new FormAttachment(0, 1000, 12);
				TaskLblLData.width = 97;
				TaskLblLData.height = 13;
				TaskLbl.setLayoutData(TaskLblLData);
				TaskLbl.setText("Select NetApp Task");
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
	
	public Label getTaskLbl() {
		return TaskLbl;
	}
	
	public Combo getTaskCmb() {
		return TaskCmb;
	}
	
	public Button getCloseBtn() {
		return closeBtn;
	}

}

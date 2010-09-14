/**
 * 
 */
package org.eclipse.gef.examples.flow.ui;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import org.eclipse.gef.examples.flow.FlowPlugin;
import org.eclipse.gef.examples.flow.model.Activity;
import org.eclipse.gef.examples.flow.model.ActivityDiagram;
import org.eclipse.gef.examples.flow.model.CodegenVariables;
import org.eclipse.gef.examples.flow.model.ConnectTask;
import org.eclipse.gef.examples.flow.model.ParallelActivity;
import org.eclipse.gef.examples.flow.model.SequentialActivity;
import org.eclipse.gef.examples.flow.model.Transition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author antani
 *
 */
public class FlowWizardPage2 extends WizardNewFileCreationPage{

	private IWorkbench	workbench;
	private static int exampleCount = 1;

	public FlowWizardPage2(IWorkbench aWorkbench, IStructuredSelection selection) {
		super("sampleFlowPage1", selection);
		this.setTitle("Provide NetApp Connection Settings");
		this.setDescription("Provide NetApp Connection Settings");
		this.setImageDescriptor(ImageDescriptor.createFromFile(FlowPlugin.class,"images/flowbanner.gif"));
		this.workbench = aWorkbench;
	}
	public void createControl(Composite parent) {
		super.createControl(parent);
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NONE);
	    int nColumns = 4;
	    GridLayout layout = new GridLayout();
	    layout.numColumns = nColumns;
	    composite.setLayout(layout);
	    createInitParamComposite(composite, nColumns);
		//setPageComplete(validatePage());
	}
	
	private Text host = null;
	private Text port = null;
	private Text username = null;
	private Text password = null; 
	protected CodegenVariables codegenVariables = new CodegenVariables();
	public CodegenVariables getCodegenVariables() {
		return codegenVariables;
	}

	public void setCodegenVariables(CodegenVariables codegenVariables) {
		this.codegenVariables = codegenVariables;
	}

	private void createInitParamComposite(Composite parent, int nColumns)
	  {
	    Label hostlbl = new Label(parent, SWT.NONE);
	    hostlbl.setText("Connection Host");
	    host = new Text(parent, SWT.SINGLE | SWT.BORDER);
	    host.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    host.addModifyListener(new ModifyListener()
	      {

	        public void modifyText(ModifyEvent e)
	        {
	        	getCodegenVariables().setHost(host.getText());
	        }
	      });
	    host.setText("0.0.0.0");

	    //new Label(parent, SWT.NONE);
	    //new Label(parent, SWT.NONE);

	    Label portlbl = new Label(parent, SWT.NONE);
	    portlbl.setText("Port");
	    port = new Text(parent, SWT.SINGLE | SWT.BORDER);
	    port.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    port.addModifyListener(new ModifyListener()
	      {

	        public void modifyText(ModifyEvent e)
	        {
	        	getCodegenVariables().setPort(port.getText());
	        }
	      });
	    port.setText("8080");
	    
	    Label usernamelbl = new Label(parent, SWT.NONE);
	    usernamelbl.setText("Username");
	    username = new Text(parent, SWT.SINGLE | SWT.BORDER);
	    username.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    username.addModifyListener(new ModifyListener()
	      {

	        public void modifyText(ModifyEvent e)
	        {
	        	getCodegenVariables().setUsername(username.getText());
	        }
	      });
	    username.setText("root");
	    
	    Label pwdlbl = new Label(parent, SWT.NONE);
	    pwdlbl.setText("Password");
	    password = new Text(parent, SWT.SINGLE | SWT.BORDER);
	    password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    password.addModifyListener(new ModifyListener()
	      {

	        public void modifyText(ModifyEvent e)
	        {
	        	getCodegenVariables().setPassword(password.getText());
	        }
	      });
	    password.setText("");

	    //new Label(parent, SWT.NONE);
	    //new Label(parent, SWT.NONE);
	  }


	
}

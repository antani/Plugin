/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.flow.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import org.eclipse.gef.examples.flow.FlowPlugin;
import org.eclipse.gef.examples.flow.codegen.Config;
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
 * FlowWizardPage1
 * @author Daniel Lee
 */
public class FlowWizardPage1 extends WizardNewFileCreationPage {

private IWorkbench	workbench;
private static int exampleCount = 1;

public FlowWizardPage1(IWorkbench aWorkbench, IStructuredSelection selection) {
	super("sampleFlowPage1", selection);
	this.setTitle("Create NetApp SDK Flow Diagram File");
	this.setDescription("Create a new flow file resource");
	this.setImageDescriptor(ImageDescriptor.createFromFile(FlowPlugin.class,"images/flowbanner.gif"));
	this.workbench = aWorkbench;
}

/**
 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
 */
public void createControl(Composite parent) {
	super.createControl(parent);
	//initializeDialogUnits(parent);
	this.setFileName("netappflow" + exampleCount + ".flow");
	Composite composite = (Composite)getControl();
	createAuthorVersionControls(composite, 2);
	setPageComplete(validatePage());
}
private ActivityDiagram createNetAppConnect()
{
	ActivityDiagram diagram = new ActivityDiagram();
	Activity connect = new ConnectTask("Init Connect Parameters");
	//connect.
	diagram.addChild(connect);
	return diagram;
}
private ActivityDiagram createWakeupModel() {
	ActivityDiagram diagram = new ActivityDiagram();
	SequentialActivity wakeup = new SequentialActivity();
	Activity backToSleep = new Activity("Go back to sleep - 5 more minutes ?");
	Activity turnOff = new Activity("Turn off alarm");
	wakeup.setName("Wake up");
	wakeup.addChild(new Activity("Hit snooze button"));
	wakeup.addChild(backToSleep);
	wakeup.addChild(turnOff);
	wakeup.addChild(new Activity("Get out of bed"));
	diagram.addChild(wakeup);

	SequentialActivity bathroom = new SequentialActivity();
	bathroom.addChild(new Activity("Brush teeth"));
	bathroom.addChild(new Activity("Take shower"));
	bathroom.addChild(new Activity("Comb hair"));
	bathroom.setName("Get Ready");
	diagram.addChild(bathroom);
	
	ParallelActivity relaxation = new ParallelActivity();
	relaxation.addChild(new Activity("Watch cartoons"));
	relaxation.addChild(new Activity("Power Yoga"));
	relaxation.setName("Morning relaxation ritual");
	diagram.addChild(relaxation);
	
	Activity sleep, alarm, alarm2, clothes, spare, no, yes, drive;
	diagram.addChild(sleep = new Activity("Sleep....."));
	diagram.addChild(alarm = new Activity("Alarm!!!"));
	diagram.addChild(alarm2 = new Activity("Alarm!!!"));
	diagram.addChild(clothes = new Activity("Put on clothes"));
	diagram.addChild(spare = new Activity("Is there time to spare?"));
	diagram.addChild(yes = new Activity("YES"));
	diagram.addChild(no = new Activity("NO"));
	diagram.addChild(drive = new Activity("Drive to work"));
	
	new Transition(sleep, alarm);
	new Transition(alarm, wakeup);
	new Transition(backToSleep,alarm2);
	new Transition(alarm2,turnOff);
	new Transition(wakeup, bathroom);
	new Transition(bathroom, clothes);
	new Transition(clothes, spare);
	new Transition(spare, yes);
	new Transition(spare, no);
	new Transition(yes, relaxation);
	new Transition(no, drive);
	new Transition(relaxation, drive);
	return diagram;
}

protected InputStream getInitialContents() {
	//ActivityDiagram diag = createWakeupModel();
	ActivityDiagram diag = createNetAppConnect();
	ByteArrayInputStream bais = null;
	try {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(diag);
		oos.flush();
		oos.close();
		baos.close();
		bais = new ByteArrayInputStream(baos.toByteArray());
		bais.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return bais;
}

public boolean finish() {
	IFile newFile = createNewFile();
//	
	if (newFile == null) 
		return false;  // ie.- creation was unsuccessful

	// Since the file resource was created fine, open it for editing
	// if requested by the user
	try {
		IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = dwindow.getActivePage();
		if (page != null)
			IDE.openEditor(page, newFile, true); 
	} 
	catch (org.eclipse.ui.PartInitException e) {
		e.printStackTrace();
		return false;
	}
	exampleCount++;
	return true;
}
/**
 * 
 */
protected CodegenVariables codegenVariables = new CodegenVariables();
public CodegenVariables getCodegenVariables() {
	return codegenVariables;
}

public void setCodegenVariables(CodegenVariables codegenVariables) {
	this.codegenVariables = codegenVariables;
}
private Text mTextAuthor = null;
private Text mTextVersion = null;
private Text host = null;
private Text port = null;
private Text username = null;
private Text password = null; 

/**
 * @param composite
 * @param nColumns
 */
private void createAuthorVersionControls(Composite parent, int nColumns)
{
  Label author = new Label(parent, SWT.NONE);
  author.setText("Author");
  mTextAuthor = new Text(parent, SWT.SINGLE | SWT.BORDER);
  mTextAuthor.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
  mTextAuthor.addModifyListener(new ModifyListener()
    {

      public void modifyText(ModifyEvent e)
      {
    	  getCodegenVariables().setAuthor(mTextAuthor.getText());
      }
    });
  mTextAuthor.setText(System.getProperty("user.name"));

  Label version = new Label(parent, SWT.NONE);
  version.setText("Version");
  mTextVersion = new Text(parent, SWT.SINGLE | SWT.BORDER);
  mTextVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
  mTextVersion.addModifyListener(new ModifyListener()
    {

      public void modifyText(ModifyEvent e)
      {
    	  getCodegenVariables().setVersion(mTextVersion.getText());
      }
    });
  mTextVersion.setText("1.0");
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
  password = new Text(parent, SWT.PASSWORD | SWT.BORDER);
  password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
  password.addModifyListener(new ModifyListener()
    {

      public void modifyText(ModifyEvent e)
      {
      	getCodegenVariables().setPassword(password.getText());
      }
    });
  password.setText("");


//  new Label(parent, SWT.NONE);
//  new Label(parent, SWT.NONE);
}
}


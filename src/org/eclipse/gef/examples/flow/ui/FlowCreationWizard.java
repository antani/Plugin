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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.gef.examples.flow.FlowPlugin;
import org.eclipse.gef.examples.flow.codegen.Config;
import org.eclipse.gef.examples.flow.codegen.JETMain;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;


/**
 * FlowCreationWizard
 * @author Daniel Lee
 */
public class FlowCreationWizard extends Wizard implements INewWizard {

//private FlowWizardPage1 flowWizardPage;
//private FlowWizardPage2 flowWizardPage2;
private IStructuredSelection selection;
private IWorkbench workbench;
private static FlowWizardPage1 flowWizardPage1;
private static FlowWizardPage2 flowWizardPage2;
/**
 * @see org.eclipse.jface.wizard.IWizard#addPages()
 */
public void addPages(){
	flowWizardPage1 = new FlowWizardPage1(workbench,selection);
	addPage(flowWizardPage1);
//	flowWizardPage2 = new FlowWizardPage2(workbench,selection);	
//	addPage(flowWizardPage2);
}

/**
 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
 */
public void init(IWorkbench aWorkbench,IStructuredSelection currentSelection) {
	workbench = aWorkbench;
	selection = currentSelection;
}


/**
 * Obtains the user input from the wizard pages, creates a <code>Config</code>
 * object that contains all this information, and delegates the source code
 * generation to a <code>JETGateway</code> object. Finally, the generated
 * Java source code file is opened in an editor.
 */
protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException
{
  //Config config = new Config();
  Config config = Config.getInstance();
  config.setModel(flowWizardPage1.getCodegenVariables());
  config.setPackageName("com.netapp.nmsdk.flow");
  config.setTargetFile("NetAppFlowMain" + ".java");
  config.setTargetFolder(flowWizardPage1.getContainerFullPath().toPortableString());
 
  config.setClasspathVariable("NETAPP_PATH");
  
  config.setPluginId(FlowPlugin.getPluginId());
  config.setTemplateRelativeUri("NetAppFlowMain.javajet");

  JETMain gateway = new JETMain(config);
  String content = gateway.generate(monitor);
  IFile file = gateway.save(monitor, content.getBytes());

  selectAndReveal(file);
  openResource(file);
}

/*
 * @see Wizard#performFinish
 */
//@Override
public boolean performFinish()
{
  IWorkspaceRunnable op = new IWorkspaceRunnable()
    {
      public void run(IProgressMonitor monitor) throws CoreException, OperationCanceledException
      {
        try
        {
          finishPage(monitor);
        }
        catch (InterruptedException e)
        {
          throw new OperationCanceledException(e.getMessage());
        }
      }
    };

  try
  {
    getContainer().run(false, true, new WorkbenchRunnableAdapter(op));
  }
  catch (InvocationTargetException e)
  {
    handleFinishException(getShell(), e);
    return false;
  }
  catch (InterruptedException e)
  {
    return false;
  }
  return true && flowWizardPage1.finish();
  
  //return ;
}

/**
 * An <code>IRunnableWithProgress</code> that adapts
 * <code>IWorkspaceRunnable</code> so that it can be executed inside
 * <code>IRunnableContext</code>.<code>OperationCanceledException</code>
 * thrown by the apapted runnabled are caught and rethrown as a
 * <code>InterruptedException</code>.
 */
private static class WorkbenchRunnableAdapter implements IRunnableWithProgress
{

  private IWorkspaceRunnable fWorkspaceRunnable;

  public WorkbenchRunnableAdapter(IWorkspaceRunnable runnable)
  {
    fWorkspaceRunnable = runnable;
  }

  /*
   * @see IRunnableWithProgress#run(IProgressMonitor)
   */
  public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
  {
    try
    {
      JavaCore.run(fWorkspaceRunnable, monitor);
    }
    catch (OperationCanceledException e)
    {
      throw new InterruptedException(e.getMessage());
    }
    catch (CoreException e)
    {
      throw new InvocationTargetException(e);
    }
  }
}
protected void handleFinishException(Shell shell, InvocationTargetException e)
{
  Diagnostic diagnostic = BasicDiagnostic.toDiagnostic(e);
  //log(diagnostic);

  String title = "Finish Exception"; //$NON-NLS-1$
  String message = "Wizard.op_error.message"; //$NON-NLS-1$
  DiagnosticDialog.open(shell, title, message, diagnostic);
}

protected void openResource(final IResource resource)
{
  if (resource.getType() == IResource.FILE)
  {
    //final IWorkbenchPage activePage = TypesafeEnumPlugin.getActivePage();
    
    IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    final IWorkbenchPage activePage = window.getActivePage();
    
    if (activePage != null)
    {
      final Display display = getShell().getDisplay();
      if (display != null)
      {
        display.asyncExec(new Runnable()
          {
            public void run()
            {
              try
              {
                IDE.openEditor(activePage, (IFile)resource, true);
              }
              catch (PartInitException e)
              {
                //TypesafeEnumPlugin.log(e);
              }
            }
          });
      }
    }
  }
}

protected void selectAndReveal(IResource newResource)
{
  BasicNewResourceWizard.selectAndReveal(newResource, workbench.getActiveWorkbenchWindow());
}

public IStructuredSelection getSelection()
{
  return selection;
}



}

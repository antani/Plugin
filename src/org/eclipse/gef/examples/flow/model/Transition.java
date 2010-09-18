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
package org.eclipse.gef.examples.flow.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.gef.examples.flow.codegen.Config;
import org.eclipse.gef.examples.flow.codegen.JETMain;
import org.eclipse.gef.examples.flow.ui.dialog.DfmAboutDialog;
import org.eclipse.gef.examples.flow.ui.dialog.DfmAboutDlg;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author hudsonr
 * Created on Jun 30, 2003
 */
public class Transition extends FlowElement {

public Activity source, target;

public Transition(Activity source, Activity target) {
	this.source = source;
	this.target = target;
	System.out.println("Created Transition between "+source.getName() + " and  " + target.getName());	
	source.addOutput(this);
	target.addInput(this);
	Display display = Display.getDefault();
	Shell shell = new Shell(display);
	DfmAboutDlg inst = new DfmAboutDlg(shell, SWT.NULL);
	inst.open();
	Config config = Config.getInstance();
	JETMain gateway = new JETMain(config);
    String content = null;
	try {
		content = gateway.generateAll(null,"");
	} catch (CoreException e) {
		e.printStackTrace();
	}
	try {		
		config.setTargetFile("HelloDfm.java");
		IFile file = gateway.save(null, content.getBytes());
	} catch (CoreException e) {
		e.printStackTrace();
	}
	//Find main file
	String base = Platform.getBundle(config.getPluginId()).getEntry("/").toString();
    String relativeUri = "com/netapp/nmsdk/flow/NetAppFlowMain.java";
//    String f="";
//	try {
//		f = Platform.resolve(Platform.find(Platform.getBundle(config.getPluginId()), new Path(relativeUri))).getFile();
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//    //IResource 
	System.out.println("base+rel" + base+relativeUri);
//	System.out.println("f - " + f);    
	
	
//	try {
//		Scanner scanner = new Scanner(mainFile);
//		while(scanner.hasNextLine()){
//			String line = scanner.nextLine();
//			System.out.println("Line:" +line);
//		}
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
    
}

}

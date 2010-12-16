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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import org.eclipse.gef.examples.flow.codegen.Config;
import org.eclipse.gef.examples.flow.codegen.JETMain;
import org.eclipse.gef.examples.flow.ui.dialog.DfmAboutDialog;
import org.eclipse.gef.examples.flow.ui.dialog.DfmAboutDlg;
import org.eclipse.gef.examples.flow.util.SourceUtil;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Monitor;

public class Transition extends FlowElement {

public Activity source, target;
public static HashMap fileList = new HashMap();
public static HashMap returnList = new HashMap();
public static HashMap returnTextList = new HashMap();


public Transition(Activity source, Activity target) {
	
	fileList.put("HelloDfm", "HelloDfm.java");	
	fileList.put("ListAllDataset", "DatasetList.java");
	/*-------------------------------------------------------*/
	returnList.put("ListAllDataset",Boolean.TRUE);
	returnList.put("PrintList",Boolean.FALSE);
	returnTextList.put("ListAllDataset", "ListAllDataset - activity returns a List type. You should use this list in the target activity.");
	returnTextList.put("HelloDfm", "");	
	returnTextList.put("PrintList", "");
	/*-------------------------------------------------------*/
	
	this.source = source;
	this.target = target;
	System.out.println("Created Transition between "+source.getName()+source.getActivityIndex()+ " and  " + target.getName()+target.getActivityIndex());	
	source.addOutput(this);
	target.addInput(this);
	//check if the destination activity is returning any payload
	if(Boolean.TRUE.equals(returnList.get(target.getName()))) {
//		Display display = Display.getDefault();
//		Shell shell = new Shell(display);
//		DfmAboutDlg inst = new DfmAboutDlg(shell, SWT.NULL);
//		inst.open(returnTextList.get(target.getName())+"");	
		target.setReturnPayload(true); //Indicate that this target can return a payload.
	}

	
	Config config = Config.getInstance();
	JETMain gateway = new JETMain(config);
    String content = null;
    String targetKey = target.getName().trim();
	String filename = (String)fileList.get(targetKey);
	//Its not always necessray to have a separate source file
    //Call generateAll only if the separate source file needs to be generated  
	if(filename != null) {
		try {
			
				System.out.println("Value of : file list : " + fileList.get(targetKey));
				content = gateway.generateAll(null,filename);
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		try {		
			config.setTargetFile(filename);
			IFile file = gateway.save(null, content.getBytes());
		} catch (CoreException e) {
			e.printStackTrace();
		}
    }
	//Find main file
	String base = Platform.getBundle(config.getPluginId()).getEntry("/").toString();
    String relativeUri = "com/netapp/nmsdk/flow/NetAppFlowMain.java";
	System.out.println("base+rel" + base+relativeUri);
	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	System.out.println("root workspace : " + root.getName());
	IResource resourceInRuntimeWorkspace = root.findMember("testplugin/com/netapp/nmsdk/flow/NetAppFlowMain.java");
	File mainFile = new File(resourceInRuntimeWorkspace.getLocationURI());

	StringBuilder sb = new StringBuilder();
	StringBuilder customCode = new StringBuilder();
	String delim = System.getProperty("line.separator");
	String line = "";
	try {
		Scanner scanner = new Scanner(mainFile);
		
		int x = target.getActivityIndex();
		
		while(scanner.hasNextLine()){
			SourceUtil src = new SourceUtil();
			line = scanner.nextLine();
			if(line.contains("Custom Code Start 1")){
				sb.append(src.generate(targetKey, x, source,target)).append(delim).append("/*Custom Code Start 1*/").append(delim);
			}else {
				sb.append(line).append(delim);
			}
			
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(sb.toString());
	//Overwrite the main java file with modified contents.
	String mainfilePath = mainFile.getAbsolutePath();
	BufferedWriter outputStream = null;
	try {
		outputStream = new BufferedWriter(new FileWriter(mainfilePath));
		outputStream.write(sb.toString());
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
}

}
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

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		
		config.setTargetFile("HelloDfm.java");
		IFile file = gateway.save(null, content.getBytes());
	} catch (CoreException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}

}

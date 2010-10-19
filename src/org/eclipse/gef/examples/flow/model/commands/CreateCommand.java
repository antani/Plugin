/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.flow.model.commands;

import org.eclipse.gef.commands.Command;

import org.eclipse.gef.examples.flow.codegen.Config;
import org.eclipse.gef.examples.flow.model.Activity;
import org.eclipse.gef.examples.flow.model.StructuredActivity;
import org.eclipse.gef.examples.flow.model.Task;
import org.eclipse.gef.examples.flow.ui.dialog.TaskList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Daniel Lee
 */
public class CreateCommand extends Command {

private StructuredActivity parent;
private Activity child;
private int index = 0;

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	System.out.println("Created a node");
	String selectedTask = showTaskList();
	if(selectedTask != null){
		child.setName(selectedTask);
		//child.setIndex(index);
	}
	
	if (index > 0)
		parent.addChild(child, index);
	else
		parent.addChild(child);
	
	
}

private String showTaskList() {
	String selectedTask = "";
	try {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		TaskList inst = new TaskList(shell, SWT.NULL);
		inst.open();
		selectedTask= inst.getSelectedTask();
		System.out.println("Selected task : " + selectedTask);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return selectedTask;
}

/**
 * Sets the index to the passed value
 * @param i the index
 */
public void setIndex(int i) {
	index = i;
}
public int getIndex()
{
	return index;
}
/**
 * Sets the parent ActivityDiagram
 * @param sa the parent
 */
public void setParent(StructuredActivity sa) {
	parent = sa;
}

/**
 * Sets the Activity to create
 * @param activity the Activity to create
 */
public void setChild(Activity activity) {
	child = activity;
	child.setName(activity.getName());
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	parent.removeChild(child);
}

}

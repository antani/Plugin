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
package org.eclipse.gef.examples.flow.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.examples.flow.codegen.Config;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Task extends Activity
{

protected static IPropertyDescriptor[] descriptors;

public static final String NAME = "NetApp Task"; //$NON-NLS-1$

static {
	descriptors = new IPropertyDescriptor[] {
		new TextPropertyDescriptor(NAME, "Name")};
}

static final long serialVersionUID = 1;
private List inputs = new ArrayList();
private String name = "NetApp Task";
private List outputs = new ArrayList();
private int sortIndex;



public Task() {}
public Task(String s) {
	setName(s);
}

public void addInput(Transition transition) {
	inputs.add(transition);
	fireStructureChange(INPUTS, transition);
}

public void addOutput(Transition transtition) {
	outputs.add(transtition);
	fireStructureChange(OUTPUTS, transtition);
}

public List getIncomingTransitions() {
	return inputs;
}

public String getName() {
	return name;
}

public List getOutgoingTransitions() {
	return outputs;
}

//public List getConnections() {
//	Vector v = (Vector)outputs.clone();
//	Enumeration ins = inputs.elements();
//	while (ins.hasMoreElements())
//		v.addElement(ins.nextElement());
//	return v;
//}

/**
 * Returns useful property descriptors for the use
 * in property sheets. this supports location and
 * size.
 *
 * @return  Array of property descriptors.
 */
public IPropertyDescriptor[] getPropertyDescriptors() {
	return descriptors;
}


/**
 * Returns an Object which represents the appropriate
 * value for the property name supplied.
 *
 * @param propName  Name of the property for which the
 *                  the values are needed.
 * @return  Object which is the value of the property.
 */
public Object getPropertyValue(Object propName) {
	if (NAME.equals(propName))
		return getName();
	return super.getPropertyValue(propName);
}

public int getSortIndex() {
	return sortIndex;
}

public void removeInput(Transition transition) {
	inputs.remove(transition);
	fireStructureChange(INPUTS,transition);
	cleanCode(transition);
}

public void removeOutput(Transition transition) {
	outputs.remove(transition);
	fireStructureChange(OUTPUTS,transition);
}

public void setName(String s) {
	name = s;
	firePropertyChange(NAME, null, s);
}

/**
 * Sets the value of a given property with the value supplied.
 * 
 * @param id Name of the parameter to be changed.
 * @param value Value to be set to the given parameter.
 */
public void setPropertyValue(Object id, Object value){
	if (id == NAME)
		setName((String)value);
}

public void setSortIndex(int i) {
	sortIndex = i;
}

/**
 * @see java.lang.Object#toString()
 */
public String toString() {
	String className = getClass().getName();
	className = className.substring(className.lastIndexOf('.') + 1);
	return className + "(" + name +")";
}

/**
 * @return the index
 */

private void cleanCode(Transition transition) {
	//Find main file
	
	Config config = Config.getInstance();
	String base = Platform.getBundle(config.getPluginId()).getEntry("/").toString();
    String relativeUri = "com/netapp/nmsdk/flow/NetAppFlowMain.java";
	System.out.println("base+rel" + base+relativeUri);
	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	System.out.println("root workspace : " + root.getName());
	IResource resourceInRuntimeWorkspace = root.findMember("testplugin/com/netapp/nmsdk/flow/NetAppFlowMain.java");
	File mainFile = new File(resourceInRuntimeWorkspace.getLocationURI());

	StringBuilder sb = new StringBuilder();
	//StringBuilder customCode = new StringBuilder();
	String delim = System.getProperty("line.separator");
	int i = this.getActivityIndex();
	String start = "//"+transition.target.getName()+" Start "+i;
	String end   = "//"+transition.target.getName()+" End "+i;
	
	try {
		Scanner scanner = new Scanner(mainFile);
		String line = "";
		while(scanner.hasNextLine()){
			
			line = scanner.nextLine();
			if(line.contains(start)){
				//Ignore the lines till we reach end.
				while(!line.contains(end)){
					if(!line.equals(delim))
						line = scanner.nextLine();
				}
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

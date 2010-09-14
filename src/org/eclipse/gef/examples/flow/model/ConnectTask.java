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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ConnectTask extends Activity
{

protected static IPropertyDescriptor[] descriptors;

public static final String NAME = "Name"; //$NON-NLS-1$
public static  String dfmServer = "dfmServer";
public static  String dfmUser = "dfmUser";
public static  String dfmPwd = "dfmPwd";
public static  String useHttps = "useHttps";
public static  String ignoreCert = "ignoreCert";
public static  String[] booleanValues = {"True", "False"};

static {
	descriptors = new IPropertyDescriptor[] {
		new TextPropertyDescriptor(NAME, "Name"),
		new TextPropertyDescriptor(dfmServer, "DFM Server"),	
		new TextPropertyDescriptor(dfmUser, "DFM User"),
		new TextPropertyDescriptor(dfmPwd, "DFM Password"),
//		new TextPropertyDescriptor(useHttps, "False"),
		//new TextPropertyDescriptor(ignoreCert, "False")
		new ComboBoxPropertyDescriptor(useHttps, "Use HTTPS", booleanValues),
		new ComboBoxPropertyDescriptor(ignoreCert, "Ignore Certificate Errors", booleanValues)

	};
}

static final long serialVersionUID = 1;
private List inputs = new ArrayList();
private String name = "Init Connect Parameters";
private List outputs = new ArrayList();
private int sortIndex;

public ConnectTask() {}
public ConnectTask(String s) {
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
	if ("Name".equals(propName))
		return getName();
	if("dfmServer".equals(propName))
		return getDfmserver();
	if("dfmUser".equals(propName))
		return getDfmuser();
	if("dfmPwd".equals(propName))
		return getDfmpwd();
	if("useHttps".equals(propName))
		return getUsehttps();
	if("ignoreCert".equals(propName))
		return getIgnorecert();
	
	return super.getPropertyValue(propName);
}

public int getSortIndex() {
	return sortIndex;
}

public void removeInput(Transition transition) {
	inputs.remove(transition);
	fireStructureChange(INPUTS,transition);
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
//public static  String dfmServer = "";
//public static  String dfmUser = "";
//public static  String dfmPwd = "";
//public static  String useHttps = "False";
//public static  String ignoreCert = "False";
public void setPropertyValue(Object id, Object value){
	if (id == NAME)
		setName((String)value);
	if(id == dfmServer)
		setDfmServer((String)value);
	if(id == dfmUser)
		setDfmUser((String)value);
	if(id == dfmPwd)
		setDfmPwd((String)value);
	if(id == useHttps)
		setUseHttps((Integer)value);
	if(id == ignoreCert)
		setIgnoreCert((Integer)value);
	
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
public static String getDfmserver() {
	return dfmServer;
}
public static String getDfmuser() {
	return dfmUser;
}
public static String getDfmpwd() {
	return dfmPwd;
}
public static Integer getUsehttps() {
	if("True".equals(useHttps))	
		return new Integer(0);
	else 
		return new Integer(1);
	//return useHttps;
}
public static Integer getIgnorecert() {
	if("True".equals (ignoreCert))
		return new Integer(0);
	else
		return new Integer(1);
//	return ignoreCert;
}
public static void setDfmServer(String dfmServer) {
	ConnectTask.dfmServer = dfmServer;
}
public static void setDfmUser(String dfmUser) {
	ConnectTask.dfmUser = dfmUser;
}
public static void setDfmPwd(String dfmPwd) {
	ConnectTask.dfmPwd = dfmPwd;
}
public static void setUseHttps(Integer useHttps) {
	if(useHttps.intValue() == 0)
		ConnectTask.useHttps = "True";
	else
		ConnectTask.useHttps = "False";
	
}
public static void setIgnoreCert(Integer ignoreCert) {
	if(ignoreCert.intValue() == 0)
		ConnectTask.ignoreCert = "True";
	else
		ConnectTask.ignoreCert = "False";
	
}

}

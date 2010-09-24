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
package org.eclipse.gef.examples.flow;

import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * Plugin class used by the flow editor.
 */
public class FlowPlugin extends AbstractUIPlugin {
	/**
	 * The single instance of this plug-in runtime class.
	 */
	private static FlowPlugin sPlugin = null;
	

	
/**
 * Creates a new FlowPlugin with the given descriptor
 * @param descriptor the descriptor
 */
public FlowPlugin() {
	super();
	sPlugin = this;
}

public static FlowPlugin getDefault()
{
  return sPlugin;
}
public static String getPluginId()
{
  return getDefault().getBundle().getSymbolicName();
}

//public static void log(Throwable e)
//{
//  log(BasicDiagnostic.toDiagnostic(e));
//}
//
//public static void log(Diagnostic diagnostic)
//{
//  log(BasicDiagnostic.toIStatus(diagnostic));
//}
//
//public static void log(IStatus status)
//{
//  getDefault().getLog().log(status);
//}

}

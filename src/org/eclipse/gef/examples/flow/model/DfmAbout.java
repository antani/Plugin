/**
 * 
 */
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

/**
 * @author antani
 *
 */
public class DfmAbout extends Activity {
	static final long serialVersionUID = 1;
	private List inputs = new ArrayList();
	private String name = "DFM About";
	private List outputs = new ArrayList();
	private int sortIndex;
	private String injectStart="/*Custom Code Start 1 */";
	private String injectEnd="/*Custom Code End 1 */";
	
	protected static IPropertyDescriptor[] descriptors;

	public static final String NAME = "name"; //$NON-NLS-1$
	public static final String SIZE = "size"; //$NON-NLS-1$
	public static final String LOC = "loc"; //$NON-NLS-1$
	static {
		descriptors = new IPropertyDescriptor[] {
			new TextPropertyDescriptor(NAME, "Name"),
			new TextPropertyDescriptor(SIZE, "Size"),
			new TextPropertyDescriptor(LOC, "Location")};
	}
	
	public DfmAbout() { }
	public DfmAbout(String s){
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
//		Vector v = (Vector)outputs.clone();
//		Enumeration ins = inputs.elements();
//		while (ins.hasMoreElements())
//			v.addElement(ins.nextElement());
//		return v;
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
		int i = Config.getActivityIndex();
		Config.setActivityIndex(i-1);
		cleanCode(transition);
	}

	public void removeOutput(Transition transition) {
		outputs.remove(transition);
		fireStructureChange(OUTPUTS,transition);
	}

	public void setName(String s) {
		name = s;
		System.out.println("Name - " +s);
		
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
	public boolean isPropertySet(Object id)
	{ 
		return true;
	}
	public void resetPropertyValue(Object id)
	{
	}
	/**
	 * @return the injectStart
	 */
	public String getInjectStart() {
		return injectStart;
	}
	/**
	 * @param injectStart the injectStart to set
	 */
	public void setInjectStart(String injectStart) {
		this.injectStart = injectStart;
	}
	
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
		int index = transition.target.getIndex();
		String start = "//"+transition.target.getName()+" Start "+index;
		String end   = "//"+transition.target.getName()+" End "+index;
		
		try {
			Scanner scanner = new Scanner(mainFile);
			String line = "";
			while(scanner.hasNextLine()){
				
				line = scanner.nextLine();
				if(line.contains(start)){
					//Ignore the lines till we reach end.
					while(!line.contains(end)){
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

/**
 * 
 */
package org.eclipse.gef.examples.flow.model;

import java.util.ArrayList;
import java.util.List;

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
	private static int index = 0;
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
	/**
	 * @return the index
	 */
	public static int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public static void setIndex(int index) {
		DfmAbout.index = index;
	}
	
	
}

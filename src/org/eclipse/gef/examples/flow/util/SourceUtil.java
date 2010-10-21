/**
 * 
 */
package org.eclipse.gef.examples.flow.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author antani
 *
 */
public class SourceUtil {
	
	private static String delim = System.getProperty("line.separator");
	private static String line = "";
	private static HashMap <String, String> srcMap = new HashMap<String, String> ();
	static {
		srcMap.put("HelloDfm","generateDfmAbout");
		srcMap.put("ListAllDataset","generateListAllDataset");
		
	}
	public StringBuilder generateListAllDataset(String target, Integer x){
		StringBuilder customCode = new StringBuilder();
		customCode.append("\t\t").append("//"+target+" Start ").append(x).append(delim);
		customCode.append("\t\t").append("List listAllDataSet"+x+" = DatasetList.datasetList(server,username,password);").append(delim);		
		customCode.append("\t\t").append("//"+target+" End ").append(x).append(delim);
		customCode.append(line).append(delim);
		return customCode;
		
	}
	
	public StringBuilder generate(String target, int x) 
	{
		//Call method by reflection.
		Class types[] = new Class[] {String.class, Integer.class};
		StringBuilder returnStr = new StringBuilder("");
		try {
			Method m = this.getClass().getMethod(srcMap.get(target), types);
			Object[] args = new Object[]{target, new Integer(x)};
			Object value = new StringBuilder();
			SourceUtil u = new SourceUtil();
			value =  m.invoke(u, args);
			if(value != null){
				returnStr.append(value.toString());
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStr;

		
	}
	public static StringBuilder generateDfmAbout(String target, Integer x)
	{
		StringBuilder customCode = new StringBuilder();
		customCode.append("\t\t").append("//"+target+" Start ").append(x).append(delim);
		customCode.append("\t\t").append("String dfmAboutResult"+x+" = HelloDfm.helloDfm(server,username,password);").append(delim);
		customCode.append("\t\t").append("System.out.println(\"Result of HelloDfm -\"+dfmAboutResult"+x+" )").append(delim);
		customCode.append("\t\t").append("//"+target+" End ").append(x).append(delim);
		customCode.append(line).append(delim);
		return customCode;
	}
	
}

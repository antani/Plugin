/**
 * 
 */
package org.eclipse.gef.examples.flow.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.eclipse.gef.examples.flow.model.Activity;

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
		srcMap.put("PrintList","generatePrintList");
		
	}
	public StringBuilder generatePrintList(String target, Integer x, Activity sourceObj,Activity targetObj){
		StringBuilder customCode = new StringBuilder();
		customCode.append(tabs(2)).append("//"+target+" Start ").append(x).append(delim);
		if(sourceObj.isReturnPayload()){
			if(sourceObj.getPayloadVariableName() != null){
				String payloadVar =sourceObj.getPayloadVariableName();
				customCode.append(tabs(2)).append("Iterator<String> iterator = ").append(payloadVar).append(".iterator();").append(delim);
				customCode.append(tabs(2)).append("while ( iterator.hasNext() ){").append(delim).append(tabs(3)).append("System.out.println( iterator.next());").append(delim).append(tabs(2)).append("}");
			}
		}
		customCode.append(tabs(2)).append("//"+target+" End ").append(x).append(delim);
		return customCode;
	}
	
	public StringBuilder generateListAllDataset(String target, Integer x, Activity sourceObj,Activity targetObj){
		StringBuilder customCode = new StringBuilder();
		customCode.append(tabs(2)).append("//"+target+" Start ").append(x).append(delim);
		customCode.append(tabs(2)).append("List listAllDataSet"+x+" = DatasetList.datasetList(server,username,password);").append(delim);		
		customCode.append(tabs(2)).append("//"+target+" End ").append(x).append(delim);
		customCode.append(line).append(delim);
		if (targetObj.isReturnPayload()==true){
			targetObj.setPayloadType("List");
			targetObj.setPayloadVariableName("listAllDataSet"+x);
		}
		return customCode;
		
	}
	public static String tabs(int x){
		String t ="";
		for (int i =0; i<x; i++){
			t=t+"\t";
		}
		return t;
	}
	public StringBuilder generate(String target, int x, Activity sourceObj,Activity targetObj) 
	{
		//Call method by reflection.
		Class types[] = new Class[] {String.class, Integer.class,Activity.class,Activity.class};
		StringBuilder returnStr = new StringBuilder("");
		try {
			Method m = this.getClass().getMethod(srcMap.get(target), types);
			Object[] args = new Object[]{target, new Integer(x), sourceObj,targetObj};
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
	public static StringBuilder generateDfmAbout(String target, Integer x,Activity sourceObj,Activity targetObj)
	{
		StringBuilder customCode = new StringBuilder();
		customCode.append(tabs(2)).append("//"+target+" Start ").append(x).append(delim);
		customCode.append(tabs(2)).append("String dfmAboutResult"+x+" = HelloDfm.helloDfm(server,username,password);").append(delim);
		customCode.append(tabs(2)).append("System.out.println(\"Result of HelloDfm -\"+dfmAboutResult"+x+" )").append(delim);
		customCode.append(tabs(2)).append("//"+target+" End ").append(x).append(delim);
		customCode.append(line).append(delim);
		return customCode;
	}
	
}

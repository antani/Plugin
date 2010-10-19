/**
 * 
 */
package org.eclipse.gef.examples.flow.codegen;

//import java.io.IOException;
//import java.net.URL;

//import org.eclipse.core.runtime.FileLocator;
//import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
//import org.osgi.framework.Bundle;

/**
 * @author antani
 *
 */



public class Config {
	private Object model;
	/**
	 * @return the model
	 */
	public Object getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Object model) {
		this.model = model;
	}

	/**
	 * @return the pluginId
	 */
	public String getPluginId() {
		return pluginId;
	}

	/**
	 * @param pluginId the pluginId to set
	 */
	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	/**
	 * @return the classpathVariable
	 */
	public String getClasspathVariable() {
		return classpathVariable;
	}

	/**
	 * @param classpathVariable the classpathVariable to set
	 */
	public void setClasspathVariable(String classpathVariable) {
		this.classpathVariable = classpathVariable;
	}

	/**
	 * @return the templateRelativeUri
	 */
	public String getTemplateRelativeUri() {
		return templateRelativeUri;
	}

	/**
	 * @param templateRelativeUri the templateRelativeUri to set
	 */
	public void setTemplateRelativeUri(String templateRelativeUri) {
		this.templateRelativeUri = templateRelativeUri;
	}

	/**
	 * @return the mergeXmlRelativeUri
	 */
	public String getMergeXmlRelativeUri() {
		return mergeXmlRelativeUri;
	}

	/**
	 * @param mergeXmlRelativeUri the mergeXmlRelativeUri to set
	 */
	public void setMergeXmlRelativeUri(String mergeXmlRelativeUri) {
		this.mergeXmlRelativeUri = mergeXmlRelativeUri;
	}

	/**
	 * @return the targetFolder
	 */
	public String getTargetFolder() {
		return targetFolder;
	}

	/**
	 * @param targetFolder the targetFolder to set
	 */
	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	/**
	 * @return the targetFile
	 */
	public String getTargetFile() {
		return targetFile;
	}

	/**
	 * @param targetFile the targetFile to set
	 */
	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the forceOverwrite
	 */
	public boolean isForceOverwrite() {
		return forceOverwrite;
	}

	/**
	 * @param forceOverwrite the forceOverwrite to set
	 */
	public void setForceOverwrite(boolean forceOverwrite) {
		this.forceOverwrite = forceOverwrite;
	}

	private String pluginId;
	private String classpathVariable;
	private String templateRelativeUri;
	private String mergeXmlRelativeUri;
	private String targetFolder;
	private String targetFile;
	private String packageName;
	private boolean forceOverwrite = true;
	private static int activityIndex = 0;
	

	/**
	 * Constructs an uninitialized instance.
	 */
	private Config()
	{
	  super();
	}
	private static class ConfigHolder{
		private static final Config INSTANCE = new Config();
	}
	public static Config getInstance(){
		return ConfigHolder.INSTANCE;
	}
	
	  /**
	   * Returns the full URI of the JET template. This URI is found by appending
	   * the relative template URI to the installation URI of the plugin specified
	   * by the {@link #getPluginId() plugin id}.
	   * 
	   * @return the full URI of the JET template
	   */
	  public String getTemplateFullUri()
	  {
	    return getUri(getPluginId(), getTemplateRelativeUri());
	  }

	  /**
	   * Returns the full URI of the the XML file containing the settings for the
	   * JMerge control model. This URI is found by appending the relative merge XML
	   * URI to the installation URI of the plugin specified by the
	   * {@link #getPluginId() plugin id}.
	   * 
	   * @return the full URI of the the XML file containing the settings for the
	   *         JMerge control model
	   */
	  public String getMergeXmlFullUri()
	  {
	    return getUri(getPluginId(), getMergeXmlRelativeUri());
	  }
	  private String getUri(String pluginId, String relativeUri)
	  {
		  String base = Platform.getBundle(pluginId).getEntry("/").toString();
		  String result = base + relativeUri;
		  return result;
//		  String base = Platform.getPlugin(pluginId).getDescriptor().getInstallURL().toString();
//	      String uri = base + relativeUri;
//	      return uri;

	      

	  }

	/**
	 * @return the activityIndex
	 */
	public static int getActivityIndex() {
		return activityIndex;
	}

	/**
	 * @param activityIndex the activityIndex to set
	 */
	public static void setActivityIndex(int activityIndex) {
		Config.activityIndex = activityIndex;
	}
	public static void addActivityIndex(){
		activityIndex++;
	}
	public static void minusActivityIndex(){
		activityIndex--;
	}  
//	private String getURI(String pluginId, String relativeUri){
//		  Bundle bundle = Platform.getBundle(pluginId); 
//		  Path path = new Path("templates/NetAppFlowMain.javajet"); 
//	      URL fileURL = Platform.find(bundle, path);
//		  System.out.println("URL : " + fileURL.toString());
//		  return fileURL.toString();
//	  }
	  
	  
 
}

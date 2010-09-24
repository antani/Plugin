/**
 * 
 */
package org.eclipse.gef.examples.flow.model;



/**
 * @author antani
 *
 */
public class CodegenVariables {
	private String mPackageName = "";
	private String mClassName = "NetAppFlowMain";
	private String mAuthor = "";
	private String mVersion = "";
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String host = null;
	private String port = null;
	private String username = null;
	private String password = null; 
	
	public String getmPackageName() {
		return mPackageName;
	}
	public void setPackageName(String mPackageName) {
		this.mPackageName = mPackageName;
	}
	public String getClassName() {
		return mClassName;
	}
	public void setClassName(String mClassName) {
		this.mClassName = mClassName;
	}
	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}
	public String getVersion() {
		return mVersion;
	}
	public void setVersion(String mVersion) {
		this.mVersion = mVersion;
	}

}

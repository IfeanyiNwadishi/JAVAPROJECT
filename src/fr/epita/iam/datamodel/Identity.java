package fr.epita.iam.datamodel;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Identity {
	private String uid;
	private String displayName;
	private String email;
	private Date birthday;
	private String password;
	private boolean isAdmin = false;
	private Map<String, String> attributes = new HashMap<String, String>();
	private Set<Address> addresses = new HashSet<Address>();
	/**
	 * @param uid: Identity ID
	 * @param displayName: The name of the user
	 * @param email: Email of the user
	 */
	public Identity(String uid, String displayName, String email) {
		super();
		this.uid = uid;
		this.displayName = displayName;
		this.email = email;
	}
	
	public Identity(String uid, String displayName,
			String email, Date birthday, String password) {
		super();
		this.uid = uid;
		this.displayName = displayName;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAttribute(String name, String value){
		this.attributes.put(name, value);
	}
	public void setAttributes(Map<String, String> attributes){
		this.attributes = attributes;
	}
	public Map<String, String> getAttributes(){
		return this.attributes;
	}
	
	
	public Set<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddress(Address address){
		address.setIdentityID(this.uid);
		addresses.add(address);
	}
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		StringBuilder attributesStr = new StringBuilder();
		attributesStr.append("Attributes=[");
		for (Map.Entry<String, String> entry : attributes.entrySet()){
			attributesStr.append(entry.getKey()+"= "+entry.getValue()+",");
		}
		attributesStr.append("] , ");
		
		StringBuilder addressStr = new StringBuilder();
		attributesStr.append("Addresses=[");
		int count = 1;
		for (Address address : addresses){
			addressStr.append("Address "+ count+" = "+ address+",");
			count++;
		}
		addressStr.append("]");
		
		return "Identity [uid=" + this.uid +
				", isAdmin=" + this.isAdmin +
				", displayName=" + this.displayName +
				", email=" + this.email +
				", password=" + this.password +
				", birthday= "+this.birthday +
				", " + attributesStr.toString()+
				""+ addressStr.toString()+"]";
	}
	
	
}

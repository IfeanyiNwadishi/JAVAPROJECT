package fr.epita.iam.datamodel;


import java.util.HashMap;
import java.util.Map;

/**
 * This class define an Address
 * @author Nwadishi Ifeanyi
 *
 */
public class Address {
	private String addressID;
	private String identityID;
	private String street;
	private String city;
	private String zipCode;
	private Map<String, String> attributes = new HashMap<String,String>();

	public Address(String addressID, String identityID, String street, String city, String zipCode) {
		super();
		this.addressID = addressID;
		this.identityID = identityID;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getIdentityID() {
		return identityID;
	}
	public void setIdentityID(String identityID) {
		this.identityID = identityID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressID == null) ? 0 : addressID.hashCode());
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((identityID == null) ? 0 : identityID.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (addressID == null) {
			if (other.addressID != null)
				return false;
		} else if (!addressID.equals(other.addressID))
			return false;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (identityID == null) {
			if (other.identityID != null)
				return false;
		} else if (!identityID.equals(other.identityID))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Address [addressID=" + addressID + ", identityID=" + identityID + ", street=" + street + ", city="
				+ city + ", zipCode=" + zipCode + ", attributes=" + attributes + "]";
	}
	
	
}
package fr.epita.iam.datamodel;

public class Attribute {
	private String attributeID;
	private String name;
	private String value;
	
	
	/**
	 * @param identityID
	 * @param name
	 * @param value
	 */
	public Attribute(String attributeID, String name,  String value) {
		super();
		this.attributeID = attributeID;
		this.name = name;
		this.value = value;
	}
	public String getattributeID() {
		return attributeID;
	}
	public void setattributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	public void setAValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeID == null) ? 0 : attributeID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		Attribute other = (Attribute) obj;
	
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.toLowerCase().equals(other.name.toLowerCase()))
			return false;
	
		
		return true;
	}
	@Override
	public String toString() {
		return "Attribute [attributeID=" + attributeID + ", name=" + name + ", value=" + value + "]";
	}
	
	
	
}

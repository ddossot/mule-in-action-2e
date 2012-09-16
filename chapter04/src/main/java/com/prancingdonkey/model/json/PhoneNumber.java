package com.prancingdonkey.model.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;

//<start id="lis_04_json-phone-number-class"/>
@JsonAutoDetect
public class PhoneNumber
{
    private String name;

    private String email;

    // getters and setters here
    //<end id="lis_04_json-phone-number-class"/>

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

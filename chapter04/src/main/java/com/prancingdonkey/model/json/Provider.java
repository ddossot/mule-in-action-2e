package com.prancingdonkey.model.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

//<start id="lis_04_json-provider-class"/>
@JsonAutoDetect //<co id="lst_04_provider_annotation"/>
public class Provider
{
    private String name;

    private List<PhoneNumber> phoneNumbers;  //<co id="lst_04_provider_phonenumbers"/> 

    private List<EmailAddress> emailAddresses; //<co id="lst_04_provider_email_addresses"/>

    // getters and setters here
    //<end id="lis_04_json-provider-class"/>
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

}

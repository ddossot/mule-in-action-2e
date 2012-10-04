package com.prancingdonkey.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//<start id="canonical_data_model"/>
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer implements Serializable {

    String customerId;
    String firstName;
    String lastName;

//<end id="canonical_data_model"/>


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (firstName != null ? !firstName.equals(customer.firstName)
                : customer.firstName != null) return false;
        if (lastName != null ? !lastName.equals(customer.lastName)
                : customer.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null
                ? lastName.hashCode() : 0);
        return result;
    }
}


package com.prancingdonkey.model.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;

//<start id="lis_04_json-email-address-class"/>
@JsonAutoDetect
public class EmailAddress
{
    private String name;

    private String number;

    // getters and setters here
    // <end id="lis_04_json-email-address-class"/>

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

}

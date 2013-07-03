
package com.prancingdonkey.model.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

//<start id="lis_04_json-create-mixin"/>
@JsonAutoDetect
public abstract class ExternalItemMixin
{

    public abstract String getItemNumber();

    public abstract void setItemNumber(String itemNumber);

    @JsonIgnore
    public abstract String getUnwantedValue();

    @JsonIgnore
    public abstract void setUnwantedValue(String unwantedValue);
}
//<end id="lis_04_json-create-mixin"/>

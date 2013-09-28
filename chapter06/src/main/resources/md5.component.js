importPackage(org.apache.commons.codec.digest);

var PROPERTY_NAME = "MD5SUM";

if (!message.getProperty(PROPERTY_NAME)) {//<co id="lis_06_rhino_md5_1"/>
    log.debug("Setting " + PROPERTY_NAME 
    		+ " property for message: " + id);//<co id="lis_06_rhino_md5_2"/>
    message.setProperty(PROPERTY_NAME, getMD5Sum(payload));//<co id="lis_06_rhino_md5_3"/>
    result = message;//<co id="lis_06_rhino_md5_4"/>
}

function getMD5Sum(input) {
    return DigestUtils.md5Hex(input);
}

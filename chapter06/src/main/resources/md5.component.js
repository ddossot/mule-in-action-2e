importPackage(org.apache.commons.codec.digest);

var PROPERTY_NAME = "MD5SUM";

if (!message.getProperty(PROPERTY_NAME)) {
    log.debug("Setting " + PROPERTY_NAME 
    		+ " property for message: " + id);
    message.setProperty(PROPERTY_NAME, getMD5Sum(payload));
    result = message;
}

function getMD5Sum(input) {
    return DigestUtils.md5Hex(input);
}

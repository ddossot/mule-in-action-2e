package org.external;

import org.apache.commons.lang.RandomStringUtils;
import org.external.model.Weather;

public class MyExternalApi {

	public String connect(String username, String password) {
		// STUB
		return RandomStringUtils.randomAlphabetic(15);
	}
	
	public void logout(String connectionId) {
		// STUB
	}
	
	public Weather getWeather(String sessionId, boolean celsius, String countryName, String cityName) {
		// STUB
		return new Weather();
	}
	
	public boolean isConnected() {
		return true;
	}
}

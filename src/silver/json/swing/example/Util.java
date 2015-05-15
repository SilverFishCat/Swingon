package silver.json.swing.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public final class Util {
	public static final JsonElement getExampleJsonObject(){
		JsonElement rootElement = new JsonParser().parse("{"
				+ "\"text\" : \"lorem ipsum etc etc etc\", "
				+ "\"number\" : 123, "
				+ "\"array\" : ["
				+ "1, 2, 3, "
				+ "\"a\", \"b\", \"c\","
				+ "true, null, false"
				+ "],"
				+ "\"object\" : {"
				+ "\"a\" : \"x\","
				+ "\"b\" : \"y\","
				+ "\"c\" : \"z\""
				+ "}"
				+ "}");
		
		return rootElement;
	}
}

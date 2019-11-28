import java.util.List;
import java.util.Map;
import org.testng.Assert;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class JSONAssert{

	public static void main(String[] args) {

		String requiredJSONBody = "{'name’:’john’,’last name’:’doe’}”;
		String responseJSONBody = "{'name’:’john’,’last name’:’doe’,’address':{'city':' new delhi','state':'delhi'}}";
		assertRequiredJSON(responseJSONBody, requiredJSONBody);

	}

	public static void assertRequiredJSON(String responseJSONBody, String requiredJSONBody) {

		Configuration configuration = Configuration.defaultConfiguration().addOptions(Option.AS_PATH_LIST);
		Configuration configurationDefault = Configuration.defaultConfiguration();
		List<String> jsonPaths = JsonPath.using(configuration).parse(requiredJSONBody).read("$..*");
		DocumentContext responseJSONBodyParsed = JsonPath.using(configurationDefault).parse(responseJSONBody);
		DocumentContext requiredJSONBodyParsed = JsonPath.using(configurationDefault).parse(requiredJSONBody);

		for (String jsonPath : jsonPaths) {
			Object obj = responseJSONBodyParsed.read(jsonPath);
			if (obj != null
					&& !(List.class.isAssignableFrom(obj.getClass()) || Map.class.isAssignableFrom(obj.getClass()))) {
				Assert.assertEquals(obj, requiredJSONBodyParsed.read(jsonPath));
			}
		}
	}

}

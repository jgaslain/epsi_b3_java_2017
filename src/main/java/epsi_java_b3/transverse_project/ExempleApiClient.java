package epsi_java_b3.transverse_project;

import java.util.Base64;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SensorApi;
import io.swagger.client.model.SensorsReportsModel;

/**
 * Hello world!
 *
 */
public class ExempleApiClient {
	public static void main(String[] args) {

		try {
			ApiClient client = new ApiClient();
			client.addDefaultHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString("julien.gaslain:kamahu29".getBytes()));
			ApiResponse<SensorsReportsModel> result = new SensorApi(client)
					.v2SensorsBoxNameLimitGetWithHttpInfo("PEEK_BREHOU_1", 10, 4, null);
			System.out.println(result);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

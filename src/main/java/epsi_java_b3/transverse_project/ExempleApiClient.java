package epsi_java_b3.transverse_project;

import epsi.client_copeeks_v3.CopeeksClient;
import io.swagger.client.api.PicturesApi;
import io.swagger.client.api.SensorsApi;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.BoxWakeUp;
import io.swagger.client.model.BoxWakeUpList;
import io.swagger.client.model.Picture;
import io.swagger.client.model.PicturesCountBetweenDates;
import io.swagger.client.model.PicturesList;
import io.swagger.client.model.SensorValue;
import io.swagger.client.model.SensorValuesList;

public class ExempleApiClient {

	public static void main(String[] args) {
		
		try {
			
			// getSensorGraph(extrapolationFactor, boxName, reportType, sensorType)
			// 		extrapolationFactor : lissage de courbe, conserver la valeur "1"
			// 		boxName : "PEEK_BREHOU_1" ou "PEEK_BREHOU_2"
			// 		reportType : période sur laquelle récupérer les données (hour, day, week, month), conserver "month"
			// 		sensorType : "temperature", "pression", "humidite"
			
			SensorValuesList sensorValueList = new SensorsApi(CopeeksClient.get()).getSensorGraph("1", "PEEK_BREHOU_2", "week", "humidite");
			
			for (SensorValue value : sensorValueList.getResults()) {
				System.out.println(value.toString());
			}
			
			// Attention : 
			//  - certaines données ne sont viables que pour un seul des 2 boitiers.
			//  - les données renvoyées ne sont pas triées par date 
			
			
			// Récupération des boitiers auquel l'utilisateur a accès
			
			BoxWakeUpList boxList = new UsersApi(CopeeksClient.get()).getBoxList(null);
			
			for (BoxWakeUp box : boxList.getAvailableBoxes()) {
				System.out.println(box.toString());
			}
			
			// Récupération des photos pour une période donnée
			PicturesCountBetweenDates count = new PicturesApi(CopeeksClient.get()).getPicturesBetweenDatesCount("PEEK_BREHOU_2", "2017-12-15", "2017-12-18", null);
			System.out.println(count.getTotalPictures());
			
			PicturesList pictures = new PicturesApi(CopeeksClient.get()).getPicturesByDay("PEEK_BREHOU_2", "2017-12-18", null);
			
			for (Picture picture : pictures.getPicturesList()) {
				System.out.println(picture.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

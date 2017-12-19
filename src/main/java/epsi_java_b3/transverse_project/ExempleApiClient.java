package epsi_java_b3.transverse_project;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import epsi.client_copeeks_v3.CopeeksClient;
import io.swagger.client.api.SensorsApi;
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
			
			
			java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
			java.text.SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

			SensorValuesList sensorValueList = new SensorsApi(CopeeksClient.get()).getSensorGraph("1", "PEEK_BREHOU_2", "week", "temperature");
			
			//--------Variable pour la température-----------//
			double temperature = 0;
			double compteur = 0;
			double temperatureMax = 0;
			double temperatureMin = 999;
			//-----------------------------------------------//
			String nomBoitier = sensorValueList.getBoxName();
			//---------------Date du boitier--------------------//
			String date;
			String annee;
			String mois;
			int jour;
			//-----------------------------------------------//
			//---------------Date du jour--------------------//
			String[] d;
			String dateJour;
			String anneeJour;
			String moisJour;
			int jourJour;
			Calendar c = Calendar.getInstance ();
			dateJour = dt.format(c.getTime());
			System.out.println(dateJour);
			
			d = dateJour.split("/");
			anneeJour = d[2];
			moisJour = d[1];
			jourJour = Integer.parseInt(d[0]);
			//-----------------------------------------------//
			
			if(sensorValueList.getNumberOfResults().equals(0)){
				System.out.println("Aucune information à afficher");
			}
			else{
				for (SensorValue value : sensorValueList.getResults()) {
					date = value.getDate();
					String[] dateTemp = date.split(" ");
					date = dateTemp[0];
					dateTemp = date.split("-");
					annee = dateTemp[0];
					mois = dateTemp[1];
					jour = Integer.parseInt(dateTemp[2]);
					
					if((annee.equals(anneeJour)) && (mois.equals(moisJour))){
						if((jourJour - jour > 0) && (jourJour - jour < 4)){
							System.out.println(value.toString());
							temperature = temperature + value.getValue().doubleValue();
							compteur = compteur + 1;
							
							if (temperatureMax < value.getValue().doubleValue()){
								temperatureMax = value.getValue().doubleValue();
							}
							if (temperatureMin > value.getValue().intValue()){
								temperatureMin = value.getValue().intValue();
							}
						}
					}
				}
				//------Température moyenne / max / min pour les 3 derniers jour sans compter le jour d'aujourd'hui-------------//
				System.out.println("La moyenne des températures pour le boitier : " + nomBoitier + " est de : " + df.format(temperature / compteur));
				System.out.println("La tempéraure maximum est de " + temperatureMax);
				System.out.println("La tempéraure minimum est de " + temperatureMin);
				// Attention : 
				//  - certaines données ne sont viables que pour un seul des 2 boitiers.
				//  - les données renvoyées ne sont pas triées par date 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
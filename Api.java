import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Api {

    public static void main(String[] args) {
        // Reemplaza "TU_CLAVE_DE_API" con tu clave de API real de OpenWeatherMap
        String apiKey = "2fa3fe88c94fe03bd784b101b2253687";
        String city = "El Mante"; // Reemplaza con el nombre de la ciudad para la que deseas obtener el clima
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?id=3528756&appid=1a6a4b01018ba8ffe2386c5df48c0433&lang=es&units=metric&cnt=24";

        try {
            // Crear la URL para la solicitud HTTP
            URL url = new URL(apiUrl);

            // Abrir la conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar el código de respuesta
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta de la API
                System.out.println("Entro en la api");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Procesar la respuesta JSON
                //System.out.println(response.toString());
                // Parsear el JSON de respuesta
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray hourlyForecast = jsonObject.getJSONArray("list");

                // Procesar la información de la temperatura por horas
                for (int i = 0; i < hourlyForecast.length(); i++) {
                    JSONObject hourData = hourlyForecast.getJSONObject(i);
                    long timestamp = hourData.getLong("dt");
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String hora = sdf.format(timestamp*1000);
                    JSONObject mainData = hourData.getJSONObject("main");
                    double temperature = mainData.getDouble("temp");
                    JSONObject fecha = hourData.getJSONObject("sys");
                    String horario = hourData.getString("dt_txt");
                    // Convertir el timestamp a hora legible (puedes usar SimpleDateFormat)
                    // Imprimir la temperatura por horas

                    // Definir el formato de fecha y hora que tiene el String
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // Parsear el String a un objeto Date
            Date date = formato.parse(horario);
            
            // Obtener un objeto Calendar y establecer la fecha del objeto Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            
            // Obtener el día y la hora de la fecha
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Los meses en Calendar están basados en 0, por lo que se le suma 1 para obtener el valor correcto.
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            String fecha_mostar = day +"/"+ month + "/"+ year;
            String hora_mostrar = hour + "";
                    System.out.println("Fecha: " + fecha_mostar + " Hora: " + hora_mostrar + ", Temperatura: " + temperature);
                }
            } else {
                // Manejar el error si no se obtiene una respuesta exitosa
                System.out.println("Error al realizar la solicitud. Código de respuesta: " + responseCode);
            }

            // Cierra la conexión
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

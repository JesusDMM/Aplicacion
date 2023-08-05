import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static void main(String[] args) {
        // Reemplaza "TU_CLAVE_DE_API" con tu clave de API real de OpenWeatherMap
        String apiKey = "2fa3fe88c94fe03bd784b101b2253687";
        String city = "New York"; // Reemplaza con el nombre de la ciudad para la que deseas obtener el clima
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=1a6a4b01018ba8ffe2386c5df48c0433";

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
                System.out.println("hola");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar la respuesta JSON
                System.out.println("Respuesta de la API: ");
                System.out.println(response.toString());
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
package co.edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRemote {


    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String[] LOG_SERVERS = new String[]{
            "http://service1:4568",
            "http://service2:4568",
            "http://service3:4568"


    };


    private static int currentServer = 0;

    public static String remoteLogCall(String message) throws IOException {
        String encodedMessage = URLEncoder.encode(message, "UTF-8");
        return remoteHttpCall(LOG_SERVERS[currentServer] + "/logService?value=" + encodedMessage);
    }

    public static String remoteHttpCall(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);


        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);
        StringBuffer response = new StringBuffer();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE from Port: " + LOG_SERVERS[currentServer]);

        rotateRoundrobinServer();
        return response.toString();
    }

    public static void rotateRoundrobinServer() {
        currentServer = (currentServer + 1) % LOG_SERVERS.length;
    }
}

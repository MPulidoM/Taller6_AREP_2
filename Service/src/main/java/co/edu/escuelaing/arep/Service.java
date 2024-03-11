package co.edu.escuelaing.arep;

import static spark.Spark.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Service {
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        // get("/cliente", (req, pesp) -> getCliente());
        get("/logService", (req, pesp) -> {
            String encodedVal = req.queryParams("value");
            String val = URLDecoder.decode(encodedVal, "UTF-8");
            return logMessage(val);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

    private static String logMessage(String val) {
        // Configura la conexión a MongoDB

        // Construir la URL de conexión a MongoDB
        String mongoDbUrl = "mongodb://mongo:27017";

        try (MongoClient mongoClient = MongoClients.create(mongoDbUrl)) {
            // Selecciona la base de datos y la colección
            MongoDatabase database = mongoClient.getDatabase("dbmongo");
            MongoCollection<Document> collection = database.getCollection("messages");

            // Crea un documento para guardar en la colección
            Document document = new Document("message", val).append("date", new Date());


            // Inserta el documento en la colección
            collection.insertOne(document);

            // Recupera los últimos 10 registros de la colección
            FindIterable<Document> documents = collection.find().limit(10).sort(new Document("date", -1));

            // Construye una lista de mensajes a partir de los documentos
            List<String> messages = new ArrayList<>();
            for (Document doc : documents) {
                messages.add(doc.getString("message"));
            }

            // Crea un objeto JSON con los mensajes
            JSONObject responseJson = new JSONObject();
            responseJson.put("last_10_messages", new JSONArray(messages));

            // Retorna la respuesta JSON
            return responseJson.toString();


        } catch (Exception e) {
            e.printStackTrace();
            return "Error al guardar en MongoDB"; // Manejo de errores
        }

    }


}

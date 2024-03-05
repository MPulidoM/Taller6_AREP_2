package co.org.example;

import static spark.Spark.port;
import static spark.Spark.get;

public class LogService {
    public static void main(String[] args) {
        port(5000);
        get("/logserver",(req,res) -> {
            System.out.println("logservice");
            res.type("Aplication/json");
            return "Hola mundo"; });
    }
}

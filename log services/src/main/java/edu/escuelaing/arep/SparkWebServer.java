package edu.escuelaing.arep;

import spark.Request;
import spark.Response;
import org.json.JSONObject;
import static spark.Spark.port;
import static spark.Spark.get;

public class SparkWebServer
{
    public static void main(String[] args){
        port(getPort());
        get("/Datos", (req,res) -> addDatos(req,res));
        get("/Resultados", (req,res) -> viewDatos(req,res));
    }
    public static String addDatos(Request req, Response res){
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Registro de cadenas con Docker y AWS</h2>"
                + "<form action=\"/consultar\">"
                + "  Ingrese la cadena a insertar <br>"
                + "  <input type=\"text\" name=\"datos\" >"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static JSONObject viewDatos(Request req, Response res) {

        String datos = req.queryParams("datos");
        MongoServer conexiondb = new MongoServer();
        conexiondb.insertData(datos);
        res.header("Content-Type","application/json");
        return conexiondb.getData();

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}

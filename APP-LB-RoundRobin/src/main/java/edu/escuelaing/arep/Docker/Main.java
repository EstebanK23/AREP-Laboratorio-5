package edu.escuelaing.arep.Docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Main {
    
    private static int balanceo = 1;

    public static void main(String args[]) {

        port(getPort());
        get("/Datos", (req, res) -> inputDataPage(req, res) );
        get("/Resultados", (req, res) -> resultsPage(req, res));

    }

    private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Registro de cadenas con Docker y AWS</h2>"
                + "<form action=\"/Resultados\">"
                + "  Ingrese la cadena a insertar <br>"
                + "  <input type=\"text\" name=\"cadena\" >"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return pageContent;
    }
    
    private static String resultsPage(Request req, Response res) throws MalformedURLException, IOException {
        
        String cadena = req.queryParams("cadena").replace(" ","+");
        
        //System.out.println(req.toString());
        
        BufferedReader in = null;
        
        balanceo = getNumLogService();
        
        URL logService = null;
        if (balanceo == 1){
            logService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:35001/Resultados?cadena="+cadena);
        } else if (balanceo == 2){
            logService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:35002/Resultados?cadena="+cadena);
        } else {
            logService = new URL("http://ec2-54-236-9-109.compute-1.amazonaws.com:35003/Resultados?cadena="+cadena);
        }
        
        System.out.println(logService);
        
        URLConnection con = logService.openConnection();
        
        in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		
	BufferedReader stdIn = new BufferedReader(
	new InputStreamReader(System.in));

        String line = in.readLine();
        
        System.out.println(line);

        in.close();
        stdIn.close();
        res.header("Content-Type", "application/json");
        
        JSONObject jsonObject = new JSONObject(line.replace("\\\"", ""));
        return line.replace("\\\"", "").replace(",","");
    }
    
    private static int getNumLogService(){
        if (balanceo == 3){
            balanceo = 0;
        }
        balanceo += 1;
        
        return balanceo;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 20000;
    }

}

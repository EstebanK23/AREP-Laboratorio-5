package edu.escuelaing.arep;

import java.util.ArrayList;
import java.util.Date;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.json.JSONObject;

public class MongoServer {
    public MongoServer(){}

    public void insertData(String datos){
        ArrayList<Data> file = new ArrayList<Data>();
        file.add(new Data(datos, new Date()));

        MongoClient Client = new MongoClient(new MongoClientURI("localhost:27017"));

        DB database = Client.getDB("dbmongo");

        DBCollection collection = database.getCollection("CollectionDatos");

        for (Data x : file) {
            collection.insert(x.toDBObjectData());
        }

        Client.close();
    }

    public JSONObject getData() {
        JSONObject json = new JSONObject();
        MongoClient Client = new MongoClient(new MongoClientURI("localhost:27017"));

        DB database = Client.getDB("dbmongo");

        DBCollection collection = database.getCollection("CollectionDatos");

        DBCursor cursor = collection.find();

        ArrayList<String> lista = new ArrayList<String>();
        try {
            while (cursor.hasNext()) {
                lista.add(cursor.next().toString());
            }
        } finally {
            cursor.close();
        }
        if(lista.size()<11){
            json = lastTen(lista);
        }

        Client.close();
        return json;
    }

    public JSONObject lastTen(ArrayList lista){
        int j = 0;
        JSONObject json2 = new JSONObject();
        for (int i=lista.size()-1 ; i>=0 && j<=9 ; i--){
            json2.append("nombres", lista.get(i));
            j++;
        }
        return json2;
    }
}

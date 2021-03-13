package edu.escuelaing.arep;

import com.mongodb.BasicDBObject;
import java.util.Date;

public class Data {
    private String datos;
    private Date date;

    public Data(String datos, Date date) {
        this.datos = datos;
        this.date = date;
    }
    public Data(BasicDBObject dBObject) {
        this.datos = dBObject.getString("datos");
        this.date = dBObject.getDate("date");
    }

    public BasicDBObject toDBObjectData() {

        BasicDBObject dBObjectData = new BasicDBObject();

        dBObjectData.append("datos", this.getDatos());
        dBObjectData.append("date", this.getDate());

        return dBObjectData;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

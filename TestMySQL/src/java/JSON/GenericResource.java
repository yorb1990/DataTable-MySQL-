/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSON;

import DB.MySQLController;
import DT.DataSet;
import DT.DataTable;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author yorb1990
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    private Connection conn = null;
    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","1+1-1=1");
        }
        catch (Exception e)
        {
            System.out.println("NO CONNECTION =(");
        }
    }

    /**
     * Retrieves representation of an instance of JSON.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("NC")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String NC() {
        DB.MySQLController my=new MySQLController();
        return my.ExecMulti("ERROR").toJSON();
    }
    @GET
    @Path("NE")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String NE() {
        DB.MySQLController my=new MySQLController();
        my.SetConnection(conn);
        return my.ExecMulti("ERROR").toJSON();
    }
    @GET
    @Path("E1")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String E1() {
        DB.MySQLController my=new MySQLController();
        my.SetConnection(conn);
        return my.ExecMulti("select * from test").toJSON();
    }
}

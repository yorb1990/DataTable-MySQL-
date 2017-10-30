/*
 * CLASE DE CONEXION DIRECTA CON MySQL 
 */
package MySQL.Direct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BRAIN RAFAEL ORTEGA YAÑEZ
 */
public class MySQLController 
{
    private Connection _Connection = null;
    private boolean _CloseConnection=true;
    /**
     * 
     * @param connection CONEXION SQL A USAR
     * @throws SQLException error al conectarse
     */
    public MySQLController(Connection connection) {
        _Connection=connection;
    }
    public MySQLController(){
        
    }

    /**
     *
     * @param c
     */
    public void SetConnection(Connection c){
        this._Connection=c;
    }
    /**
     * 
     * @param connection CONEXION SQL A USAR
     * @param close cerrar conexion al finalizar la ejecucion de la consulta
     * @throws SQLException error al conectarse
     */
    public MySQLController(Connection connection,boolean close) {
        _Connection=connection;
        _CloseConnection=close;
    }
    /**
     * 
     * @param command COMANDO SQL ESTILO UPDATE,INSERT O DELETE O UNA CONSULTA QUE NO REGRESE NINGUN VALOR
     * @return RESPONDE SI SE EJECUTO CON EXITO
     * @throws SQLException ERROR DE EJECUCION EN EL COMANDO
     */
    public DataSet ExecNonQuery(String command,String MSG) {
        DataSet dt=new DataSet();
        if(_Connection==null){
            dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
            dt.Tables.get(0).AddRow(new Object[]{"CONECTAR","la conexión a la base de datos no es valida.","CERRADA"});
            return dt;
        }
        try {
            if(_Connection.isClosed()){
                dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
                dt.Tables.get(0).AddRow(new Object[]{"CONECTAR","la conexión a la base de datos no es valida.","CERRADA"});
                return dt;
            }
            Statement s = _Connection.createStatement(); 
            s.execute(command);      
            dt.Tables.add(new DataTable(new String[]{"MSG"}));
            dt.Tables.get(0).AddRow(new Object[]{MSG});
        } catch (Exception ex) {                        
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
            dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
            dt.Tables.get(dt.Tables.size()-1).AddRow(new Object[]{"EJECUTAR",ex.getMessage(),ex.getStackTrace()});            
        }finally{
            if(_CloseConnection){
                try { 
                    _Connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }   
        return dt;
    }
    /**
     * 
     * @param command comando SQL a ejecutar
     * @return un arreglo de tablas
     * @throws SQLException error en la sql
     * @throws Exception error de lectura en resultset
     */
    public DataSet ExecMulti(String command) {
            DataSet dt=new DataSet();
            if(_Connection==null){
                dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
                dt.Tables.get(0).AddRow(new Object[]{"CONECTAR","La conexión a la base de datos no es válida.","NO CONECTADA"});
                return dt;
            }
        try {
            if(_Connection.isClosed()){
                dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
                dt.Tables.get(0).AddRow(new Object[]{"CONECTAR","La conexión a la base de datos no es válida.","CERRADA"});
                return dt;
            }
            PreparedStatement s = _Connection.prepareStatement(command);
            dt=new DataSet(s);
        }catch (Exception ex) {                        
            try {
                Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
                dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
                dt.Tables.get(dt.Tables.size()-1).AddRow(new Object[]{"EJECUTAR",ex.getMessage(),ex.getStackTrace()});
            } catch (Exception ex1) {
                Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            if(_CloseConnection){
                try { 
                    _Connection.close();
                } catch (SQLException ex) {
                    try {
                        Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
                        dt.Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));                    
                        dt.Tables.get(dt.Tables.size()-1).AddRow(new Object[]{"CERRAR SESION",ex.getMessage(),ex.getStackTrace()});
                    } catch (Exception ex1) {
                        Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }            
        }   
        return dt;
        
    }
}

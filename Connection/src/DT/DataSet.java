/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BRAIN RAFAEL ORTEGA YAÑEZ
 */
public class DataSet {
    public List<DataTable> Tables=new ArrayList<>();;
    public DataSet(PreparedStatement   statment) throws Exception{
        Load(statment);
    }
    public DataSet(){}
    /**
     * 
     * @param statment prepareStatement(String) para generar multiples tablas (no administra el cierre del stanment)
     * @throws Exception ERROR EN EJECUCION DEL ESTANMENT 
     */
    public void Load(PreparedStatement   statment) {
        ResultSet  resultset=null;
        boolean hasResults = false;
        try {
            hasResults = statment.execute();
            while (hasResults) {
                resultset=statment.getResultSet();
                Tables.add(new DataTable(resultset));
                hasResults = statment.getMoreResults();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSet.class.getName()).log(Level.SEVERE, null, ex);
            Tables.add(new DataTable(new String[]{"TIPO","ERROR","INNER"}));
            Tables.get(Tables.size()-1).AddRow(new Object[]{"EJECUTAR",ex.getMessage(),ex.getStackTrace()});
        }finally{
            try {
                if(resultset!=null&&!resultset.isClosed()){
                    resultset.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * 
     * @return genera el arreglo de tablas en formato json
     */
    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        for(int index=0;index<Tables.size();index++){
            sb.append(String.format("%1$s%2$s", 
                    (index>0&&index<Tables.size())?",":"",
                    Tables.get(index).toJSON()));
        }
        if(sb.length()>0){
            sb.append("]");
            sb.insert(0, "[");
        }
        return sb.toString();
    }
}

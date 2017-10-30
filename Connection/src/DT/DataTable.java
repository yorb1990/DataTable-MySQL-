/*
 * OBJECTO 'DataTable' CONTENEDOR DE UNA TABLA
 */
package DT;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BRAIN RAFAEL ORTEGA YAÑEZ
 */
public class DataTable {
    private DataColumn[] _Columns;
    private List<DataRow> _Rows=new ArrayList<>();
    /**
     * 
     * @param resultset ResultSet CONTENEDORA DE UNA SOLA TABLA
     * <BR>
     * (SI ES MULTIPLE SE TOMA SOLO LA PRIMERA)
     * @throws Exception COINCIDENCIA DE DATOS No COLUMAS EN No FILAS
     */
    public DataTable(ResultSet  resultset) {
        Load(resultset);
    }
    /**
     * 
     * @param columns COLUMNAS QUE TENDRA LA TABLA
     */
    public DataTable(DataColumn[] columns){
        _Columns=columns;
    }
    /**
     * 
     * @param names NOMBRES DE LAS COLUMNAS QUE TENDRA LA TABLA
     */
    public DataTable(String[] names){
        _Columns=new DataColumn[names.length];
        for(int index=0;index<names.length;index++){
            _Columns[index]=new DataColumn(names[index]);
        }
    }
    /**
     * 
     * @param row FILA NUEVA
     * @return INDICE DE LA NUEVA FILA
     * @throws Exception COINCIDENCIA DE No DE COLUMNAS EN FILA
     */
    public int AddRow(DataRow row) {
        if(row.Length()!=_Columns.length){
            //throw new Exception("The length does not match"); 
            return 0;
        }
        _Rows.add(row);
        return _Rows.size()-1;
    }
    /**
     * 
     * @param items ARREGLO DE OBJETOS PARA LA FILA
     * @return INDICE DE LA NUEVA FILA
     * @throws Exception COINCIDENCIA DE No DE COLUMNAS EN FILA
     */
    public int AddRow(Object[] items) {
        return AddRow(new DataRow(items));
    }
    /**
     * 
     * @return POJO DE LA COLUMNA
     */
    public DataRow  NewRow(){
        return new DataRow(new Object[_Columns.length]);
    }
    /**
     * 
     * @param index INDEICE RELACIONADO AL NOMBRE DE LA COLUMNA
     * @return NOMBRE DE LA COLUMNA INDICADA
     */
    public String GetColumnName(int index){
        return _Columns[index].toString();
    }
    public DataColumn[] GetDataColumn(){
        return _Columns;
    }
    /**
     * 
     * @return LISTA DE LAS FILAS EN LA TABLA
     */        
    public List<DataRow> GetRows(){        
        return _Rows;
    }
    /**
     * 
     * @return TABLA EN FORMATO JSON 
     */
    public String toJSON(){
        StringBuilder sb=new StringBuilder();
        if(_Rows.isEmpty()){
            sb.append("{");
            for(int index=0;index<_Columns.length;index++){
                sb.append(String.format("%1$s\"%2$s\":\"\"",
                        (index>0 && index<_Columns.length)?",":"",
                        _Columns[index].toString()));
            }
            sb.append("}");
        }
        for(int index1=0;index1<_Rows.size();index1++){
            sb.append("{");
            for(int index2=0;index2<_Columns.length;index2++)
            {                
                Object item=_Rows.get(index1).GetByIndex(index2);
                sb.append(String.format("%1$s\"%2$s\":%3$s",
                        (index2>0 && index2<_Columns.length)?",":"",
                        _Columns[index2].toString(),
                      (item==null)?"null":
                      (item.getClass().equals(Integer.class))?item:
                      (item.getClass().equals(Float.class))?item:
                      (item.getClass().equals(Boolean.class))?item:
                      (item.getClass().equals(java.math.BigInteger.class))?item:        
                              String.format("\"%1$s\"",RemplazarEspeciales(item.toString().trim()))
                      //String.format("\"%1$s - %2$S\"",item,item.getClass())
                ));                
            }
            sb.append(String.format("}%1s", (index1>-1 && index1<_Rows.size()-1)?",":"" ));
        }
        if(sb.length()>0){
            sb.append("]");
            sb.insert(0, "[");
        }
        return sb.toString();
    }
    private String RemplazarEspeciales(String item){
                     return item.replace("\n", "<br>")
                                .replace("\t", "   ")
                                .replace("\r", "<br>")
                                .replace("'", "\\u0027")
                                .replace("\"", "\\u0022");
    }
    /**
     * 
     * @param resultset CONVERSION DE ResultSet  A 'DataTable' (no administra el cierre de ResultSet)
     * @throws SQLException ERROR DE LECTURA EN EL ResultSet
     */
    public void Load(ResultSet  resultset)  {
        ResultSetMetaData rsmd = null;
        try {
            rsmd = resultset.getMetaData();
            _Columns=new DataColumn[rsmd.getColumnCount()];
            for(int index=rsmd.getColumnCount();index>0;index--){
                _Columns[index-1]=new DataColumn(rsmd.getColumnName(index));
            }
            while(resultset.next())
            {
                Object[] items=new Object[rsmd.getColumnCount()];
                for(int index=rsmd.getColumnCount();index>0;index--){
                    items[index-1]=resultset.getObject(index);
                }
                AddRow(items);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
            _Columns=new DataColumn[]{new DataColumn("TIPO"),new DataColumn("ERROR"),new DataColumn("INNER")};
            AddRow(new Object[]{"EJECUTAR",ex.getMessage(),ex.getStackTrace()});
        }/*finally{
            resultset.close();
        }  */   
    }
}

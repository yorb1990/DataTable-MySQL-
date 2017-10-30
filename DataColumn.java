/*
ESTA CLASE PERMITE ALMACENAR UNA COLUMNA DEL OBJETO 'DataTable'
 */
package MySQL.Direct;

/**
 *
 * @author BRAIN RAFAEL ORTEGA YAÑEZ
 */
public class DataColumn {
    private String ColumnName;
/**
*CREA UNA COLUMNA CON EL NOMBRE ENVIADO
 *
 * @param  Name  NOMBRE DE LA COLUMNA
 * @see         MySQL.Direct.DataTable
 */
    public DataColumn(String Name){
        ColumnName=Name;
    }
    /**
     * RETORNA EL NOMBRE DE LA COLUMNA
     * @return NOMBRE DE LA COLUMNA
     */
    @Override
    public String toString(){
        return ColumnName;
    }
}

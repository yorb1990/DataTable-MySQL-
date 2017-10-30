/*
ESTA CLASE ALMACENA UNA FILA DEL OBJETO 'DataTable'
 */
package DT;

/**
 *
 * @author BRAIN RAFAEL ORTEGA YAÑEZ
 */
public class DataRow {
    private Object[] ItemArray;
        /**
*CREA UNA FILA APARTIR DEL ARREGLO DE OBJETOS
 *
 * @param  Values  ELEMENTOS DENTRO DE LA FILA
 * @see         DT.DataTable
 */
    public DataRow(Object[] Values){
        ItemArray=Values;
    }
            /**
             * 
             * CREA UNS FILA VACIA
             * 
             * @see         DT.DataTable
             */
    public DataRow(){
        
    }
    /**
*RETORNA EL OBJETO RELACIONADO AL INDEX
 *
 * @param  Index  INDICE RELACIONADO AL OBJETO
 * @return      RETORNA EL OBJETO SOLICITADO
 * @see         DT.DataTable
 */
    public Object GetByIndex(int Index){
        return ItemArray[Index];
    }
    /**
    *RETORNA LA LONGITUD DE LA FILA
 *
 * @return      LONGITUD
 * @see         DT.DataTable
 */
    public int Length(){
        return ItemArray.length;
    }

    /**
     *
     * @param Index indice
     * @return valor en tipo string
     */
    public String toString(int Index){
        return (ItemArray[Index]==null)?"null":ItemArray[Index].toString();
    }
}

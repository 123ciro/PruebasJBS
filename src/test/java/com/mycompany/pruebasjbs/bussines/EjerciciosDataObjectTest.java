package com.mycompany.pruebasjbs.bussines;

import com.mycompany.pruebasjbs.tables.Moneda;
import com.mycompany.pruebasjbs.tables.Pais;
import java.math.BigDecimal;
import java.util.Map;
import javax.naming.NamingException;
import org.javabeanstack.data.IDataSet;
import org.javabeanstack.data.IGenericDAORemote;
import org.javabeanstack.data.model.DataSet;
import org.javabeanstack.datactrl.DataObject;
import org.javabeanstack.datactrl.IDataObject;
import org.javabeanstack.error.IErrorReg;
import org.javabeanstack.exceptions.SessionError;
import org.junit.Test;
import static org.junit.Assert.*;

public class EjerciciosDataObjectTest extends TestClass {

    private static IGenericDAORemote dao;

    @Test
    public void varios() throws Exception, NamingException, SessionError {
        System.out.println("OPEN,INSERT,DELETE,CLOSE and SETFIELD");
        if (error != null) {
            System.out.println(error);
            return;
        }
        //MONEDA
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (!moneda.find("codigo", "URU")) {
            moneda.insertRow();
            moneda.setField("codigo", "URU");
            moneda.setField("nombre", "Uruguay");
            moneda.setField("cambio", BigDecimal.ONE);
            boolean result = moneda.update(false);

            assertTrue(result);

        }

        if (moneda.find("codigo", "URU")) {
            moneda.deleteRow();
            boolean result = moneda.update(false);

            assertTrue(result);

        }
        moneda.close();
    }

    @Test
    public void testFilter() throws Exception, NamingException, SessionError {
        System.out.println("SetFilter ");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.setFilter("codigo = 'EUR'");
        moneda.getFilter();
        moneda.requery();

        System.out.println("Moneda Filtrada--> " + moneda.getRow());
        assertNotNull(moneda.getRow());
    }

    @Test
    public void testOrder() throws Exception, NamingException, SessionError {
        System.out.println("Order");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.setOrder("idmoneda asc");
        moneda.getOrder();
        moneda.requery();

        System.out.println("Moneda Ordenada --> " + moneda.getDataRows());
    }

    @Test
    public void testFisrtRow() throws Exception, NamingException, SessionError {
        System.out.println("FirstRow");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.setFirstRow(2);
        moneda.getFirstRow();
        moneda.requery();

        System.out.println("Registro --> " + moneda.getRow());
    }

    @Test
    public void testMaxRow() throws Exception, NamingException, SessionError {
        System.out.println("MaxRow");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        //Cantidad de registros que queremos traer
        moneda.setMaxRows(2);
        moneda.getMaxRows();
        moneda.requery();

        System.out.println("Registros --> " + moneda.getDataRows());
    }

    @Test
    public void testReadWriteFalse() throws Exception, NamingException, SessionError {
        System.out.println("ReadWrite False");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        //En este caso no va a insertar el registro porque esta en false el ReadWrite
        moneda.setReadWrite(false);
        moneda.insertRow();
        moneda.setField("codigo", "PER");
        moneda.setField("nombre", "PERU");
        moneda.setField("cambio", BigDecimal.valueOf(1.65));
        Boolean result = moneda.update(false);

        assertFalse(result);

    }

    @Test
    public void testReadWriteTrue() throws Exception, NamingException, SessionError {
        System.out.println("ReadWrite True");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        //aca si permite ingresar los datos ya que el Readwrite esta en true
        moneda.setReadWrite(true);
        moneda.insertRow();
        moneda.setField("codigo", "PER");
        moneda.setField("nombre", "PERU");
        moneda.setField("cambio", BigDecimal.valueOf(1.65));
        Boolean result = moneda.update(false);
        assertTrue(result);

        if (moneda.find("codigo", "PER")) {
            moneda.deleteRow();
            moneda.update(false);
        }

    }

    @Test
    public void testClaveForanea() throws Exception, NamingException, SessionError {
        System.out.println("Clave Foranea");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();

        assertFalse(moneda.isForeingKey("pais"));
        assertTrue(!moneda.isForeingKey("pais"));

    }
    
        @Test
    public void testClaveForaneaPais() throws Exception, NamingException, SessionError {
        System.out.println("Clave Foranea");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Pais> pais = new DataObject(Pais.class, null, dataLink, null);
        pais.open();

        
        assertTrue(pais.isForeingKey("Region"));

    }
    

    @Test
    public void testColumnaExiste() throws Exception, NamingException, SessionError {
        System.out.println("Existencia de Columna");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();

        assertTrue(moneda.isFieldExist("nombre"));
        assertFalse(moneda.isFieldExist("nombres"));

    }

    @Test
    public void testgetRow() throws Exception, NamingException, SessionError {
        System.out.println("GET ROW");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.setFilter("idmoneda=245");
        moneda.requery();
        System.out.println("Valor -->" + moneda.getRow());
        assertNotNull(moneda.getRow());
    }

    @Test
    public void testGetRows() throws Exception, NamingException, SessionError {
        System.out.println("GET ROWS");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();

        System.out.println("valores --> " + moneda.getDataRows());
        assertNotNull(moneda.getDataRows());

    }

    @Test
    public void testGetRecno() throws Exception, NamingException, SessionError {
        System.out.println("GetRecno");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.find("codigo", "EUR");
        int pos = 2;
        assertEquals(pos, moneda.getRecno());
        moneda.close();
    }

    @Test
    public void testGetField() throws Exception, NamingException, SessionError {
        System.out.println("GetField");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.find("codigo", "EUR");
        String cod = "EUR";
        System.out.println("valor ->" + moneda.getRow());
        assertEquals(cod, moneda.getField("codigo"));

    }

    @Test
    public void testFind() throws Exception, NamingException, SessionError {
        System.out.println("Find");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.find("codigo", "EUR");
        assertNotNull(moneda.getRow());

    }

    @Test
    public void testGetFindNext() throws Exception, NamingException, SessionError {
        System.out.println("FindNext");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.find("codigo", "GS ");
        moneda.findNext();
        moneda.requery();
        assertTrue(moneda.findNext());

    }

    @Test
    public void testGoTo() throws Exception, NamingException, SessionError {
        System.out.println("GoTo");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.goTo(2);
        String esperado = "EUR";
        System.out.println("valor -->" + moneda.getRow());
        assertEquals(esperado, moneda.getField("codigo"));

    }

    @Test
    public void testMoveFirst() throws Exception, NamingException, SessionError {
        System.out.println("MoveFirst");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.moveFirst();
        String esperado = "GS ";
        assertEquals(esperado, moneda.getField("codigo"));
    }

    @Test
    public void testMoveNext() throws Exception, NamingException, SessionError {
        System.out.println("MoveNext");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.moveFirst();
        moneda.moveNext();
        String esperado = "USD";
        assertEquals(esperado, moneda.getField("codigo"));
    }

    @Test
    public void testMovePrevius() throws Exception, NamingException, SessionError {
        System.out.println("MovePrevius");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.find("codigo", "EUR");
        moneda.movePrevious();
        String esperado = "USD";
        assertEquals(esperado, moneda.getField("codigo"));
    }

    @Test
    public void testMoveLast() throws Exception, NamingException, SessionError {
        System.out.println("MoveLast");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.moveLast();
        String esperado = "EUR";
        assertEquals(esperado, moneda.getField("codigo"));
    }

    @Test
    public void testIsEoF() throws Exception, NamingException, SessionError {
        System.out.println("EoF: significa el final de la lista");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.moveLast();
        assertFalse(moneda.isEof());

        moneda.moveLast();
        moneda.moveNext();
        assertTrue(moneda.isEof());

    }

    @Test
    public void testCantidad() throws Exception, NamingException, SessionError {
        System.out.println("Cantidad de Valores");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        int canti = 3;
        System.out.println("cantidad de valores -->" + moneda.getRowCount());
        assertEquals(canti, moneda.getRowCount());

    }

    @Test
    public void testGetError() throws Exception, NamingException, SessionError {
        System.out.println("Get Error");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.insertRow();
        //se quiere ingresar en una columna que no existe en la tabla
        moneda.setField("cantidad", "5");
        //se pone un not null ya que si existe error.
        assertNotNull(moneda.getErrorApp());
    }

    @Test
    public void testGetErrorMsg() throws Exception, NamingException, SessionError {
        System.out.println("Get Error Msg");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        moneda.insertRow();
        //se quiere ingresar en una columna que no existe en la tabla
        moneda.setField("cantidad", "5");
        // El mensaje de error ya que la columna cantidad no existe
        System.out.println("Mensaje de Error --> " + moneda.getErrorMsg(true));

        assertNotNull(moneda.getErrorMsg(true));
    }

    @Test
    public void testCheckData() throws Exception, NamingException, SessionError {
        System.out.println("Check Data");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (!moneda.find("codigo", "URU")) {
            moneda.insertRow();
            moneda.setField("codigo", "URU");
            moneda.setField("nombre", "Uruguay");
            moneda.setField("cambio", BigDecimal.TEN);
            moneda.update(false);
            assertTrue(moneda.checkData(true));

        }

        //Se encuentra el codigo URU y elimina los datos.
        if (moneda.find("codigo", "URU")) {
            moneda.deleteRow();
            moneda.update(false);
        }
    }

    @Test
    public void testCheckDataRow() throws Exception, NamingException, SessionError {
        System.out.println("Check Data Row");
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataObject<Moneda> moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();

        if (!moneda.find("codigo", "URU")) {

            Map<String, IErrorReg> resultadoEsp = null;
            moneda.insertRow();
            moneda.setField("codigo", "URU");
            moneda.setField("nombre", "Uruguay");
            moneda.setField("cambio", BigDecimal.TEN);
            moneda.update(false);
            Map<String, IErrorReg> resultado = moneda.checkDataRow();
            System.out.println("Errores -->" + resultado);
            assertEquals(resultadoEsp, resultado);
            assertNull(resultado);

        }

        //Se encuentra el codigo URU y elimina los datos.
        if (moneda.find("codigo", "URU")) {
            moneda.deleteRow();
            moneda.update(false);
        }

    }

    @Test
    public void Revert() throws Exception, NamingException, SessionError {
        System.out.println("Revert");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (moneda.find("codigo", "URU")) {
            moneda.insertRow();
            moneda.setField("codigo", "URU");
            moneda.setField("nombre", "Uruguay");
            moneda.setField("cambio", BigDecimal.TEN);
        }

        IDataSet dataSet = new DataSet();
        dataSet.addDataObject("moneda", moneda);

        moneda.revert(dataSet);

        assertTrue("El registro que estaba en memoria para guardarse ahora esta vacio. ", moneda.getDataRowsChanged().isEmpty());

    }

    @Test
    public void BeforeOpen() throws Exception, NamingException, SessionError {
        System.out.println("Open Con Parametros");
        if (error != null) {
            System.out.println(error);
            return;
        }

        // DataEventsTest dataEvents = new DataEventsTest();
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);

        moneda.open("idmoneda desc", null, true, 10);
        System.out.println("datos " + moneda.getDataRows());

        if (!moneda.find("codigo", "URU")) {
            moneda.insertRow();
            moneda.setField("codigo", "URU");
            moneda.setField("nombre", "Uruguay");
            moneda.setField("cambio", BigDecimal.TEN);
            moneda.update(false);
        }

        if (moneda.find("codigo", "URU")) {

            moneda.deleteRow();
            moneda.update(false);

        }

    }

    @Test
    public void IdCompany() throws Exception, NamingException, SessionError {
        System.out.println("Id Company");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);

        System.out.println("Id Empresa ->" + moneda.getIdcompany());

    }

}
//
////Clase de los Data Events
//class DataEventsTest extends DataEvents {
//
//    int beforeOpen = 0;
//    int beforeDataFill = 0;
//    int afterDataFill = 0;
//    int afterOpen = 0;
//    int beforeRequery = 0;
//    int afterRequery = 0;
//    int onAllowOperation = 0;
//    int beforeRowMove = 0;
//    int afterRowMove = 0;
//    int beforeRefreshRow = 0;
//    int afterRefreshRow = 0;
//    int beforeSetfield = 0;
//    int afterSetfield = 0;
//    int beforeInsertRow = 0;
//    int afterInsertRow = 0;
//    int beforeDeleteRow = 0;
//    int afterDeleteRow = 0;
//    int beforeUpdate = 0;
//    int beforeCheckData = 0;
//    int afterCheckData = 0;
//    int afterUpdate = 0;
//    int beforeClose = 0;
//    int afterClose = 0;
//
//    @Override
//    public boolean onAllowOperation() {
//        assertTrue(getContext().getRow() != null);
//        onAllowOperation++;
//        return true;
//    }
//
//    @Override
//    public boolean beforeRowMove(IDataRow curRow) {
//        beforeRowMove++;
//        return true;
//    }
//
//    @Override
//    public void afterRowMove(IDataRow newRow) {
//        if (!getContext().isEof()) {
//            assertNotNull(newRow);
//        }
//        afterRowMove++;
//    }
//
//    /**
//     * Se ejecuta antes buscar los registros en la base de datos.
//     *
//     * @param order orden de la selección de datos.
//     * @param filter filtro de datos.
//     * @param readwrite si es lectura/escritura
//     * @param maxrows maxima cantidad de registros a recuperar.
//     */
//    @Override
//    public void beforeOpen(String order, String filter, boolean readwrite, int maxrows) {
//        assertFalse(getContext().isOpen());
//        beforeOpen++;
//    }
//
//    /**
//     * Se ejecuta antes de recuperar los datos de la base de datos.
//     */
//    @Override
//    public void beforeDataFill() {
//        assertFalse(getContext().isOpen());
//        beforeDataFill++;
//    }
//
//    /**
//     * Se ejecuta posterior a la recuperación de los registros de la base de
//     * datos.
//     */
//    @Override
//    public void afterDataFill() {
//        assertTrue(getContext().isOpen());
//        afterDataFill++;
//    }
//
//    /**
//     * Se ejecuta posterior a la recuperación de los registros de la base de
//     * datos.
//     *
//     * @param order orden de la selección de datos.
//     * @param filter filtro de datos.
//     * @param readwrite si es lectura/escritura.
//     * @param maxrows maxima cantidad de registros que deberian haberse
//     * recuperado.
//     */
//    @Override
//    public void afterOpen(String order, String filter, boolean readwrite, int maxrows) {
//        assertTrue(getContext().isOpen());
//        afterOpen++;
//    }
//
//    /**
//     * Se ejecuta antes de recuperar los registros de la base de datos.
//     */
//    @Override
//    public void beforeRequery() {
//        assertTrue(getContext().isOpen());
//        beforeRequery++;
//    }
//
//    /**
//     * Se ejecuta posterior a haberse recuperado los registros de la base de
//     * datos.
//     */
//    @Override
//    public void afterRequery() {
//        assertTrue(getContext().isOpen());
//        afterRequery++;
//    }
//
//    /**
//     * Se ejecuta antes de refrescar los datos de un registro de la base de
//     * datos.
//     *
//     * @param row registro a refrescar
//     */
//    @Override
//    public void beforeRefreshRow(IDataRow row) {
//        assertNotNull(row);
//        beforeRefreshRow++;
//    }
//
//    /**
//     * Se ejecuta posterior a refrescar un registro de la base de datos.
//     *
//     * @param row registro refrescado.
//     */
//    @Override
//    public void afterRefreshRow(IDataRow row) {
//        assertNotNull(row);
//        afterRefreshRow++;
//    }
//
//    /**
//     * Se ejecuta antes del metodo insertRow
//     *
//     * @param newRow fila a ser insertada
//     * @return verdadero o falso si se permite o no la inserción.
//     */
//    @Override
//    public boolean beforeInsertRow(IDataRow newRow) {
//        assertNotNull(newRow);
//        beforeInsertRow++;
//        return true;
//    }
//
//    /**
//     * Se ejecuta posterior al metodo insertRow
//     *
//     * @param row registro insertado
//     */
//    @Override
//    public void afterInsertRow(IDataRow row) {
//        assertNotNull(row);
//        afterInsertRow++;
//    }
//
//    /**
//     * Se ejecuta antes del metodo deleteRow
//     *
//     * @param row fila a ser marcada para eliminarse.
//     * @return verdadero o falso si es permitido o no la operación.
//     */
//    @Override
//    public boolean beforeDeleteRow(IDataRow row) {
//        assertNotNull(row);
//        beforeDeleteRow++;
//        return true;
//    }
//
//    /**
//     * Se ejecuta posterior al metodo deleteRow.
//     */
//    @Override
//    public void afterDeleteRow() {
//        assertTrue(getContext().getRecStatus() == IDataRow.DELETE);
//        afterDeleteRow++;
//    }
//
//    /**
//     * Se ejecuta antes del metodo setField.
//     *
//     * @param row registro
//     * @param fieldname nombre del campo
//     * @param oldValue valor anterior
//     * @param newValue nuevo valor.
//     * @return verdadero o falso si se permite la modificación del campo
//     */
//    @Override
//    public boolean beforeSetField(IDataRow row, String fieldname, Object oldValue, Object newValue) {
//        assertNotNull(row);
//        assertTrue(getContext().isFieldExist(fieldname));
//        beforeSetfield++;
//        return true;
//    }
//
//    /**
//     * Se ejecuta posterior al metodo setField
//     *
//     * @param row registro
//     * @param fieldname nombre del campo
//     * @param oldValue valor anterior
//     * @param newValue nuevo valor
//     * @return verdadero o falso si tuvo exito.
//     */
//    @Override
//    public boolean afterSetField(IDataRow row, String fieldname, Object oldValue, Object newValue) {
//        assertNotNull(row);
//        assertTrue(getContext().isFieldExist(fieldname));
//        afterSetfield++;
//        return true;
//    }
//
//    /**
//     * Se ejecuta antes del metodo update
//     *
//     * @param allRows si se va a procesar todos los registros
//     * @return verdadero o falso si se permite la ejecución de update.
//     */
//    @Override
//    public boolean beforeUpdate(boolean allRows) {
//        assertTrue(getContext().isOpen());
//        beforeUpdate++;
//        return true;
//    }
//
//    /**
//     * Se ejecuta antes del metodo checkData
//     *
//     * @param allRows si se esta procesando todos los registros modificados, o
//     * solo el actual.
//     */
//    @Override
//    public void beforeCheckData(boolean allRows) {
//        assertTrue(getContext().isOpen());
//        beforeCheckData++;
//    }
//
//    /**
//     * Se ejecuta posterior al metodo checkData
//     *
//     * @param allRows si se proceso todos los registros.
//     */
//    @Override
//    public void afterCheckData(boolean allRows) {
//        assertTrue(getContext().isOpen());
//        afterCheckData++;
//    }
//
//    /**
//     * Se ejecuta posterior al metodo update
//     *
//     * @param allRows se se proceso todos los registros modificados.
//     */
//    @Override
//    public void afterUpdate(boolean allRows) {
//        assertTrue(getContext().isOpen());
//        afterUpdate++;
//    }
//
//    /**
//     * Se ejecuta antes de cerrar el dataObject
//     */
//    @Override
//    public void beforeClose() {
//        assertTrue(getContext().isOpen());
//        beforeClose++;
//    }
//
//    /**
//     * Se ejecuta posterior a cerrar el dataObject.
//     */
//    @Override
//    public void afterClose() {
//        assertFalse(getContext().isOpen());
//        afterClose++;
//    }
//
//}

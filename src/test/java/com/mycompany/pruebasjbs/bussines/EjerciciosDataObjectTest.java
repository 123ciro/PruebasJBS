/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebasjbs.bussines;

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
import org.javabeanstack.model.tables.Moneda;
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

        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (!moneda.find("codigo", "$")) {
            moneda.insertRow();
            moneda.setField("codigo", "$");
            moneda.setField("nombre", "DOLARES");
            moneda.setField("cambio", BigDecimal.ONE);
            boolean result = moneda.update(false);

            assertTrue(result);

        }

        if (moneda.find("codigo", "$")) {
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
        moneda.requery();

        System.out.println("Set Filter--> " + moneda.getRow());
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
        moneda.requery();

        System.out.println("Order --> " + moneda.getDataRows());
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
        moneda.setFirstRow(0);
        moneda.getFirstRow();
        moneda.requery();

        System.out.println("Registros --> " + moneda.getDataRows());
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
        //en este caso no va a insertar el registro porque esta en false el ReadWrite
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
        //aca si permite ingresar los datos ya que el readwrite esta en true
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

        assertTrue("Los registros que estaban en memoria para guardarse ahora estan vacios. ", moneda.getDataRowsChanged().isEmpty());

    }

}

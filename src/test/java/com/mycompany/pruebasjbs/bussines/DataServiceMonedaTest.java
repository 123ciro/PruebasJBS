/*
* JavaBeanStack FrameWork
*
* Copyright (C) 2017 - 2018 Jorge Enciso
* Email: jorge.enciso.r@gmail.com
*        jenciso@javabeanstack.org
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 3 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
* MA 02110-1301  USA
 */
package com.mycompany.pruebasjbs.bussines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javabeanstack.data.IDataResult;
import org.javabeanstack.data.IDataRow;
import org.javabeanstack.data.IDataSet;
import org.javabeanstack.data.IGenericDAO;
import org.javabeanstack.data.model.DataSet;
import org.javabeanstack.datactrl.DataObject;
import org.javabeanstack.datactrl.IDataObject;
import org.javabeanstack.error.IErrorReg;
import org.javabeanstack.model.tables.Moneda;
import org.javabeanstack.services.IDataServiceRemote;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;


public class DataServiceMonedaTest extends TestClass {

    private static IDataServiceRemote dataService;

    public DataServiceMonedaTest() {
    }

    @BeforeClass
    public static void setUpClass2() {
        try {
            dataService
                    = (IDataServiceRemote) context.lookup(jndiProject + "DataService!org.javabeanstack.services.IDataServiceRemote");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test01Instance() throws Exception {
        System.out.println("1-DataService - TestInstance");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataServiceRemote instance
                = (IDataServiceRemote) context.lookup(jndiProject + "DataService!org.javabeanstack.services.IDataServiceRemote");
        assertNotNull(instance);
    }

    /**
     * Prueba control de los unique keys
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02CheckUnique1() throws Exception {
        System.out.println("2-DataService - CheckUnique1");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Moneda row = dataService.find(Moneda.class, sessionId).get(0);
        // Va a pasar la prueba porque es el mismo objeto
        assertTrue(dataService.checkUniqueKey("", row));
    }

    /**
     * Prueba control de los unique keys
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03CheckUnique2() throws Exception {
        System.out.println("3-DataService - TestCheckUnique2");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        List<Moneda> rows = dataService.find(Moneda.class, sessionId);
        Moneda row = rows.get(0);
        // Necesita del parametro sessionId para acceder a la unidad de persistencia adecuado
        assertTrue(dataService.checkUniqueKey(sessionId, row));
    }

    /**
     * Prueba unique key
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04CheckUniqueKey3() throws Exception {
        System.out.println("4-DataService - TestCheckUniqueKey3");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Moneda moneda = dataService.find(Moneda.class, sessionId).get(0);

        Map<String, IErrorReg> errors;

        moneda.setAction(IDataRow.MODIFICAR);
        errors = dataService.checkDataRow(sessionId, moneda);
        assertTrue(errors.isEmpty());

        moneda.setIdmoneda(0L);
        moneda.setAction(IDataRow.AGREGAR);
        errors = dataService.checkDataRow(sessionId, moneda);
        assertFalse(errors.isEmpty());

        moneda.setAction(IDataRow.BORRAR);
        errors = dataService.checkDataRow(sessionId, moneda);
        assertTrue(errors.isEmpty());

        moneda.setCodigo("xxx");
        moneda.setAction(IDataRow.AGREGAR);
        errors = dataService.checkDataRow(sessionId, moneda);
        assertTrue(errors.isEmpty());
    }

    /**
     * Chequeo de los foreignkeys
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test06CheckForeignKey2() throws Exception {
        System.out.println("6-DataService - TestCheckForeignkey2");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Map<String, IErrorReg> errors;
        Moneda moneda = dataService.find(Moneda.class, sessionId).get(0);
        moneda.setAction(IDataRow.MODIFICAR);
        // Necesita del parametro sessionId para acceder a la unidad de persistencia adecuado        
        errors = dataService.checkDataRow(sessionId, moneda);
        assertTrue(errors.isEmpty());
    }

    /**
     * Test of setListFieldCheck method, of class AbstractDataService.
     */
    @Test
    public void test09SetFieldsChecked() throws Exception {
        System.out.println("9-DataService - setFieldsChecked");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IMonedaSrv dataServiceMoneda
                = (IMonedaSrv) context.lookup(jndiProject + "MonedaSrv!org.javabeanstack.services.IMonedaSrvRemote");

        IGenericDAO dao = dataLink.getDao();
        dataLink.setDao(dataServiceMoneda);
        //Moneda
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (moneda.find("codigo", "ZZZ")) {
            moneda.refreshRow();
            moneda.deleteRow();
            IDataResult dataResult = dataServiceMoneda.save(sessionId, moneda.getRow());
            assertTrue(dataResult.getRowUpdated().isFieldChecked("codigo"));
            assertTrue(dataResult.getRowUpdated().isFieldChecked("nombre"));
        }
        moneda.insertRow();
        moneda.setField("codigo", "ZZZ");
        moneda.setField("nombre", "ZZZ BORRAR");

        IDataResult dataResult = dataServiceMoneda.save(sessionId, moneda.getRow());
        assertTrue(dataResult.isSuccessFul());
        assertTrue(dataResult.getRowUpdated().isRowChecked());
        assertTrue(dataResult.getRowUpdated().isFieldChecked("codigo"));
        assertTrue(dataResult.getRowUpdated().isFieldChecked("nombre"));

        moneda.close();
        moneda.open();
        if (moneda.find("codigo", "ZZZ")) {
            moneda.deleteRow();
            dataResult = dataServiceMoneda.save(sessionId, moneda.getRow());
            assertTrue(dataResult.isSuccessFul());
            assertTrue(dataResult.getRowUpdated().isFieldChecked("codigo"));
            assertTrue(dataResult.getRowUpdated().isFieldChecked("nombre"));
        }
        dataLink.setDao(dao);
    }

    /**
     * Test of findById method, of class AbstractDataService.
     */
    @Test
    public void test10FindById() throws Exception {
        System.out.println("10-DataService - findById");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Moneda moneda = dataService.findById(Moneda.class, sessionid, 1L);
        assertNotNull(moneda);

        moneda = dataService.findById(Moneda.class, sessionid, 0L);
        assertNull(moneda);
    }

    /**
     * Test of find method, of class AbstractDataService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test12Find() throws Exception {
        System.out.println("12-DataService - find");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        List<Moneda> monedas = dataService.find(Moneda.class, sessionid);
        assertTrue(monedas.size() > 0);

        String order = "code desc";
        String filter = "";
        monedas = dataService.find(Moneda.class, sessionid, order, filter, null);
        assertTrue(monedas.size() > 0);

        filter = "code = 'Administrador'";
        monedas = dataService.find(Moneda.class, sessionid, order, filter, null);
        assertTrue(monedas.size() == 1);

        filter = "code = :code";
        Map<String, Object> params = new HashMap();
        params.put("code", "Administrador");
        monedas = dataService.find(Moneda.class, sessionid, order, filter, params);
        assertTrue(monedas.size() == 1);

        monedas = dataService.find(Moneda.class, sessionid, null, "", null, 0, 4);
        assertTrue(monedas.size() == 4);
    }

    /**
     * Test of findByQuery method, of class AbstractDataService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test13FindByQuery() throws Exception {
        System.out.println("13-DataService - findByQuery");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Moneda moneda = dataService.findByQuery(sessionid, "select o from Moneda o where idmoneda = 1L", null);
        assertNotNull(moneda);

        moneda = dataService.findByQuery(sessionid, "select o from Moneda o where idmoneda = 0L", null);
        assertNull(moneda);

        Map<String, Object> params = new HashMap();
        params.put("idmoneda", 1L);
        moneda = dataService.findByQuery(sessionid, "select o from Moneda o where idmoneda = :idmoneda", params);
        assertNotNull(moneda);
    }

    /**
     * Test of findListByQuery method, of class AbstractDataService.
     */
    @Test
    public void test14FindListByQuery() throws Exception {
        System.out.println("14-DataService - findListByQuery");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        List<Moneda> monedas = dataService.findListByQuery(sessionid, "select o from Moneda o where idmoneda = 1L", null);
        assertNotNull(monedas);

        monedas = dataService.findListByQuery(sessionid, "select o from Moneda o where idmoneda = 0L", null);
        assertTrue(monedas.isEmpty());

        Map<String, Object> params = new HashMap();
        params.put("idmoneda", 1L);
        monedas = dataService.findListByQuery(sessionid, "select o from Moneda o where idmoneda = :idmoneda", params);
        assertNotNull(monedas);
    }

    /**
     * Test of findByNativeQuery method, of class AbstractDataService.
     */
    @Test
    public void test16FindByNativeQuery() throws Exception {
        System.out.println("16-DataService - findByNativeQuery");
        //No hubo conexión con el servidor de aplicaciones
        
        if (error != null) {
            System.out.println(error);
            return;
        }
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sqlSentence = "select * from {schema}.moneda where idmoneda > :id";
        Map<String, Object> params = new HashMap();
        params.put("id", 144);
        List<Object> query1 = dataService.findByNativeQuery(sessionId, sqlSentence, params);
        
        assertTrue(!query1.isEmpty());

        // Un grupo de registros first, max
        query1 = dataService.findByNativeQuery(sessionId, sqlSentence, params, 0, 10);
        assertTrue(!query1.isEmpty());
    }

    /**
     * Test of getDataRows method, of class AbstractDataService.
     */
    @Test
    public void get20DataRows() throws Exception {
        System.out.println("20-DataService - getDataRows");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        List<Moneda> monedas = dataService.getDataRows(sessionId, Moneda.class, "", "", null, 0, 1000);
        System.out.println("valores dentro de moneda  ->" + dataService.getDataRows(sessionId, Moneda.class, "", "", null, 0, 1000));
        assertFalse(monedas.isEmpty());
    }

    /**
     * Test of update method, of class AbstractDataService.
     */
    @Test
    public void test26Update_String_List() throws Exception {
        System.out.println("26-DataService - update");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        //Moneda
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        if (moneda.find("codigo", "ZZZ")) {
            moneda.refreshRow();
            moneda.deleteRow();
            moneda.checkDataRow();
            IDataResult dataResult = dataService.update(sessionId, moneda.getDataRows());
            assertTrue(dataResult.isSuccessFul());
        }
        moneda.insertRow();
        moneda.setField("codigo", "ZZZ");
        moneda.setField("nombre", "ZZZ BORRAR");
        moneda.checkDataRow();

        IDataResult dataResult = dataService.update(sessionId, moneda.getDataRows());
        assertTrue(dataResult.isSuccessFul());

        moneda.close();
        moneda.open();
        if (moneda.find("codigo", "ZZZ")) {
            moneda.deleteRow();
            moneda.checkDataRow();
            dataResult = dataService.update(sessionId, moneda.getDataRows());
            assertTrue(dataResult.isSuccessFul());
        }
    }

    /**
     * Test of update method, of class AbstractDataService.
     */
    @Test
    public void test27Update_String_IDataSet() throws Exception {
        System.out.println("27-DataService - update");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IGenericDAO dao = dataLink.getDao();
        //Cambiar dao por dataService.
        dataLink.setDao(dataService);
        //Moneda
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
        IDataSet dataSet = new DataSet();
        dataSet.addDataObject("moneda", moneda);

        if (moneda.find("codigo", "ZZZ")) {
            moneda.refreshRow();
            moneda.deleteRow();
            assertTrue(moneda.update(dataSet));
        }
        moneda.insertRow();
        moneda.setField("codigo", "ZZZ");
        moneda.setField("nombre", "ZZZ BORRAR");
        dataSet = new DataSet();
        dataSet.addDataObject("moneda", moneda);
        assertTrue(moneda.update(dataSet));

        moneda.close();
        moneda.open();
        if (moneda.find("codigo", "ZZZ")) {
            moneda.deleteRow();
            moneda.checkDataRow();
            dataSet = new DataSet();
            dataSet.addDataObject("moneda", moneda);
            assertTrue(moneda.update(dataSet));
        }
        //Devolver dataLink a valores por defecto
        dataLink.setDao(dao);
    }

}

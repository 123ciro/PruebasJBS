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

import com.mycompany.pruebasjbs.model.AppUser;
import com.mycompany.pruebasjbs.tables.Moneda;
import java.math.BigDecimal;
import java.util.List;
import org.javabeanstack.data.IDataResult;
import org.javabeanstack.data.IDataSet;
import org.javabeanstack.data.IGenericDAO;
import org.javabeanstack.data.model.DataSet;
import org.javabeanstack.data.services.IDataServiceRemote;
import org.javabeanstack.datactrl.DataObject;
import org.javabeanstack.datactrl.IDataObject;
import org.javabeanstack.util.Strings;
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
                    = (IDataServiceRemote) context.lookup(jndiProject + "DataService!org.javabeanstack.data.services.IDataServiceRemote");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test01Instance1() throws Exception {
        System.out.println("1-DataService - TestInstance");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IDataServiceRemote instance
                = (IDataServiceRemote) context.lookup(jndiProject + "DataService!org.javabeanstack.data.services.IDataServiceRemote");
        assertNotNull(instance);
    }

    @Test
    public void test10FindById() throws Exception {
        System.out.println("Find By Id");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        //   String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Moneda moneda = dataService.findById(Moneda.class, sessionId, 244L);
        System.out.println("valores --> " + dataService.findById(Moneda.class, sessionId, 244L));
        assertNotNull(moneda);

        moneda = dataService.findById(Moneda.class, sessionId, 0L);
        assertNull(moneda);
    }

    @Test
    public void DataServicePersist() throws Exception {
        System.out.println("Test persist");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        //Agregar
        Moneda moneda = new Moneda();
        moneda.setCodigo("BOL");
        moneda.setNombre("Bolivares");
        moneda.setIdempresa(Long.valueOf(41));
        moneda.setCambio(BigDecimal.ONE);
        IDataResult dataResult = dataService.persist(sessionId, moneda);
        assertNotNull(dataResult);

        //Buscar la moneda
        List<Moneda> monedas = dataService.findListByQuery(sessionId, "select o from Moneda o where codigo = 'BOL'", null);
        //eliminar
        dataService.remove(sessionId, monedas.get(0));
    }

//    @Test
//    public void test11FindByUk() throws Exception {
//        System.out.println("FindByUk");
//        //No hubo conexión con el servidor de aplicaciones
//        String sessionid = null;
//        if (error != null) {
//            System.out.println(error);
//            return;
//        }
//        AppUser user = new AppUser();
//        user.setCode("j");
//        user = dataService.findByUk(sessionid, user);
//        System.out.println("valores" + user);
//        assertNotNull(user);
//
//    }
    @Test
    public void TestInsertandDeleteMoneda() throws Exception {
        System.out.println("Insert and Delete Moneda");
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
        if (!moneda.find("codigo", "PER")) {
            moneda.insertRow();
            moneda.setField("codigo", "PER");
            moneda.setField("nombre", "PERU");
            moneda.setField("cambio", BigDecimal.TEN);
            dataSet = new DataSet();
            dataSet.addDataObject("moneda", moneda);
            assertTrue(moneda.update(dataSet));
            moneda.close();
        }
        moneda.open();
        if (moneda.find("codigo", "PER")) {
            moneda.deleteRow();
            moneda.checkDataRow();
            dataSet = new DataSet();
            dataSet.addDataObject("moneda", moneda);
            assertTrue(moneda.update(dataSet));
            moneda.close();
        }
        //Devolver dataLink a valores por defecto
        dataLink.setDao(dao);
    }

    @Test
    public void TestInsertandDeleteUser() throws Exception {
        System.out.println("Insert and Delete User");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IGenericDAO dao = dataLinkCat.getDao();
        //Cambiar dao por dataService.
        dataLinkCat.setDao(dataService);
        //Moneda
        IDataObject user = new DataObject(AppUser.class, null, dataLinkCat, null);
        user.open();
        String clave="contraseñaparaelingreso";
        String codigo ="ciroferra7";
        String nombre ="Ciro Enrique Ferrario Chavez";
        String añadir= Strings.encode64(nombre+codigo);
        String clave64 = Strings.encode64(clave);
        
        IDataSet dataSet = new DataSet();
        dataSet.addDataObject("user", user);
        if (!user.find("code", "LUISI")) {
            user.insertRow();
            user.setField("code", "LUISI");
            user.setField("fullname", "LUISITO");
            user.setField("pass", clave64+añadir);
            user.setField("idcompany", Long.valueOf(2));
            dataSet = new DataSet();
            dataSet.addDataObject("user", user);
            assertTrue(user.update(dataSet));
            user.close();
        }
        user.open();
        if (user.find("code", "LUISI")) {
            user.deleteRow();
            user.checkDataRow();
            dataSet = new DataSet();
            dataSet.addDataObject("user", user);
            assertTrue(user.update(dataSet));
            user.close();
        }
        //Devolver dataLink a valores por defecto
        dataLinkCat.setDao(dao);
    }

    
    //TODO: ERROR QUE ME SALE --> datos.moneda is not mapped [select count(*) FROM datos.moneda o]
//    @Test
//    public void CantidadMoneda() throws Exception {
//        System.out.println("Cantidad Moneda");
//        // Cuando sessionId es null solo se puede acceder al schema catalogo
//        String sessionid = null;
//        //No hubo conexión con el servidor de aplicaciones
//        if (error != null) {
//            System.out.println(error);
//            return;
//        }
//        Long rec = dataService.getCount(sessionId, "select o FROM {schema}.moneda o", null);
//    //    System.out.println("valor ->" + dataService.getCount(sessionId, "select o FROM moneda o", null));
//        assertTrue(rec > 0L);
//    }
    
    @Test
    public void CantidadUser() throws Exception {
        System.out.println("Cantidad User");
        // Cuando sessionId es null solo se puede acceder al schema catalogo
        String sessionid = null;
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        Long rec = dataService.getCount(sessionid, "select o FROM AppUser o", null);
        System.out.println("valor ->" + dataService.getCount(sessionid, "select o FROM AppUser o", null));
        assertTrue(rec > 0L);
    }

    @Test
    public void EsquemaCatalodo() throws Exception {
        System.out.println("EsquemaCatalogo");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        //trae el esquema de catalogo.
        System.out.println(dataService.getSchema("PU1"));

        String expResult = dataService.getSchema("PU1");
        assertNotNull(expResult);
    }

    @Test
    public void EsquemaDatos() throws Exception {
        System.out.println("EsquemaDatos");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        //trae el esquema de datos.
        System.out.println(dataService.getSchema("PU2"));

        String expResult = dataService.getSchema("PU2");
        assertNotNull(expResult);
    }

    @Test
    public void SesionUsuario() throws Exception {
        System.out.println("Datos de la Sesión del Usuario");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        System.out.println("valores->" + dataService.getUserSession(sessionId));
        assertNotNull(dataService.getUserSession(sessionId));
    }

    @Test
    public void FindMonedas() throws Exception {
        System.out.println("FindMonedas");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;
        String order = "idmoneda desc";
        String filter = "";
        if (error != null) {
            System.out.println(error);
            return;
        }
        System.out.println(dataService.find(Moneda.class, sessionId, order, filter, null));
    }

    @Test
    public void FindUser() throws Exception {
        System.out.println("FindUser");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;
        String order = "idusuario desc";
        String filter = "";
        if (error != null) {
            System.out.println(error);
            return;
        }
        System.out.println(dataService.find(AppUser.class, sessionid, order, filter, null));
        // assertNotNull(dataService.find(AppUser.class, sessionid, order, filter, null));
    }

    @Test
    public void FindByQueryMonedas() throws Exception {
        System.out.println("FindByQueryMonedas");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;

        if (error != null) {
            System.out.println(error);
            return;
        }

        Moneda moneda = dataService.findByQuery(sessionId, "select o from Moneda o where idmoneda = 244L", null);
        System.out.println("valor moneda -->" + moneda);
        assertNotNull(moneda);

    }

    @Test
    public void FindByQueryUser() throws Exception {
        System.out.println("FindByQueryUser");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;

        if (error != null) {
            System.out.println(error);
            return;
        }
        AppUser user = dataService.findByQuery(sessionid, "select o from AppUser o where iduser = 1L", null);
        System.out.println("valor usuario -->" + user);
        assertNotNull(user);
    }

    @Test
    public void FindByNativeQueryMoneda() throws Exception {
        System.out.println("FindByNativeQueryMoneda");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;

        if (error != null) {
            System.out.println(error);
            return;
        }

        String sql = "select * from {schema}.moneda where idmoneda > 0";

        List<Object> query1 = dataService.findByNativeQuery(sessionId, sql, null);

        System.out.println("valor monedas -->" + query1);
        assertNotNull(query1);
    }

    @Test
    public void FindByNativeQueryUser() throws Exception {
        System.out.println("FindByNativeQueryUser");
        //No hubo conexión con el servidor de aplicaciones
        String sessionid = null;

        if (error != null) {
            System.out.println(error);
            return;
        }

        String sql = "select * from catalogo.Usuario where idusuario > 0";

        //para el getCount
        String sql1 = "select o from AppUser o where idusuario > 0";

        List<Object> query1 = dataService.findByNativeQuery(sessionid, sql, null);
        System.out.println("cantidad datos " + dataService.getCount(sessionid, sql1, null));
        System.out.println("valor usuarios --> \n" + query1 );
        assertNotNull(query1);
    }

}

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

import com.mycompany.pruebasjbs.tables.Moneda;
import org.javabeanstack.data.IDataRow;
import java.math.BigDecimal;
import org.javabeanstack.data.IGenericDAO;
import org.javabeanstack.data.services.IDataServiceRemote;
import org.javabeanstack.datactrl.DataObject;
import org.javabeanstack.datactrl.IDataObject;
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
        System.out.println("valores --> " + dataService.findById(Moneda.class, sessionId, 244L) );
        assertNotNull(moneda);

        moneda = dataService.findById(Moneda.class, sessionId, 0L);
        assertNull(moneda);
    }

    
    @Test
    public void DataServicePersist() throws Exception {
        System.out.println("Test update");
        // Cuando sessionId es null solo se puede acceder al schema catalogo

        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }

        Moneda moneda = new Moneda();
        moneda.setCodigo("10");
        moneda.setNombre("DOLARES AMERICANOS");
        moneda.setCambio(BigDecimal.TEN);
        moneda.setAction(IDataRow.INSERT);
        //Si el rowchecked esta en false, va a salir error.
        moneda.setRowChecked(true);
        dataService.update(sessionId, moneda);
    }
    
    
      @Test
    public void test25Update_String_IDataObject() throws Exception{
        System.out.println("25-DataService - update");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        IMonedaSrv dataServiceMoneda = 
                (IMonedaSrv) context.lookup(jndiProject+"MonedaSrv!org.pruebasciro.business.IRegionSrvRemote");
        
        IGenericDAO dao = dataLink.getDao();
        //Cambiar dao por dataService.
        dataLink.setDao(dataServiceMoneda);
        //Region
        IDataObject moneda = new DataObject(Moneda.class, null, dataLink, null);
        moneda.open();
//        if (region.find("codigo", "ZZZ")){
//            region.refreshRow();
//            region.deleteRow();
//            assertTrue(region.update(false));
//        }
        moneda.insertRow();
        moneda.setField("codigo", "ZZZ");
        moneda.setField("nombre", "ZZZ BORRAR");
        assertTrue(moneda.update(false));        

        moneda.close();
//        region.open();
//        if (region.find("codigo", "ZZZ")){
//            region.deleteRow();
//            assertTrue(region.update(false));
//        }
        
//        //No corresponde el tipo de dato 
//        AppTablesRelation relation = new AppTablesRelation();
//        relation.setAction(IDataRow.INSERT);
//        assertFalse((dataServiceRegion.checkDataRow(sessionId, relation)).isEmpty());
//        //Devolver dataLink a valores por defecto
//        dataLink.setDao(dao);
    }
    
    

}

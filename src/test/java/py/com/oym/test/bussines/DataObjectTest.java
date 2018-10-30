/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.oym.test.bussines;

import java.math.BigDecimal;
import javax.naming.NamingException;
import org.javabeanstack.data.IDataSet;
import org.javabeanstack.data.IGenericDAORemote;
import org.javabeanstack.data.model.DataSet;
import org.javabeanstack.datactrl.DataObject;
import org.javabeanstack.datactrl.IDataObject;
import org.javabeanstack.exceptions.SessionError;
import org.javabeanstack.model.tables.Moneda;
import org.javabeanstack.model.tables.Pais;
import org.junit.Test;
import static org.junit.Assert.*;
import org.javabeanstack.model.tables.Region;

public class DataObjectTest extends TestClass {

    private static IGenericDAORemote dao;

    @Test
    public void testPersistRegion() throws Exception, NamingException, SessionError {
        System.out.println("Test Region add");
        if (error != null) {
            System.out.println(error);
            return;
        }

        IDataObject region = new DataObject(Region.class, null, dataLink, null);
        region.open();
        if (!region.find("codigo", "AFR")) {
            region.insertRow();
            region.setField("codigo", "AFR");
            region.setField("nombre", "Africa");

            boolean result = region.update(false);

            assertTrue(result);

        } else {
            System.out.println("No pasa nada");
        }

    }

    @Test
    public void testPersistMoneda() throws Exception, NamingException, SessionError {
        System.out.println("Test Moneda add");
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

        } else {
            System.out.println("No pasa nada");

        }

    }

    @Test
    public void test3AddDeleteDataRegionPais() throws NamingException, SessionError, Exception {
        System.out.println("test3AddDeleteDataRegionPais");
        //No hubo conexión con el servidor de aplicaciones
        if (error != null) {
            System.out.println(error);
            return;
        }
        //Region
        IDataObject region = new DataObject(Region.class, null, dataLink, null);
        region.open();
        if (!region.find("codigo", "PCF")) {
            region.insertRow();
            region.setField("codigo", "PCF");
            region.setField("nombre", "Pacífico");
        }
        //Pais
        IDataObject pais = new DataObject(Pais.class, null, dataLink, null);
        pais.open();
        if (!pais.find("codigo", "124")) {
            pais.insertRow();
            pais.setField("codigo", "124");
            pais.setField("nombre", "Perú");
            pais.setField("region", region.getRow());
        }
        IDataSet dataSet = new DataSet();
        dataSet.addDataObject("region", region);
        dataSet.addDataObject("pais", pais);

        boolean resultado = pais.update(dataSet);
        System.out.println(resultado);
        assertTrue(resultado);

//        //Delete
//        if (pais.find("codigo", "124")) {
//            pais.deleteRow();
//            resultado = pais.update(false);
//            if (!resultado) {
//                System.out.println(pais.getErrorMsg(true));
//            }
//        }
//        if (region.find("codigo", "PCF")) {
//            region.deleteRow();
//            resultado = region.update(false);
//            if (!resultado) {
//                System.out.println(region.getErrorMsg(true));
//            }
//        }
    }

}

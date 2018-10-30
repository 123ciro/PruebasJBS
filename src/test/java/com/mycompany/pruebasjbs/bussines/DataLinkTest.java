/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebasjbs.bussines;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.javabeanstack.data.IDataResult;
import org.javabeanstack.data.IDataRow;
import org.javabeanstack.data.IGenericDAORemote;
import org.junit.Test;
import static org.junit.Assert.*;
import org.javabeanstack.model.tables.Moneda;

public class DataLinkTest extends TestClass {

    private static IGenericDAORemote dao;

    public DataLinkTest() {
    }

    @Test
    public void monedas() throws Exception {
        System.out.println("Test Moneda Persist");
        if (error != null) {
            System.out.println(error);
            return;
        }

        Moneda moneda = new Moneda();

        moneda.setCodigo("LIB");
        moneda.setNombre("Libras Esterlinas");
        moneda.setCambio(BigDecimal.valueOf(5L));
        moneda.setIdempresa(Long.parseLong("41"));
        moneda.setObservacion("Moneda para hacer transacciones con Inglaterra");
        IDataResult dataResult = dataLink.persist(moneda);
        Moneda monedaResult = dataResult.getRowUpdated();
        System.out.println(dataResult.getErrorMsg());
        assertTrue(dataResult.isSuccessFul());
        dataLink.remove(monedaResult);
    }

    @Test
    public void listas_monedas() throws Exception {
        System.out.println("Lista de Monedas");
        if (error != null) {
            System.out.println(error);
            return;
        }
        String codigo_aux;
        String nombre_aux;
        BigDecimal cambios_aux;
        List<Moneda> monedas = new ArrayList();
        String[] v_codigo = {"AUS", "ARG", "RUS", "BOL"};
        String[] v_nombre = {"AUSTRIA", "ARGENTINA", "RUSIA", "BOLIVIA"};
        BigDecimal[] v_cambios = {BigDecimal.valueOf(3.5), BigDecimal.valueOf(1.2), BigDecimal.valueOf(6.5), BigDecimal.valueOf(0.5)};
        for (int i = 0; i < 4; i++) {
            codigo_aux = v_codigo[i];
            nombre_aux = v_nombre[i];
            cambios_aux = v_cambios[i];
            Moneda moneda = new Moneda();
            moneda.setCodigo(codigo_aux);
            moneda.setNombre(nombre_aux);
            moneda.setCambio(cambios_aux);
            moneda.setIdempresa(41L);
            monedas.add(moneda);
        }
        IDataResult dataResult = dataLink.persist(monedas);
        List<IDataRow> monedasResult = dataResult.getRowsUpdated();
        System.out.println(dataResult.getErrorMsg());
        assertTrue(dataResult.isSuccessFul());
        dataLink.remove(monedasResult);
    }

    @Test
    public void Merge() throws Exception {
        System.out.println("Merge");
        if (error != null) {
            System.out.println(error);
            return;
        }
        //INSERTAR
        Moneda moneda = new Moneda();
        moneda.setCodigo("LIB");
        moneda.setNombre("Libras Esterlinas");
        moneda.setCambio(BigDecimal.valueOf(5L));
        moneda.setIdempresa(41L);
        dataLink.persist(moneda);
        //MERGE
        moneda = dataLink.findByQuery("select o from Moneda o where codigo = 'LIB'", null);
        moneda.setNombre("LIBRAS ESTERLINAS");
        moneda.setCambio(BigDecimal.TEN);
        IDataResult dataResult = dataLink.merge(moneda);
        Moneda monedaResult = dataResult.getRowUpdated();
        System.out.println(dataResult.getErrorMsg());
        assertTrue(dataResult.isSuccessFul());
        dataLink.remove(monedaResult);
    }

    @Test
    public void monedasaction() throws Exception {
        System.out.println("Test INSERTAR,MODIFICAR,BORRAR");
        if (error != null) {
            System.out.println(error);
            return;
        }

        //insertar
        Moneda moneda = new Moneda();
        moneda.setCodigo("LIB");
        moneda.setNombre("Libras Esterlinas");
        moneda.setCambio(BigDecimal.valueOf(5L));
        moneda.setIdempresa(Long.parseLong("41"));
        moneda.setObservacion("Moneda para hacer transacciones con Inglaterra");
        moneda.setAction(IDataRow.AGREGAR);
        dataLink.update(moneda);

        //actualizar
        moneda = dataLink.findByQuery("select o from Moneda o where codigo = 'LIB'", null);
        moneda.setNombre("LIBRAS ESTERLINAS");
        moneda.setCambio(BigDecimal.TEN);
        moneda.setAction(IDataRow.MODIFICAR);
        dataLink.update(moneda);

        //Borrar
        moneda = dataLink.findByQuery("select o from Moneda o where codigo = 'LIB'", null);
        moneda.setAction(IDataRow.BORRAR);
        dataLink.update(moneda);

    }

    @Test
    public void monedasactionlist() throws Exception {
        System.out.println("Test INSERTAR LISTAS DE MONEDAS");
        if (error != null) {
            System.out.println(error);
            return;
        }
        
        //  AGREGAR
        String codigo_aux;
        String nombre_aux;
        BigDecimal cambios_aux;
        List<Moneda> monedas = new ArrayList();
        String[] v_codigo = {"AUS", "ARG", "RUS", "BOL"};
        String[] v_nombre = {"AUSTRIA", "ARGENTINA", "RUSIA", "BOLIVIA"};
        BigDecimal[] v_cambios = {BigDecimal.valueOf(3.5), BigDecimal.valueOf(1.2), BigDecimal.valueOf(6.5), BigDecimal.valueOf(0.5)};
        for (int i = 0; i < 4; i++) {
            codigo_aux = v_codigo[i];
            nombre_aux = v_nombre[i];
            cambios_aux = v_cambios[i];
            Moneda moneda = new Moneda();
            moneda.setCodigo(codigo_aux);
            moneda.setNombre(nombre_aux);
            moneda.setCambio(cambios_aux);
            moneda.setIdempresa(41L);
            moneda.setAction(IDataRow.INSERT);
            monedas.add(moneda);

        }
        dataLink.update(monedas);

        //ELIMINAR
        for (int i = 0; i < 4; i++) {
            monedas.get(i).setAction(IDataRow.BORRAR);

        }

        dataLink.update(monedas);
        IDataResult dataResult = dataLink.update(monedas);
        System.out.println(dataResult.getErrorMsg());

    }

}

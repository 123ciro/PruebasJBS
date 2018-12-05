package com.mycompany.pruebasjbs.bussines;

import com.mycompany.pruebasjbs.tables.Moneda;
import org.javabeanstack.annotation.CheckMethod;
import org.javabeanstack.data.IDataRow;
import org.javabeanstack.error.IErrorReg;

import org.javabeanstack.data.services.IDataService;

/**
 *
 * @author Jorge Enciso
 */
public interface IMonedaSrv extends IDataService{

    @CheckMethod(fieldName = "codigo", action = {IDataRow.AGREGAR, IDataRow.MODIFICAR, IDataRow.BORRAR})
    IErrorReg checkCodigo(String sessionId, Moneda row);

    @CheckMethod(fieldName = "codigo", action = {IDataRow.BORRAR})
    IErrorReg checkCodigo2(String sessionId, Moneda row);

    @CheckMethod(fieldName = "nombre",  action = {IDataRow.AGREGAR, IDataRow.MODIFICAR})
    IErrorReg checkNombre(String sessionId, Moneda row);
    
    @CheckMethod(fieldName = "cambio",  action = {IDataRow.AGREGAR, IDataRow.MODIFICAR})
    IErrorReg checkCambio(String sessionId, Moneda row);
    
}

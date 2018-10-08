/*
* JavaBeanStack FrameWork
*
* Copyright (C) 2017 Jorge Enciso
* Email: jorge.enciso.r@gmail.com
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
package com.mycompany.pruebasjbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Funciones wrapper que facilitan el manejo de variables Date
 *
 * @author Jorge Enciso
 */
public class Dates {

    private Dates() {
    }

    /**
     * Convierte una cadena a una fecha
     *
     * @param dateString
     * @return un dato fecha resultante de los parámetros introducidos.
     */
    public static Date toDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return toDate(dateString, formatter);
    }

    /**
     * Convierte una cadena a una fecha
     *
     * @param dateString
     * @param format ejemplo dd/mm/yyyy
     * @return un dato fecha resultante de los parámetros introducidos.
     */
    public static Date toDate(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return toDate(dateString, formatter);
    }

    /**
     * Convierte una fecha hora a una fecha
     *
     * @param date
     * @return un dato fecha resultante de los parámetros introducidos.
     */
    public static Date toDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Convierte una cadena a una fecha
     *
     * @param dateString
     * @param formatter
     * @return un dato fecha resultante de los parámetros introducidos.
     */
    public static Date toDate(String dateString, SimpleDateFormat formatter) {
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(Fn.class).error(ex.getMessage());
        }
        return date;
    }

    /**
     * Convierte una fecha a variable de cadena, según formato deseado.
     *
     * @param date
     * @param formater
     * @return string con el formato deseado.
     */
    public static String toString(Date date, SimpleDateFormat formater) {
        return formater.format(date);
    }

    /**
     * Convierte una fecha a variable de cadena, según formato deseado.
     *
     * @param date
     * @param format ejemplo dd/mm/yyyy
     * @return string con el formato deseado.
     */
    public static String toString(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * Devuelve una variable Date con la fecha y hora del momento
     *
     * @return fecha y hora de ahora.
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Devuelve una variable Date con el valor de la fecha del día
     *
     * @return fecha de hoy
     */
    public static Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Suma un periodo a una fecha
     *
     * @param date fecha
     * @param quantity periodo
     * @param interval tipo de intervalo (dias, segundos, meses) ejemplo
     * Calendar.DAY_OF_YEAR
     * @return fecha resultado de la suma del periodo
     */
    public static Date sum(Date date, int quantity, int interval) {
        if (quantity == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(interval, quantity);
        return calendar.getTime();
    }

    /**
     * Suma días a una fecha
     *
     * @param date fecha
     * @param days dias a sumar
     * @return fecha resultante de la suma de los días
     */
    public static Date sumDays(Date date, int days) {
        if (days == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    
       /**
     * Suma Horas a una fecha
     *
     * @param date fecha
     * @param hours horas a sumar
     * @return fecha resultante de la suma de las horas
     */
    public static Date sumHours(Date date, int hours) {
        if (hours == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * Suma minutos a una fecha
     *
     * @param date fecha
     * @param minutes minutos a sumar
     * @return fecha resultante de la suma de los minutos
     */
    public static Date sumMinutes(Date date, int minutes) {
        if (minutes == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }


    /**
     * Suma segundos a una fecha
     *
     * @param date fecha
     * @param seconds segundos
     * @return fecha resultante de la suma de los segundos.
     */
    public static Date sumSeconds(Date date, int seconds) {
        if (seconds == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Devuelve el ultimo momento de una fecha
     *
     * @param date fecha
     * @return ultimo momento de una fecha ejemplo 31/12/2017 23:59:59
     */
    public static Date getLastTimeOfDay(Date date) {
        date = Dates.sum(date, 1, Calendar.DAY_OF_YEAR);
        date = Dates.sum(date, -1, Calendar.SECOND);
        return date;
    }
}

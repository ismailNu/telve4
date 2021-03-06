/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.query.columns;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Tarih tipi alanlar için kolon oluşturur.
 * 
 * @author Hakan Uygun
 * @param <E> işlenecek olan Entity sınıfı
 */
public class DateColumn<E> extends Column<E>{

    public DateColumn(SingularAttribute<? super E, ? extends Date> attribute, String labelKey) {
        super(attribute, labelKey);
    }

    @Override
    public String getTemplate() {
        return "dateColumn";
    }
    
}

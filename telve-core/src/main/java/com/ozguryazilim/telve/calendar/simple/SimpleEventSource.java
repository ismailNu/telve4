/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.calendar.simple;

import com.ozguryazilim.telve.calendar.AbtsractCalendarEventSource;
import com.ozguryazilim.telve.calendar.CalendarEventSource;
import com.ozguryazilim.telve.entities.CalendarEvent;
import com.ozguryazilim.telve.view.Pages;
import java.io.Serializable;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.picketlink.Identity;

/**
 * SimpleEvent için kaynak control sınıfı.
 * 
 * @author Hakan Uygun
 */
@CalendarEventSource( hasDialog = true, creatable = true, dialogPage = Pages.Calendar.SimpleEventDialog.class, styleClass = "tlv-yellow" )
public class SimpleEventSource extends AbtsractCalendarEventSource<SimpleEvent> implements Serializable{

    @Inject
    private Identity identity;
    
    @Override
    public void createNewModel() {
        CalendarEvent ce = new CalendarEvent();
        ce.setSourceName("simpleEventSource");
        ce.setActor(identity.getAccount().getId());
        
        DateTime dt = new DateTime();
        
        ce.setStartDate(dt.toDate());
        ce.setEndDate(dt.plusHours(1).toDate());
        
        setCalendarEvent(ce);
        
        setData(new SimpleEvent());
    }
    
}

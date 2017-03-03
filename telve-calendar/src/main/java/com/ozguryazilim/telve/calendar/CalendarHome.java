/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.calendar;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calendar sistemi UI control sınıfı
 * @author Hakan Uygun
 */
@Named
@WindowScoped 
public class CalendarHome implements Serializable{
    private static final Logger LOG = LoggerFactory.getLogger(CalendarHome.class);
    
    @Inject
    private CalendarFilterModel filterModel;
    
    /**
     * GUI'den gelen newEvent komutunu 
     */
    public void newEvent(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String eventSource = params.get("eventSource");
        
        CalendarEventSource cec = (CalendarEventSource) BeanProvider.getContextualReference(eventSource);
        cec.createEvent();
    }
    
    public void editEvent(){
        //CalendarEventMetadata ce = (CalendarEventMetadata)selectedEvent.getData();
        
        //CalendarEventSource cec = (CalendarEventSource) BeanProvider.getContextualReference(ce.getSourceName());
        //cec.process( ce );
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
        //selectedEvent = (ScheduleEvent) selectEvent.getObject();
        //CalendarEventMetadata ce = (CalendarEventMetadata)selectedEvent.getData();
        
        //CalendarEventSource cec = (CalendarEventSource) BeanProvider.getContextualReference(ce.getSourceName());
        //cec.process( ce );
    }
    
    public void onViewChange(SelectEvent selectEvent) {
        String s  = (String) selectEvent.getObject();
        System.out.println(s);
    }
    
}

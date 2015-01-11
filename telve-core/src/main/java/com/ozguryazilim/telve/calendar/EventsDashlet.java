/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.calendar;

import com.ozguryazilim.telve.dashboard.AbstractDashlet;
import com.ozguryazilim.telve.dashboard.Dashlet;
import com.ozguryazilim.telve.dashboard.DashletCapability;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import org.primefaces.model.ScheduleEvent;

/**
 * Yakın tarihte yapılması gereken olayları sunar.
 * 
 * @author Hakan Uygun
 */
@Dashlet(capability = {DashletCapability.canHide, DashletCapability.canMinimize, DashletCapability.canRefresh})
public class EventsDashlet extends AbstractDashlet{
    
    @Inject
    private DefaultCaledarEventStore eventStore;
    
    private LocalDate date;
    List<ScheduleEvent> todayEvents;
    List<ScheduleEvent> tomorrowEvents;
    List<ScheduleEvent> soonEvents;

    @Override
    public void load() {
        date = new LocalDate();
    }

    @Override
    public void refresh() {
        todayEvents = null;
        tomorrowEvents = null;
        soonEvents = null;
    }

    
    
    public Date getDate() {
        return date.toDate();
    }

    public List<ScheduleEvent> getTodayEvents(){
        if( todayEvents == null ){
            todayEvents = eventStore.getEvents(date.toDate(), date.plusDays(1).toDate());
        }
        return todayEvents;
    }
    
    public List<ScheduleEvent> getTomorrowEvents(){
        if( tomorrowEvents == null ){
            tomorrowEvents = eventStore.getEvents(date.plusDays(1).toDate(), date.plusDays(2).toDate());
        }
        return tomorrowEvents;
    }
    
    public List<ScheduleEvent> getSoonEvents(){
        if( soonEvents == null ){
            soonEvents = eventStore.getEvents(date.plusDays(2).toDate(), date.plusDays(7).toDate());
        }
        return soonEvents;
    }
    
    public void nextDay(){
        date = date.plusDays(1);
        refresh();
    }
    
    public void prevDay(){
        date = date.minusDays(1);
        refresh();
    }
    
    public void toDay(){
        date = new LocalDate();
        refresh();
    }

    public LocalDate getDateTime() {
        return date;
    }
    
    
}
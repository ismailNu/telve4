/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.calendar;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.ozguryazilim.mutfak.kahve.Kahve;
import com.ozguryazilim.mutfak.kahve.KahveEntry;
import com.ozguryazilim.mutfak.kahve.annotations.UserAware;
import com.ozguryazilim.telve.auth.Identity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Takvim arayüzlerinden setlenecek filtre modeli.
 *
 * Bu model context üzerinde yaşar. Kullanıcın seçimleri kahve içerisinde
 * saklanır.
 *
 * @author Hakan Uygun
 */
@Named
@SessionScoped
public class CalendarFilterModel implements Serializable {

    @Inject
    @UserAware
    private Kahve kahve;

    @Inject
    private Identity identity;
    
    private List<String> calendarSources;
    private Boolean showPersonalEvents;
    private Boolean showClosedEvents;

    private Map<String,String> calendarSourceStyles = new HashMap<>();
    
    public List<String> getCalendarSources() {

        if (calendarSources == null) {
            calendarSources = new ArrayList<>();
            KahveEntry o = kahve.get("calendar.filter.sources");
            //Kullanıcı için önceden tanımlanmış bir liste var mı?
            if( o != null){
                List<String> ls = Splitter.on(',').omitEmptyStrings().trimResults().splitToList(o.getAsString());
                List<String> all = getRegisteredEventSources();
                for( String s : ls ){
                    if( all.contains(s) ){
                        //FIXME: Burada yetki kontrolü de yapılacak. Aslında all kısmında yetki filtresinden geçmişleri almak lazım.
                        calendarSources.add( s );
                    }
                }
            }
            
            //TODO: Yoksa ya da boşsa sistemdekilerin hepsi gelse mi?
            //if (calendarSources == null || calendarSources.isEmpty()) {
            //    calendarSources = CalendarEventSourceRegistery.getRegisteredEventSources();
            //}
        }

        return calendarSources;
    }

    public void setCalendarSources(List<String> calendarSources) {
        this.calendarSources = calendarSources;
        String s = Joiner.on(',').join(calendarSources);
        kahve.put("calendar.filter.sources", s);
    }

    public Boolean getShowPersonalEvents() {
        if (showPersonalEvents == null) {
            KahveEntry o = kahve.get("calendar.filter.ShowPOT", Boolean.FALSE);
            showPersonalEvents = o.getAsBoolean();
        }
        return showPersonalEvents;
    }

    public void setShowPersonalEvents(Boolean showPersonalEvents) {
        this.showPersonalEvents = showPersonalEvents;
        kahve.put("calendar.filter.ShowPOT", showPersonalEvents);
    }

    public Boolean getShowClosedEvents() {

        if (showClosedEvents == null) {
            KahveEntry o = kahve.get("calendar.filter.ShowClosed", Boolean.FALSE);
            showClosedEvents = o.getAsBoolean();
        }

        return showClosedEvents;
    }

    public void setShowClosedEvents(Boolean showClosedEvents) {
        this.showClosedEvents = showClosedEvents;
        kahve.put("calendar.filter.ShowClosed", showClosedEvents);
    }

    public void toggleShowClosedEvent(){
        setShowClosedEvents(!showClosedEvents);
    }
    
    public void toggleShowPersonalEvents(){
        setShowPersonalEvents(!showPersonalEvents);
    }
    
    /**
     * Geriye tanımlı event source listesini döndürür.
     *
     * @return
     */
    public List<String> getRegisteredEventSources() {
        
        //FIXME: Burada yetki kontrolünden bir geçirmemiz lazım.
        List<String> result = new ArrayList<>();
        
        List<String> all = CalendarEventSourceRegistery.getRegisteredEventSources();
        
        for( String s : all ){
            com.ozguryazilim.telve.calendar.annotations.CalendarEventSource esm = CalendarEventSourceRegistery.getMetadata(s);
            String perm = esm.permission();
            if( Strings.isNullOrEmpty(perm) || "NEED_LOGIN".equals(perm)){
                result.add(s);
            } else {
                if( identity.hasPermission(perm, "select") ){
                    result.add(s);
                }
            }
        }
        
        return result;
    }
    
    public String getSourceStyle( String name ){
        
        String style = calendarSourceStyles.get(name);
        if( Strings.isNullOrEmpty(style)){
            KahveEntry o = kahve.get("calendar.style." + name, "#3c8dbc");
            style = o.getAsString();
            calendarSourceStyles.put(name, style);
        }
        return style;
    }
    
    public void setSourceStyle( String name, String style ){
        calendarSourceStyles.put(name, style);
        kahve.put("calendar.style." + name, style);
    }
    
    public void toggleSelectSource( String name ){
        if( calendarSources.contains(name)){
            calendarSources.remove(name);
        } else {
            calendarSources.add(name);
        }
        //Değişiklikleri kaydedelim
        String s = Joiner.on(',').join(calendarSources);
        kahve.put("calendar.filter.sources", s);
    }
    
    public Boolean getIsSourceSelected( String name ){
        return getCalendarSources().contains(name);
    }
}

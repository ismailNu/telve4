/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ozguryazilim.telve.forms;

import com.ozguryazilim.telve.entities.EntityBase;
import com.ozguryazilim.telve.query.QueryControllerBase;
import com.ozguryazilim.telve.entities.ViewModel;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.config.view.navigation.NavigationParameterContext;

/**
 * Browse sınıfları için taban.
 * 
 * @author Hakan Uygun
 * @param <E>
 * @param <R>
 */
public abstract class BrowseBase<E extends EntityBase,R extends ViewModel> extends QueryControllerBase<E,R> {
   
    
    @Inject
    private ViewConfigResolver viewConfigResolver;
    
    @Inject
    private NavigationParameterContext navigationParameterContext;
    
    public Class<? extends ViewConfig> edit( Long id ){
        navigationParameterContext.addPageParameter("eid", id);
        return getEditPage();
    }
    
    public Class<? extends ViewConfig> view( Long id ){
        navigationParameterContext.addPageParameter("eid", id);
        return getContainerViewPage();
    }
    
    
    public String getContainerViewId(){
        return viewConfigResolver.getViewConfigDescriptor(getContainerViewPage()).getViewId();
    }
    
    public String getEditViewId(){
        return viewConfigResolver.getViewConfigDescriptor(getEditPage()).getViewId();
    }
    
    /**
     * Geriye FormEdit annotation'ı ile tanımlanmış BrowsePage'i döndürür.
     *
     * @return
     */
    public Class<? extends ViewConfig> getBrowsePage() {
        return this.getClass().getAnnotation(Browse.class).browsePage();
    }

    /**
     * Geriye FormEdit annotation'ı ile tanımlanmış EditPage'i döndürür.
     *
     * @return
     */
    public Class<? extends ViewConfig> getEditPage() {
        return this.getClass().getAnnotation(Browse.class).editPage();
    }

    /**
     * Geriye FormEdit annotation'ı ile tanımlanmış EditPage'i döndürür.
     *
     * @return
     */
    public Class<? extends ViewConfig> getContainerViewPage() {
        return this.getClass().getAnnotation(Browse.class).viewContainerPage();
    }

    /**
     * RefreshBrowseEvent'i dinlenir ve ilgili domain ise search komutu çalıştırılır.
     * @param event 
     */
    public void refreshListener( @Observes(notifyObserver = Reception.IF_EXISTS) RefreshBrowserEvent event ){
        if( event.getDomain().equals( getRepository().getEntityClass().getName()) ){
            search();
        }
    }
}

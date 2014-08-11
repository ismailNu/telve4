/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.forms;

import com.ozguryazilim.telve.messages.FacesMessages;
import com.ozguryazilim.telve.view.Pages;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.scope.GroupedConversation;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parametre tipi ekranlar için taban sınıf.
 *
 * @author Hakan Uygun
 * @param <E> İşlenecek olan Entity sınıfı
 * @param <PK> PK kolon sınıfı
 */
public abstract class ParamBase<E, PK extends Serializable> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(ParamBase.class);

    private E entity;
    private List<E> filteredList;
    private List<E> entityList;

    @Inject
    private GroupedConversation conversation;
    
    public List<E> getEntityList() {
        LOG.debug("super.getEntityList");
        if( entityList == null ){
            entityList = getRepository().findAll();
        }
        return entityList;
    }
    
    /**
     * Geriye kullanılacak olan repository'i döndürür.
     *
     * @return
     */
    protected abstract AbstractEntityRepository<E, PK> getRepository();

    /**
     * TODO: Aslında bu methodu Repository'e taşımak lazım.
     *
     * @return
     */
    protected abstract E getNewEntity();

    public void createNew() {
        entity = getNewEntity();
    }

    public Class<? extends ViewConfig> saveAndNew() {
        Class<? extends ViewConfig> result = save();
        createNew();
        return result;
    }

    public void edit(E e) {
        LOG.debug("Edit edicedik : {}", e);
        entity = e;
    }

    public void delete(E e) {
        entity = e;
        delete();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class<? extends ViewConfig> save() {
        if (entity == null) {
            return Pages.Home.class;
        }

        //try {
            getRepository().saveAndFlush(entity);
            if( !getEntityList().contains(entity) ){
                getEntityList().add(entity);
            }
            /*
        } catch (ConstraintViolationException e) {
            log.error("Hata : #0", e);
            facesMessages.add("#{messages['general.message.record.NotUnique']}");
            return BaseConsts.FAIL;
        } catch (Exception e) {
            log.error("Hata : #0", e);
            facesMessages.add("#{messages['general.message.record.SaveFail']}");
            return BaseConsts.FAIL;
        }*/
        LOG.debug("Entity Saved : {0} ", entity);
        
        FacesMessages.info("#{messages['general.message.record.SaveSuccess']}");
        
        refreshEntityList();

        return null;
    }

    public Class<? extends ViewConfig> delete() {
        if (entity == null) {
            return Pages.Home.class;
        }

        try {
            getRepository().removeAndFlush(entity);
            
        } catch (Exception e) {
            LOG.debug("Hata : #0", e);
            FacesMessages.error("#{messages['general.message.record.DeleteFaild']}");
            //facesMessages.add("#{messages['general.message.record.DeleteFaild']}");
            return null;
        }

        //Listeden de çıkaralım
        getEntityList().remove(entity);
        
        
        LOG.debug("Entity Removed : {0} ", entity);
        
        //Mevcut silindi dolayısı ile null verdik
        entity = null;
        
        FacesMessages.info("#{messages['general.message.record.DeleteSuccess']}");
        
        refreshEntityList();

        return null;
    }

    /**
     * TODO: Burada GroupConversationScope kapatılacak
     * @return 
     */
    public Class<? extends ViewConfig> close(){
        conversation.close();
        return Pages.Home.class;
    }
    
    /**
     * Kayıt işlemlerinden sonra çağırılır. 
     * 
     * Eğer isteniyor ise listenin tekrardan çelkilmesi içindir.
     * 
     */
    public void refreshEntityList() {

    }

    public E getEntity() {
        if( entity == null){
            createNew();
        }
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public List<E> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<E> filteredList) {
        this.filteredList = filteredList;
    }
    
    
}
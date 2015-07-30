/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.nav;

import com.google.common.base.Strings;
import com.ozguryazilim.telve.auth.SecuredPage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigDescriptor;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.picketlink.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session başına navigosyan itemlarını belirler ve gerektiğinde sunar.
 *
 * DeltaSpike viewConfig üzerinden Navigation ile işaretlenmiş olan bilgileri
 * SecuredPages üzerinde kontrol ederek kullanıcı ( session ) başına oluşturup
 * saklar.
 *
 * @author Hakan Uygun
 */
@SessionScoped
@Named
public class NagivationController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(NagivationController.class);

    @Inject
    private Identity identity;

    @Inject
    private ViewConfigResolver viewConfigResolver;

    private List<NavigationLinkModel> mainNavigations = new ArrayList<>();
    private List<NavigationLinkModel> sideNavigations = new ArrayList<>();
    private List<NavigationSection> navigationSections = new ArrayList<>();
    private Map<String, List<NavigationLinkModel>> navigations = new HashMap<>();

    /**
     * Session için navigasyon ağacını toparlar.
     */
    @PostConstruct
    public void init() {
        try {
            buildNav();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error("NavigationError", ex);
        }
    }

    protected void buildNav() throws InstantiationException, IllegalAccessException {
        //FIXME: Burada yetki kontrolü de yapılacak.
        for (ViewConfigDescriptor vi : viewConfigResolver.getViewConfigDescriptors()) {

            //Yetki kontrolü yapıyoruz. Eğer erişim yetkisi yoksa bahsi geçen View için nav oluşturulmayacak.
            List<SecuredPage> sec = vi.getMetaData(SecuredPage.class);
            if (!sec.isEmpty()) {
                SecuredPage sc = sec.get(0);
                if (!Strings.isNullOrEmpty(sc.value())) {
                    if (!identity.hasPermission(sc.value(), "SELECT")) {
                        continue;
                    }
                }
            }

            //Navigation işaretlerini topla
            List<Navigation> ls = vi.getMetaData(Navigation.class);

            //Eğer birden fazla navigasyon tanımı varsa onları da toparlıyoruz.
            List<Navigations> lss = vi.getMetaData(Navigations.class);
            for (Navigations nvs : lss) {
                ls.addAll(Arrays.asList(nvs.value()));
            }

            //Şimdi gerekli modeller build ediliyor
            for (Navigation nav : ls) {
                
                //Eğer label boş ise sınıf adını döndürüyoruz
                String label = Strings.isNullOrEmpty(nav.label()) ? "nav.label." + vi.getConfigClass().getSimpleName() : nav.label();
                
                NavigationLinkModel nlm = new NavigationLinkModel(vi.getViewId(), label, nav.icon(), nav.order());

                if (nav.section() == MainNavigationSection.class) {
                    mainNavigations.add(nlm);
                } else if (nav.section() == SideNavigationSection.class) {
                    sideNavigations.add(nlm);
                } else {
                    NavigationSection ns = findNavigationSection(nav.section());
                    List<NavigationLinkModel> navs = navigations.get(ns.getLabel());
                    if (navs == null) {
                        navs = new ArrayList<>();
                        navigations.put(ns.getLabel(), navs);
                    }
                    navs.add(nlm);
                }

            }
        }

        //Sıralamaları yapalım.
        Collections.sort(mainNavigations);
        Collections.sort(sideNavigations);
        Collections.sort(navigationSections);
        //Collections.sort(navigations);

        LOG.info("Main Nav : {}", mainNavigations);
        LOG.info("Side Nav : {}", sideNavigations);
        LOG.info("Other Nav : {}", navigations);
    }

    /**
     * Lİsteye bakıp navigation section bulur. 
     * 
     * Bulamazsa yeni bir instance oluşturup listeye ekler.
     * 
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    private NavigationSection findNavigationSection( Class< ? extends  NavigationSection> clazz ) throws InstantiationException, IllegalAccessException{
        for( NavigationSection ns : navigationSections ){
            if( ns.getClass() == clazz ) {
                return ns;
            }
        }
        
        NavigationSection ns = clazz.newInstance();
        navigationSections.add(ns);
        return ns;
    }
    
    public List<NavigationLinkModel> getMainNavigations() {
        return mainNavigations;
    }

    public void setMainNavigations(List<NavigationLinkModel> mainNavigations) {
        this.mainNavigations = mainNavigations;
    }

    public List<NavigationLinkModel> getSideNavigations() {
        return sideNavigations;
    }

    public void setSideNavigations(List<NavigationLinkModel> sideNavigations) {
        this.sideNavigations = sideNavigations;
    }

    public Map<String, List<NavigationLinkModel>> getNavigations() {
        return navigations;
    }

    /**
     * Geriye section key'lerini barındıran Set döndürür.
     *
     * @return
     */
    public List<NavigationSection> getNavigationSections() {
        return navigationSections;
    }

    /**
     * Geriye ismi verilen section'ın itemlarını barındıran liste döndürür.
     *
     * @param section
     * @return
     */
    public List<NavigationLinkModel> getSectionLinks(String section) {
        LOG.debug("Nav request for {}", section);
        return navigations.get(section);
    }

}
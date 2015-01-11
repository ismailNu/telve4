/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.messagebus;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.impl.DefaultShutdownStrategy;
import org.apache.camel.spi.ShutdownStrategy;

/**
 * Telve Camel Context Bean
 * @author Hakan Uygun
 */
@ApplicationScoped
@ContextName("telve")
public class TelveCamelContext extends CdiCamelContext{
    
    @PostConstruct
    public void init(){
        setTracing(Boolean.TRUE);
        setDelayer(1000l);
        ShutdownStrategy ss = new DefaultShutdownStrategy(); 
        //ss.setShutdownNowOnTimeout(true);
        ss.setTimeout(15l);
        setShutdownStrategy(ss);
    }
}
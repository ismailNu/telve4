/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ozguryazilim.mutfak.telve;

import com.ozguryazilim.telve.api.module.TelveModule;
import javax.annotation.PostConstruct;

/**
 * Telve Core Module Tanımı
 * 
 * @author Hakan Uygun
 */
@TelveModule("TelveCoreModule")
public class TelveCoreModule {
   
    private String testMessage;
    
    @PostConstruct
    public void init(){
        System.out.println("TelveCore inited");
        testMessage = "Inited";
        
    }
    
    public String getTestMessage() {
        return testMessage;
    }

    public void setTestMessage(String testMessage) {
        this.testMessage = testMessage;
    }
}

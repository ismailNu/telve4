/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.idm.entities;

import com.ozguryazilim.telve.annotations.BizKey;
import com.ozguryazilim.telve.entities.EntityBase;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Kullanıcı Modeli
 * 
 * @author Hakan Uygun
 */
@Entity
@Table(name = "TLI_USER")
public class User extends EntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "LOGIN_NAME")
    @BizKey
    private String loginName;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PW_HASH")
    private String passwordEncodedHash;
    
    @Column(name = "PW_SALT")
    private String passwordSalt;
    
    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
    
    /**
     * Uygulama tarafından register edilen farklı kullanıcı tipleri.
     */
    @Column(name = "USER_TYPE")
    private String userType = "STANDART";
    
    /**
     * Kullanıcıya ek veri tanım alanları. Extention2lar için
     */
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "key")
    private Map<String,UserAttribute> attributes = new HashMap<>();

    
    @Column(name = "INFO")
    private String info;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordEncodedHash() {
        return passwordEncodedHash;
    }

    public void setPasswordEncodedHash(String passwordEncodedHash) {
        this.passwordEncodedHash = passwordEncodedHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Map<String, UserAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, UserAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
}

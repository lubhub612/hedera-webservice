package com.lubdhak.hederaapplication.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lubdhak.hederaapplication.model.ImpUser;

public class ImpUserPrinciple implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private Long id;
    
	private String username;

    @JsonIgnore
    private String password;
    
    private String privatekey;
    
    private String publickey;
    
    private String accountid;
    
    private String privateaddress;
    
    private String publicaddress;
    
    private String cpharse;

    private Collection<? extends GrantedAuthority> authorities;
    
    public ImpUserPrinciple(Long id,  
    		String username, String password, String privatekey, String publickey,
    		String accountid1, String privateaddress, String publicaddress, String cpharse,
    		Collection<? extends GrantedAuthority> authorities) {
    	this.id = id;
        this.username = username;
        this.password = password;
        this.privatekey = privatekey;
        this.publickey = publickey;
        this.accountid = accountid1;
        this.privateaddress = privateaddress;
        this.publicaddress = publicaddress;
        this.cpharse = cpharse;
        this.authorities = authorities;
    	
        }
    
    
   public static  ImpUserPrinciple build(ImpUser impuser){
    	List<GrantedAuthority> authorities = impuser.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())
).collect(Collectors.toList());
    	
    	return new ImpUserPrinciple(
    			impuser.getId(),
    			impuser.getUsername(),
    			impuser.getPassword(),
    			impuser.getPrivatekey(),
    			impuser.getPublickey(),
    			impuser.getAccountid(),
    			impuser.getPrivateaddress(),
    			impuser.getPublicaddress(),
    			impuser.getCpharse(),
    			authorities
    			);

             }
    
    public Long getId() {
        return id;
    }

    

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
   
    public String getPrivatekey() {
    	return privatekey;
    }
    
    public String getPublickey() {
    	return publickey;
    }
    
    public String getAccountid() {
    	return accountid;
    }
    
    public String getPrivateaddress() {
		return privateaddress;
	}

    public String getPublicaddress() {
		return publicaddress;
	}
    
    public String getCpharse() {
		return cpharse;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ImpUserPrinciple impuser = (ImpUserPrinciple) o;
        return Objects.equals(id, impuser.id);
    }

    
    
 }
    



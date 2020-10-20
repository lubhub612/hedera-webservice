package com.lubdhak.hederaapplication.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lubdhak.hederaapplication.model.SellUser;

public class SellUserPrinciple implements UserDetails  {

	private static final long serialVersionUID = 1L;

	private Long id;
    
	private String username;
    
    private String privatekey;
    
    private String publickey;
    
    private String accountid;
    
    private String privateaddress;
    
    private String publicaddress;
    
    private String fiatprice;
    
    private Double hedamount;
    
    private String lumenamount;
    
    private String totalprice; 
    
    private String stoplose;
    
    private String stoplose1;
    
    private String stoplose2;
    
    private String stoplose3;
    
    private String stoplose4;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    
    public SellUserPrinciple(Long id,  
    		String username, String privatekey, String publickey,
    		String accountid1, String privateaddress, String publicaddress, String fiatprice, Double hedamount, String lumenamount, String totalprice, String stoplose,String stoplose1,String stoplose2,String stoplose3,String stoplose4,
    		Collection<? extends GrantedAuthority> authorities) {
    
    	
    	this.id = id;
        this.username = username;
        this.privatekey = privatekey;
        this.publickey = publickey;
        this.accountid = accountid1;
        this.privateaddress = privateaddress;
        this.publicaddress = publicaddress;
        this.fiatprice = fiatprice;
        this.hedamount = hedamount;
        this.lumenamount = lumenamount;
        this.totalprice = totalprice;
        this.stoplose = stoplose;
        this.stoplose1 = stoplose1;
        this.stoplose2 = stoplose2;
        this.stoplose3 = stoplose3;
        this.stoplose4 = stoplose4;
        this.authorities = authorities;
	
         }
    
          
    
         public static SellUserPrinciple build(SellUser selluser) {
    	   
        	 
        	 
        	 List<GrantedAuthority> authorities = selluser.getRoles().stream().map(role ->
         new SimpleGrantedAuthority(role.getName().name())
 ).collect(Collectors.toList());
    	 return new SellUserPrinciple(
    			 selluser.getId(),
    			 selluser.getUsername(),
    			 selluser.getPrivatekey(),
    			 selluser.getPublickey(),
    			 selluser.getAccountid(),
    			 selluser.getPrivateaddress(),
    			 selluser.getPublicaddress(),
    			 selluser.getFiatprice(),
    			 selluser.getHedamount(),
    			 selluser.getLumenamount(),
    			 selluser.getTotalprice(),
    			 selluser.getStoplose(),
    			 selluser.getStoplose1(),
    			 selluser.getStoplose2(),
    			 selluser.getStoplose3(),
    			 selluser.getStoplose4(),
    			 authorities
    			 );
    	 
          }
     
     public static long getSerialversionuid() {
 		return serialVersionUID;
 	}

 	public Long getId() {
 		return id;
 	}
 	@Override
 	public String getUsername() {
 		return username;
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

 	public String getFiatprice() {
 		return fiatprice;
 	}

 	public Double getHedamount() {
 		return hedamount;
 	}

 	public String getLumenamount() {
 		return lumenamount;
 	}

 	public String getTotalprice() {
 		return totalprice;
 	}
 	
 	public String getStoplose() {
		return stoplose;
	}
	public String getStoplose1() {
		return stoplose1;
	}
	public String getStoplose2() {
		return stoplose2;
	}
	public String getStoplose3() {
		return stoplose3;
	}
	public String getStoplose4() {
		return stoplose4;
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
 	        
 	        SellUserPrinciple selluser = (SellUserPrinciple) o;
 	        return Objects.equals(id, selluser.id);
 	    }
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return null;
		}

     
    }

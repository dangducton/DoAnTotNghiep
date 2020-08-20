package com.dangducton.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.dangducton.entities.Nguoidung;

public class PdfUserDetails implements UserDetails {
    

	private static final long serialVersionUID = 1L;
	private Nguoidung nguoiDung;
	
    public PdfUserDetails(Nguoidung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return nguoiDung.getRoleCollection().stream().map(authority -> new SimpleGrantedAuthority(authority.getTen().toString())).collect(Collectors.toList());
    }
    public long getId() {
        return nguoiDung.getId();
    }
    @Override
    public String getPassword() {
        return nguoiDung.getPassword();
    }
    @Override
    public String getUsername() {
        return nguoiDung.getEmail();
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
    public Nguoidung getUserDetails() {
        return nguoiDung;
    }

}


package com.uguraytekin.getircasestudy.common.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uguraytekin.getircasestudy.customer.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static CustomerDetailsImpl build(Customer customer) {
        List<SimpleGrantedAuthority> authorities = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();

        return new CustomerDetailsImpl(
                customer.getId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getPassword(),
                authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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


}

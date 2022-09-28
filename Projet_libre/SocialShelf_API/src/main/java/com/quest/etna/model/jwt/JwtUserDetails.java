package com.quest.etna.model.jwt;

import java.util.Collection;
import java.util.Collections;

import com.quest.etna.model.UserRole;
import com.quest.etna.model.player.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 417352943544136718L;
	
	private final String username;
	private final String password;
    private final UserRole role;
    
    public JwtUserDetails(Player player) {
    	super();
    	this.username = player.getUsername();
    	this.role = player.getRole();
		this.password = player.getPassword();
    }

    public UserRole getRole() {
        return role;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
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

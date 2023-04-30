package ee.nutikas.games.api.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.security.dto.CustomGrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"authorities"}, allowGetters = true)
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String nickname;

    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Long coins;
    private Long experience;

    @JsonIgnore
    private String password;

    private Collection<CustomGrantedAuthority> authorities;

    public static UserDetailsImpl build(UserModel user) {
        List<CustomGrantedAuthority> authorities = user.getUserRoles()
                .stream()
                .map(role -> new CustomGrantedAuthority(role.getRole().getName().name(), role.getSchool().getId(), user.getId(), role.getDisabled())).toList();
        return new UserDetailsImpl(user.getId(), user.getNickname(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getBirthDate(), user.getCoins(), user.getExperience(), user.getPasswordHash(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
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
        return authorities.stream().anyMatch(a -> !a.getCustomAuthority().getDisabled());
    }
}

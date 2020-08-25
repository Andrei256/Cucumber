package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+[@][A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Некорректный email")
    private String email;

    @Pattern(regexp = "\\Q+375\\E(\\Q29\\E)?(\\Q33\\E)?(\\Q44\\E)?(\\Q25\\E)?[0-9]{7}", message = "Некорректный номер телефона")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Length(max = 2048, message = "Поле слишком длинное(Больше 2048 знаков)")
    private String text;

    private boolean active;

    @Column(name = "activation_code")
    private String activationCode;

/*    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "buyer")
    private Basket basket;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "seller")
    private List<Offer> offers;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;*/

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
        return isActive();
    }
}

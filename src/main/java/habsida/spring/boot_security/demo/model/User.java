package habsida.spring.boot_security.demo.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;


    @Entity
    @Table(name = "users")
    public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "First name must contain only letters"
        )
        @Column(name = "first_name", nullable = false)
        @NotBlank(message = "First name is required")
        private String firstName;

        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "Last name must contain only letters"
        )
        @Column(name = "last_name", nullable = false)
        @NotBlank(message = "Last name is required")
        private String lastName;

        @Column(name = "email", nullable = false, unique = true)
        @NotBlank(message = "Email is required")
        @Email(message = "Please enter a valid email address")
        private String email;

        // Password for Spring Security
        @Column(nullable = false)
        @NotBlank(message = "Password is required")
        private String password;

        // One user can have multiple roles
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();

        public User() {
        }

        public User(String firstName, String lastName, String email,String password,Set<Role> roles) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            if (roles != null){
                this.roles = roles;
            }
        }

        // Getters and setters

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

        @Override
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        // UserDetails implementation

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles;
        }

        @Override
        public String getUsername() {
            return email;
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


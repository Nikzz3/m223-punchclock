package ch.zli.m223.punchclock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    @JsonIgnoreProperties("users")
    @ManyToOne()
    @JoinColumn(name = "role")
    private Role role;

    @JsonIgnoreProperties({"user", "category"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Entry> entries;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public List<Entry> getEntries() {
        return entries;
    }
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

}

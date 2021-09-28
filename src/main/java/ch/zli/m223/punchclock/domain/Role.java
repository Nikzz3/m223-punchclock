package ch.zli.m223.punchclock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role")
    private List<ApplicationUser> appUsers;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<ApplicationUser> getAppUsers() {
        return appUsers;
    }
    public void setAppUsers(List<ApplicationUser> appUsers) {
        this.appUsers = appUsers;
    }
}

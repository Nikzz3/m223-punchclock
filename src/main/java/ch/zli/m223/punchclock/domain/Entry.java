package ch.zli.m223.punchclock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(nullable = false)
    private LocalDateTime checkIn;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(nullable = false)
    private LocalDateTime checkOut;

    @JsonIgnoreProperties("entries")
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @JsonIgnoreProperties({"entries", "role"})
    @ManyToOne
    @JoinColumn(name = "user")
    private ApplicationUser user;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }
    public LocalDateTime getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public ApplicationUser getUser() {
        return user;
    }
    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}

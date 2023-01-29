package com.myapp.ws.ws_app.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "groups")
public class GroupEntity implements Serializable {

    private static final long serialVersionUID = 7036771250313229584L;

    @Id
    @GeneratedValue
    private int id;
    @Column(name="name" , length =30)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(name="groups_users", joinColumns = {@JoinColumn(name="groups_id")}, inverseJoinColumns = {@JoinColumn(name="users_id")})
    private Set<UserEntity> users = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}

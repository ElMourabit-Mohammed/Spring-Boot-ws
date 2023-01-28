package com.myapp.ws.ws_app.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity(name="contacts")
public class ContactEntity implements Serializable {

    private static final long serialVersionUID = 6102996860786332461L;

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(length = 50)
    private String contactId;

    @NotBlank
    private String mobile;

    private String skype;

    @OneToOne
    @JoinColumn(name="users_id")
    private UserEntity user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

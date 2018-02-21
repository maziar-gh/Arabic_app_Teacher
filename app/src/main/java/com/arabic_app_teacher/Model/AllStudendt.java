package com.arabic_app_teacher.Model;

/**
 * Created by Maziar on 11/25/2017.
 */

public class AllStudendt {
    private String id;
    private String email;
    private String fullname;
    private String active;

    public AllStudendt() {
    }

    public AllStudendt(String id, String email, String fullname, String active) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

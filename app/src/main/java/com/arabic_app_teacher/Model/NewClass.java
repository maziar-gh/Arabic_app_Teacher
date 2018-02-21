package com.arabic_app_teacher.Model;

/**
 * Created by Maziar on 11/25/2017.
 */

public class NewClass {
    private String id;
    private String className;
    private String desc;
    private String ostadName;

    public NewClass() {
    }

    public NewClass(String id, String className, String desc , String ostadName) {
        this.id = id;
        this.className = className;
        this.desc = desc;
        this.ostadName = ostadName;

    }

    public String getOstadName() {
        return ostadName;
    }

    public void setOstadName(String ostadName) {
        this.ostadName = ostadName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

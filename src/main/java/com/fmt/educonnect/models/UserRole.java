package com.fmt.educonnect.models;

public enum UserRole {
    ADMIN("docente");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}

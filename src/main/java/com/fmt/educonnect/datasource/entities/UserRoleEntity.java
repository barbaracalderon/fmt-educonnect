package com.fmt.educonnect.datasource.entities;

public enum UserRoleEntity {
    ADMIN("docente");

    private String role;

    UserRoleEntity(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}

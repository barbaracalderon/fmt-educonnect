package com.fmt.educonnect.datasource.entities;

public enum UserRoleEntity {
    ADMIN("ADMIN"),
    PROFESSOR("PROFESSOR"),
    PEDAGOGICO("PEDAGOGICO"),
    RECRUITER("RECRUITER"),
    ALUNO("ALUNO");

    private final String role;

    UserRoleEntity(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}

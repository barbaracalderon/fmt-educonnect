package com.fmt.educonnect.models;

public enum UsersRole {
    ROLE_ADMIN("docente"),
    ROLE_PEDAGOGICO("docente"),
    ROLE_RECRUITER("docente"),
    ROLE_PROFESSOR("docente"),
    ROLE_ALUNO("aluno");

    private String role;

    UsersRole(String role){
        this.role = role;
    }


    public String getRole(){
        return role;
    }
}

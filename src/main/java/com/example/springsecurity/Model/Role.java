package com.example.springsecurity.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private URole roleName;

    public Role(){}

    public Role(URole name){
        this.roleName = name;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public URole getRoleName(){
        return roleName;
    }

    public void setRoleName(URole name){
        this.roleName = name;
    }
}

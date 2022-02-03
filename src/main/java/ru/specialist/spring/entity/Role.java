package ru.specialist.spring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name")
    private String roleName;

    @ManyToMany(mappedBy = "rolesList")
    private List<Users> UsersList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Users> getUsersList() {
        return UsersList;
    }

    public void setUsersList(List<Users> usersList) {
        UsersList = usersList;
    }
}

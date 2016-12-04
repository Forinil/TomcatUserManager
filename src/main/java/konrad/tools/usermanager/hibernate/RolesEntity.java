package konrad.tools.usermanager.hibernate;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.SortedSet;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-07.
 */
@Entity
@Table(name = "ROLES", schema = "PUBLIC", catalog = "USERS")
public class RolesEntity implements Serializable, Comparable<RolesEntity>, IEntity {
    private String roleName;
    //private SortedSet<UserRolesEntity> rolesEntities;
    private SortedSet<UsersEntity> usersEntitySet;

    @Id
    @Column(name = "ROLE_NAME", nullable = false, length = 15)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @ManyToMany(mappedBy = "rolesEntitySet", fetch = FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    @OrderBy(value = "userName")
    public SortedSet<UsersEntity> getUsersEntitySet() {
        return usersEntitySet;
    }

    public void setUsersEntitySet(SortedSet<UsersEntity> usersEntitySet) {
        this.usersEntitySet = usersEntitySet;
    }

    @Override
    public String toString() {
        return roleName == null ? super.toString() : roleName;
    }

    @Override
    public int compareTo(RolesEntity o) {
        return this.getRoleName().compareTo(o.getRoleName());
    }

    @Override
    @Transient
    public Serializable getID() {
        return getRoleName();
    }
}

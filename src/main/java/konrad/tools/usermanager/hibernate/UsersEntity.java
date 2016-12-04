package konrad.tools.usermanager.hibernate;

import konrad.tools.usermanager.collections.RolesEntitySet;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.SortedSet;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-06.
 */
@Entity
@Table(name = "USERS", schema = "PUBLIC", catalog = "USERS")
public class UsersEntity implements Serializable, Comparable<UsersEntity>, IEntity {
    private String userName;
    private String userPass;
    private RolesEntitySet rolesEntitySet;

    @Id
    @Column(name = "USER_NAME", nullable = false, length = 15)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "USER_PASS", nullable = false, length = 15)
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = {@JoinColumn(name = "USER_NAME")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_NAME")})
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.SAVE_UPDATE, CascadeType.REPLICATE, CascadeType.LOCK, CascadeType.DETACH})
    @OrderBy(value = "roleName")
    public SortedSet<RolesEntity> getRolesEntitySet() {
        if (rolesEntitySet == null) {
            rolesEntitySet = new RolesEntitySet();
        }
        return rolesEntitySet.getRolesEntitySortedSet();
    }

    public void setRolesEntitySet(SortedSet<RolesEntity> rolesEntitySet) {
        if (this.rolesEntitySet == null) {
            this.rolesEntitySet = new RolesEntitySet();
        }
        this.rolesEntitySet.setRolesEntitySortedSet(rolesEntitySet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userPass != null ? !userPass.equals(that.userPass) : that.userPass != null) return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username: ");
        if (userName != null) {
            stringBuilder.append(userName);
        } else {
            stringBuilder.append("empty");
        }
        stringBuilder.append("\nPassword: ");
        if (userPass != null) {
            stringBuilder.append(userPass);
        } else {
            stringBuilder.append("empty");
        }
        if (rolesEntitySet != null && rolesEntitySet.getRolesEntitySortedSet() != null && rolesEntitySet.getRolesEntitySortedSet().size() > 0) {
            stringBuilder.append("\nRoles: ");
            for (RolesEntity role: rolesEntitySet.getRolesEntitySortedSet()) {
                stringBuilder.append("\n\t");
                stringBuilder.append(role);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (userPass != null ? userPass.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(UsersEntity o) {
        return this.getUserName().compareTo(o.getUserName());
    }

    @Override
    @Transient
    public Serializable getID() {
        return getUserName();
    }
}

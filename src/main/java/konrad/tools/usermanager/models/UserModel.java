package konrad.tools.usermanager.models;

import konrad.tools.usermanager.collections.RolesSet;

import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Stworzone przez Konrad Botor dnia 2016-07-07.
 */
public class UserModel {
    private String userName;

    private String userPass;

    private String repeatUserPass;

    private RolesSet roleNames;

    private boolean changePassword;

    private boolean changeRoles;

    public UserModel() {
        userName = "";
        userPass = "";
        repeatUserPass = "";
        roleNames = new RolesSet();
        changePassword = false;
        changeRoles = false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username: ");
        stringBuilder.append(userName);
        stringBuilder.append("\nPassword: ");
        stringBuilder.append(userPass);
        stringBuilder.append("\nRoles: ");
        for (String role: roleNames) {
            stringBuilder.append("\n\t");
            stringBuilder.append(role);
        }
        return stringBuilder.toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public RolesSet getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(RolesSet roleNames) {
        this.roleNames = roleNames;
    }

    public String getRepeatUserPass() {
        return repeatUserPass;
    }

    public void setRepeatUserPass(String repeatUserPass) {
        this.repeatUserPass = repeatUserPass;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public boolean isChangeRoles() {
        return changeRoles;
    }

    public void setChangeRoles(boolean changeRoles) {
        this.changeRoles = changeRoles;
    }
}

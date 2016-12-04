package konrad.tools.usermanager.models;

/**
 * Created by kbotor on 09.07.16.
 */
public class RoleModel {
    private String roleName;

    public RoleModel() {
        roleName = "";
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}

package konrad.tools.usermanager.collections;

import konrad.tools.usermanager.hibernate.RolesEntity;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-15.
 */
public class RolesEntitySet implements Serializable {
    private SortedSet<RolesEntity> rolesEntitySortedSet;

    public RolesEntitySet() {
        rolesEntitySortedSet = new TreeSet<>();
    }

    public SortedSet<RolesEntity> getRolesEntitySortedSet() {
        return rolesEntitySortedSet;
    }

    public void setRolesEntitySortedSet(SortedSet<RolesEntity> rolesEntitySortedSet) {
        this.rolesEntitySortedSet = rolesEntitySortedSet;
    }
}

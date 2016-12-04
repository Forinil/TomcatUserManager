package konrad.tools.usermanager.converters.dozer;

import konrad.tools.usermanager.collections.RolesSet;
import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.utils.DBUtil;
import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.SortedSet;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-15.
 */
@Component("rolesToRolesEntitySetConverter")
public class RolesToRolesEntitySetConverter extends DozerConverter<RolesSet, SortedSet> {

    @Autowired
    private DBUtil dbUtil;

    public RolesToRolesEntitySetConverter() {
        super(RolesSet.class, SortedSet.class);
    }

    @Override
    public SortedSet convertTo(RolesSet source, SortedSet destination) {
        for (String role: source) {
            RolesEntity rolesEntity = dbUtil.getRolesEntityByRoleName(role);
            destination.add(rolesEntity);
        }
        return destination;
    }

    @Override
    public RolesSet convertFrom(SortedSet source, RolesSet destination) {
        for (Object role: source) {
            RolesEntity rolesEntity = (RolesEntity) role;
            destination.add(rolesEntity.getRoleName());
        }
        return destination;
    }
}

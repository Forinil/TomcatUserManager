package konrad.tools.usermanager.converters.spring;

import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-15.
 */
@Component("stringToRolesEntitiesConverter")
public class StringToRolesEntitiesConverter implements Converter<String, SortedSet<RolesEntity>> {
    @Autowired
    private DBUtil dbUtil;

    @Override
    public SortedSet<RolesEntity> convert(String source) {
        SortedSet<RolesEntity> rolesEntities = new TreeSet<>();
        source = source.substring(source.indexOf("[")+1, source.indexOf("]"));
        String[] roles = source.split(", ");
        for (String role: roles) {
            RolesEntity rolesEntity = dbUtil.getRolesEntityByRoleName(role);
            rolesEntities.add(rolesEntity);
        }
        return rolesEntities;
    }
}

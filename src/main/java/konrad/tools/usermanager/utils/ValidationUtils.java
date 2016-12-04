package konrad.tools.usermanager.utils;

import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.hibernate.UsersEntity;
import konrad.tools.usermanager.models.RoleModel;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Set;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-08.
 */
public class ValidationUtils extends org.springframework.validation.ValidationUtils {
    private static DBUtil dbUtil;

    private ValidationUtils() {

    }

    public static void rejectIfNotMatch(Errors errors, String field, String secondField, String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        String value = (String) errors.getFieldValue(field);
        String secondValue = (String) errors.getFieldValue(secondField);
        if (value == null || !StringUtils.hasLength(value) || secondValue == null || !StringUtils.hasLength(secondValue) || !value.equals(secondValue)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }

    public static void rejectIfNotMatch(Errors errors, String field, String secondField, String errorCode, Object[] errorArgs) {
        rejectIfNotMatch(errors, field, secondField, errorCode, errorArgs, null);
    }

    public static void rejectIfNotMatch(Errors errors, String field, String secondField, String errorCode) {
        rejectIfNotMatch(errors, field, secondField, errorCode, null, null);
    }

    public static void rejectIfNotMatch(Errors errors, String field, String secondField, String errorCode, String defaultMessage) {
        rejectIfNotMatch(errors, field, secondField, errorCode, null, defaultMessage);
    }

    public static void rejectIfListEmpty(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object value = errors.getFieldValue(field);
        List<Object> list;
        Set<Object> set;

        if (value != null && value instanceof List) {
            list = (List<Object>) value;
            if (list.size() == 0) {
                errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
            }
        }

        if (value != null && value instanceof Set) {
            set = (Set<Object>) value;
            if (set.size() == 0) {
                errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
            }
        }

        if (value == null) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }

    public static void rejectIfListEmpty(Errors errors, String field, String errorCode, Object[] errorArgs) {
        rejectIfListEmpty(errors, field, errorCode, errorArgs, null);
    }

    public static void rejectIfListEmpty(Errors errors, String field, String errorCode) {
        rejectIfListEmpty(errors, field, errorCode, null, null);
    }

    public static void rejectIfListEmpty(Errors errors, String field, String errorCode, String defaultMessage) {
        rejectIfListEmpty(errors, field, errorCode, null, defaultMessage);
    }

    public static void rejectIfExistsInDB(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        String value = (String) errors.getFieldValue(field);
        if (value == null || !StringUtils.hasLength(value)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
        switch (field) {
            case "roleName":
                List<RolesEntity> rolesEntities = dbUtil.getAllRoles();
                if (rolesEntities.size() > 0 && isOnTheList(value, rolesEntities)) {
                    errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
                }
                break;
            case "userName":
                List<UsersEntity> usersEntities = dbUtil.getAllUsers();
                if (usersEntities.size() > 0 && isOnTheList(value, usersEntities)) {
                    errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
                }
                break;
            default:
                errors.rejectValue(field, "error.unknownField", errorArgs, "Unknown field code");
        }
    }

    private static boolean isOnTheList(String value, List entities) {
        for (Object entity: entities) {
            if (entity instanceof RolesEntity && ((RolesEntity) entity).getRoleName().equals(value)) {
                return true;
            }
            if (entity instanceof UsersEntity && ((UsersEntity) entity).getUserName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static void rejectIfExistsInDB(Errors errors, String field, String errorCode, Object[] errorArgs) {
        rejectIfExistsInDB(errors, field, errorCode, errorArgs, null);
    }

    public static void rejectIfExitsInDB(Errors errors, String field, String errorCode) {
        rejectIfExistsInDB(errors, field, errorCode, null, null);
    }

    public static void rejectIfExistsInDB(Errors errors, String field, String errorCode, String defaultMessage) {
        rejectIfExistsInDB(errors, field, errorCode, null, defaultMessage);
    }

    public static DBUtil getDbUtil() {
        return dbUtil;
    }

    public static void setDbUtil(DBUtil dbUtil) {
        ValidationUtils.dbUtil = dbUtil;
    }
}

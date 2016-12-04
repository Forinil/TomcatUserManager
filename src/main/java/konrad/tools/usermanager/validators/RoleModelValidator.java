package konrad.tools.usermanager.validators;

import konrad.tools.usermanager.models.RoleModel;
import konrad.tools.usermanager.utils.ValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by kbotor on 09.07.16.
 */
@Component
public class RoleModelValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RoleModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.setDbUtil(dbUtil);
        ValidationUtils.rejectIfEmpty(errors, "roleName", "error.roleName", "Role name is required.");
        ValidationUtils.rejectIfExistsInDB(errors, "roleName", "error.exists", "This role already exists.");
    }
}

package konrad.tools.usermanager.validators;

import konrad.tools.usermanager.models.UserModel;
import konrad.tools.usermanager.utils.ValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-08.
 */
@Component
public class UserModelValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.setDbUtil(dbUtil);
        ValidationUtils.rejectIfEmpty(errors, "userName", "error.userName", "Username is required.");
        if (!((UserModel)o).isChangeRoles()) {
            ValidationUtils.rejectIfEmpty(errors, "userPass", "error.userPass", "Password is required.");
            ValidationUtils.rejectIfEmpty(errors, "repeatUserPass", "error.repeatUserPass", "Password is required.");
            ValidationUtils.rejectIfNotMatch(errors, "userPass", "repeatUserPass", "error.passwordsNotMatch", "Fields 'Password' and 'Repeat password' must match.");
        }
        if (!((UserModel)o).isChangePassword()) {
            ValidationUtils.rejectIfListEmpty(errors, "roleNames", "error.roleNames", "At least one role must be selected.");
        }
        if (!((UserModel)o).isChangeRoles() && !((UserModel)o).isChangePassword()) {
            ValidationUtils.rejectIfExistsInDB(errors, "userName", "error.userExists", "This user already exists.");
        }
    }
}

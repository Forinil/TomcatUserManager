package konrad.tools.usermanager.validators;

import konrad.tools.usermanager.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.context.ServletContextAware;

/**
 * Created by kbotor on 09.07.16.
 */
public abstract class BaseValidator implements Validator {
    @Autowired
    protected DBUtil dbUtil;
}

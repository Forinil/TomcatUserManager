package konrad.tools.usermanager.controllers;

import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.hibernate.UsersEntity;
import konrad.tools.usermanager.models.RoleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-06.
 */
@Controller
public class MainController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @PostConstruct
    public void init() {
        logger.info("init");
        super.init();
    }

    @RequestMapping(value = {"/", "/users"}, method = RequestMethod.GET)
    public String getIndex(Model model) {
        logger.info("getIndex");

        List<UsersEntity> users =  dbUtil.getAllUsers();

        model.addAttribute("users", users);

        return "index";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getRoles(Model model) {
        logger.info("getIndex");

        List<RolesEntity> roles = dbUtil.getAllRoles();

        model.addAttribute("roles", roles);
        model.addAttribute("role", new RoleModel());

        return "roles";
    }
}

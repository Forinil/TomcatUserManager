package konrad.tools.usermanager.controllers;

import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.models.RoleModel;
import konrad.tools.usermanager.validators.RoleModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

/**
 * Created by kbotor on 09.07.16.
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleModelValidator roleModelValidator;

    @PostConstruct
    public void init() {
        logger.info("init");
        super.init();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createRole(@ModelAttribute("role") RoleModel role, BindingResult bindingResult, Model model) {
        logger.info("createRole: {}", role);

        roleModelValidator.validate(role, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return "newRole";
        }

        dbUtil.saveRole(role);

        return "redirect:/roles";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRole(Model model) {
        logger.info("newRole");

        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new RoleModel());
        }

        return "newRole";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteRole(@ModelAttribute("role") RoleModel roleModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.info("deleteRole: {}", roleModel);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("role", roleModel);
            return "redirect:/roles";
        }

        RolesEntity rolesEntity = dbUtil.getRolesEntityByRoleName(roleModel.getRoleName());
        if (rolesEntity != null) {
            dbUtil.deleteRole(rolesEntity);
        }

        return "redirect:/roles";
    }
}

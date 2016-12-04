package konrad.tools.usermanager.controllers;

import konrad.tools.usermanager.hibernate.RolesEntity;
import konrad.tools.usermanager.hibernate.UsersEntity;
import konrad.tools.usermanager.models.UserModel;
import konrad.tools.usermanager.validators.UserModelValidator;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-07.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserModelValidator userModelValidator;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        logger.info("init");
        super.init();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirectToList() {
        logger.info("redirectToList");

        return "redirect:/users";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String getUser(@PathVariable("username") String userName, Model model) {
        logger.info("getUser: {}", userName);

        if (!model.containsAttribute("user")) {
            UsersEntity user =  dbUtil.getUsersByUsername(userName);
            model.addAttribute("user", user);
        }

        return selectView("user");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model) {
        logger.info("newUser: {}", userModel);

        userModelValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors()) {
            List<RolesEntity> roles = dbUtil.getAllRoles();

            model.addAttribute("user", userModel);
            model.addAttribute("roles", roles);
            return selectView("newUser");
        }

        dbUtil.saveUser(userModel);

        return "redirect:/user/" + userModel.getUserName();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        logger.info("newUser");
        UserModel user = new UserModel();
        List<RolesEntity> roles = dbUtil.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return selectView("newUser");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("user") UsersEntity usersEntity, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.info("deleteUser: {}", usersEntity);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", usersEntity);
            return "redirect:/user/" + usersEntity.getUserName();
        }

        dbUtil.deleteUser(usersEntity);

        return "redirect:/users";
    }

    @RequestMapping(value = "/{username}/changepassword", method = RequestMethod.GET)
    public String changePassword(@PathVariable("username") String userName, Model model) {
        logger.info("changePassword: {}", userName);

        UserModel user = new UserModel();
        user.setUserName(userName);

        model.addAttribute("user", user);

        return selectView("changePassword");
    }

    @RequestMapping(value = "/{username}/changepassword", method = RequestMethod.POST)
    public String setPassword(@PathVariable("username") String userName, @ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model) {
        logger.info("setPassword: {}", userModel);

        userModel.setChangePassword(true);
        userModelValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors() || !userName.equals(userModel.getUserName())) {
            model.addAttribute("user", userModel);
            return selectView("changePassword");
        }

        dbUtil.updateUser(userModel);

        return "redirect:/user/" + userModel.getUserName();
    }

    @RequestMapping(value = "/{username}/changeroles", method = RequestMethod.GET)
    public String changeRoles(@PathVariable("username") String userName, Model model) {
        logger.info("changeRoles: {}", userName);

        List<RolesEntity> roles = dbUtil.getAllRoles();
        UsersEntity usersEntity = dbUtil.getUsersByUsername(userName);

        UserModel user = dozerBeanMapper.map(usersEntity, UserModel.class);

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return selectView("changeRoles");
    }

    @RequestMapping(value = "/{username}/changeroles", method = RequestMethod.POST)
    public String setRoles(@PathVariable("username") String userName, @ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model) {
        logger.info("setRoles: {}", userModel);

        userModel.setChangeRoles(true);
        userModelValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors() || !userName.equals(userModel.getUserName())) {
            List<RolesEntity> roles = dbUtil.getAllRoles();

            model.addAttribute("user", userModel);
            model.addAttribute("roles", roles);
            return selectView("changeRoles");
        }

        dbUtil.updateUser(userModel);

        return "redirect:/user/" + userModel.getUserName();
    }
}

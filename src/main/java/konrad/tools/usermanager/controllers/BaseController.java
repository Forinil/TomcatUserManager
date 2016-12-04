package konrad.tools.usermanager.controllers;

import konrad.tools.usermanager.utils.BeanUtil;
import konrad.tools.usermanager.utils.ConfigUtil;
import konrad.tools.usermanager.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * Created by kbotor on 09.07.16.
 */
public abstract class BaseController implements ServletContextAware {

    @Autowired
    protected ServletContext servletContext;

    @Autowired
    protected DBUtil dbUtil;

    @Autowired
    protected ConfigUtil configUtil;

    @Autowired
    protected BeanUtil beanUtil;

    @PostConstruct
    public void init() {
        dbUtil.setServletContext(servletContext);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod(){
        return "Page not found";
    }
}

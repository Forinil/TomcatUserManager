package konrad.tools.usermanager.controllers;

import konrad.tools.usermanager.utils.BeanUtil;
import konrad.tools.usermanager.utils.ConfigUtil;
import konrad.tools.usermanager.utils.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kbotor on 09.07.16.
 */
public abstract class BaseController implements ServletContextAware {
    private static Logger baseLogger = LoggerFactory.getLogger(BaseController.class);

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
    public String fallbackMethod(){
        baseLogger.debug("fallbackMethod");
        return "404.html";
    }

    protected String selectView(String viewName) {
        baseLogger.debug("selectView: {}", viewName);
        try {
            URL fullPath = servletContext.getResource("/WEB-INF/templates/" + viewName + ".html");
            String view = "";
            if (fullPath != null) {
                view = viewName + ".html";
            } else {
                view = viewName + ".jsp";
            }
            baseLogger.debug("Returning view: {}", view);
            return view;
        } catch (MalformedURLException e) {
            baseLogger.error("Error locating template: {}", viewName, e);
        }
        return viewName;
    }
}

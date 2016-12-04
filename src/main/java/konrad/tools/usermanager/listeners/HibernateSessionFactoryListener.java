package konrad.tools.usermanager.listeners;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.internal.util.config.ConfigurationException;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-04.
 */
@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(HibernateSessionFactoryListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory sessionFactory = (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
        if(sessionFactory != null && !sessionFactory.isClosed()){
            logger.debug("Closing session factory");
            sessionFactory.close();
        }
        logger.debug("Released Hibernate session factory resource");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String hibernateConfigFile = "hibernate.cfg.xml";
        ServiceRegistry serviceRegistry;
        SessionFactory sessionFactory;

        if (sce.getServletContext().getInitParameter("hibernateConfigFile") != null) {
            hibernateConfigFile = sce.getServletContext().getInitParameter("hibernateConfigFile");
        }
        logger.debug("Trying to create Hibernate session factory using configuration file: {}", hibernateConfigFile);

        try {
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure(hibernateConfigFile) // configures settings from hibernate.cfg.xml
                    .build();
        } catch (ConfigurationException ex) {
            logger.trace("Cannot locate {} as a resource. Trying to open as a file.", hibernateConfigFile, ex);
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure(new File(hibernateConfigFile)) // configures settings from hibernate.cfg.xml
                    .build();
        }

        try {
            sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            sce.getServletContext().setAttribute("SessionFactory", sessionFactory);
        } catch (Exception e) {
            logger.error("Error creating Hibernate session factory", e);
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
        logger.info("Hibernate session factory configured successfully");
    }
}

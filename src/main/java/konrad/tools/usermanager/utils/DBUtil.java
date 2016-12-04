package konrad.tools.usermanager.utils;

import konrad.tools.usermanager.hibernate.*;
import konrad.tools.usermanager.models.RoleModel;
import konrad.tools.usermanager.models.UserModel;
import org.dozer.DozerBeanMapper;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-06.
 */
@Service
public class DBUtil {
    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private ServletContext servletContext;

    @Autowired
    private BeanUtil beanUtil;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    public List<UsersEntity> getAllUsers() {
        logger.debug("getAllUsers");
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getUsersEntityList(sessionFactory);
    }

    public List<RolesEntity> getAllRoles() {
        logger.debug("getAllRoles");
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getRolesEntityList(sessionFactory);
    }

    public UsersEntity getUsersByUsername(String userName) {
        logger.debug("getUsersByUsername: {}", userName);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getUsersEntityByUsername(sessionFactory, userName);
    }

    public void saveRole(RoleModel role) {
        logger.debug("saveRole: {}", role);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setRoleName(role.getRoleName());
        saveRolesEntity(sessionFactory, rolesEntity);
    }

    public void saveUser(UserModel user) {
        logger.debug("saveUser: {}", user);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        UsersEntity usersEntity = dozerBeanMapper.map(user, UsersEntity.class);
        SortedSet<RolesEntity> rolesEntities = usersEntity.getRolesEntitySet();
        usersEntity.setRolesEntitySet(new TreeSet<RolesEntity>());
        saveUsersEntity(sessionFactory, usersEntity);
        for (RolesEntity rolesEntity: rolesEntities) {
            addUserToRole(sessionFactory, usersEntity.getUserName(), rolesEntity.getRoleName());
        }
    }

    public void updateUser(UserModel user) {
        logger.debug("updateUser: ", user);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        UsersEntity usersEntity = getUsersByUsername(user.getUserName());
        UsersEntity newUsersEntity = beanUtil.deepCopy(usersEntity);
        String password = user.getUserPass();
        if (user.isChangePassword()) {
            newUsersEntity.setUserPass(password);
        }
        if (user.isChangeRoles()) {
            newUsersEntity.getRolesEntitySet().clear();
            updateUser(sessionFactory, newUsersEntity);
            for (String roleName: user.getRoleNames()) {
                addUserToRole(sessionFactory, newUsersEntity.getUserName(), roleName);
            }
            return;
        }
        updateUser(sessionFactory, newUsersEntity);
    }

    public void deleteUser(UsersEntity usersEntity) {
        logger.debug("deleteUser: {}", usersEntity);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        usersEntity.getRolesEntitySet().clear();
        saveUsersEntity(sessionFactory, usersEntity);
        deleteUsersEntity(sessionFactory, usersEntity);
    }

    public void deleteRole(RolesEntity rolesEntity) {
        logger.debug("deleteRole: {}", rolesEntity);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        deleteRolesEntity(sessionFactory, rolesEntity);
    }

    public SortedSet<RolesEntity> getRolesEntitiesForUser(String userName) {
        logger.debug("getRolesEntitiesForUser: {}", userName);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getRolesEntitiesForUser(sessionFactory, userName);
    }

    public SortedSet<UsersEntity> getUsersEntitiesForRole(String roleName) {
        logger.debug("getUsersEntitiesForRole: {}", roleName);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getUsersEntitiesForRole(sessionFactory, roleName);
    }

    public RolesEntity getRolesEntityByRoleName(String roleName) {
        logger.debug("getRolesEntityByRoleName: {}", roleName);
        SessionFactory sessionFactory = (SessionFactory) servletContext.getAttribute("SessionFactory");
        return getRolesEntityByRoleName(sessionFactory, roleName);
    }

    private List<UsersEntity> getUsersEntityList(SessionFactory sessionFactory) {
        logger.debug("getUsersEntityList");
        Session session = null;
        Transaction tx = null;

        List<UsersEntity> usersEntityList = new LinkedList<>();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            usersEntityList = session.createQuery("from UsersEntity").list();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return usersEntityList;
    }

    private List<RolesEntity> getRolesEntityList(SessionFactory sessionFactory) {
        logger.debug("getRolesEntityList");
        Session session = null;
        Transaction tx = null;

        List<RolesEntity> rolesEntityList = new LinkedList<>();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            rolesEntityList = session.createQuery("from RolesEntity").list();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return rolesEntityList;
    }

    private UsersEntity getUsersEntityByUsername(SessionFactory sessionFactory, String userName) {
        logger.debug("getUsersEntityByUsername: {}", userName);
        Session session = null;
        Transaction tx = null;

        UsersEntity usersEntity = new UsersEntity();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            usersEntity = session.get(UsersEntity.class, userName);


        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return usersEntity;
    }

    private RolesEntity getRolesEntityByRoleName(SessionFactory sessionFactory, String roleName) {
        logger.debug("getRolesEntityByRoleName: {}, {}", roleName, sessionFactory);
        Session session = null;
        Transaction tx = null;

        RolesEntity rolesEntity = new RolesEntity();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            rolesEntity = session.get(RolesEntity.class, roleName);


        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return rolesEntity;
    }

    private SortedSet<RolesEntity> getRolesEntitiesForUser(SessionFactory sessionFactory, String userName) {
        logger.debug("getRolesEntitiesForUser: {}", userName);
        Session session = null;
        Transaction tx = null;

        List<RolesEntity> rolesEntities = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            Query query = session.createQuery("from RolesEntity r join r.usersEntitySet u where u.userName = :userName");
            query.setString("userName", userName);
            rolesEntities = query.list();


        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        SortedSet<RolesEntity> rolesEntitySortedSet = new TreeSet<>();
        rolesEntitySortedSet.addAll(rolesEntities);

        return rolesEntitySortedSet;
    }

    private SortedSet<UsersEntity> getUsersEntitiesForRole(SessionFactory sessionFactory, String roleName) {
        logger.debug("getUsersEntitiesForRole: {}", roleName);
        Session session = null;
        Transaction tx = null;

        List<UsersEntity> usersEntities = new ArrayList<>();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data
            Query query = session.createQuery("from UsersEntity u join u.rolesEntitySet r where r.roleName = :roleName");
            query.setString("roleName", roleName);
            usersEntities = query.list();


        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        SortedSet<UsersEntity> usersEntitySortedSet = new TreeSet<>();
        usersEntitySortedSet.addAll(usersEntities);

        return usersEntitySortedSet;
    }

    private void saveRolesEntity(SessionFactory sessionFactory, RolesEntity rolesEntity) {
        logger.debug("saveRolesEntity: {}", rolesEntity);

        saveObject(sessionFactory, rolesEntity);
    }

    private void saveUsersEntity(SessionFactory sessionFactory, UsersEntity usersEntity) {
        logger.debug("saveUsersEntity: {}", usersEntity);

        saveObject(sessionFactory, usersEntity);
    }

    private void deleteUsersEntity(SessionFactory sessionFactory, UsersEntity usersEntity) {
        logger.debug("deleteUsersEntity: {}", usersEntity);

        deleteObject(sessionFactory, usersEntity);
    }

    private void deleteRolesEntity(SessionFactory sessionFactory, RolesEntity rolesEntity) {
        logger.debug("deleteRolesEntity: {}", rolesEntity);

        deleteObject(sessionFactory, rolesEntity);
    }

    private void updateUser(SessionFactory sessionFactory, UsersEntity usersEntity) {
        logger.debug("updateUser: {}", usersEntity);

        updateObject(sessionFactory, usersEntity);
    }

    private void addUserToRole(SessionFactory sessionFactory, String userName, String roleName) {
        logger.debug("addUserToRole: {} - {}", userName, roleName);
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            SQLQuery query = session.createSQLQuery("INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES (:userName, :roleName)");
            query.setString("userName", userName);
            query.setString("roleName", roleName);
            query.executeUpdate();
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void saveObject(SessionFactory sessionFactory, Object object) {
        logger.trace("saveObject: {}", object);
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(object);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void deleteObject(SessionFactory sessionFactory, Object object) {
        logger.trace("deleteObject: {}", object);
        Session session = null;
        Transaction tx = null;

        if (!(object instanceof IEntity)) {
            logger.error("Object {} is not an entity", object);
            return;
        }

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            IEntity entity = (IEntity) object;

            Object fromDB = session.load(entity.getClass(), entity.getID());
            session.delete(fromDB);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void updateObject(SessionFactory sessionFactory, Object object) {
        logger.trace("updateObject: {}", object);
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(object);
            tx.commit();
        } catch (Exception ex) {
            logger.error("SQL error occurred", ex);

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

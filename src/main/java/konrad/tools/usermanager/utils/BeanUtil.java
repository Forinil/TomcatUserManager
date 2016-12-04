package konrad.tools.usermanager.utils;

import konrad.tools.usermanager.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-12.
 */
@Component
public class BeanUtil {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    public <T> T deepCopy(T oldObj) {
        logger.debug("deepCopy", oldObj);
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            return (T) ois.readObject();
        } catch (IOException e) {
            logger.error("I/O Error", e);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found", e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                logger.error("Error closing outpit stream", e);
            }
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                logger.error("Error closing input stream", e);
            }
        }
        return null;
    }
}

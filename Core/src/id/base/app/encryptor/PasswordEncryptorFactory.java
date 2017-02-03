package id.base.app.encryptor;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Factory object for getting a PasswordEncyrptor
 * @version 0.1
 */
public class PasswordEncryptorFactory {
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncryptorFactory.class);

    private static ResourceBundle setting = ResourceBundle.getBundle("encryptor");

    private static String ENCRYPTOR_CLASS_NAME;
    private static PasswordEncryptor encryptor = null;

    /**
     * Get application password encryptor
     * @return password encyrptor
     */
    public static PasswordEncryptor getEncryptor() {
        if (encryptor == null) {
            readEncryptorConfig();
            logger.debug("Initializing Encryptor with Class Name: {}", ENCRYPTOR_CLASS_NAME);
            try {
                encryptor = (PasswordEncryptor) Class.forName(ENCRYPTOR_CLASS_NAME).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return encryptor;
    }

    /**
     * Initiate Encyrptor.
     * Read encryptor config from resource bundle.
     * The properties/config file should be put in
     * package id.base.app.collsys.properties with name:
     * Encryptor.properties.
     * <b>Should be called from static class initialization</b>
     */
    private static void readEncryptorConfig() {
        ENCRYPTOR_CLASS_NAME = setting.getString("encryptor.class");
    }



}


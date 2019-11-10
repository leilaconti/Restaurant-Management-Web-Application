package team14.arms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * `HasLogger` is an interface that provides logging capabilities for anyone
 * who needs a logger to operate in a serializable environment without being
 * static.
 */
public interface HasLogger {
    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}

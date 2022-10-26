package org.aldebaran.common.utils.run;

import java.io.Serializable;

/**
 * Callable.
 *
 * @author Alejandro
 *
 */
@FunctionalInterface
public interface Callable<V> extends java.util.concurrent.Callable<V>, Serializable {

}

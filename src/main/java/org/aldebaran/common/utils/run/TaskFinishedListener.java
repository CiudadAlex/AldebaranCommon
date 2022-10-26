package org.aldebaran.common.utils.run;

import java.util.function.Function;

/**
 * TaskFinishedListener.
 *
 * @author Alejandro
 *
 */
public class TaskFinishedListener {

    private final Function<String, Void> onTaskFinished;

    /**
     * Constructor for TaskFinishedListener.
     *
     * @param onTaskFinished
     *            onTaskFinished
     */
    public TaskFinishedListener(final Function<String, Void> onTaskFinished) {
        this.onTaskFinished = onTaskFinished;
    }

    /**
     * Reports a finished task.
     *
     * @param idTask
     *            idTask
     */
    public synchronized void reportTaskFinished(final String idTask) {
        onTaskFinished.apply(idTask);
    }
}

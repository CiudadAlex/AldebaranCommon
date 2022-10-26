package org.aldebaran.common.utils.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ParallelExecutor.
 *
 * @author Alejandro
 *
 */
public final class ParallelExecutor {

    private ParallelExecutor() {
    }

    /**
     * Executes in parallel.
     *
     * @param <T>
     *            type of object to return
     * @param nThreads
     *            nThreads
     * @param mapIdWorker
     *            mapIdWorker
     * @param taskFinishedListener
     *            taskFinishedListener
     * @return results of workers
     * @throws Exception
     */
    public static <T> Map<String, Object> execute(final int nThreads, final Map<String, Callable<?>> mapIdWorker,
            final TaskFinishedListener taskFinishedListener) throws Exception {

        final List<Callable<IdAndResultDTO>> listWorkers = new ArrayList<Callable<IdAndResultDTO>>();

        for (final String idJob : mapIdWorker.keySet()) {

            final Callable<?> job = mapIdWorker.get(idJob);

            final Callable<IdAndResultDTO> worker = () -> {

                final Object result = job.call();
                taskFinishedListener.reportTaskFinished(idJob);
                return new IdAndResultDTO(idJob, result);
            };

            listWorkers.add(worker);
        }

        final List<IdAndResultDTO> listIdAndResultDTO = execute(nThreads, listWorkers);

        final Map<String, Object> mapResults = new HashMap<String, Object>();

        for (final IdAndResultDTO idAndResultDTO : listIdAndResultDTO) {
            mapResults.put(idAndResultDTO.getId(), idAndResultDTO.getResult());
        }

        return mapResults;
    }

    /**
     * Executes in parallel.
     *
     * @param <T>
     *            type of object to return
     * @param nThreads
     *            nThreads
     * @param listWorkers
     *            listWorkers
     * @return results of workers
     * @throws Exception
     */
    public static <T> List<T> execute(final int nThreads, final List<Callable<T>> listWorkers) throws Exception {

        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        final List<Future<T>> listFuture = new ArrayList<Future<T>>();

        for (final Callable<T> worker : listWorkers) {
            final Future<T> future = executorService.submit(worker);
            listFuture.add(future);
        }

        final List<T> listResults = new ArrayList<T>();

        for (final Future<T> future : listFuture) {
            final T result = future.get();
            listResults.add(result);
        }

        return listResults;
    }
}

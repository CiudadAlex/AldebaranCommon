package org.aldebaran.common.utils.serviceinvokers;

import org.aldebaran.common.constants.CommonConstants;
import org.aldebaran.common.dto.JobDTO;
import org.aldebaran.common.dto.ProgressAndResultDTO;
import org.aldebaran.common.dto.RedistributionOfJARsDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * AgentServiceInvoker.
 *
 * @author Alejandro
 *
 */
public final class AgentServiceInvoker {

    private AgentServiceInvoker() {
    }

    /**
     * Redistribution of JARs.
     *
     * @param password
     *            password
     * @param redistributionOfJARsDTO
     *            redistributionOfJARsDTO
     * @param locationToSend
     *            locationToSend
     */
    public static void redistributeJars(final String password, final RedistributionOfJARsDTO redistributionOfJARsDTO,
            final String locationToSend) {

        final RestTemplate restTemplate = buildRestTemplate();
        final HttpHeaders headers = buildHeaders(password);

        final HttpEntity<RedistributionOfJARsDTO> entity = new HttpEntity<RedistributionOfJARsDTO>(redistributionOfJARsDTO, headers);
        restTemplate.postForEntity("http://" + locationToSend + "/redistributeJars/", entity, Void.class);
    }

    private static RestTemplate buildRestTemplate() {

        final RestTemplateBuilder builder = new RestTemplateBuilder();
        final RestTemplate restTemplate = builder.build();
        return restTemplate;
    }

    private static HttpHeaders buildHeaders(final String password) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set(CommonConstants.HEADER_SERVICE_PASSWORD, password);
        return headers;
    }

    /**
     * Checks if the agent is up and running.
     *
     * @param locationAgent
     *            locationAgent
     * @return if the agent is up and running
     */
    public static boolean isAlive(final String locationAgent) {

        final RestTemplate restTemplate = buildRestTemplate();

        final HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());

        final String name = "Aldebaran";

        final String url = "http://" + locationAgent + "/echo/" + name;
        final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        final String responseStr = response.getBody();

        return responseStr.equals("echo " + name);
    }

    /**
     * Checks if the JARs are up to date.
     *
     * @param password
     *            password
     * @param applicationName
     *            applicationName
     * @param hash
     *            hash
     * @param locationAgent
     *            locationAgent
     * @return if the JARs are up to date
     */
    public static boolean checkJarsUpToDate(final String password, final String applicationName, final Integer hash,
            final String locationAgent) {

        final RestTemplate restTemplate = buildRestTemplate();
        final HttpHeaders headers = buildHeaders(password);

        final HttpEntity<Void> entity = new HttpEntity<Void>(headers);

        final String url = "http://" + locationAgent + "/checkJarsUpToDate/" + applicationName + "/" + hash;
        final ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);

        return response.getBody();
    }

    /**
     * Executed the worker.
     *
     * @param password
     *            password
     * @param jobDTO
     *            jobDTO
     * @param locationToSend
     *            locationToSend
     */
    public static void executeWorker(final String password, final JobDTO jobDTO, final String locationToSend) {

        final RestTemplate restTemplate = buildRestTemplate();
        final HttpHeaders headers = buildHeaders(password);

        final HttpEntity<JobDTO> entity = new HttpEntity<JobDTO>(jobDTO, headers);
        restTemplate.postForEntity("http://" + locationToSend + "/executeWorker/", entity, Void.class);
    }

    /**
     * Checks the progress of the task.
     *
     * @param password
     *            password
     * @param applicationName
     *            applicationName
     * @param locationAgent
     *            locationAgent
     * @return the progress of the task
     */
    public static ProgressAndResultDTO checkProgress(final String password, final String applicationName, final String locationAgent) {

        final RestTemplate restTemplate = buildRestTemplate();
        final HttpHeaders headers = buildHeaders(password);

        final HttpEntity<Void> entity = new HttpEntity<Void>(headers);

        final String url = "http://" + locationAgent + "/checkProgress/" + applicationName;
        final ResponseEntity<ProgressAndResultDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                ProgressAndResultDTO.class);

        return response.getBody();
    }

}

package io.pivotal.pal.tracker.timesheets;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String endpoint;
    private Map<Long, ProjectInfo> cache = new HashMap<>();

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo pi = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);
        cache.put(projectId, pi);
        return pi;
    }

    public ProjectInfo getProjectFromCache(long projectId) {
        ProjectInfo pi = cache.get(projectId);
        if (pi == null) {
            pi = new ProjectInfo(false);
        }
        return pi;
    }
}

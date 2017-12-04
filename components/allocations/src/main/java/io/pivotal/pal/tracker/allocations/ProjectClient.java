package io.pivotal.pal.tracker.allocations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String registrationServerEndpoint;
    private Map<Long, ProjectInfo> cache = new HashMap<>();

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations= restOperations;
        this.registrationServerEndpoint = registrationServerEndpoint;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo pi = restOperations.getForObject(registrationServerEndpoint + "/projects/" + projectId, ProjectInfo.class);
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

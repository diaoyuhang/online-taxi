package com.diao.apipassenger.gray;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:47 2020/11/24
 */
public class GrayRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {

        return choose(getLoadBalancer(), key);
    }

    private Server choose(ILoadBalancer loadBalancer, Object key) {
        Server server = null;
        List<Server> serverList = loadBalancer.getReachableServers();
        while (server == null) {
            Map<String, String> map = RibbonParameters.get();
            String version = "";
            if (map != null && map.containsKey("version")) {
                version = map.get("version");
            }
            System.out.println("当前rule的version:" + version);
            for (int i = 0; i < serverList.size(); i++) {
                Server server1 = serverList.get(i);
                Map<String, String> metadata = ((DiscoveryEnabledServer) server1).getInstanceInfo().getMetadata();
                if (metadata.get("version").trim().equals(version)) {
                    server = server1;
                    return server;
                }
            }

        }
        return server;
    }
}

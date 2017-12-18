package org.ametiste.notification.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @since 0.1.0
 */
@ConfigurationProperties(ElasticsearchClientProperties.PREFIX)
public class ElasticsearchClientProperties {

    public static final String PREFIX = "org.ametiste.sns.elastic";

    private Cluster cluster = new Cluster();

    private Client client = new Client();

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public static class Cluster {

        private String name = "elasticsearch";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Client {

        private String addresses = "127.0.0.1:9300";

        private Boolean transportSniff = Boolean.TRUE;

        private Boolean ignoreClusterName = Boolean.FALSE;

        private String pingTimeout = "5s";

        private String nodesSamplerInterval = "5s";

        public String getAddresses() {
            return addresses;
        }

        public void setAddresses(String addresses) {
            this.addresses = addresses;
        }

        public Boolean getTransportSniff() {
            return transportSniff;
        }

        public void setTransportSniff(Boolean transportSniff) {
            this.transportSniff = transportSniff;
        }

        public Boolean getIgnoreClusterName() {
            return ignoreClusterName;
        }

        public void setIgnoreClusterName(Boolean ignoreClusterName) {
            this.ignoreClusterName = ignoreClusterName;
        }

        public String getPingTimeout() {
            return pingTimeout;
        }

        public void setPingTimeout(String pingTimeout) {
            this.pingTimeout = pingTimeout;
        }

        public String getNodesSamplerInterval() {
            return nodesSamplerInterval;
        }

        public void setNodesSamplerInterval(String nodesSamplerInterval) {
            this.nodesSamplerInterval = nodesSamplerInterval;
        }
    }
}

package org.ametiste.notification.infrastructure.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

/**
 *
 * @since 0.1.0
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchClientProperties.class)
public class ElasticsearchClientConfiguration {

    @Autowired
    private ElasticsearchClientProperties props;

    @Bean(destroyMethod = "close")
    public Client elasticsearchClient() throws Exception {
        final TransportClient client = new PreBuiltTransportClient(settings());
        enrichWithTransportAddresses(client::addTransportAddress);
        return client;
    }


    private Settings settings() {
        return Settings.builder()
                .put("cluster.name", props.getCluster().getName())
                .put("client.transport.sniff", props.getClient().getTransportSniff())
                .put("client.transport.ignore_cluster_name", props.getClient().getIgnoreClusterName())
                .put("client.transport.ping_timeout", props.getClient().getPingTimeout())
                .put("client.transport.nodes_sampler_interval", props.getClient().getNodesSamplerInterval())
                .build();
    }

    private void enrichWithTransportAddresses(Consumer<TransportAddress> consumer) throws Exception {
        Assert.hasText(props.getClient().getAddresses(), "Client addresses settings missing.");
        for (String address : props.getClient().getAddresses().split(",")) {
            consumer.accept(toAddress(address));
        }
    }

    private static TransportAddress toAddress(String address) throws UnknownHostException {
        String hostName = substringBeforeLast(address, ":");
        String port = substringAfterLast(address, ":");

        Assert.hasText(hostName, "Missing host name in client addresses");
        Assert.hasText(port, "Missing port in client addresses");

        return new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
    }
}

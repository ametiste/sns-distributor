package org.ametiste.notification.sweeper.application.connector;

import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.ametiste.notification.sweeper.model.IndicesStats;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;

import java.util.Map;

/**
 * Created by ametiste on 7/2/15.
 */
public class ClientElasticConnector implements ElasticConnector {

    private Client client;
    private String indexPrefixWildcard;
    private String indexPrefix;

    public ClientElasticConnector(Client client, String indexPrefix) {

        this.client = client;
        this.indexPrefixWildcard = indexPrefix + "*";
        this.indexPrefix = indexPrefix;
    }

    @Override
    public IndicesStats stats() {
        IndicesStatsResponse indicesStatsResponse = client.admin().indices().prepareStats(indexPrefixWildcard).setStore(true).execute().actionGet();
        IndicesStats indicesStats = new IndicesStats(indicesStatsResponse.getTotal().getStore().getSizeInBytes());

        for(Map.Entry<String, IndexStats> entry: indicesStatsResponse.getIndices().entrySet()) {
            indicesStats.addIndexStat(entry.getKey(), entry.getValue().getTotal().getStore().getSizeInBytes());
        }

        return indicesStats;
    }

    @Override
    public void deleteByName(String name) {
        if(name.startsWith(indexPrefix)) {
            client.admin().indices().prepareDelete(name).execute().actionGet();
        }
        else {
            throw new IllegalArgumentException("Index with this name isnt supposes to be deleted. Name: " + name);
        }

    }

    @Override
    public IndexStatistics statsByName(String name) {
        IndicesExistsResponse indicesExistsResponse = client.admin().indices().prepareExists(name).execute().actionGet();
        if (indicesExistsResponse.isExists()) {
            IndicesStatsResponse indicesStatsResponse = client.admin().indices().prepareStats(name).setStore(true).execute().actionGet();
            return new IndexStatistics(name, indicesStatsResponse.getTotal().getStore().getSizeInBytes());
        }
        throw new IllegalArgumentException("Index with this name doesnt exist");

    }

    @Override
    public String getIndexWildcard() {
        return indexPrefixWildcard;
    }
}

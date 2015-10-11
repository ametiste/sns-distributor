package org.ametiste.notification.sweeper.interfaces.dto;

import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.apache.commons.io.FileUtils;

/**
 * Created by ametiste on 7/2/15.
 */
public class IndexStatDTO {

    private String indexName;
    private String indexSize;

    public IndexStatDTO(IndexStatistics stat) {
        this.indexName = stat.getIndexName();
        this.indexSize = FileUtils.byteCountToDisplaySize(
                stat.getStoreSize());
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndexSize() {
        return indexSize;
    }
}

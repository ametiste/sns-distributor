package org.ametiste.notification.sweeper.interfaces.dto;

import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.ametiste.notification.sweeper.model.IndicesStats;
import org.apache.commons.io.FileUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ametiste on 7/2/15.
 */
public class IndicesStatDTO {

    private String totalSize;
    private List<IndexStatDTO> details;

    public IndicesStatDTO(IndicesStats stats, Comparator<IndexStatistics> comparator) {
        this.totalSize = FileUtils.byteCountToDisplaySize(stats.getTotal());
        details = stats.getDetails().stream()
                .sorted(comparator)
                .map(IndexStatDTO::new).collect(Collectors.toList());
    }


    public String getTotalSize() {
        return totalSize;
    }

    public List<IndexStatDTO> getDetails() {
        return details;
    }
}

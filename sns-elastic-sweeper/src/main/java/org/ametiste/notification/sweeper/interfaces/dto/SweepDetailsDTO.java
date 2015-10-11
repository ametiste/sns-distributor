package org.ametiste.notification.sweeper.interfaces.dto;

import org.ametiste.notification.sweeper.model.SweepDetails;
import org.apache.commons.io.FileUtils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by ametiste on 7/3/15.
 */
public class SweepDetailsDTO {

    private String date;
    private String indexName;
    private String capacity;
    private String indexSize;
    private String totalSize;

    public SweepDetailsDTO(SweepDetails details) {
        date = DateFormat.getDateTimeInstance().format(new Date(details.getTimestamp()));
        indexName = details.getIndexName();
        indexSize = FileUtils.byteCountToDisplaySize(details.getIndexSize());
        capacity = FileUtils.byteCountToDisplaySize(details.getCapacity());
        totalSize = FileUtils.byteCountToDisplaySize(details.getTotalSize());
    }

    public String getDate() {
        return date;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndexSize() {
        return indexSize;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTotalSize() {
        return totalSize;
    }
}

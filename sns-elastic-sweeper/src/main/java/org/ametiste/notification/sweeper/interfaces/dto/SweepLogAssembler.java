package org.ametiste.notification.sweeper.interfaces.dto;

import org.ametiste.notification.sweeper.model.SweepLog;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ametiste on 7/3/15.
 */
public class SweepLogAssembler {

    public static List<SweepLogDTO> assemble(List<SweepLog> logs) {
       return logs.stream()
               .map(SweepLogDTO::new)
               .collect(Collectors.toList());
    }
}

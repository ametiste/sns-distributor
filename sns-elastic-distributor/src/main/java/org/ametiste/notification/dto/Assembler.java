package org.ametiste.notification.dto;


import org.ametiste.notification.model.Report;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by atlantis on 5/29/15.
 */
public class Assembler {

    public static Report assemble(ReportDTO dto) {

        UUID id = UUID.fromString(dto.getId());

        return new Report(id, dto.getDate(), dto.getType(), dto.getSender(), dto.getContent());
    }

    public static Report randomAssemble() {

        UUID id = UUID.randomUUID();
        return new Report(id, System.currentTimeMillis(),
                "RANDOM_TEST_REPORT", "RANDOM_TEST_REPORT_SENDER", new HashMap());
    }
 }

package com.profittracker.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class PlayerVerificationDto {
    @Getter
    @Setter
    private List<String> leaguePlayerIds;

    @Getter
    @Setter
    private String leaguePlayerGameName;

    @Getter
    @Setter
    private String screenshot;

    @Getter
    @Setter
    private Date timestamp;
}

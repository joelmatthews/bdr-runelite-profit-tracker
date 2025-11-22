package com.profittracker.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PlayerVerificationDto {
    @Getter
    @Setter
    private String leaguePlayerId;

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

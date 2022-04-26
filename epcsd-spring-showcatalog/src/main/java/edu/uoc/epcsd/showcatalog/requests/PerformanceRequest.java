package edu.uoc.epcsd.showcatalog.requests;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRequest {

    private Date date;

    private Time time;

    private String streamingURL;

}
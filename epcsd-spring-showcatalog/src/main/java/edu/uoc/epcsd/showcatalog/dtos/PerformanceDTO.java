package edu.uoc.epcsd.showcatalog.dtos;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDTO {

    private Date date;

    private Time time;

    private String streamingURL;

}

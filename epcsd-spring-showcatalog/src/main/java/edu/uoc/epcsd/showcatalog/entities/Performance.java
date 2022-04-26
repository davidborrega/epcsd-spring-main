package edu.uoc.epcsd.showcatalog.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

@Embeddable
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @Column(name = "streamingURL", nullable = true)
    private String streamingURL;

    @Column(name = "remainingSeats", nullable = false)
    private int remainingSeats;

    @Column(name = "status", nullable = false)
    private boolean status;

}

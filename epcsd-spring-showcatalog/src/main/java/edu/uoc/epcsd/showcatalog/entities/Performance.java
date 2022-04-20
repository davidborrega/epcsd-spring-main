package edu.uoc.epcsd.showcatalog.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.sql.Date;

@Embeddable
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

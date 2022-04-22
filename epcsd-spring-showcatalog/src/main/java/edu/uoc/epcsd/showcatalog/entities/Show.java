package edu.uoc.epcsd.showcatalog.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "price", nullable = false, scale = 2)
    private double price;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "onSaleDate", nullable = true)
    private Date onSaleDate;

    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, updatable = false)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "show", orphanRemoval = true)
    private List<Performance> performances;

    public void addPerformance(Performance performance) {
        this.performances.add(performance);
    }

}

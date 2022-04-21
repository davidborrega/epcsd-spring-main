package edu.uoc.epcsd.showcatalog.requests;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequest {

    private String name;

    private String description;

    private String image;

    private double price;

    private int duration;

    private int capacity;

    private Long categoryId;

}

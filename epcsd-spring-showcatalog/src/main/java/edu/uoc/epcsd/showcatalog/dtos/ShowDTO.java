package edu.uoc.epcsd.showcatalog.dtos;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {

    private Long id;

    private String name;

    private String description;

    private String image;

    private double price;

    private int duration;

    private int capacity;

    private CategoryDTO category;

}

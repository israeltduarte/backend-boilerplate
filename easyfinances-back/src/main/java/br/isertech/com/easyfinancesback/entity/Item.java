package br.isertech.com.easyfinancesback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends RepresentationModel<Item> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "item-uuid-generator")
    @GenericGenerator(
            name = "item-uuid-generator",
            strategy = "br.isertech.com.easyfinancesback.util.IserUUIDGenerator",
            parameters = @Parameter(name = "prefix", value = "Item")
    )
    private String id;
    private String description;
    private String measurement;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean isInStock;

    @ManyToOne(targetEntity = ITUser.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    @ToString.Exclude
    private ITUser user;

}


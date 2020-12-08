package BlinovMS.ConCash.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "currency", schema = "public", catalog = "concash")

public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "num_code")
    private Integer numCode;
    @Column(name = "char_code")
    private String charCode;
    @Column(name = "nominal")
    private Integer nominal;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "date")
    private LocalDate date;

}

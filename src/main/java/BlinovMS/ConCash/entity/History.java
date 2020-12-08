package BlinovMS.ConCash.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "history", schema = "public", catalog = "concash")

public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "original_sum")
    private BigDecimal originalSum;

    @Column(name = "result_sum")
    private BigDecimal resultSum;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateCourse date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

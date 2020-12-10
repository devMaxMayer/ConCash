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

    @ManyToOne
    @JoinColumn(name = "from_currency_id")
    private Currency fromCurrency;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency toCurrency;

    @Column(name = "original_sum")
    private Integer originalSum;

    @Column(name = "result_sum")
    private BigDecimal resultSum;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateCourse date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

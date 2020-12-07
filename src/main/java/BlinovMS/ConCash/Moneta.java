package BlinovMS.ConCash;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class Moneta {
        private String id;
        private String date;
        private Integer numCode;
        private String charCode;
        private Integer nominal;
        private String name;
        private BigDecimal value;

    @Override
    public String toString() {
        return "Moneta{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", numCode='" + numCode + '\'' +
                ", charCode='" + charCode + '\'' +
                ", nominal='" + nominal + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

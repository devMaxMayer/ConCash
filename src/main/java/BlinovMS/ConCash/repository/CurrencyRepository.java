package BlinovMS.ConCash.repository;

import BlinovMS.ConCash.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findByName(String name);
}

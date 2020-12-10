package BlinovMS.ConCash.RESTController;


import BlinovMS.ConCash.dto.BaseDto;
import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import BlinovMS.ConCash.service.CurrencyService;
import BlinovMS.ConCash.service.HistoruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class ConvertController {
    private final CurrencyService currencyService;
    private final HistoruService historuService;

    @Autowired
    public ConvertController(CurrencyService currencyService, HistoruService historuService) {
        this.currencyService = currencyService;
        this.historuService = historuService;
    }

    @GetMapping("/all")
    public List<Currency> all(){
        currencyService.checkCourse();
     return currencyService.getList();
    }

    @PostMapping("/convert")
    public BigDecimal convert(
            @AuthenticationPrincipal User user,
            @RequestBody  History history
            ) {
        History result = currencyService.convert(history, user);

        return result.getResultSum();
    }

    @GetMapping("/history")
    public Integer history(@RequestBody BaseDto userId){
        //return historuService.getAll(user);
        return userId.getId();
    }
}

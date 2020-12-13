package BlinovMS.ConCash.RESTController;


import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.service.CurrencyService;
import BlinovMS.ConCash.service.HistoruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @PostMapping("/user/convert")
    public BigDecimal convert(
            HttpServletRequest user,
            @RequestBody  History history
            ) {
        History result = currencyService.convert(history, user);

        return result.getResultSum();
    }

    @GetMapping("/user/history")
    public List<History> history(HttpServletRequest req){
        String username = req.getUserPrincipal().getName();

        return historuService.getAll(username);
    }
}

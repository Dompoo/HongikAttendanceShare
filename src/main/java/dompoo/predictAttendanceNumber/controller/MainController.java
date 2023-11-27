package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NumberService numberService;

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    @PostMapping("/refresh/user")
    public void refreshByUser() {
        numberService.transactionRefresh();
    }

    //24시간마다 호출되어야 함
    @PostMapping("/refresh/24h")
    public void refreshNumberRepository() {
        numberService.numberRefresh();
        numberService.emptyTransaction();
    }

    @PostMapping("/refresh/1h30m")
    public void refreshTransaction() {
        numberService.emptyTransaction();
    }
}

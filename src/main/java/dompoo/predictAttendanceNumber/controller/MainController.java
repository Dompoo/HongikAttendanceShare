package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NumberService numberService;

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    @GetMapping("/getNumber")
    public String numberGet() {
        return "find_number_form";
    }

    @GetMapping("/setNumber")
    public String numberSet() {
        return "register_number_form";
    }

    @GetMapping("/refresh")
    public void refreshByUser() {
        numberService.transactionRefresh();
    }
}

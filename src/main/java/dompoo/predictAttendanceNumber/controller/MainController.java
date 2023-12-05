package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NumberService numberService;

    @GetMapping("/")
    public String root() {
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String main() {
        return "main";
    }

    @GetMapping("/getNumber")
    public String numberGet(Model model, NumberSearchRequest request) {
        model.addAttribute("request", request);
        return "find_number_form";
    }

    @GetMapping("/setNumber")
    public String numberSet(Model model, NumberCreateRequest request) {
        model.addAttribute("request", request);
        return "register_number_form";
    }

    @GetMapping("/refresh")
    public void refreshByUser() {
        numberService.transactionRefresh();
    }
}

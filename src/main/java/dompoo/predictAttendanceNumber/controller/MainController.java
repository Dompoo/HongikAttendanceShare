package dompoo.predictAttendanceNumber.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String main() {
        return "main";
    }
}

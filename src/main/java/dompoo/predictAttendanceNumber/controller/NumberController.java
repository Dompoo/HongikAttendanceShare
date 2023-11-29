package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import dompoo.predictAttendanceNumber.service.NumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class NumberController {

    private final NumberService numberService;

    @PostMapping("/attnumber/find")
    public String getNumber(Model model, @RequestParam String classNum) {
        NumberResponse findNumber = numberService.getNumber(new NumberSearchRequest(classNum));
        model.addAttribute("findNumber", findNumber);
        return "display_number";
    }

    @GetMapping("/attnumber/retry")
    public String getNumberRefresh(Model model, @RequestBody @Valid NumberSearchRequest request) {
        numberService.transactionRefresh();
        NumberResponse findNumber = numberService.getNumber(request);
        model.addAttribute("findNumber", findNumber);
        return "display_number";
    }

    @PostMapping("/attnumber")
    public String inputNumber(@RequestBody @Valid NumberCreateRequest request) {
        numberService.createNumber(request);
        return "main";
    }
}

package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import dompoo.predictAttendanceNumber.service.NumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class NumberController {

    private final NumberService numberService;

    @GetMapping("/attnumber")
    public NumberResponse getNumber(@RequestBody @Valid NumberSearchRequest request) {
        return numberService.getNumber(request);
    }

    @PostMapping("/attnumber")
    public void inputNumber(@RequestBody @Valid NumberCreateRequest request) {
        numberService.createNumber(request);
    }
}

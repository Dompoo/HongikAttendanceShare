package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.GetNumberRequest;
import dompoo.predictAttendanceNumber.service.PossibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final PossibilityService posService;

    @GetMapping("/possibility")
    public void getNumber(@RequestBody GetNumberRequest request) {
        posService.getNumber(request);
    }
}

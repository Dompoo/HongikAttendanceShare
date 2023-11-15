package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RandomController {

    private final RandomService randomService;
}

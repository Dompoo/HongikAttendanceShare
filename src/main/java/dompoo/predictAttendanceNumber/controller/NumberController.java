package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import dompoo.predictAttendanceNumber.service.NumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NumberController {

    private final NumberService numberService;

    @PostMapping("/attnumber/find")
    public String getNumber(Model model, @ModelAttribute @Valid NumberSearchRequest request) {
        log.info("출결번호 검색, 학수번호 : {}", request.getClassNum());

        NumberResponse findNumber = numberService.getNumber(request);
        model.addAttribute("findNumber", findNumber);
        log.info("출결번호 검색 성공, 출결번호 : {}", findNumber.getNumber());

        return "display_number";
    }

    @PostMapping("/attnumber/retry")
    public String getNumberRefresh(Model model, @ModelAttribute @Valid NumberSearchRequest request) {
        log.info("출결번호 검색, 학수번호 : {}", request.getClassNum());

        numberService.refreshTransaction();
        NumberResponse findNumber = numberService.getNumber(request);
        model.addAttribute("findNumber", findNumber);
        model.addAttribute("classNumber", request.getClassNum());
        log.info("출결번호 검색 성공, 출결번호 : {}", findNumber.getNumber());

        return "display_number";
    }

    @PostMapping("/attnumber")
    public String inputNumber(@ModelAttribute @Valid NumberCreateRequest request) {
        log.info("출결번호 등록, 학수번호 : {} / 출결번호 : {}", request.getClassNum(), request.getNumber());
        numberService.createNumber(request);
        return "main";
    }
}

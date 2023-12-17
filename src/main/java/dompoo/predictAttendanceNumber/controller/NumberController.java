package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberFindFailResponse;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import dompoo.predictAttendanceNumber.service.MemberService;
import dompoo.predictAttendanceNumber.service.NumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/attnumber")
public class NumberController {

    private final NumberService numberService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/find")
    public String findNumber(Model model, NumberSearchRequest request) {
        model.addAttribute("numberSearchRequest", request);
        return "find_number_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/find")
    public String findNumber(Model model, @ModelAttribute @Valid NumberSearchRequest request, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "find_number_form";
        }

        NumberResponse findNumber = numberService.getNumber(request, principal.getName());

        if (findNumber.getClass().isInstance(NumberFindFailResponse.class)) {
            model.addAttribute("classNumber", request.getClassNum());
            return "display_number_fail";
        }

        model.addAttribute("numberResponse", findNumber);

        return "display_number";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String registerNumber(Model model, NumberCreateRequest request) {
        model.addAttribute("numberCreateRequest", request);

        return "register_number_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String registerNumber(@ModelAttribute @Valid NumberCreateRequest request, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "register_number_form";
        }

        numberService.createNumber(request, principal.getName());

        return "redirect:/";
    }
}

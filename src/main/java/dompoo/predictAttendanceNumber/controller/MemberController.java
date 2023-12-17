package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.MemberCreateRequest;
import dompoo.predictAttendanceNumber.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberCreateRequest request) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!request.getPassword().equals(request.getCheckPassword())) {
            bindingResult.rejectValue("passwordCheck", "passwordCheckFail", "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        memberService.registerMember(request);

        return "redirect:/";
    }

}

package dompoo.predictAttendanceNumber.controller;

import dompoo.predictAttendanceNumber.request.MemberCreateRequest;
import dompoo.predictAttendanceNumber.response.MemberResponse;
import dompoo.predictAttendanceNumber.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(Model model, MemberCreateRequest request) {
        model.addAttribute("memberCreateRequest", request);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid MemberCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!request.getPassword().equals(request.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordCheckFail", "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        memberService.registerMember(request);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/info")
    public String info(Model model, Principal principal) {
        MemberResponse memberResponse = memberService.getMemberInfo(principal.getName());
        model.addAttribute("memberResponse", memberResponse);
        return "member_info";
    }
}

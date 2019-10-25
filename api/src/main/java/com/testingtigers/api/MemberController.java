package com.testingtigers.api;

import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto memberToCreate) {
        return memberService.registerMember(memberToCreate);
    }
}

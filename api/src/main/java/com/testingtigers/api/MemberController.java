package com.testingtigers.api;

import com.testingtigers.domain.dtos.CreateAdminOrLibrarianDto;
import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final MemberService memberService;
    public static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto memberToCreate) {
        return memberService.registerMember(memberToCreate);
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_MEMBERS')")
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> viewAllMembers() {
        return memberService.viewAllMembers();
    }

    @PreAuthorize("hasAuthority('REGISTER_ADMIN')")
    @PostMapping(consumes = "application/json", path = "/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@RequestBody CreateAdminOrLibrarianDto adminToCreate) {
        memberService.registerAdmin(adminToCreate);
    }

    @PreAuthorize("hasAuthority('REGISTER_LIBRARIAN')")
    @PostMapping(consumes = "application/json", path = "/librarian")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLibrarian(@RequestBody CreateAdminOrLibrarianDto librarianToCreate) {
        memberService.registerLibrarian(librarianToCreate);
    }

}

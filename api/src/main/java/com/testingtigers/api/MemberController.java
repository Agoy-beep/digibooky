package com.testingtigers.api;

import com.testingtigers.domain.dtos.CreateAdminOrLibrarianDto;
import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.exceptions.EmptyFields;
import com.testingtigers.domain.exceptions.MemberNotFound;
import com.testingtigers.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        if(StringUtils.isEmpty(memberToCreate.getEmailAddress()) || StringUtils.isEmpty(memberToCreate.getInss()) ||
                StringUtils.isEmpty(memberToCreate.getLastName()) || StringUtils.isEmpty(memberToCreate.getCity())) {
            throw new EmptyFields(HttpStatus.BAD_REQUEST, "Some required fields don't have input for " +
                    "the member you are trying to create!");
        }
        logger.info("User created member with name: " + "\"" + memberToCreate.getFirstName() + "\"" + memberToCreate.getLastName() + "\"" + ".");
        return memberService.registerMember(memberToCreate);
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_MEMBERS')")
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> viewAllMembers() {
        logger.info("User looked for a list of all the members.");
        return memberService.viewAllMembers();
    }

    @PreAuthorize("hasAuthority('REGISTER_ADMIN')")
    @PostMapping(consumes = "application/json", path = "/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@RequestBody CreateAdminOrLibrarianDto adminToCreate) {
        if(StringUtils.isEmpty(adminToCreate.getEmail()) || StringUtils.isEmpty(adminToCreate.getFirstName()) ||
                StringUtils.isEmpty(adminToCreate.getLastName())) {
            throw new EmptyFields(HttpStatus.BAD_REQUEST, "Some required fields don't have input for " +
                    "the administrator you are trying to create!");
        }
        logger.info("An administrator was created.");
        memberService.registerAdmin(adminToCreate);
    }


    @PreAuthorize("hasAuthority('REGISTER_LIBRARIAN')")
    @PostMapping(consumes = "application/json", path = "/librarian")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLibrarian(@RequestBody CreateAdminOrLibrarianDto librarianToCreate) {
        if(StringUtils.isEmpty(librarianToCreate.getEmail()) || StringUtils.isEmpty(librarianToCreate.getFirstName()) ||
                StringUtils.isEmpty(librarianToCreate.getLastName())) {
            throw new EmptyFields(HttpStatus.BAD_REQUEST, "Some required fields don't have input for " +
                    "the librarian you are trying to create!");
        }
        logger.info("A librarian was created");
        memberService.registerLibrarian(librarianToCreate);
    }

    @ExceptionHandler(MemberNotFound.class)
    protected void memberNotFound(MemberNotFound ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for member that could not be found.");
    }

    @ExceptionHandler(EmptyFields.class)
    protected void fieldsAreEmpty(EmptyFields ex, HttpServletResponse response) throws IOException{
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User did not provide input for all the relevant fields. Message: " + ex.getMessage());
    }
}

package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/communityEmail")
@RequiredArgsConstructor
public class CommunityEmailController {

private final CommunityEmailService service;



    @GetMapping(params = "city")
    public ResponseEntity<List<String>> getEmails(@RequestParam String city) {


        log.debug("GET /communityEmail?city={}", city);

        List<String> emails = service.getEmailsByCity(city);
        log.info("Emails trouvés!!!!! :{}", emails.size());
        return ResponseEntity.ok(emails);
    }


}

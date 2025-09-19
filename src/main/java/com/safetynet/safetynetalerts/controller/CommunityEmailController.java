package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/communityEmail")
@RequiredArgsConstructor
public class CommunityEmailController {

private final CommunityEmailService communityEmailService;


}

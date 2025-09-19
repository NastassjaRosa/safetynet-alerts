package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.service.PersonInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/personInfolastName")
public class PersonInfoController {
    private final PersonInfoService personInfoService;

    @GetMapping
    public String getPersonInfoLastName() {
        return String;
    }
}

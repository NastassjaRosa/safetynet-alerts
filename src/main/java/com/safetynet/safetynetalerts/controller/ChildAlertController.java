package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * The type Child alert controller.
 */
@Slf4j

@RestController

@RequestMapping("/childAlert")
@RequiredArgsConstructor

public class ChildAlertController {

    private final ChildAlertService childAlertService;


    /**
     * Gets children.
     *
     * @param address the address
     * @return the children
     */
    @GetMapping
    public List<ChildAlertDTO> getChildren(@RequestParam String address) {
        return childAlertService.getChildrenByAddress(address);
    }

}

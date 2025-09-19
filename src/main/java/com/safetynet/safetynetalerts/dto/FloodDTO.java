package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class FloodDTO {

    private Map<String, List<FirePersonDTO>> households;
}

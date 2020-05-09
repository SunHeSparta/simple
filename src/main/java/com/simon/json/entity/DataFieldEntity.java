package com.simon.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Create by SunHe on 2020/4/9
 */
@Data
@AllArgsConstructor
public class DataFieldEntity {

    private String className;
    private String dataFieldMapNames;
    private String dataFieldNewlyMapNames;
    private DataFieldHandleMethod dataFieldHandleMethods;

}

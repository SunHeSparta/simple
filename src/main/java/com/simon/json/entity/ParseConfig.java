package com.simon.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Create by SunHe on 2020/4/9
 */
@Data
@AllArgsConstructor
public class ParseConfig {

    private Integer minRowsNum;
    private Integer minColumnsNum;
    private String delimiter;
    private String subtotalPosition;
    private Integer subtotalIndex;
    private String subtotalFieldMapNames;
    private Integer dataFieldStart;
    private Integer dataFieldEnd;
    private List<DataFieldEntity> dataFieldEntities;

}

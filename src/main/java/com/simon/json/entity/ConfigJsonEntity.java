package com.simon.json.entity;

import lombok.Data;

import java.util.List;

/**
 * Create by SunHe on 2020/4/9
 */
@Data
public class ConfigJsonEntity {

    private List<Account> accounts;
    private DownloadConfig downloadConfig;
    private ParseConfig parseConfig;


}

package com.simon.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Create by SunHe on 2020/4/9
 */
@Data
@AllArgsConstructor
public class Account {

    private String host;
    private String port;
    private String username;
    private String password;

}


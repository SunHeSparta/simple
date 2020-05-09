package com.simon.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Create by SunHe on 2020/4/9
 */
@Data
@AllArgsConstructor
public class DownloadConfig {

    private String downloadPath;
    private String secretAlgorithm;
    private String secretKey;
    private List<String> downloadFiles;


}

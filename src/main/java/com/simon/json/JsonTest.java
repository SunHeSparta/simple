package com.simon.json;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.simon.json.entity.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by SunHe on 2020/4/9
 */
public class JsonTest {

    private static final String jsonPath = "/Users/edz/workspace/simple/src/main/resources/json";
    public static final String UTF_8 = "UTF-8";

    public static void main(String[] args) {
        File f = new File(jsonPath);
        File[] files = f.listFiles();
        assert files != null;
        for (File file : files) {
            try {
                String jsonString = FileUtils.readFileToString(file, UTF_8);
                Gson gson = new Gson();
                ConfigJsonEntity configJsonEntity = gson.fromJson(jsonString, ConfigJsonEntity.class);
                System.out.println(configJsonEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        ConfigJsonEntity configJsonEntity = new ConfigJsonEntity();
//
//        configJsonEntity.setAccounts(Lists.newArrayList(
//                new Account("127.0.0.1", "8080", "root", "root"),
//                new Account("192.168.1.1", "8088", "admin", "admin")));
//        configJsonEntity.setDownloadConfig(new DownloadConfig("/download/path", "rsa", "xxxxxxx",
//                Lists.newArrayList("file1", "file2")));
//        configJsonEntity.setParseConfig(
//                new ParseConfig(2, 10, "|", "head", 1,
//                        "accountNo1,accountNo2,transType,subTransType,totalNumber,totalAmount,totalFeeAmount,clearDate",
//                        3, 0,
//                        Lists.newArrayList(
//                                new DataFieldEntity("cn.nflow.reconcile.domain.model.reconcile.BankLoan",
//                                        "bankApplyNo,loanNo,handleDate,loanAmount,interestRate,loanTerm,userName,certNo,bankCardNo",
//                                        "bankLoanStatus,bankChannel,merClearRemark",
//                                        new DataFieldHandleMethod("@cn.nflow.nfsp.common.entity.BatchChannelEnum@LJBC.getName()",
//                                                "#fieldValue,\"S\"", "#fieldValue,\"\"")))));
//
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(configJsonEntity));
    }
}

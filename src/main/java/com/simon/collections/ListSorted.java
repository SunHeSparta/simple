package com.simon.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by SunHe on 2020/2/25
 */
public class ListSorted {

    public static void main(String[] args) {

        List<LoanRePlanPo> loanRePlanPos = Arrays.asList(
                new LoanRePlanPo(3),
                new LoanRePlanPo(1),
                new LoanRePlanPo(4),
                new LoanRePlanPo(2),
                new LoanRePlanPo(5)
        );

//        LoanRePlanPo loanRePlanPo = loanRePlanPos.stream()
//                .sorted((o1, o2) -> o2.getCurPeriod() - o1.getCurPeriod())
//                .filter(o -> o.getCurPeriod() >= 3)
//                .findFirst().orElse(null);
        LoanRePlanPo loanRePlanPo = loanRePlanPos.stream()
                .filter(o -> o.getCurPeriod() >= 3)
                .min(Comparator.comparingInt(LoanRePlanPo::getCurPeriod))
                .orElse(null);
        System.out.println(loanRePlanPo);
    }

}

class LoanRePlanPo {
    private int CurPeriod;

    public LoanRePlanPo(int curPeriod) {
        CurPeriod = curPeriod;
    }

    public int getCurPeriod() {
        return CurPeriod;
    }

    public void setCurPeriod(int curPeriod) {
        CurPeriod = curPeriod;
    }
}

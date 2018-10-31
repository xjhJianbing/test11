package com.jianbing.model;

import lombok.Data;

@Data
public class GetUserLoanInfoCase {
    private int id;
    private String phone;
    private Double loan_val;
    private int loan_daycount;
}

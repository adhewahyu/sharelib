package com.dan.sharelib.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseApprovalEntity implements Serializable {

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String approvedBy;

    private Date approvedDate;

}

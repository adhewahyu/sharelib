package com.dan.sharelib.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class BaseApprovalEntity implements Serializable {

    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String approvedBy;
    private Date approvedDate;

}

package com.dan.sharelib.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

}

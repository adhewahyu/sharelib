package com.dan.sharelib.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class BaseApprovalEntity implements Serializable {

    @Column(value = "created_by")
    private String createdBy;

    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "updated_by")
    private String updatedBy;

    @Column(value = "updated_date")
    private LocalDateTime updatedDate;

    @Column(value = "approved_by")
    private String approvedBy;

    @Column(value = "approved_date")
    private LocalDateTime approvedDate;

}

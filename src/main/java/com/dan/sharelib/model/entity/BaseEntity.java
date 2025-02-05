package com.dan.sharelib.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class BaseEntity implements Serializable {

    @Column(value = "created_by")
    private String createdBy;

    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "updated_by")
    private String updatedBy;

    @Column(value = "updated_date")
    private LocalDateTime updatedDate;

}

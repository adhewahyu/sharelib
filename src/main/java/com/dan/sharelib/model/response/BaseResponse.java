package com.dan.sharelib.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class BaseResponse implements Serializable {
    
}

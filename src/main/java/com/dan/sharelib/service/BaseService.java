package com.dan.sharelib.service;


import com.dan.sharelib.model.request.BaseRequest;
import com.dan.sharelib.model.response.BaseResponse;

public interface BaseService<I extends BaseRequest, O extends BaseResponse>{

    O execute(I input);

}

package com.dan.sharelib.service;


import com.dan.sharelib.model.request.BaseRequest;
import com.dan.sharelib.model.response.BaseResponse;
import reactor.core.CorePublisher;

public interface ReactiveBaseService<I extends BaseRequest, O extends CorePublisher<? extends BaseResponse>>{

    O execute(I input);

}

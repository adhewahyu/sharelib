package com.dan.sharelib.controller;

import com.dan.sharelib.enums.Message;
import com.dan.sharelib.model.response.CommonResponse;
import com.dan.sharelib.model.response.ValidationResponse;
import reactor.core.publisher.Mono;

public class BaseController {

//    public Mono<CommonResponse> getCommonResponse(Mono<ValidationResponse> validationResponseMono) {
//        return validationResponseMono.flatMap(validationResponse -> Mono.just(CommonResponse.builder()
//                .data(null)
//                .message(validationResponse.getResult() ? Message.MSG_OK.getMsg() : Message.MSG_NOK.getMsg())
//                .result(validationResponse.getResult())
//                .build()));
//    }

}

package com.dan.sharelib.controller;

import com.dan.sharelib.enums.Message;
import com.dan.sharelib.model.request.SearchRequest;
import com.dan.sharelib.model.response.CommonResponse;
import com.dan.sharelib.model.response.PageResponse;
import com.dan.sharelib.model.response.ValidationResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public class BaseController {

    public Mono<CommonResponse> getCommonResponse(Mono<ValidationResponse> validationResponseMono) {
        return validationResponseMono.flatMap(validationResponse -> Mono.just(CommonResponse.builder()
                .data(null)
                .message(validationResponse.getResult() ? Message.MSG_OK.getMsg() : Message.MSG_NOK.getMsg())
                .result(validationResponse.getResult())
                .build()));
    }

    public Mono<CommonResponse> getCommonPageResponse(Mono<PageResponse> pageResponseMono) {
        return pageResponseMono.flatMap(pageResponse -> Mono.just(CommonResponse.builder()
                .data(pageResponse)
                .message(Message.DATA_FOUND.getMsg())
                .result(true)
                .build()));
    }

    protected Pageable buildPageableFromRequest(SearchRequest input){
        String sortBy = StringUtils.isEmpty(input.getSortBy()) ? "id" : input.getSortBy();
        Sort.Direction sortOrder = StringUtils.isNotEmpty(input.getSortOrder()) && input.getSortOrder().equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Integer page = input.getPage() == 0 ? input.getPage() : input.getPage() - 1;
        return PageRequest.of(page, input.getSize(), Sort.by(sortOrder, sortBy));
    }

}

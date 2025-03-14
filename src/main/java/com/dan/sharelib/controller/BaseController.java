package com.dan.sharelib.controller;

import com.dan.sharelib.enums.Message;
import com.dan.sharelib.model.request.SearchRequest;
import com.dan.sharelib.model.response.CommonResponse;
import com.dan.sharelib.model.response.PageResponse;
import com.dan.sharelib.model.response.ValidationResponse;
import com.dan.sharelib.util.Constants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpCookie;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class BaseController {

    public Mono<CommonResponse> getCommonResponse(Mono<ValidationResponse> validationResponseMono) {
        return validationResponseMono.flatMap(validationResponse -> Mono.just(CommonResponse.builder()
                .data(null)
                .message(Boolean.TRUE.equals(validationResponse.getResult()) ? Message.MSG_OK.getMsg() : Message.MSG_NOK.getMsg())
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
        Integer size = input.getSize() == 0 ? 5 : input.getSize();
        return PageRequest.of(page, size, Sort.by(sortOrder, sortBy));
    }

    protected String getAuthorizationFromHeaderOrCookie(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(Constants.REQ_HEADER_AUTHORIZATION);
        HttpCookie authCookie = exchange.getRequest().getCookies().getFirst(Constants.REQ_HEADER_AUTHORIZATION);
        return ObjectUtils.isNotEmpty(authCookie)
                && StringUtils.isNotBlank(authCookie.getValue()) ? authCookie.getValue() : authHeader;
    }

}

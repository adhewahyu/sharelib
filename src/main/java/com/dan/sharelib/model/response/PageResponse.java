package com.dan.sharelib.model.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse extends BaseResponse {

    private transient List<?> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;

}

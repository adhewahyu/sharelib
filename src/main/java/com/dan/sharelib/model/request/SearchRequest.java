package com.dan.sharelib.model.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest extends BaseRequest {

    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;

}

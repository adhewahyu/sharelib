package com.dan.sharelib.model.request;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableRequest extends BaseRequest{

    private Pageable pageable;

}

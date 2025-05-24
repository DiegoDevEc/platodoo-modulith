package com.playtodoo.modulith.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortInfo {
    private String field;
    private String direction;
}

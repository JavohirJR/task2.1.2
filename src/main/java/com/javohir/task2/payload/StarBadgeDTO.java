package com.javohir.task2.payload;

import com.javohir.task2.entity.enums.StarBadgeValue;
import lombok.Data;

@Data
public class StarBadgeDTO {
    private StarBadgeValue starBadgeValue;
    private Integer programmingLangId;
}

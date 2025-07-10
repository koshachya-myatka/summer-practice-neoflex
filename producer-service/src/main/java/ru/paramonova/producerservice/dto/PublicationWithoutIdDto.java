package ru.paramonova.producerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PublicationWithoutIdDto {
    @JsonProperty("author_id")
    private Long authorId;
    private String content;
}

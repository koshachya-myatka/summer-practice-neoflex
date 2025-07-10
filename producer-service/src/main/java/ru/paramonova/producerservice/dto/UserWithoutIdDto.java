package ru.paramonova.producerservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserWithoutIdDto {
    private String nickname;
}

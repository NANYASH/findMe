package com.findMe.model.viewData;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
@Setter
public class MessageParametersData {
    private Long id;
    private String text;
    private Long userFromId;
    private Long userToId;
}

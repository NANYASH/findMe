package com.findMe.model.viewData;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
@Setter
public class PostFilterData {
    private Long userPageId;
    private Long userPostedId;
    private String byFriends;
}

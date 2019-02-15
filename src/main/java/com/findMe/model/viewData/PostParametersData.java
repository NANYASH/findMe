package com.findMe.model.viewData;

import com.findMe.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
@Setter
public class PostParametersData {
    private String text;
    private Long userPageId;
    private User userPosted;
    private String usersTagged;
    private String location;
}

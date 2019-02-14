package com.findMe.model.validateData;

import com.findMe.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class PostParametersData {
    private String text;
    private Long userPageId;
    private User userPosted;
    private String usersTagged;
    private String location;
}

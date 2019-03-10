package com.findMe.viewController;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.model.viewData.PostFilterData;
import com.findMe.service.PostService;
import com.findMe.service.RelationshipService;
import com.findMe.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.findMe.util.Util.convertId;

@Controller
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private UserService userService;
    private RelationshipService relationshipService;
    private PostService postService;

    @Autowired
    public UserController(UserService userService, RelationshipService relationshipService, PostService postService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
        this.postService = postService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, @PathVariable String userId, @ModelAttribute PostFilterData postFilterData) throws InternalServerError, NotFoundException, BadRequestException {
        Long userProfileId = convertId(userId);
        postFilterData.setUserPageId(userProfileId);
        User userSession = (User) session.getAttribute("user");
        User foundUserProfile = userService.findUserById(userProfileId);

        model.addAttribute("user", foundUserProfile);
        model.addAttribute("posts", postService.findPostsByPage(postFilterData));

        if (userSession != null) {

            RelationshipStatus relationshipStatus = relationshipService.findStatusById(userSession.getId(), userProfileId);

            if (relationshipStatus != null) {
                model.addAttribute("status", relationshipStatus.toString());

                if (relationshipStatus.equals(RelationshipStatus.ACCEPTED))
                    model.addAttribute("friends", relationshipService.findByRelationshipStatus(userProfileId, RelationshipStatus.ACCEPTED));

            } else if (userSession.equals(foundUserProfile)) {
                model.addAttribute("status", RelationshipStatus.MY_PROFILE.toString());
                model.addAttribute("outgoingRequests", relationshipService.findOutgoingRequests(userProfileId));
                model.addAttribute("incomingRequests", relationshipService.findIncomingRequests(userProfileId));
                model.addAttribute("friends", relationshipService.findByRelationshipStatus(userProfileId, RelationshipStatus.ACCEPTED));
            }
        }
        LOGGER.info("User page (id: "+userId+") opened.");
        return "profile";
    }
}

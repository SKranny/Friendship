package FriendshipService.controllers;

import FriendshipService.dto.FriendshipDTO;
import FriendshipService.dto.security.TokenAuthentication;
import FriendshipService.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/friendship")
@RequiredArgsConstructor
@Tag(name = "Friendship Service", description = "Friendship management")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/{id}/request")
    @Operation(summary = "Friendship request")
    public void sendFriendshipRequest(@PathVariable Long dstUserId, TokenAuthentication authentication){
        friendshipService.sendFriendshipRequest(dstUserId, authentication.getTokenData().getUserName());
    }
    @PutMapping("/{id}/accept")
    @Operation(summary = "Accept a friendship request")
    public FriendshipDTO acceptFriendshipRequest(@PathVariable Long requesterId){
        return friendshipService.acceptFriendshipRequest(requesterId);
    }
    @PutMapping("/{id}/decline")
    @Operation(summary = "Decline friendship request")
    public void declineFriendshipRequest(@PathVariable Long requesterId){
        friendshipService.declineFriendshipRequest(requesterId);
    }
    @PutMapping("/{id}/unsubscribe")
    @Operation(summary = "Unsubscribe from a respondent")
    public FriendshipDTO unsubscribe(TokenAuthentication authentication, @PathVariable Long respondentId){
        return friendshipService.unsubscribe(authentication.getTokenData().getUserName(),respondentId);
    }
    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a friendship")
    public FriendshipDTO deleteFriendRequest(TokenAuthentication authentication, @PathVariable Long respondentId){
        return friendshipService.deleteFriendship(authentication.getTokenData().getUserName(),respondentId);
    }

}

package FriendshipService.service;

import FriendshipService.dto.FriendshipDTO;

public interface FriendshipService {
    void sendFriendshipRequest(Long id, String userName);

    FriendshipDTO declineFriendshipRequest(Long requesterId);

    FriendshipDTO unsubscribe(String username, Long respondentId);

    FriendshipDTO acceptFriendshipRequest(Long requesterId);

    FriendshipDTO deleteFriendship(String username,Long respondentId);
}

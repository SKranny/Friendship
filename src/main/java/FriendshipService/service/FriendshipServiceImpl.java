package FriendshipService.service;

import FriendshipService.constants.FriendshipStatus;
import FriendshipService.dto.FriendshipDTO;
import FriendshipService.mappers.FriendshipMapper;
import FriendshipService.models.Friendship;
import FriendshipService.repository.FriendshipRepository;
import FriendshipService.service.feign.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendShipRepository;
    private final FriendshipMapper friendshipMapper;
    private final PersonService personService;

    @Override
    public void sendFriendshipRequest(Long dstUserId, String userName) {
        Friendship friendship = Friendship.builder()
                .srcPersonId(personService.getPersonDTOByUsername(userName).getId())
                .dstPersonId(dstUserId)
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();
        friendShipRepository.save(friendship);
    }

    @Override
    public FriendshipDTO acceptFriendshipRequest(Long requesterId){
        Friendship friendship = friendShipRepository.findBySrcPersonId(requesterId)
                .orElseThrow(()-> new RuntimeException("Error! Friendship is not found by requester id"));
        friendship.setSrcGeneralStatus(FriendshipStatus.FRIENDS);
        friendship.setDstGeneralStatus(FriendshipStatus.FRIENDS);
        friendship.setSrcSubscriptionStatus(FriendshipStatus.SUBSCRIBED);
        friendship.setDstSubscriptionStatus(FriendshipStatus.SUBSCRIBED);
        friendShipRepository.save(friendship);
        return friendshipMapper.toDTO(friendship);
    }

    @Override
    public FriendshipDTO declineFriendshipRequest(Long requesterId) {
        Friendship friendship = friendShipRepository.findBySrcPersonId(requesterId)
                .orElseThrow(()-> new RuntimeException("Error! Friendship is not found by requester id"));
        friendship.setDstGeneralStatus(FriendshipStatus.DECLINED);
        friendShipRepository.save(friendship);
        return friendshipMapper.toDTO(friendship);
    }
    @Override
    public FriendshipDTO unsubscribe(String username, Long respondentId){
        Friendship friendship = friendShipRepository.findByDstPersonId(respondentId)
                .orElseGet(() -> friendShipRepository.findBySrcPersonId(respondentId).get());
        changeSubscriptionStatus(username,friendship);
        friendShipRepository.save(friendship);
        return friendshipMapper.toDTO(friendship);
    }

    @Override
    public FriendshipDTO deleteFriendship(String username, Long friendId){

        Friendship friendship = friendShipRepository
                .findByDstPersonId(friendId)
                .orElseGet(() -> friendShipRepository.findBySrcPersonId(friendId).get());

        if (friendshipCheck(username,friendship)){
            friendship.setSrcGeneralStatus(FriendshipStatus.NO_FRIEND);
            friendship.setDstGeneralStatus(FriendshipStatus.NO_FRIEND);
            changeSubscriptionStatus(username,friendship);
            friendShipRepository.save(friendship);
        }

        return friendshipMapper.toDTO(friendship);
    }

    private Boolean iAmFriendshipSrc(String username, Friendship friendship){
        Long myId = personService.getPersonDTOByUsername(username).getId();
        return friendship.getSrcPersonId().equals(myId);
    }

    private Boolean friendshipCheck(String username,Friendship friendship){
        Long myId = personService.getPersonDTOByUsername(username).getId();
        if (myId.equals(friendship.getDstPersonId()) || myId.equals(friendship.getSrcPersonId())){
            return true;
        }
        return false;
    }

    private void changeSubscriptionStatus(String username,Friendship friendship){
        if (iAmFriendshipSrc(username,friendship)){
            friendship.setSrcSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED);
        }else {
            friendship.setDstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED);
        }
    }

}

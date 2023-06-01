package FriendshipService.service;

import FriendshipService.constants.FriendshipStatus;
import FriendshipService.dto.FriendshipDTO;
import FriendshipService.dto.person.PersonDTO;
import FriendshipService.dto.security.TokenData;
import FriendshipService.mappers.FriendshipMapper;
import FriendshipService.models.Friendship;
import FriendshipService.repository.FriendshipRepository;
import FriendshipService.service.feign.PersonService;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendShipServiceImplTest {

    private FriendshipRepository friendShipRepository = mock(FriendshipRepository.class);

    private PersonService personService = mock(PersonService.class);

    private FriendshipMapper friendshipMapper = mock(FriendshipMapper.class);

    private TokenData tokenData = TokenData.builder()
            .token("some8-jwt9-token")
            .id(1L)
            .email("test@mail.com")
            .userName("superUser1337")
            .build();

    private ZonedDateTime time = ZonedDateTime.now();

    PersonDTO personDTO = PersonDTO.builder()
            .id(1L)
            .username("superUser1337")
            .build();

    PersonDTO personDTO2 = PersonDTO.builder()
            .id(2L)
            .username("superUser1338")
            .build();

    private FriendshipServiceImpl friendshipService = new FriendshipServiceImpl(friendShipRepository,friendshipMapper,personService);

    @Test
    void sendFriendshipRequest() {
        Long dstUserId = 2L;
        String userName = "superUser1337";

        Friendship friendship = Friendship.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(dstUserId)
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        when(personService.getPersonDTOByUsername(userName)).thenReturn(personDTO);
        when(friendShipRepository.save(friendship)).thenReturn(friendship);
        when(friendShipRepository.findByDstPersonId(dstUserId)).thenReturn(Optional.of(friendship));

        friendshipService.sendFriendshipRequest(dstUserId,userName);

        assertEquals(friendship, friendShipRepository.findByDstPersonId(dstUserId).get());
    }

    @Test
    void declineFriendshipRequest() {
        Long requesterId = 2L;

        Friendship friendship = Friendship.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.DECLINED)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        FriendshipDTO friendshipDTO = FriendshipDTO.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.DECLINED)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        when(friendShipRepository.findBySrcPersonId(requesterId)).thenReturn(Optional.of(friendship));
        when(friendShipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toDTO(friendship)).thenReturn(friendshipDTO);

        assertEquals(friendshipDTO, friendshipService.declineFriendshipRequest(requesterId));
    }

    @Test
    void unsubscribe() {
        Long respondentId = 2L;

        Friendship friendship = Friendship.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        FriendshipDTO friendshipDTO = FriendshipDTO.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        when(friendShipRepository.findByDstPersonId(respondentId)).thenReturn(Optional.of(friendship));
        when(friendShipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toDTO(friendship)).thenReturn(friendshipDTO);
        when(personService.getPersonDTOByUsername(personDTO.getUsername())).thenReturn(personDTO);

        assertEquals(friendshipDTO, friendshipService.unsubscribe(personDTO.getUsername(),respondentId));

    }

    @Test
    void acceptFriendshipRequest() {
        Long requesterId = 2L;

        Friendship friendship = Friendship.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(2L)
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        FriendshipDTO friendshipDTO = FriendshipDTO.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.FRIENDS)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.FRIENDS)
                .dstSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .build();
        when(friendShipRepository.findBySrcPersonId(requesterId)).thenReturn(Optional.of(friendship));
        when(friendShipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toDTO(friendship)).thenReturn(friendshipDTO);

        assertEquals(friendshipDTO, friendshipService.acceptFriendshipRequest(requesterId));
    }

    @Test
    void deleteFriendship() {
        Long respondentId = 2L;

        Friendship friendship = Friendship.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(2L)
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .build();

        FriendshipDTO friendshipDTO = FriendshipDTO.builder()
                .srcPersonId(personDTO.getId())
                .dstPersonId(personDTO2.getId())
                .srcGeneralStatus(FriendshipStatus.NO_FRIEND)
                .srcSubscriptionStatus(FriendshipStatus.NOT_SUBSCRIBED)
                .dstGeneralStatus(FriendshipStatus.NO_FRIEND)
                .dstSubscriptionStatus(FriendshipStatus.SUBSCRIBED)
                .build();

        when(friendShipRepository.findByDstPersonId(respondentId)).thenReturn(Optional.of(friendship));
        when(friendShipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toDTO(friendship)).thenReturn(friendshipDTO);
        when(personService.getPersonDTOByUsername(personDTO.getUsername())).thenReturn(personDTO);

        assertEquals(friendshipDTO, friendshipService.deleteFriendship(personDTO.getUsername(),respondentId));

    }
}
package FriendshipService.mappers;

import FriendshipService.dto.FriendshipDTO;
import FriendshipService.models.Friendship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {
    FriendshipDTO toDTO(Friendship friendship);
    Friendship toFriendship (FriendshipDTO friendshipDTO);
}

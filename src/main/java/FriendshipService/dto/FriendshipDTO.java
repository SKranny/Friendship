package FriendshipService.dto;

import FriendshipService.constants.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {
    private FriendshipStatus srcGeneralStatus;
    private FriendshipStatus dstGeneralStatus;
    private FriendshipStatus srcSubscriptionStatus;
    private FriendshipStatus dstSubscriptionStatus;
    private Long srcPersonId;
    private Long dstPersonId;
}

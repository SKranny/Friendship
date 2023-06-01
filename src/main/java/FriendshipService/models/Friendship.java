package FriendshipService.models;

import FriendshipService.constants.FriendshipStatus;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus srcGeneralStatus;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus dstGeneralStatus;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus srcSubscriptionStatus;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus dstSubscriptionStatus;
    private Long srcPersonId;
    private Long dstPersonId;


}

package FriendshipService.repository;

import FriendshipService.models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByDstPersonId(Long respondentId);
    Optional<Friendship> findBySrcPersonId(Long requesterId);
}

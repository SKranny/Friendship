package FriendshipService.service.feign;

import FriendshipService.dto.person.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("person-service")
public interface PersonService {
    PersonDTO getPersonDTOByUsername(String userName);
}

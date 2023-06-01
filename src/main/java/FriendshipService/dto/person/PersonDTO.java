package FriendshipService.dto.person;

import FriendshipService.constants.MessagesPermission;
import FriendshipService.constants.RoleType;
import FriendshipService.constants.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
    private Long id;

    @NotNull
    @Email
    @JsonProperty(required = true)
    private String email;

    @NotNull
    @JsonProperty(required = true)
    private String username;

    private String photo;

    private String about;

    private StatusCode statusCode;

    private LocalDate birthDay;

    private MessagesPermission messagesPermission;

    private LocalDateTime lastOnlineTime;

    @Builder.Default
    private Boolean isOnline = true;

    @Builder.Default
    @JsonProperty(required = true)
    private Boolean isBlocked = false;

    @Builder.Default
    private Boolean isDeleted = false;

    @NotNull
    @JsonProperty(required = true)
    private Set<RoleType> roles;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String password;
}

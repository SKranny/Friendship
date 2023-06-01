package FriendshipService.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_ADMIN("admin"),

    ROLE_USER("user"),

    ROLE_MODERATOR("moderator");

    private final String name;

    public static RoleType fromName(@NotBlank String name) {
        return Arrays.stream(values()).filter(r -> r.name.equalsIgnoreCase(name)).findFirst()
                .orElseThrow(() -> new RuntimeException("Error! Invalid role!"));
    }

}

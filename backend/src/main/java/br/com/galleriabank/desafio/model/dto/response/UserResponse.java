package br.com.galleriabank.desafio.model.dto.response;

import br.com.galleriabank.desafio.model.entity.User;

public record UserResponse(
        Long id,
        String name,
        String username,
        Boolean enabled
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEnabled()
        );
    }
}

package com.developlife.reviewtwits.message.response.user;

import lombok.Builder;

/**
 * @author ghdic
 * @since 2023/02/24
 */
public record UserInfoResponse(
        String userId,
        String nickname,
        String accountId,
        String introduceText,
        String profileImage
) {
    @Builder
    public UserInfoResponse {
    }
}

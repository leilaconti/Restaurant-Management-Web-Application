package team14.arms.backend.security;

import team14.arms.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {
    User getUser();
}

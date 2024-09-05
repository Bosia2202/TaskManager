package com.interview.taskmanager.domain.user;

public interface AvatarStorage {

    String getDefaultAvatarImgUrl();

    String uploadAvatarImg(byte[] image);

    void deleteAvatarImg(String oldAvatarUrl);
}

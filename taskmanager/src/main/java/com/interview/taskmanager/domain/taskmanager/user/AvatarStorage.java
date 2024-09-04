package com.interview.taskmanager.domain.taskmanager.user;

public interface AvatarStorage {

    String getDefaultAvatarImgUrl();

    String uploadAvatarImg(Byte[] image);

    void deleteAvatarImg(String oldAvatarUrl);
}

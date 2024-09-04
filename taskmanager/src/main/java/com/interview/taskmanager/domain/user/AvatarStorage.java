package com.interview.taskmanager.domain.user;

public interface AvatarStorage {

    String getDefaultAvatarImgUrl();

    String uploadAvatarImg(Byte[] image);

    void deleteAvatarImg(String oldAvatarUrl);
}

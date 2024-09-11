package com.interview.taskmanager.application.ports.out;

public interface AvatarPort {

    String getDefaultAvatarImgUrl();

    String uploadAvatarImg(byte[] image);

    void deleteAvatarImg(String oldAvatarUrl);
}

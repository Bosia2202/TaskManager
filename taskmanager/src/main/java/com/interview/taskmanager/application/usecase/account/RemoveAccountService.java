package com.interview.taskmanager.application.usecase.account;

import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.infra.exception.UserNotDeletedRuntimeException;

public class RemoveAccountService {

    private final UserPort userPort;

    private final SecurityPort securityPort;

    public RemoveAccountService(UserPort userPort, SecurityPort securityPort) {
        this.userPort = userPort;
        this.securityPort = securityPort;
    }

    public void remove() {
        Integer currentUserId = securityPort.getCurrentUserId();
        if (!userPort.remove(currentUserId)) {
            throw new UserNotDeletedRuntimeException("User wasn't deleted");
        }
    }

}

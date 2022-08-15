package com.example.demo.model.common;

import com.example.demo.model.User;
import java.util.Date;
import java.util.Objects;

public interface HasStatus<S extends ClientNameable, H extends HasStatus<S, H>> {

    /**
     * Get current status.
     *
     * @return current status
     */
    S getStatus();

    /**
     * Set new current status.
     *
     * @param newStatus new status
     */
    H setStatus(S newStatus);

    /**
     * Get entity history.
     *
     * @return history
     */
    History getHistory();

    /**
     * Change status by system user.
     *
     * @param newStatus new status
     */
    default H changeStatusAt(S newStatus) {
        return changeStatusAt(newStatus, UserUtil.system());
    }

    /**
     * Change status by specified user.
     *
     * @param newStatus new status
     * @param user      user who make changes
     */
    default H changeStatusAt(S newStatus, User user) {
        if (newStatus != getStatus()) {
            getHistory().addEvent(
                    new ChangeEvent()
                            .setWho(user)
                            .setTime(new Date())
                            .setDescription(composeDescription(getStatus(), newStatus))
            );
            setStatus(newStatus);
        }
        return (H) this;
    }

    default String composeDescription(S currentStatus, S newStatus) {
        if (Objects.isNull(currentStatus)) {
            return String.format("Статус %s установлен", newStatus.clientName());
        } else {
            return String.format("Статус изменен с %s на %s", currentStatus.clientName(), newStatus.clientName());
        }
    }
}

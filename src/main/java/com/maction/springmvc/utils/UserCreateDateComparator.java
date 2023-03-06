package com.maction.springmvc.utils;

import com.maction.springmvc.model.User;

import java.util.Comparator;

public class UserCreateDateComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getCreated().compareTo(o2.getCreated());
    }
}

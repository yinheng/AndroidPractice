// UserManagerAidl.aidl
package com.yh.aidldemo;

import com.yh.aidldemo.UserAidl;
// Declare any non-default types here with import statements

interface UserManagerAidl {

    List<User> getUsers();
    void addUser( inout User user);
    String getString();

}
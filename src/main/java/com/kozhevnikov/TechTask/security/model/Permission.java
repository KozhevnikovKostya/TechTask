package com.kozhevnikov.TechTask.security.model;

public enum Permission {

    READ_BALANCE("read:balance"), CHANGE_BALANCE("change:balance"), MANAGE_USERS("manage:user");

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}

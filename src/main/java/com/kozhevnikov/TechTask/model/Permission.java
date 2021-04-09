package com.kozhevnikov.TechTask.model;

public enum Permission {

    READ_BALANCE("read:balance"), CHANGE_BALANCE("change:balance");

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}

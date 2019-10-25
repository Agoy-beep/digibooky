package com.testingtigers.domain.users;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Feature {
    VIEW_ALL_MEMBERS(Role.ADMIN),
    REGISTER_LIBRARIAN(Role.ADMIN);

    private Role[] authorizedRoles;

    Feature(Role... authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    public List<Role> getRoles() {
        return Arrays.asList(authorizedRoles);
    }

    public static List<Feature> getFeatures(String roleString) {
        Role role = Role.valueOf(roleString);
        return Arrays.stream(Feature.values())
                .filter(feature -> feature.getRoles().contains(role))
                .collect(Collectors.toList());

    }
}

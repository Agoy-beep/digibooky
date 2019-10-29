package com.testingtigers.domain.users;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Feature {
    VIEW_ALL_MEMBERS(Role.ADMIN),
    REGISTER_LIBRARIAN(Role.ADMIN),
    REGISTER_ADMIN(Role.ADMIN),
    CREATE_BOOK(Role.ADMIN, Role.LIBRARIAN),
    DELETE_BOOK(Role.LIBRARIAN, Role.ADMIN),
    UNDELETE_BOOK(Role.LIBRARIAN, Role.ADMIN),
    UPDATE_BOOK(Role.ADMIN, Role.LIBRARIAN),
    GET_LENT_BOOKS(Role.ADMIN, Role.LIBRARIAN),
    BORROW_BOOK(Role.MEMBER),
    GET_LENT_BOOKS_BY_MEMBER(Role.ADMIN, Role.LIBRARIAN),
    GET_OVERDUE_BOOKS(Role.ADMIN, Role.LIBRARIAN);

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

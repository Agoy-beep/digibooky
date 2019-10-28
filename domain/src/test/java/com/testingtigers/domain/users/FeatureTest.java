package com.testingtigers.domain.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FeatureTest {
    @Test
    void getFeatures_givenRoleAdminAsString_ReturnListOfFeaturesForRole() {
        //GIVEN
        String role = "ADMIN";
        //WHEN
        //THEN
        Assertions.assertThat(Feature.getFeatures(role)).containsExactlyInAnyOrder(Feature.REGISTER_LIBRARIAN, Feature.VIEW_ALL_MEMBERS);

    }

    @Test
    void getFeatures_givenRoleUserAsString_ReturnListOfFeaturesForRole() {
        //GIVEN
        String role = "MEMBER";
        //WHEN
        //THEN
        Assertions.assertThat(Feature.getFeatures(role)).isEmpty();

    }
}
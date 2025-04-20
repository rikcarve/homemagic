package ch.carve.homemagic.model;

import jakarta.json.bind.annotation.JsonbProperty;

public record PaulmannRemoteAction(
        @JsonbProperty("action")
        String name,
        @JsonbProperty("action_group")
        Integer group,
        @JsonbProperty("action_action")
        String action,
        @JsonbProperty("action_direction")
        String direction,
        @JsonbProperty("action_start_hue")
        Integer startHue,
        @JsonbProperty("action_time")
        Integer time,
        @JsonbProperty("action_enhanced_hue")
        Integer enhancedHue,
        @JsonbProperty("action_hue")
        Integer hue
) {}

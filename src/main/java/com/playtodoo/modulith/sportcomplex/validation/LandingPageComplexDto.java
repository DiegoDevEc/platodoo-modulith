package com.playtodoo.modulith.sportcomplex.validation;

import java.util.List;
import java.util.Map;

public record LandingPageComplexDto(
        String nameComplex,
        String shortDescription,
        String longDescription,
        String imageLogo,
        String imageCover,
        String address,
        String phone,
        String email,
        Double rating,
        String locationMapUrl,
        List<String> services,
        List<String> paymentMethods,
        Map<String, String> businessHours,
        Map<String, String> socialMediaLinks,
        List<String> faq,
        List<String> reviews,
        List<String> highlightedEvents,
        String termsAndConditions
) {}


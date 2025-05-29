package com.playtodoo.modulith.sportcomplex.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.sportcomplex.application.SportComplexService;
import com.playtodoo.modulith.sportcomplex.domain.SportComplex;
import com.playtodoo.modulith.sportcomplex.exception.LandingPageNotFoundException;
import com.playtodoo.modulith.sportcomplex.exception.SportComplexNotFoundException;
import com.playtodoo.modulith.sportcomplex.infrastructure.SportComplexRepository;
import com.playtodoo.modulith.sportcomplex.mapper.SportComplexMapper;
import com.playtodoo.modulith.sportcomplex.validation.CreateSportComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.LandingPageComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import com.playtodoo.modulith.users.validation.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class SportComplexServiceImpl implements SportComplexService {

    private final SportComplexRepository sportComplexRepository;
    private final SportComplexMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public PageResponse<SportComplexDto> findAll(int page, int size, String sortField, String sortDirection) {
        log.debug("Fetching SportComplex list - page: {}, size: {}, sortBy: {}, direction: {}", page, size, sortField, sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<SportComplex> pageResult = sportComplexRepository.findAll(pageable);
        List<SportComplexDto> dtos = pageResult.getContent().stream()
                .map(mapper::toSportComplexDto)
                .toList();
        return PageResponse.fromPage(new PageImpl<>(dtos, pageable, pageResult.getTotalElements()), sortField, sortDirection);
    }

    @Override
    public PageResponse<SportComplexDto> findAllByUserId(UUID userId, int page, int size, String sortField, String sortDirection) {
        log.debug("Fetching SportComplex list for user {} - page: {}, size: {}, sortBy: {}, direction: {}", userId, page, size, sortField, sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<SportComplex> pageResult = sportComplexRepository.findAllByOwnerIdAndStatus(userId, "ACT", pageable);
        List<SportComplexDto> dtos = pageResult.getContent().stream()
                .map(mapper::toSportComplexDto)
                .toList();
        return PageResponse.fromPage(new PageImpl<>(dtos, pageable, pageResult.getTotalElements()), sortField, sortDirection);
    }

    @Override
    public Page<SportComplexDto> findAllByPositionUser(int page, int size, String sortBy, String direction, Double latitude, Double longitude) {
        return Page.empty();
    }

    @Override
    public SportComplexDto findById(UUID id) {
        log.debug("Fetching SportComplex with ID: {}", id);
        SportComplex sportComplex = getByIdOrThrow(id);
        return mapper.toSportComplexDto(sportComplex);
    }

    @Override
    public SportComplexDto create(CreateSportComplexDto dto) {
        log.info("Creating new SportComplex with name: {} and ownerId: {}", dto.name(), dto.ownerId());

        SportComplex sportComplex = mapper.toCreateSportComplex(dto);
        SportComplex saved = sportComplexRepository.save(sportComplex);

        log.info("SportComplex created successfully with ID: {}", saved.getId());
        return mapper.toSportComplexDto(saved);
    }

    @Override
    public SportComplexDto update(UUID id, CreateSportComplexDto dto) {
        log.info("Updating SportComplex with ID: {}", id);

        SportComplex sportComplex = getByIdOrThrow(id);

        log.debug("Updated values - name: {}, description: {}, latitude: {}, longitude: {}, status: {}",
                dto.name(), dto.description(), dto.latitude(), dto.longitude(), dto.status());

        sportComplex.setDescription(dto.description());
        sportComplex.setName(dto.name());
        sportComplex.setLatitude(dto.latitude());
        sportComplex.setLongitude(dto.longitude());
        sportComplex.setStatus(dto.status());

        SportComplex updated = sportComplexRepository.save(sportComplex);

        log.info("SportComplex with ID: {} updated successfully.", id);
        return mapper.toSportComplexDto(updated);
    }

    @Override
    public SportComplexDto updateLandingPageConfig(UUID id, String landingPageConfig) {
        SportComplex sportComplex = getByIdOrThrow(id);
        sportComplex.setLandingPageConfig(landingPageConfig);
        return mapper.toSportComplexDto(sportComplex);
    }

    @Override
    public LandingPageComplexDto getLandingPage(UUID id) {
        SportComplex sportComplex = getByIdOrThrow(id);
        if (sportComplex.getLandingPageConfig() == null) {
            log.warn("LandingPageConfig is null for SportComplex ID: {}", id);
            throw  new LandingPageNotFoundException(id.toString());
        }

        try {
            return objectMapper.readValue(sportComplex.getLandingPageConfig(), LandingPageComplexDto.class);
        } catch (Exception e) {
            log.error("Error parsing landingPageConfig JSON for SportComplex ID: {}", id, e);
            throw new IllegalStateException("Invalid landing page configuration format.");
        }
    }

    @Override
    public void delete(UUID id) {
        SportComplex sportComplex = getByIdOrThrow(id);

        if ("INA".equals(sportComplex.getStatus())) {
            log.warn("Attempted to deactivate SportComplex with ID {} but it's already inactive.", id);
            return;
        }

        sportComplex.setStatus("INA");
        sportComplexRepository.save(sportComplex);

        log.info("SportComplex with ID {} marked as inactive.", id);
    }

    private SportComplex getByIdOrThrow(UUID id) {
        return sportComplexRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("SportComplex with ID {} not found.", id);
                    return new SportComplexNotFoundException(id.toString());
                });
    }
}
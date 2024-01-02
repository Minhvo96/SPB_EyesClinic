package com.codegym.spb_eyesclinic_project.utils;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtils {
    public static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Converter<String, LocalDate> toStringDate = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, format);
            }
        };
        Converter<String, LocalDateTime> toStringDateTime = new AbstractConverter<>() {
            @Override
            protected LocalDateTime convert(String source) {
                try{
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    return LocalDateTime.parse(source, format);
                }catch (Exception e){
                    return null;
                }

            }
        };
        Converter<LocalDateTime, LocalTime> toTimeDateTime = new AbstractConverter<>() {
            @Override
            protected LocalTime convert(LocalDateTime source) {
                return source.toLocalTime();
            }
        };
        Converter<String, LocalTime> toStringTime = new AbstractConverter<>() {
            @Override
            protected LocalTime convert(String source) {
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    return LocalTime.parse(source, formatter);
                }catch (Exception e){
                    return null;
                }

            }
        };

        mapper.createTypeMap(String.class, LocalDate.class);
        mapper.addConverter(toStringDate);
        mapper.addConverter(toStringDateTime);
        mapper.addConverter(toTimeDateTime);
        mapper.addConverter(toStringTime);
    }

    public static ResponseEntity<?> mapErrorToResponse(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

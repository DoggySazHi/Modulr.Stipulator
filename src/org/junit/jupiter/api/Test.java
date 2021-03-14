package org.junit.jupiter.api;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

// Only exists for drop-in compatibility with JUnit 5.
public @interface Test { }
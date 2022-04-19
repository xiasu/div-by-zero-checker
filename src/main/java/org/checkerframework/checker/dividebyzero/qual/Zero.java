package org.checkerframework.checker.dividebyzero.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({Top.class,Geq0.class,Leq0.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface Zero {
}

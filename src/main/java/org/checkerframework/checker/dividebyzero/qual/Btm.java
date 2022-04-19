package org.checkerframework.checker.dividebyzero.qual;


import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({Zero.class,Nzero.class,Pos.class,Neg.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface Btm {
}

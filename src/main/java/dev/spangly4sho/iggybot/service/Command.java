package dev.spangly4sho.iggybot.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

  String name();

  String usage() default "";

  int minArguments() default 0;

  int maxArguments() default 0;

}

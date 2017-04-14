package com.example.markus.todoregister.data;

/**
 * Created by Markus on 7.4.2017.
 * Functional interface to make sure tasks are correct
 */

//@FunctionalInterface
public interface IValidation<T> {

    public int validate(T args);
}

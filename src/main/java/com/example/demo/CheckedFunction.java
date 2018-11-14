package com.example.demo;

import java.sql.SQLException;

@FunctionalInterface
public interface CheckedFunction<T, R> {
   R apply(T t) throws SQLException;
}
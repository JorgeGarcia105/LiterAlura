package com.EmpresasGarcia.LiterAlura.service;

public interface IDataConverter {
    <T> T convertData(String json, Class<T> clazz);
}
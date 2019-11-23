package com.kkaj.service;

import java.util.List;

/**
 *
 * @author Kenneth Parrales
 * @param <T>
 */
public interface IDao<T> {

    public List<T> buscar();

    public void insert(T data);

    public void delete(T data);

    public void update(T data);

}

package repository;

import java.sql.Connection;
import java.sql.*;
import java.util.*;

public abstract class GenericRepository<T> {

    public void save(T entity){
        throw new UnsupportedOperationException("Save not implemented");
    }
    public T findById(int id){
        throw new UnsupportedOperationException("findById not implemented");
    }
    public List<T> findAll(){
        throw new UnsupportedOperationException("findAll not implemented");
    }
    public void delete(int id){
        throw new UnsupportedOperationException("delete not implemented");
    }
}

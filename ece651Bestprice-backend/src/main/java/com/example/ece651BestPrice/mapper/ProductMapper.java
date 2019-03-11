package com.example.ece651BestPrice.mapper;

import com.example.ece651BestPrice.bean.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO Product(UPC, name, category, picture)"+
            "VALUE(#{UPC}, #{name}, #{category}, #{picture})")
    int createProduct(Product p) throws DataAccessException;

    @Select("SELECT * FROM Product WHERE UPC = #{UPC}")
    Product queryProductByUpc(@Param("UPC") String UPC) throws DataAccessException;

    @Update("UPDATE Product SET UPC = #{UPC}, name = #{name}, category = #{category}, picture = #{picture}"+
    " WHERE UPC = #{UPC}")
    int updateProduct(Product p) throws DataAccessException;

    @Delete("DELETE FROM Product WHERE UPC = #{UPC}")
    int deleteProduct(@Param("UPC") String UPC) throws DataAccessException;

    @Update("UPDATE Product SET name = #{name}"+ " WHERE UPC = #{UPC}")
    int updateProductname(@Param("name") String name, @Param("UPC") String UPC) throws DataAccessException;

    @Update("UPDATE Product SET category = #{category}"+ " WHERE UPC = #{UPC}")
    int updateProductcategory(@Param("category") String category, @Param("UPC") String UPC) throws DataAccessException;

    @Update("UPDATE Product SET picture = #{picture}"+ " WHERE UPC = #{UPC}")
    int updateProductpicture(@Param("picture") String picture, @Param("UPC") String UPC) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM Product WHERE UPC = #{UPC}")
    int queryProductnum(@Param("UPC") String UPC) throws DataAccessException;

}

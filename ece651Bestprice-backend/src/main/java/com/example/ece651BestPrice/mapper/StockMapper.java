package com.example.ece651BestPrice.mapper;

import com.example.ece651BestPrice.bean.Stock;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

@Mapper
public interface StockMapper {
    @Insert("INSERT INTO Stock(UPC, storename, price)"+
            "VALUE(#{UPC}, #{storename}, #{price})")
    int createStock(Stock stock) throws DataAccessException;

    @Select("SELECT * FROM Stock WHERE UPC=#{UPC} AND storename=#{storename}")
    Stock queryStock(@Param("UPC")String UPC, @Param("storename")String storename) throws DataAccessException;

    @Update("UPDATE Stock SET price=#{price}"+
    " WHERE UPC=#{UPC} AND storename=#{storename}")
    int updateStockprice(Stock stock) throws DataAccessException;

    @Delete("DELETE FROM Stock WHERE UPC=#{UPC} AND storename=#{storename}")
    int deleteStock(@Param("UPC")String UPC, @Param("storename")String storename) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM Stock WHERE UPC=#{UPC} AND storename=#{storename}")
    int queryStocknum(@Param("UPC")String UPC, @Param("storename")String storename) throws DataAccessException;




}

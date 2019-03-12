package com.example.ece651BestPrice.mapper;

import com.example.ece651BestPrice.bean.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface StoreMapper {
    @Insert("INSERT INTO Store(storename, address, latitude, longitude, city, province, postcode)"+
            "VALUE(#{storename}, #{address}, #{latitude}, #{longitude}, #{city}, #{province}, #{postcode})")
    int createStore(Store s) throws DataAccessException;

    @Select("SELECT * FROM Store"+ " WHERE storename = #{storename}")
    Store queryStorebyStorename(@Param("storename") String storename) throws DataAccessException;

    @Update("UPDATE Store SET storename = #{storename}, address = #{address}, latitude = #{latitude}, longitude = #{longitude}"+
            ",city = #{city}, province = #{province}, postcode = #{postcode}"+
            " where storeID = #{storeID}")
    int updateStore(Store s) throws DataAccessException;

    @Delete("DELETE from Store where storename = #{storename}")
    int deleteStore(@Param("storename") String storename) throws DataAccessException;



    @Update("UPDATE Store SET address = #{address}"+" where storename = #{storename}")
    int updateStoreaddress(Store s) throws DataAccessException;

    @Update("UPDATE Store SET latitude = #{latitude}"+" where storename = #{storename}")
    int updateStorelatitude(Store s) throws DataAccessException;

    @Update("UPDATE Store SET longitude = #{longitude}"+" where storename = #{storename}")
    int updateStorelongitude(Store s) throws DataAccessException;

    @Update("UPDATE Store SET city = #{city}"+" where storename = #{storename}")
    int updateStorecity(Store s) throws DataAccessException;

    @Update("UPDATE Store SET province  = #{province}"+" where storename = #{storename}")
    int updateStoreprovince(Store s) throws DataAccessException;

    @Update("UPDATE Store SET postcode  = #{postcode}"+" where storename = #{storename}")
    int updateStorepostcode(Store s) throws DataAccessException;

    @Select("SELECT Storename FROM Store")
    List<String> queryAllstore() throws DataAccessException;






}

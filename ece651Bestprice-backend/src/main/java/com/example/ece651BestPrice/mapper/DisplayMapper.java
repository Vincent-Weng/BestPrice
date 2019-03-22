package com.example.ece651BestPrice.mapper;

import com.example.ece651BestPrice.bean.Bestprice;
import com.example.ece651BestPrice.bean.Category;
import com.example.ece651BestPrice.bean.RStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface DisplayMapper {
    @Select("SELECT UPC, name, price, storename, min(pricesub) AS discount FROM (SELECT Stock.UPC, storename, name, price,((price-avg_price)/avg_price) as pricesub FROM Stock INNER JOIN (SELECT Stock.UPC,name,avg(price) AS avg_price FROM Product INNER JOIN Stock on Product.UPC = Stock.UPC WHERE category = #{category} group by Stock.UPC)AS tmp ON Stock.UPC = tmp.UPC order by pricesub) AS tmp2 group by UPC order by discount limit 10")
    List<Category> queryCategorybyUPC(@Param("category") String category) throws DataAccessException;

    @Select("SELECT UPC, name, price, storename, category, min(pricesub) AS minprice FROM (SELECT Stock.UPC, storename, name, price, category, ((price - avg_price) / avg_price) as pricesub FROM Stock INNER JOIN (SELECT Stock.UPC, name, avg(price) AS avg_price, category FROM Product INNER JOIN Stock on Product.UPC = Stock.UPC group by Stock.UPC) AS tmp ON Stock.UPC = tmp.UPC order by pricesub) AS tmp2 where storename = #{storename} group by UPC order by minprice limit 10")
    List<RStore> queryRecommendbyStore(@Param("storename") String storename) throws DataAccessException;

    @Select("select price,storename,UPC from Stock inner join Product using(UPC) where UPC = #{UPC} order by price limit 1")
    Bestprice queryBestpricebyUPC(@Param("UPC") String UPC) throws DataAccessException;
}

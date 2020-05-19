package top.i7un.springboot.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.i7un.springboot.model.Categories;

import java.util.List;

/**
 * @author:  Noone
 * @Date: 2018/7/17 20:54
 * Describe: 分类sql
 */
@Mapper
@Repository
public interface CategoryMapper {

    @Insert("insert into categories(categoryName) value(#{categoryName})")
    void save(Categories categories);

    @Select("select categoryName from categories")
    List<String> findCategoriesName();

    @Select("select count(*) from categories")
    int countCategoriesNum();

    @Select("select id,categoryName from categories")
    List<Categories> findAllCategories();

    @Delete("delete from categories where categoryName=#{categoryName}")
    void deleteCategory(String categoryName);

    @Select("select IFNULL((select id from categories where categoryName=#{categoryName}),0)")
    int findIsExistByCategoryName(String categoryName);

}

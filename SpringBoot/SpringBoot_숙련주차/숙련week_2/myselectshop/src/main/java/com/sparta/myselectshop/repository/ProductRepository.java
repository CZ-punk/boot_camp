package com.sparta.myselectshop.repository;

import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUser(User user, Pageable pageable);

    Page<Product> findAllByUserAndProductFolderList_FolderId(User user, Long folderId, Pageable pageable);
    /**

     select * from Product p

     left join Product_Folder pf

     on p.id = pf.product_id

     where p.user_id = ?

     and pf.folder_id = ?


     */

    /**

     < 실제 출력된 Query >

     select

     p1_0.id,p1_0.created_at,p1_0.image,p1_0.link,p1_0.lprice,p1_0.modified_at,p1_0.myprice,p1_0.title,p1_0.user_id

     from product p1_0

     left join product_folder pfl1_0

     on p1_0.id=pfl1_0.product_id

     where

     p1_0.user_id=9

     and

     pfl1_0.folder_id=6

     order by p1_0.lprice

     limit 10;

     */
}

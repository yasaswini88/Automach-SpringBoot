//package com.example.Automach.repo;
//import com.example.Automach.entity.RawMaterialOrderStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface RawMaterialOrderStatusRepo extends JpaRepository<RawMaterialOrderStatus, Long> {
//}

package com.example.Automach.repo;

import com.example.Automach.entity.RawMaterialOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialOrderStatusRepo extends JpaRepository<RawMaterialOrderStatus, Long> {

    @Query("SELECT o FROM RawMaterialOrderStatus o WHERE LOWER(TRIM(o.status)) = LOWER(:status)")
    List<RawMaterialOrderStatus> findOrdersByStatus(@Param("status") String status);

    // To get the order status of raw materials associated with specific supplier

    @Query("SELECT o FROM RawMaterialOrderStatus o " +
            "WHERE LOWER(REPLACE(o.rawMaterial.materialName, ' ', '')) = LOWER(REPLACE(:rawMaterialName, ' ', '')) " +
            "AND LOWER(REPLACE(o.supplierName, ' ', '')) = LOWER(REPLACE(:supplierName, ' ', ''))")
    List<RawMaterialOrderStatus> findOrderStatusByRawMaterialAndSupplier(@Param("rawMaterialName") String rawMaterialName, @Param("supplierName") String supplierName);


}

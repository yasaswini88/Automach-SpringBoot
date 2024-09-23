//package com.example.Automach.repo;
//import com.example.Automach.entity.RawMaterialOrderStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface RawMaterialOrderStatusRepo extends JpaRepository<RawMaterialOrderStatus, Long> {
//}

package com.example.Automach.repo;

import com.example.Automach.entity.RawMaterialOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialOrderStatusRepo extends JpaRepository<RawMaterialOrderStatus, Long> {

}

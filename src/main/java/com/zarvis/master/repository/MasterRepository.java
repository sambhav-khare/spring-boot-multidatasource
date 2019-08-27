package com.zarvis.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zarvis.model.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer>{

}

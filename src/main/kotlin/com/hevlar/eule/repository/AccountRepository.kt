package com.hevlar.eule.repository

import com.hevlar.eule.model.BasicAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface AccountRepository: JpaRepository<BasicAccount, String>{
}
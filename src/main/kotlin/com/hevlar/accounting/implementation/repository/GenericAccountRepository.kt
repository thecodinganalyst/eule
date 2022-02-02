package com.hevlar.accounting.implementation.repository

import com.hevlar.accounting.domain.model.account.Account
import org.springframework.data.jpa.repository.JpaRepository

interface GenericAccountRepository<A: Any, T :Account<A>> :JpaRepository<T, A>
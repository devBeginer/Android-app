package com.example.lab6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    private val userLiveData = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = userLiveData


    fun loginRequest(userLogin: String, userPassword: String, onResult: (user: User?) -> Unit) {
        viewModelScope.launch {
            var tmpUser = repository.getUserByLogin(userLogin)
            if (tmpUser != null && tmpUser.password != Utils.encrypt(userPassword)) {
                tmpUser = null
            }

            userLiveData.postValue(tmpUser)
            onResult(tmpUser)
        }
    }

    fun registerRequest(
        name: String,
        login: String,
        password: String,
        onResult: (user: User?) -> Unit
    ) {
        viewModelScope.launch {
            val tmpUser = repository.getUserByLogin(login)

            userLiveData.postValue(tmpUser)
            if (tmpUser == null) {
                repository.insertUser(User(
                    name = name,
                    login = login,
                    password = Utils.encrypt(password)
                ))
            }
            onResult(tmpUser)
        }
    }

    fun recoverRequest(
        login: String,
        password: String,
        onResult: () -> Unit
    ) {
        viewModelScope.launch {
            val tmpUser = repository.getUserByLogin(login)

            userLiveData.postValue(tmpUser)
            if (tmpUser != null) {
                tmpUser.password = Utils.encrypt(password)
                repository.updateUser(tmpUser)
            }
            onResult()
        }
    }

    fun loadUser(id: Long) {
        viewModelScope.launch {
            userLiveData.postValue(repository.getUserById(id))
        }
    }

    fun findUser(login:String, onResult: (user: User?) -> Unit){
        viewModelScope.launch {
            val tmpUser = repository.getUserByLogin(login)

            userLiveData.postValue(tmpUser)

            onResult(tmpUser)
        }
    }
}
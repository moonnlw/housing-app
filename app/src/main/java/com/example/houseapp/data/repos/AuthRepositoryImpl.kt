package com.example.houseapp.data.repos


import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import com.example.houseapp.data.remote.UserDaoRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl private constructor(
    private val userDaoRemote: UserDaoRemote,
    private val auth: FirebaseAuth
) : AuthRepository {

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(userDaoRemote: UserDaoRemote, auth: FirebaseAuth) =
            instance ?: synchronized(this) {
                instance ?: AuthRepositoryImpl(userDaoRemote, auth)
                    .also { instance = it }
            }
    }

    private val authenticatedUserId = MutableStateFlow(auth.currentUser?.uid)

    init {
        addAuthSateListener()
    }

    private fun addAuthSateListener() {
        auth.addAuthStateListener {
            authenticatedUserId.value = it.currentUser?.uid
        }
    }

    override fun getUserId(): String? = authenticatedUserId.value

    override fun observeUserId(): Flow<String?> = authenticatedUserId.asStateFlow()

    override suspend fun signIn(email: String, password: String): Response<User> =
        Response.to {
            auth.signInWithEmailAndPassword(email, password).await()
            User(id = auth.currentUser!!.uid)
        }

    override suspend fun createUser(email: String, password: String): Response<User> =
        Response.to {
            auth.createUserWithEmailAndPassword(email, password).await()
            User(id = auth.currentUser!!.uid)
        }

    override suspend fun deleteUser(): Response<Boolean> =
        Response.to {
            auth.currentUser?.delete()?.await()
            true
        }

    override fun signOut(): Response<Boolean> =
        Response.to {
            auth.signOut()
            true
        }
}
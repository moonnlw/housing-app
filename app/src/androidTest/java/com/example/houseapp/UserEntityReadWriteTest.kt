package com.example.houseapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.houseapp.data.local.DatabaseUser
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.local.UserDaoLocal
import com.example.houseapp.utils.TestUtil
import com.example.houseapp.blockingObserve

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException



@RunWith(AndroidJUnit4::class)
class UserEntityReadWriteTest {
    @Rule @JvmField val rule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDaoLocal
    private lateinit var db: LocalDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LocalDatabase::class.java).build()
        userDao = db.userDaoLocal
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndReadUserLiveData() {
//        val user: DatabaseUser = TestUtil.createDatabaseUser("1234")
//        userDao.insert(user)
//        val byID = userDao.getUser("1234").blockingObserve()
//        assertThat(byID, equalTo(user))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertUpdateAndReadUserLiveData() {
//        val user: DatabaseUser = TestUtil.createDatabaseUser("1234")
//        userDao.insert(user)
//        val newUser: DatabaseUser = user.copy(firstName = "Alex")
//        userDao.update(newUser)
//        val byID = userDao.getUser("1234").blockingObserve()
//        assertThat(byID, equalTo(newUser))
//    }
}

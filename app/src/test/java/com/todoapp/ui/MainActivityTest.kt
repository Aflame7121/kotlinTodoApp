package com.todoapp.ui

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [30])
class MainActivityTest {

    @Test
    fun `activity should launch successfully`() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = ActivityScenario.launch<MainActivity>(intent)
        
        scenario.onActivity { activity ->
            assertNotNull(activity)
            assertNotNull(activity.findViewById(com.todoapp.R.id.todoRecyclerView))
            assertNotNull(activity.findViewById(com.todoapp.R.id.addTodoButton))
        }
    }
}
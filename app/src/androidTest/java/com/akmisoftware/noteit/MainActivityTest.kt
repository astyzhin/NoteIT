package com.akmisoftware.noteit

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.akmisoftware.noteit.ui.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    //Add dagger test injection
    @get: Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)
//    @Test
//    fun test_fabVisibility() {
//        onView(withId(R.id.fab)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun test_recyclerViewVisibility() {
//        onView(withId(R.id.recycler_home)).check(matches(isDisplayed()))
//    }

//    @Test
//    fun test_isHomeFragmentVisible() {
//        val note = Note("1", "Test note", "This is a test note")
//        val factory = FragmentFactory
//        val bundle = Bundle()
//        bundle.putSerializable("show_note", note)
//    }

//    @Test
//    fun test() {
//        run {
//            step("Open Simple Screen") {
//                activityTestRule.launchActivity(null)
//                Log.i("Test","I am testLogger")
//                device.screenshots.take("Additional_screenshot")
//                MainScreen {
//                    simpleButton {
//                        isVisible()
//                        click()
//                    }
//                }
//            }
//        }
//    }
}


object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int? = R.layout.activity_main
    override val viewClass: Class<*>? = MainActivity::class.java

    val simpleButton = KButton { withId(R.id.fab) }
}

abstract class KScreen<out T : KScreen<T>> : Screen<T>() {

    abstract val layoutId: Int?
    abstract val viewClass: Class<*>?
}
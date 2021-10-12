# shopping_list_testing_tutorial
Testing tutorial using TDD and dependency injection with hilt
## Testing fragments with hilt
- First, we added the next dependency to gradle:
`debugImplementation "androidx.fragment:fragment-testing:1.4.0-alpha10"`

- We changed to project view instead of Android view and on the src directory we created a new directory **debug**, then inside debug we create a new directory **java** and last we created a new package with the root of our project, in our case `com.projects.shoppinglisttestingtutorial`
![new directory](https://user-images.githubusercontent.com/37948478/136692226-51e908b4-4550-4b39-99b0-4cd9f85609ec.png)

- Then inside the package we create an empty Activity class which we will use as @AndroidEntryPoint for our Hilt Tests
```
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltTestActivity:AppCompatActivity()
```

- We copied our AndroidManifiest.xml from the main directory to our new debug directory and cleaned the code so now looks like follows, we set the exported property to false because we will only use this activity for testing.
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.projects.shoppinglisttestingtutorial">

    <application>
        <activity
            android:name=".HiltTestActivity"
            android:exported="false" />
    </application>

</manifest>
```

When we come back to the android view, our project structure now looks like this
![image](https://user-images.githubusercontent.com/37948478/136692462-1bc5f541-5bd7-40c3-a46f-e4faa46f9930.png)

- Inside our androidTest directory we added a HiltExt.kt file which simulates how a fragment is launched inside an Activity using hilt
```
package com.projects.shoppinglisttestingtutorial

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
        themeResId: Int = R.style.Theme_ShoppingListTestingTutorial,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    ActivityScenario.launch<HiltTestActivity>(
        mainActivityIntent
    ).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments=fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content,fragment,"")
            .commitNow()

        (fragment as T).action()
    }
}
```

- We tested our new implementation as follows, if there is no errors, our implementation was successful

 ```
   @Test
    fun testLaunchFragmentInHiltContainer(){
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }
```


## Testing navigation with mockito and espresso
To test the navigation graph we:
- Create a mock of the NavController Class
`navController= mock(NavController::class.java)`

- Then we launch our fragment using our custom Hilt launcher (we get a reference of the viewModel if needed)
```
launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
            testViewModel=this.viewModel
            this.viewModel.setCurImageUrl("imageUrl")
        }
```
- We perform an action (a click, for example)
  -  Click on a widget
`onView(withId(R.id.ivShoppingImage)).perform(click())`
  - BackPressed
`pressBack()`
  - Click on RecyclerViewElement
```
        onView(withId(R.id.tasks_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("TITLE1")), click()))
```
- Last, we verify we call the right function with the proper parameters
```
verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
```

> IMPORTANT: We need to annotate our test class with `@MediumTest`, `@HiltAndroidTest` and `@ExperimentalCoroutinesApi` and  set the Rules for hilt and to handle Coroutines 
> ```
>    @get:Rule
>    val hiltRule=HiltAndroidRule(this)
>
>    @get:Rule
>    val instantTaskExecutorRule=InstantTaskExecutorRule()
>
>   private lateinit var navController: NavController
>
>    @Before
>    fun setup(){
>        hiltRule.inject()
>        ...
>   }
>```


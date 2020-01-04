package me.manulorenzo.mapthecity

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert

object CustomAssertions {
    /**
     * Method to assert that the navigate up button exists.
     */
    fun toolbarUpButtonExists(): Boolean = try {
        Espresso.onView(Matchers.anyOf(Matchers.instanceOf(AppCompatImageButton::class.java)))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withParent(ViewMatchers.withId(R.id.toolbar))
                )
            )
        true
    } catch (e: NoMatchingViewException) {
        false
    }

    fun actionSearchIconExists(): Boolean = try {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).check(
            ViewAssertions.matches(
                ViewMatchers.withParent(
                    ViewMatchers.withId(R.id.toolbar)
                )
            )
        )
        true
    } catch (e: NoMatchingViewException) {
        false
    }

    /**
     * Provides a RecyclerView assertion based on a view matcher. This allows you to
     * validate whether a RecyclerView contains a row in memory without scrolling the list.
     *
     * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
     * @return an Espresso ViewAssertion to check against a RecyclerView.
     */
    fun withRowContaining(viewMatcher: Matcher<View>): ViewAssertion? {
        Assert.assertNotNull(viewMatcher)
        return ViewAssertion { view, noViewException ->
            if (noViewException != null) {
                throw noViewException
            }
            Assert.assertTrue(view is RecyclerView)
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            for (position in 0 until adapter!!.itemCount) {
                val itemType = adapter.getItemViewType(position)
                val viewHolder =
                    adapter.createViewHolder(recyclerView, itemType)
                adapter.bindViewHolder(viewHolder, position)
                if (viewHolderMatcher(
                        ViewMatchers.hasDescendant(
                            viewMatcher
                        )
                    ).matches(viewHolder)
                ) {
                    return@ViewAssertion  // Found a matching row
                }
            }
            Assert.fail("No match found")
        }
    }

    fun withRecyclerViewOfSize(size: Int): ViewAssertion? {
        return ViewAssertion { view, noViewException ->
            if (noViewException != null) {
                throw noViewException
            }
            Assert.assertTrue(view is RecyclerView)
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            Assert.assertTrue(adapter?.itemCount == size)
        }
    }

    /**
     * Creates matcher for view holder with given item view matcher.
     *
     * @param itemViewMatcher a item view matcher which is used to match item.
     * @return a matcher which matches a view holder containing item matching itemViewMatcher.
     */
    private fun viewHolderMatcher(itemViewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
        return object : TypeSafeMatcher<RecyclerView.ViewHolder>() {
            override fun matchesSafely(viewHolder: RecyclerView.ViewHolder?): Boolean {
                return itemViewMatcher.matches(viewHolder!!.itemView)
            }

            override fun describeTo(description: Description) {
                description.appendText("holder with view: ")
                itemViewMatcher.describeTo(description)
            }
        }
    }
}
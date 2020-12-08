package com.satriawibawa.myapplication;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest9963 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void splashActivityTest9963() {

       // pressBack();

        //pressBack();


        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnPesan), withText("Pesan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

//        ViewInteraction floatingActionButton = onView(
//                allOf(withId(R.id.addBtn),
//                        childAtPosition(
//                                allOf(withId(R.id.frame_view_buku),
//                                        childAtPosition(
//                                                withId(R.id.fragment_transaksi),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.addBtn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_transaksi),
                                        1),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.txtJudul),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("Test"), closeSoftKeyboard());

       // pressBack();

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.txtPengirim),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("Joseva"), closeSoftKeyboard());

        //pressBack();

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.txtIsiPesan),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("Selamat"), closeSoftKeyboard());

        //pressBack();

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

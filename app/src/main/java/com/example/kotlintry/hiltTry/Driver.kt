package com.example.kotlintry.hiltTry

import android.content.Context
import com.example.kotlintry.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

// 注解提供context的形式叫做 Qualifier ，但注意不要和singleton一起使用会出问题，
@ActivityScoped
class Driver @Inject constructor( /*@ApplicationContext*/ @ActivityContext val context: Context /*,mainActivity: MainActivity // activity也能直接注入使用且不需要注解*/) {
}
# Captivate
Kotlin

一款用Kotlin语言开发的android应用，使用RxJava2 + Retrofit2 + MVP 开发框架，使用Bmob云服务

android studio 3.0 版本下配置Kotlin

```gradle
buildscript {
    ext.kotlin_version = '1.1.2-5'
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"
    }
}

```gradle
dependencies {
        ...
        compile "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
        }
        
## android studio 3.0 及以上版本自带支持Kotlin
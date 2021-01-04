
*`A B C D E F G H I J K L M N O P Q R S T U V W Z Y Z`*

[**AndroidAutoSize**](https://github.com/JessYanCoding/AndroidAutoSize)
今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案.

[**AndroidUtilCode**](https://github.com/Blankj/AndroidUtilCode)
AndroidUtilCode 🔥 是一个强大易用的安卓工具类库

[**BasePopup**](https://github.com/razerdp/BasePopup)
BasePopup - Android下打造通用便捷的PopupWindow

**BaseRecyclerViewAdapterHelper**  [](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)  [](https://www.jianshu.com/p/b343fcff51b0)
强大而灵活的RecyclerView Adapter

[**Banner**](https://github.com/youth5201314/banner)
🔥🔥🔥Banner 2.0 来了！Android广告图片轮播控件，内部基于ViewPager2实现，Indicator和UI都可以自定义。

[**FlycoTabLayout**](https://github.com/H07000223/FlycoTabLayout)
一个Android TabLayout库,目前有3个TabLayout

**Glide** [](https://github.com/search?q=glide) [](http://bumptech.github.io/glide/doc/download-setup.html#proguard) [](https://www.jianshu.com/p/df02381cbf0b)
Glide是一个快速高效的Android图片加载库，注重于平滑的滚动。Glide提供了易用的API，高性能、可扩展的图片解码管道（decode pipeline），以及自动的资源池技术。
*混淆规则*
`  
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

If you're targeting any API level less than Android API 27, also include:
---pro
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder  
`

[**ImmersionBar**](https://github.com/gyf-dev/ImmersionBar)
android 4.4以上沉浸式实现
*混淆规则*
`
 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**
 `

[LeakCanary](https://square.github.io/leakcanary/)
LeakCanary是适用于Android的内存泄漏检测库。

[MagicIndicator](https://github.com/hackware1993/MagicIndicator)
强大、可定制、易扩展的 ViewPager 指示器框架。

Material

OkHttp3 [](https://square.github.io/okhttp/) [](https://github.com/square/okhttp/) [](https://www.jianshu.com/p/da4a806e599b)
OkHttp是HTTP客户端

RxJava2 RxAndroid [](https://github.com/amitshekhariitbhu/RxJava2-Android-Samples)

RxBinding2

Retrofit2
适用于Android和Java的类型安全的HTTP客户端。

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
Android智能下拉刷新框架-SmartRefreshLayout

[StatusBarUtil](https://jaeger.itscoder.com/android/2016/03/27/statusbar-util.html)
StatusBarUtil 状态栏工具类（实现沉浸式状态栏/变色状态栏）

Timber

ViewPager2
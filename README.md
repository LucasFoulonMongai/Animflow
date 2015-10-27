# Animflow

This library offers a set of View that animate themselves by using their XML configuration.

The animation is both launched when the view is initialy displayed and when the top container is scrolling.

Currently, the lib support only vertically scrolling components (RecyclerView, ScrollView) but it will evolve to support more types/ orientation.

The animation progress is determined by the (amount of the view visible)/(amount of the view hidden) on the scroll based on the bottom (or top) of the ScrollView/RecyclerView.

The animated view displayed for the first will be animated to reach their final progress in 500ms (settable value planned in next release)

See the example to better understand the type of animations :

  -RecyclerView : http://giant.gfycat.com/MessyPastArmadillo.gif (or http://gfycat.com/MessyPastArmadillo )
  
  -ScrollView with content : http://giant.gfycat.com/HideousOilyAnchovy.gif (or http://gfycat.com/HideousOilyAnchovy )
  
  -Parralax with a ScrollView : http://giant.gfycat.com/PhysicalPreciousIntermediateegret.gif (or http://gfycat.com/PhysicalPreciousIntermediateegret )


include this library through build.gradle using theses lines :

    repositories {
      ...
      maven { url 'https://dl.bintray.com/lucasfm/maven/' }
    }
    dependencies {
      ...
      compile 'com.lfm.animflow:animflow:1.1.1'
    }

The main concept is that you only have to create the final xml view you want to display, then you had your animation. This library allow you to add animations at the end of you design integration as a an added value.

The animation will be started at their first display but the magic really happen when you scroll the screen. You can also play with the animation attribute especially the isFromTop, delayStat and delayEnd which allow you to define unique behaviors.

Animation attributes :
  fromAlpha="x" ->x is a float betwen 0f and 1f, specify from what alpha value the animation will start
  
  fromX="x" -> x is a dimension (i.e: 10dp or -10sp), specify the lateral translation that the animation will execute
  
  fromY="x" -> x is a dimension (i.e: 10dp or -10sp), specify the vertical translation that the animation will execute
  
  fromZoom="x" -> x is a float from 0f which will define the initial zoom value
  
  fromRotation="x" -> is a float to specify the inital rotation value
  
  ease="x" -> x has to be equal to "linear","quad" or "cubic (defaut value = "cubic"), this will allow you to define the easing of the animation, linear is very useful for parallax animations.
  
  delayStart="x" -> x is a float representing the delay substracted to the percentage of progress of the view. It's really usefull if you want to delay the start of you animation, the value should be below the value of delayEnd.
  ie : if you want the View stat it's animation at 50% of it's parent progress, just set it a 0.5f
  
  delayEnd="x" - > x is a float representing the delay added to the maximum value of the progress.
  ie : if you want an animation that last twice the original time, set the value to 2.0f.
  
  if delayStart="1.0f" and the delayEnd="2.0f", the animation will once the original time but will only start when one original time is elapsed.
  warning, setting a value above 2.0f can't prevent the animation to be ended when reaching the end of the screen.
  
  isFromTop="x" -> x is a boolean, if setted to true, the animation will use the top of the screen as a starting point for determining the progress. Warning, the animation will be played in reverse (finished state to initial state).
  

How to use it:
 I/ RecyclerView 
  See : https://github.com/LucasFoulonMongai/Animflow/blob/master/app/src/main/res/layout/fragment_items.xml & https://github.com/LucasFoulonMongai/Animflow/blob/master/app/src/main/res/layout/item_type_1.xml
 
  1) use this custom RecyclerView :

    <com.lfm.animflow.containers.AnimContentRecyclerViewVertical xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/itemsRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

2) Use item View implementing AnimatedView (every view that has to be animated need to have a parent view of this type)

    <com.lfm.animflow.classics.AnimatedRelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:fromX="-80dp">
        <com.lfm.animflow.classics.AnimatedImageView
            android:id="@+id/itemImage"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:src="@drawable/imgres" />
    </com.lfm.animflow.classics.AnimatedRelativeLayout>

II/ ScrollView
    See : https://github.com/LucasFoulonMongai/Animflow/blob/master/app/src/main/res/layout/fragment_content_bis

    <com.lfm.animflow.containers.AnimContentScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/contentScrollView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
          <com.lfm.animflow.classics.AnimatedLinearLayout
          android:id="@+id/contentContainerLayout"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:orientation="vertical">
          
              <!-- AnimatedView goes here -->
              
          </com.lfm.animflow.classics.AnimatedLinearLayout>
    </com.lfm.animflow.containers.AnimContentScrollView>



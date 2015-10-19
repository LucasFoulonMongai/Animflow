# Animflow

This library offers a set of View that animate themselves by using their XML configuration.

The animation is both launched when the view is initialy displayed and when the top container is scrolling.

Currently, the lib support only vertically scrolling components (RecyclerView, ScrollView) but it will evolve to support more types/ orientation.

Example :
  -RecyclerView : http://giant.gfycat.com/MessyPastArmadillo.gif (or http://gfycat.com/MessyPastArmadillo )
  
  -ScrollView with content : http://giant.gfycat.com/HideousOilyAnchovy.gif (or http://gfycat.com/HideousOilyAnchovy )
  
  -Parralax with a ScrollView : http://giant.gfycat.com/YellowCommonKangaroo.gif (or http://gfycat.com/YellowCommonKangaroo )


include this library through build.gradle using theses lines :

    repositories {
      ...
      maven { url 'https://dl.bintray.com/lucasfm/maven/' }
    }
    dependencies {
    compile 'com.lfm.animflowlibrary:animflow:1.0.0'
    }

How to use it:
 - RecyclerView :
  1) use this custom RecyclerView :
      <com.lfm.animflowlibrary.containers.AnimContentRecyclerViewVertical xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/itemsRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

2) Use views implementing AnimatedView (every view that has to be animated need to have a parent view of this type)
    <com.lfm.animflowlibrary.classics.AnimatedRelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:fromX="-80dp">

        <com.lfm.animflowlibrary.classics.AnimatedImageView
            android:id="@+id/itemImage"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:src="@drawable/imgres" />
    </com.lfm.animflowlibrary.classics.AnimatedRelativeLayout>



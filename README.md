 [ ![Download](https://api.bintray.com/packages/omidheshmatinia/maven/LiquidRadioButton/images/download.svg) ](https://bintray.com/omidheshmatinia/maven/LiquidRadioButton/_latestVersion)
 [![Twitter](https://img.shields.io/badge/Twitter-@Smartiiiiz-blue.svg?style=flat)](http://twitter.com/Smartiiiiz)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg) ![Minimun Android Sdk Version](https://img.shields.io/badge/min--sdk-11-yellowgreen.svg) 

# Liquid Radio Button
A Radio button with custom liquid animation. You can change animations duration, explode items count, radius and colors easily from xml

## ScreenShots

<img src="/sample/sample1.gif"/>

<img src="/sample/sample2.gif"/>

 # Setup
 ## 1. Provide the gradle dependency
 ```gradle
 compile 'me.omidh:liquidradiobutton:1.0.01'
 ```
 ## 2. Sample

```xml
 <me.omidh.liquidradiobutton.LiquidRadioButton
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:padding="50dp"
             android:gravity="center"
             app:lrb_strokeWidth="1dp"
             app:lrb_explodeCount="3"
             app:lrb_outterPadding="8dp"
             app:lrb_innerCircleRadius="15dp"
             app:lrb_strokeRadius="23dp"
             app:lrb_inAnimDuration="500"
             app:lrb_outAnimDuration="500"
             app:lrb_checkedColor="@android:color/holo_green_dark"
             app:lrb_unCheckedColor="@android:color/holo_red_light"
             android:text="Liquid Radio Button"
             />
```

### About the Library
This library is strongly inspired in this concept from Tamino Martinius in uplabs: https://site.uplabs.com/posts/liquid-radio-button-interface


### Thanks to

_Rey Pham_ for his great library [material](https://github.com/rey5137/material) 

_tyrantgit_ for his great library [ExplosionField](https://github.com/tyrantgit/ExplosionField) 

### Todo
* add sample
* update readme and wiki

# Developed By

> Omid Heshmatinia
> omidheshmatinia@gmail.com

# License

```
Liquid Radio Button library for Android
Copyright (c) 2017 Omid Heshmatinia (https://github.com/omidheshmatinia).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
#Easy Framework
![alt text](./img/1.jpg)
##写在前头:只适用 Android Studio和 Intellij IDEA 工具!!!
HttpLib reference for ```  Ecol ``` 's(郑毅)  code!
网络请求引用了郑毅大神的代码

library include Gson and RecyclerView support
库文件包含Gson和回收视图支持

Application class make use of ImageLoader for load Image;
应用类默认使用imageload加载图片,可自己选择是否修改配置或者调用其他第三方图片库

universal activity based on AppcompatActivity;
通用活动类基于App兼容活动

universal adapter based on RecycleAdapter;
通用适配器基于回收适配器

universal presenter did't have any 卵用(其实我还没弄明白有啥用,虽然是我自己写的.....)


##How To Use
###step1. 
First, you should have Android Studio environment.
```
minSdkVersion must higher than api 11

```
If your IDE is Eclipse, then,god bless you 
###step2. 
modify `build.gradle` in your module gradle
Add the dependencies section into `build.gradle` in your module if it doesn't already exist:
```
    dependencies {
       compile 'com.vickyleu:easy-framework:1.0.0'
       蔡海小婊砸
   }


```

###*License*

Easy Framework is debug under the [Apache 2.0 license](LICENSE).

```


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
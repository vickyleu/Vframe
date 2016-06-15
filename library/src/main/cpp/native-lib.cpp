#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_vickyleu_library_Base_Native_Native_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

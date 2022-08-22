#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_azeiee_opencv_1age_1estimator_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
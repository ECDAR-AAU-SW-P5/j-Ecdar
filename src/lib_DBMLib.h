/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class lib_DBMLib */

#ifndef _Included_lib_DBMLib
#define _Included_lib_DBMLib
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     lib_DBMLib
 * Method:    boundbool2raw
 * Signature: (IZ)I
 */
JNIEXPORT jint JNICALL Java_lib_DBMLib_boundbool2raw
  (JNIEnv *, jclass, jint, jboolean);

/*
 * Class:     lib_DBMLib
 * Method:    raw2bound
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_lib_DBMLib_raw2bound
  (JNIEnv *, jclass, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_init
 * Signature: ([II)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1init
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_zero
 * Signature: ([II)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1zero
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_constrain1
 * Signature: ([IIIIIZ)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1constrain1
  (JNIEnv *, jclass, jintArray, jint, jint, jint, jint, jboolean);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_up
 * Signature: ([II)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1up
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_isSubsetEq
 * Signature: ([I[II)Z
 */
JNIEXPORT jboolean JNICALL Java_lib_DBMLib_dbm_1isSubsetEq
  (JNIEnv *, jclass, jintArray, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_updateValue
 * Signature: ([IIII)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1updateValue
  (JNIEnv *, jclass, jintArray, jint, jint, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_isValid
 * Signature: ([II)Z
 */
JNIEXPORT jboolean JNICALL Java_lib_DBMLib_dbm_1isValid
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_intersection
 * Signature: ([I[II)Z
 */
JNIEXPORT jboolean JNICALL Java_lib_DBMLib_dbm_1intersection
  (JNIEnv *, jclass, jintArray, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_freeAllDown
 * Signature: ([II)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1freeAllDown
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_freeDown
 * Signature: ([III)[I
 */
JNIEXPORT jintArray JNICALL Java_lib_DBMLib_dbm_1freeDown
  (JNIEnv *, jclass, jintArray, jint, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_rawIsStrict
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_lib_DBMLib_dbm_1rawIsStrict
  (JNIEnv *, jclass, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_addRawRaw
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_lib_DBMLib_dbm_1addRawRaw
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     lib_DBMLib
 * Method:    dbm_minus_dbm
 * Signature: ([I[II)[[I
 */
JNIEXPORT jobjectArray JNICALL Java_lib_DBMLib_dbm_1minus_1dbm
  (JNIEnv *, jclass, jintArray, jintArray, jint);

/*
 * Class:     lib_DBMLib
 * Method:    fed_minus_dbm
 * Signature: ([[I[II)[[I
 */
JNIEXPORT jobjectArray JNICALL Java_lib_DBMLib_fed_1minus_1dbm
  (JNIEnv *, jclass, jobjectArray, jintArray, jint);

#ifdef __cplusplus
}
#endif
#endif
